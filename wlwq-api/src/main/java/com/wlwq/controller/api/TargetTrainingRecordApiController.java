package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.examine.TargetTrainingRecordParamVO;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wwb
 * 目标训练记录
 */
@RestController
@RequestMapping(value = "/api/targetTrainingRecord")
@AllArgsConstructor
public class TargetTrainingRecordApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ITargetContentService targetContentService;

    private final ITargetTrainingService targetTrainingService;

    private final ITargetTrainingRecordContentService targetTrainingRecordContentService;

    private final ITargetTrainingRecordService targetTrainingRecordService;

    private final IMeetingExamineInitiateService meetingInitiateService;

    private final IMeetingExamineFlowService meetingFlowService;
    /**
     * 获取目标训练自定义列
     *
     * @param years 年份
     * @return
     */
    @GetMapping(value = "/getTargetContent")
    @PassToken
    public ApiResult getTargetContent(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer years) {
        Map<String, Object> map = new HashMap<>(2);
        if (years == null || years == 0) {
            return ApiResult.fail("年份不能为空！");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        List<TargetTraining> targetTrainingList = targetTrainingService.selectTargetTrainingList(TargetTraining
                .builder()
                .delStatus(0)
                .years(years)
                //.companyId(account.getCompanyId())
                .build());
        if (targetTrainingList == null || targetTrainingList.size() <= 0) {
            return ApiResult.fail("此年份没有目标设定！");
        }
        List<TargetContent> targetContentList = targetContentService.selectTargetContentList(TargetContent
                .builder()
                .parentId(0L)
                .targetTrainingId(targetTrainingList.get(0).getTargetTrainingId())
                .build());

        if (targetContentList != null && targetContentList.size() > 0) {
            List<TargetContent> subList = targetContentList.stream().filter(obj -> (obj.getSubsetState() == 1)).collect(Collectors.toList());
            if (subList != null && subList.size() > 0) {
                subList.forEach(
                        obj1 -> {
                            obj1.setSubsetList(targetContentService.selectTargetContentList(TargetContent
                                    .builder()
                                    .parentId(obj1.getId())
                                    .targetTrainingId(targetTrainingList.get(0).getTargetTrainingId())
                                    .build()));
                        }
                );
            }
        }
        map.put("targetTraining", targetTrainingList.get(0));
        map.put("targetContentList", targetContentList);
        return ok(map);
    }


    /**
     * 目标训练记录提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/targetContentSub")
    public ApiResult targetContentSub(HttpServletRequest request, @RequestBody @Validated TargetTrainingRecordParamVO params, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>(2);
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        MeetingExamineFlow examineFlow = meetingFlowService.selectMeetingExamineFlow(MeetingExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(28L)
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        TargetTrainingRecord targetTrainingRecord = TargetTrainingRecord.builder().build();
        BeanUtil.copyProperties(params, targetTrainingRecord);

        targetTrainingRecord.setTargetTrainingRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        int count = targetTrainingRecordService.insertTargetTrainingRecord(targetTrainingRecord);
        if (count <= 0) {
            throw new ApiException("添加失败");
        }

        List<TargetContent> targetContentList = params.getTargetContentList();
        if (targetContentList != null && targetContentList.size() > 0) {
            targetContentList.forEach(
                    obj -> {
                        TargetTrainingRecordContent targetTrainingRecordContent = TargetTrainingRecordContent.builder().build();
                        BeanUtil.copyProperties(obj, targetTrainingRecordContent);

                        targetTrainingRecordContent.setTargetTrainingRecordContentId(IdUtil.getSnowflake(1, 1).nextIdStr());
                        targetTrainingRecordContent.setTargetTrainingRecordId(targetTrainingRecord.getTargetTrainingRecordId());
                        targetTrainingRecordContent.setContentId(obj.getId());
                        List<TargetContent> subsetList = obj.getSubsetList();

                        int counts = targetTrainingRecordContentService.insertTargetTrainingRecordContent(targetTrainingRecordContent);
                        if (counts <= 0) {
                            throw new ApiException("添加失败");
                        }

                        if (obj.getSubsetState() != null && obj.getSubsetState() == 1
                                && subsetList != null && subsetList.size() > 0) {
                            subsetList.forEach(
                                    subObj -> {
                                        TargetTrainingRecordContent subRecordContent = TargetTrainingRecordContent.builder().build();
                                        BeanUtil.copyProperties(subObj, subRecordContent);
                                        subRecordContent.setTargetTrainingRecordContentId(IdUtil.getSnowflake(1, 1).nextIdStr());
                                        subRecordContent.setTargetTrainingRecordId(targetTrainingRecord.getTargetTrainingRecordId());
                                        subRecordContent.setContentId(subObj.getId());
                                        int countSub = targetTrainingRecordContentService.insertTargetTrainingRecordContent(subRecordContent);
                                        if (countSub <= 0) {
                                            throw new ApiException("添加失败");
                                        }
                                    }
                            );
                        }
                    }
            );
        }
        map.put("targetTrainingRecord", targetTrainingRecord);
        map.put("targetContentList", targetContentList);
        return ok(map);
    }

    /**
     * 修改目标训练记录
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/updateTargetContent")
    public ApiResult updateTargetContent(HttpServletRequest request, @RequestBody @Validated TargetTrainingRecordParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        TargetTrainingRecord targetTrainingRecord = targetTrainingRecordService.selectTargetTrainingRecordById(params.getTargetTrainingRecordId());
        if (targetTrainingRecord == null) {
            throw new ApiException("没有此数据，更新失败");
        }
        //一年编辑一次
        if (StringUtils.isNotEmpty(targetTrainingRecord.getEditNum()) && ObjectUtil.isNotEmpty(targetTrainingRecord.getEditDate())) {
            Date editDate = targetTrainingRecord.getEditDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(editDate);
            if(calendar.get(Calendar.YEAR)==LocalDate.now().getYear()){
                return ApiResult.fail("编辑失败,今年已编辑过");
            } else{
                targetTrainingRecord.setEditDate(new Date());
            }
        } else {
            targetTrainingRecord.setEditNum(NumberUtils.INTEGER_ONE.toString());
            targetTrainingRecord.setEditDate(new Date());
        }
        BeanUtil.copyProperties(params, targetTrainingRecord);
        int count = targetTrainingRecordService.updateTargetTrainingRecord(targetTrainingRecord);
        if (count <= 0) {
            throw new ApiException("更新失败");
        }

        List<TargetTrainingRecordContent> targetContentList = params.getTargetTrainingRecordContentList();
        if (targetContentList != null && targetContentList.size() > 0) {
            targetContentList.forEach(
                    obj -> {
                        List<TargetTrainingRecordContent> subsetList = obj.getSubsetList();
                        int counts = targetTrainingRecordContentService.updateTargetTrainingRecordContent(obj);
                        if (counts <= 0) {
                            throw new ApiException("更新失败");
                        }
                        if (obj.getSubsetState() != null && obj.getSubsetState() == 1
                                && subsetList != null && subsetList.size() > 0) {
                            subsetList.forEach(
                                    subObj -> {
                                        int countSub = targetTrainingRecordContentService.updateTargetTrainingRecordContent(subObj);
                                        if (countSub <= 0) {
                                            throw new ApiException("更新失败");
                                        }
                                    }
                            );
                        }
                    }
            );
        }
        return ok("更新成功");
    }


    /**
     * 获取目标训练记录数据
     *
     * @param years     年份
     * @param accountId 用户id
     * @param keyword   关键字搜索
     * @return
     */
    @GetMapping(value = "/getTargetContents")
    @PassToken
    public ApiResult getTargetContents(PageParam pageParam, @RequestParam(defaultValue = "") Integer years,
                                       @RequestParam(defaultValue = "") String accountId,
                                       @RequestParam(defaultValue = "") Long deptId,
                                       String keyword) {
        Map<String, Object> map = new HashMap<>(2);
        if (years == null || years == 0) {
            return ApiResult.fail("年份不能为空！");
        }

        if (!StringUtils.isEmpty(accountId)) {
            ApiAccount account = accountService.selectApiAccountById(accountId);
            if (account == null) {
                return ApiResult.fail("该用户不存在！");
            }
            List<TargetTrainingRecord> targetTrainingRecordList = targetTrainingRecordService.selectTargetTrainingRecordList(TargetTrainingRecord
                    .builder()
                    .applicantId(accountId)
                    .years(years)
                    .delStatus(0)
                    .applicantDeptId(deptId)
                    .applicantNickName(keyword)
                    //.auditStatus(2)
                    .build());
            if (targetTrainingRecordList != null && targetTrainingRecordList.size() > 0) {
                map.put("targetTraining", targetTrainingRecordList.get(0));
                List<MeetingExamineInitiate> examineInitiateList = meetingInitiateService.selectMeetingExamineInitiateList(MeetingExamineInitiate
                        .builder()
                        .delStatus(0)
                        .accountId(accountId)
                        .meetingTrainingId(targetTrainingRecordList.get(0).getTargetTrainingRecordId())
                        .build());
                if (examineInitiateList != null && examineInitiateList.size() > 0) {
                    List<MeetingExamineInitiate> moduleId28List = examineInitiateList.stream().filter(obj -> (obj.getExamineModuleId() == 28)).collect(Collectors.toList());
                    List<MeetingExamineInitiate> moduleId29List = examineInitiateList.stream().filter(obj -> (obj.getExamineModuleId() == 29)).collect(Collectors.toList());
                    map.put("examineInitiateId", (moduleId28List == null || moduleId28List.size() <= 0) ? null : moduleId28List.get(0).getExamineInitiateId());
                    map.put("examineInitiateIdContent", (moduleId29List == null || moduleId29List.size() <= 0) ? null : moduleId29List.get(0).getExamineInitiateId());
                } else {
                    map.put("examineInitiateId", null);
                }
            } else {
                map.put("targetTraining", null);
                map.put("examineInitiateId", null);
            }

        } else {
            if (pageParam == null) {
                pageParam = PageParam.builder().build();
            }
            PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
            List<TargetTrainingRecord> targetTrainingRecordList = targetTrainingRecordService.selectTargetTrainingRecordList(TargetTrainingRecord
                    .builder()
                    .applicantId(accountId)
                    .years(years)
                    .delStatus(0)
                    .applicantDeptId(deptId)
                    .auditStatus(3)
                    .applicantNickName(keyword)
                    .build());
            PageInfo<TargetTrainingRecord> pageInfo = new PageInfo<>(targetTrainingRecordList);
            map.put("targetContentList", pageInfo);
            if (targetTrainingRecordList != null && targetTrainingRecordList.size() > 0) {
                targetTrainingRecordList.forEach(
                        obj -> {
                            List<MeetingExamineInitiate> examineInitiateList = meetingInitiateService.selectMeetingExamineInitiateList(MeetingExamineInitiate
                                    .builder()
                                    .delStatus(0)
                                    .accountId(obj.getApplicantId())
                                    .meetingTrainingId(obj.getTargetTrainingRecordId())
                                    .build());
                            if (examineInitiateList != null && examineInitiateList.size() > 0) {
                                List<MeetingExamineInitiate> moduleId28List = examineInitiateList.stream().filter(obj1 -> (obj1.getExamineModuleId() == 28)).collect(Collectors.toList());
                                List<MeetingExamineInitiate> moduleId29List = examineInitiateList.stream().filter(obj1 -> (obj1.getExamineModuleId() == 29)).collect(Collectors.toList());
                                obj.setExamineInitiateId((moduleId28List == null || moduleId28List.size() <= 0) ? null : moduleId28List.get(0).getExamineInitiateId());
                                obj.setExamineInitiateIdContent((moduleId29List == null || moduleId29List.size() <= 0) ? null : moduleId29List.get(0).getExamineInitiateId());
                            } else {
                                obj.setExamineInitiateId(null);
                                obj.setExamineInitiateIdContent(null);
                            }
                        }
                );
            }
        }
        return ok(map);
    }

    /**
     * 获取目标训练详情
     *
     * @param targetTrainingRecordId 记录id
     * @return
     */
    @GetMapping(value = "/getTargetContentInfo")
    @PassToken
    public ApiResult getTargetContentInfo(HttpServletRequest request, @RequestParam(defaultValue = "") String targetTrainingRecordId) {
        Map<String, Object> map = new HashMap<>(2);
        TargetTrainingRecord targetTrainingRecord = targetTrainingRecordService.selectTargetTrainingRecordById(targetTrainingRecordId);
        if (targetTrainingRecord == null) {
            return ApiResult.fail("没有此记录数据！");
        }
        List<TargetTrainingRecordContent> targetContentList = targetTrainingRecordContentService.selectTargetTrainingRecordContentList(TargetTrainingRecordContent
                .builder()
                .parentId(0L)
                .targetTrainingId(targetTrainingRecord.getTargetTrainingId())
                .targetTrainingRecordId(targetTrainingRecord.getTargetTrainingRecordId())
                .build());

        if (targetContentList != null && targetContentList.size() > 0) {
            List<TargetTrainingRecordContent> subList = targetContentList.stream().filter(obj -> (obj.getSubsetState() == 1)).collect(Collectors.toList());
            if (subList != null && subList.size() > 0) {
                subList.forEach(
                        obj1 -> {
                            obj1.setSubsetList(targetTrainingRecordContentService.selectTargetTrainingRecordContentList(TargetTrainingRecordContent
                                    .builder()
                                    .parentId(obj1.getContentId())
                                    .targetTrainingId(targetTrainingRecord.getTargetTrainingId())
                                    .targetTrainingRecordId(targetTrainingRecord.getTargetTrainingRecordId())
                                    .build()));
                        }
                );
            }
        }
        map.put("targetTrainingRecord", targetTrainingRecord);
        map.put("targetContentList", targetContentList);
        return ok(map);
    }
}
