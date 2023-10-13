package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.params.courseExam.MeetingMinutesParam;
import com.wlwq.params.courseExam.MeetingTrainingParam;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author wwb
 * 会议训练
 */
@RestController
@RequestMapping(value = "/api/meetingTraining")
@AllArgsConstructor
public class MeetingTrainingApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IMeetingTrainingService meetingTrainingService;

    private final IMeetingTrainingItemService meetingTrainingItemService;

    private final IMeetingTrainingMinutesService meetingTrainingMinutesService;

    private final IMeetingExamineInitiateService meetingInitiateService;
    private final IExamineFileService fileService;
    /**
     * pc@Description
     * params 新建会议
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add")
    public ApiResult add(HttpServletRequest request, @RequestBody MeetingTrainingParam params, BindingResult bindingResult) {
        HashMap map = new HashMap(2);
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        MeetingTraining meetingTraining = MeetingTraining.builder().build();
        BeanUtil.copyProperties(params, meetingTraining);

        meetingTraining.setMeetingTrainingId(IdUtil.getSnowflake(1, 1).nextIdStr());
        meetingTraining.setOrganizerAccountId(account.getAccountId());
        meetingTraining.setOrganizerNickName(account.getNickName());
        meetingTraining.setOrganizerHeadPortrait(account.getHeadPortrait());
        meetingTraining.setCompanyId(account.getCompanyId());
        List<MeetingTrainingItem> itemList = meetingTraining.getMeetingTrainingItemList();
        if(itemList != null){
            itemList.forEach(
                    obj -> {
                        int num = meetingTrainingItemService.insertMeetingTrainingItem(MeetingTrainingItem
                                .builder()
                                .companyId(account.getCompanyId())
                                .meetingTrainingId(meetingTraining.getMeetingTrainingId())
                                .synopsis(obj.getSynopsis())
                                .title(obj.getTitle())
                                .build());
                        if (num <= 0) {
                            throw new ApiException("添加会议流程失败。");
                        }
                    }
            );
        }

        int num = meetingTrainingService.insertMeetingTraining(meetingTraining);
        if (num <= 0) {
            throw new ApiException("添加会议失败。");
        }
        map.put("result", meetingTraining);
        map.put("state", 200);
        return ok(map);
    }

    /**
     * 删除会员流程
     *
     * @param meetingTrainingItemId 会议训练流程事项主键id
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping(value = "/delete/{meetingTrainingItemId}")
    public ApiResult update(@PathVariable("meetingTrainingItemId") String meetingTrainingItemId, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        int num = meetingTrainingItemService.deleteMeetingTrainingItemById(meetingTrainingItemId);
        if (num <= 0) {
            throw new ApiException("删除会议流程失败。");
        }
        return ok("删除会议流程成功。");
    }

    /**
     * pc@Description
     * params 修改会议
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/update")
    public ApiResult update(HttpServletRequest request, @RequestBody MeetingTrainingParam params, BindingResult bindingResult) {
        HashMap map = new HashMap(2);
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(params.getMeetingTrainingId());
        if (meetingTraining == null) {
            return fail("没有此会议。");
        }

        BeanUtil.copyProperties(params, meetingTraining);
        meetingTraining.setOrganizerAccountId(account.getAccountId());
        meetingTraining.setOrganizerNickName(account.getNickName());
        meetingTraining.setOrganizerHeadPortrait(account.getHeadPortrait());
        meetingTraining.setCompanyId(account.getCompanyId());
        List<MeetingTrainingItem> itemList = meetingTraining.getMeetingTrainingItemList();
        if(itemList != null){
            int number = meetingTrainingItemService.deleteMeetingTrainingItemByMeetingTrainingId(meetingTraining.getMeetingTrainingId());
            if (number <= 0) {
                throw new ApiException("修改会议流程失败。");
            }
            itemList.forEach(
                    obj -> {
                        int num = meetingTrainingItemService.insertMeetingTrainingItem(MeetingTrainingItem
                                .builder()
                                .companyId(account.getCompanyId())
                                .meetingTrainingId(meetingTraining.getMeetingTrainingId())
                                .synopsis(obj.getSynopsis())
                                .title(obj.getTitle())
                                .build());
                        if (num <= 0) {
                            throw new ApiException("修改会议流程失败。");
                        }
                    }
            );
        }

        int num = meetingTrainingService.updateMeetingTraining(meetingTraining);
        if (num <= 0) {
            throw new ApiException("修改会议失败。");
        }
        map.put("result", meetingTraining);
        map.put("state", 200);
        return ok(map);
    }

    /**
     * pc@Description
     * params 新建会议纪要
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/addMinutes")
    public ApiResult addMinutes(HttpServletRequest request, @RequestBody MeetingMinutesParam params, BindingResult bindingResult) {
        HashMap map = new HashMap(2);
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(params.getMeetingTrainingId());
        if (meetingTraining == null) {
            return fail("此会议不存在。");
        }

        MeetingTrainingMinutes meetingMinutes = MeetingTrainingMinutes.builder().build();
        BeanUtil.copyProperties(params, meetingMinutes);
        meetingMinutes.setMeetingTrainingId(IdUtil.getSnowflake(1, 1).nextIdStr());
        meetingMinutes.setAccountId(account.getAccountId());
        meetingMinutes.setNickName(account.getNickName());
        meetingMinutes.setHeadPortrait(account.getHeadPortrait());
        meetingMinutes.setCompanyId(account.getCompanyId());
        meetingMinutes.setDeptId(account.getDeptId());
        meetingMinutes.setPhone(account.getPhone());
        meetingMinutes.setPostIds(account.getPostIds());
        meetingMinutes.setMeetingTrainingId(meetingTraining.getMeetingTrainingId());
        int num = meetingTrainingMinutesService.insertMeetingTrainingMinutes(meetingMinutes);
        if (num <= 0) {
            throw new ApiException("添加会议纪要失败。");
        }
        // 异步操作
        fileSave(params.getFileList(), meetingMinutes.getMeetingTrainingMinutesId());
        map.put("result", meetingTraining);
        map.put("state", 200);
        return ok(map);
    }
    /**
     * 文件存储
     *
     * @param fileList
     */
    public void fileSave(List<ExamineFile> fileList, String examineInitiateId) {
        if (fileList != null && fileList.size() > 0) {
            fileList.forEach((ExamineFile file) -> {
                if (file.getExamineFileType() != null
                        && StringUtils.isNotBlank(file.getExamineFileName())
                        && StringUtils.isNotBlank(file.getExamineFileUrl())) {
                    // 计算文件的大小
                    try {
                        file.setExamineFileSize(fileSize(file.getExamineFileUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 将文件存储起来
                    file.setExamineInitiateId(examineInitiateId);
                    int countFlag = fileService.insertExamineFile(file);
                    if (countFlag <= 0) {
                        throw new ApiException("文件保存失败！");
                    }
                }
            });
        }
    }
    /**
     * 获取会议纪要
     *
     * @param meetingTrainingId 会议训练主键id
     * @return
     */
    @PassToken
    @GetMapping("/getMinutes")
    public ApiResult getMinutes(@RequestParam(defaultValue = "0") String meetingTrainingId) {
        List<MeetingTrainingMinutes> list = meetingTrainingMinutesService.selectMeetingTrainingMinutesList(MeetingTrainingMinutes
                .builder()
                .meetingTrainingId(meetingTrainingId)
                .build());
        return ok(list);
    }

    /**
     * 获取会议领导评价
     *
     * @param examineInitiateId 流程id
     * @return
     */
    @PassToken
    @GetMapping("/getEvaluate")
    public ApiResult getEvaluate(@RequestParam(defaultValue = "0") String examineInitiateId) {
        MeetingExamineInitiate meetingExamineInitiate = meetingInitiateService.selectMeetingExamineInitiateById(examineInitiateId);
        return ok(meetingExamineInitiate);
    }

}
