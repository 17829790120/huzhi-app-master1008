package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.examine.*;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskMeetingExamineFlowAccountService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.wlwq.common.apiResult.ApiResult.okMsg;

/**
 * @author wwb
 * 会议训练流程
 */
@RestController
@RequestMapping(value = "/api/meetingExamine")
@AllArgsConstructor
public class MeetingExamineApiController extends ApiController {

    private final IExamineModuleService examineModuleService;

    private final TokenService tokenService;

    private final IMeetingExamineInitiateService meetingInitiateService;

    private final IMeetingExamineFlowService meetingFlowService;

    private final IMeetingExamineFlowAccountService meetingFlowAccountService;

    private final IApiAccountService accountService;

    private final TaskMeetingExamineFlowAccountService taskExamineFlowAccountService;

    private final IExamineFileService fileService;

    private final IMeetingTrainingService meetingTrainingService;

    private final IMeetingTrainingItemService meetingTrainingItemService;

    /**
     * 审批类型列表（一级）
     *
     * @return
     */
    @GetMapping(value = "/oneList")
    @PassToken
    public ApiResult oneList() {
        List<ExamineModule> classList = examineModuleService.selectMeetingExamineModuleList(ExamineModule.builder().parentId(0L).showStatus(1).build());
        return ok(classList);
    }

    /**
     * 审批类型列表（三级）
     *
     * @param examineModuleId 二级分类ID（会议类型）
     * @param keyword         关键词搜索
     * @return
     */
    @GetMapping(value = "/threeList")
    @PassToken
    public ApiResult threeList(@RequestParam(defaultValue = "0") Long examineModuleId, String keyword) {
        List<ExamineModule> classList = examineModuleService.selectMeetingExamineModuleList(ExamineModule
                .builder()
                .parentId(examineModuleId)
                .showStatus(1)
                .moduleName(keyword)
                .build());
        return ok(classList);
    }


    /**
     * 会议训练审核提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/meetingSub")
    public ApiResult meetingSub(HttpServletRequest request, @RequestBody @Validated MeetingParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(params.getMeetingTrainingId());
        if (meetingTraining == null) {
            return ApiResult.fail("会议不存在！");
        }
        // 0:直接走后台设置的审批流 1：先部门领导审批，然后再走后台设置的审批流
        ApiAccount deptAccount = null;
        Integer examineFlowTag = 0;
        // 第一级审批都是自己部门的领导审批，如果是本部门领导提交,进入审批流程
        // 查询本部门的领导
        ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(account.getDeptId()).positionType("1").build());
        if (apiAccount != null) {
            examineFlowTag = 1;
            deptAccount = apiAccount;
        }
        if (examineFlowTag == 0) {
            // 查询用户所在的部门的某一各类型是否添加审批流程
            MeetingExamineFlow examineFlow = meetingFlowService.selectMeetingExamineFlow(MeetingExamineFlow.builder()
                    .companyId(account.getCompanyId())
                    .examineModuleId(24L)
                    .build());
            if (examineFlow == null) {
                return fail("该部门下该模块没有添加审批流程，请联系管理员！");
            }
        }

        MeetingExamineInitiate examineInitiate = MeetingExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        examineInitiate.setExamineModuleId(24);

        examineInitiate.setTitle(meetingTraining.getTitle());
        examineInitiate.setMeetintBeginTime(meetingTraining.getBeginTime());
        examineInitiate.setMeetintEndTime(meetingTraining.getEndTime());
        examineInitiate.setAddress(meetingTraining.getAddress());
        examineInitiate.setMeetingStatus(meetingTraining.getMeetingStatus());
        examineInitiate.setJoinAccountDeptId(meetingTraining.getJoinAccountDeptId());
        examineInitiate.setJoinAccountDeptName(meetingTraining.getJoinAccountDeptName());
        examineInitiate.setJoinAccountId(meetingTraining.getJoinAccountId());
        examineInitiate.setJoinAccountNickName(meetingTraining.getJoinAccountNickName());
        examineInitiate.setSynopsis(meetingTraining.getSynopsis());
        examineInitiate.setOrganizerAccountId(meetingTraining.getOrganizerAccountId());
        examineInitiate.setOrganizerHeadPortrait(meetingTraining.getOrganizerHeadPortrait());
        examineInitiate.setOrganizerNickName(meetingTraining.getOrganizerNickName());
        examineInitiate.setMeetingType(meetingTraining.getMeetingType());
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
        int count = meetingInitiateService.insertMeetingExamineInitiate(examineInitiate);
        if (count <= 0) {
            throw new ApiException("提交失败");
        }
        if (examineFlowTag == 1 && deptAccount != null) {
            // 部门领导审批
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            MeetingExamineFlow examineFlow1 = MeetingExamineFlow.builder()
                    .examineModuleId(24L)
                    .companyId(account.getCompanyId())
                    .build();
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
        }
        return okMsg("提交成功,请耐心等待审核！");
    }

    /**
     * 我发起的审批列表
     *
     * @param request
     * @param pageParam     分页
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myInitiateList")
    public ApiResult myInitiateList(HttpServletRequest request, PageParam pageParam, ExamineSearchParamVO searchParamVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<MeetingExamineInitiate> initiateList = meetingInitiateService.selectExamineInitiateApiList(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .meetingType(searchParamVO.getMeetingType())
                .searchTimeTag(searchParamVO.getSearchTimeTag())
                .title(searchParamVO.getKeyword())
                .parentId("0")
                .build());
        PageInfo<MeetingExamineInitiate> pageInfo = new PageInfo<>(initiateList);
        initiateList.forEach(
                obj -> {
                    List<MeetingTrainingItem> meetingTrainingItemList = meetingTrainingItemService.selectMeetingTrainingItemList(MeetingTrainingItem
                            .builder()
                            .meetingTrainingId(obj.getMeetingTrainingId())
                            .build());
                    obj.setMeetingTrainingItemList(meetingTrainingItemList);
                }
        );
        return ok(pageInfo);
    }

    /**
     * 我发起的审批列表各个状态未读的数量
     *
     * @param request
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myInitiateCount")
    public ApiResult myInitiateCount(HttpServletRequest request, ExamineSearchParamVO searchParamVO) {
        MeetingExamineInitiate examineInitiate = MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .meetingType(searchParamVO.getMeetingType())
                .parentId("0")
                .readStatus(0)
                .build();
        Map<String, Object> map = new HashMap<>(10);
        // 待审批未读数量
        examineInitiate.setExamineStatus(1);
        map.put("stayExamineNoReadyCount", meetingInitiateService.selectExamineInitiateApiCount(examineInitiate));
        // 审批中未读数量
        examineInitiate.setExamineStatus(2);
        map.put("examineIngNoReadyCount", meetingInitiateService.selectExamineInitiateApiCount(examineInitiate));
        // 已通过未读数量
        examineInitiate.setExamineStatus(3);
        map.put("passExamineNoReadyCount", meetingInitiateService.selectExamineInitiateApiCount(examineInitiate));
        // 已驳回未读数量
        examineInitiate.setExamineStatus(4);
        map.put("refuseExamineNoReadyCount", meetingInitiateService.selectExamineInitiateApiCount(examineInitiate));
        return ok(map);
    }


    /**
     * 我发起的审批详情
     *
     * @param initiateId 审批发起ID
     * @return
     */
    @GetMapping("/initiateDetail")
    public ApiResult initiateDetail(@RequestParam(defaultValue = "0") String initiateId) {
        MeetingExamineInitiate examineInitiate = meetingInitiateService.selectMeetingExamineInitiateById(initiateId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }
        // 审批类型ID  1：会议训练审核 2：会议训练评价 3：其他
        if (examineInitiate.getExamineModuleId() == 24) {
            List<MeetingExamineInitiate> initiateList = meetingInitiateService.selectExamineInitiateApiList(MeetingExamineInitiate.builder().parentId(examineInitiate.getExamineInitiateId()).build());
            initiateList.forEach((MeetingExamineInitiate initiate) -> {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiate.getExamineInitiateId()).build());
                initiate.setFileList(fileList);
            });
            examineInitiate.setInitiateDetailList(initiateList);
        } else {
            if (examineInitiate.getExamineModuleId() == 25) {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiateId).build());
                examineInitiate.setFileList(fileList);
            }
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("examineInitiate", examineInitiate);

        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(examineInitiate.getMeetingTrainingId());
        map.put("meetingTraining", meetingTraining);
        List<MeetingTrainingItem> meetingTrainingItemList = meetingTrainingItemService.selectMeetingTrainingItemList(MeetingTrainingItem
                .builder()
                .meetingTrainingId(examineInitiate.getMeetingTrainingId())
                .build());
        map.put("meetingTrainingItemList", meetingTrainingItemList);

        // 将信息改为已读
        meetingInitiateService.updateMeetingExamineInitiate(MeetingExamineInitiate.builder()
                .examineInitiateId(initiateId)
                .readStatus(1)
                .build());
        return ok(map);
    }

    /**
     * 查询员工第一次发起的审批管理人员列表
     *
     * @param examineModuleId 审批类型ID  24：会议训练审核 25：会议训练评价
     * @return
     */
    @GetMapping("/examineFlowList")
    public ApiResult examineFlowList(HttpServletRequest request, @RequestParam(defaultValue = "0") Long examineModuleId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询审批流程
        MeetingExamineFlow examineFlow = MeetingExamineFlow.builder()
                .examineModuleId(examineModuleId)
                .companyId(account.getCompanyId())
                .build();
        // 查询员工发起的审批管理人员列表
        List<ExamineFlowResultVO> examineFlowResultVOList = meetingFlowService.selectStaffInitiateExaminePeopleList(examineFlow);
        // 默认第一级是本部门的领导
        // 查询本部门的领导
        ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(account.getDeptId()).positionType("1").build());
        if (apiAccount != null) {
            List<ExamineFlowAccountResultVO> accountResultVOList = new ArrayList<>();
            accountResultVOList.add(ExamineFlowAccountResultVO.builder()
                    .accountId(apiAccount.getAccountId())
                    .accountName(apiAccount.getNickName())
                    .accountHead(apiAccount.getHeadPortrait())
                    .phone(apiAccount.getPhone())
                    .postIds(apiAccount.getPostIds())
                    .companyId(apiAccount.getCompanyId())
                    .postName(apiAccount.getPostNames())
                    .examineSequence(0)
                    .build());
            examineFlowResultVOList.add(ExamineFlowResultVO.builder()
                    .examineSequence(0)
                    .examinePeopleCount(1)
                    .accountResultVOList(accountResultVOList)
                    .build());
            // 排序，examineSequence从小到大
            Collections.sort(examineFlowResultVOList, Comparator.comparingInt(ExamineFlowResultVO::getExamineSequence));
        }
        return ok(examineFlowResultVOList);
    }


    /**
     * 我审批的审批列表
     *
     * @param request
     * @param pageParam     分页
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myExamineList")
    public ApiResult myExamineList(HttpServletRequest request, PageParam pageParam, ExamineSearchParamVO searchParamVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> initiateList = meetingFlowAccountService.selectMyExamineList(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .searchTimeTag(searchParamVO.getSearchTimeTag())
                .meetingType(searchParamVO.getMeetingType())
                .title(searchParamVO.getKeyword())
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(initiateList);
        return ok(pageInfo);
    }

    /**
     * 全部会议列表（审核通过的）
     *
     * @param request
     * @param pageParam     分页
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/allExamineList")
    public ApiResult allExamineList(HttpServletRequest request, PageParam pageParam, ExamineSearchParamVO searchParamVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> initiateList = meetingFlowAccountService.selectAllExamineList(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(searchParamVO.getAccountId())
                .companyId(account.getCompanyId())
                .searchTimeTag(searchParamVO.getSearchTimeTag())
                .meetingType(searchParamVO.getMeetingType())
                .tag(searchParamVO.getTag())
                .title(searchParamVO.getKeyword())
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(initiateList);
        if (initiateList != null && initiateList.size() > 0) {
            initiateList.forEach(
                    obj -> {
                        MeetingExamineInitiate meetingExamineInitiate = meetingInitiateService.selectInitiateByMeetingTrainingId(obj.get("meetingTrainingId").toString());
                        if (meetingExamineInitiate != null) {
                            obj.put("examineInitiateIdByEvaluate", meetingExamineInitiate.getExamineInitiateId());
                        }
                    }
            );
        }
        return ok(pageInfo);
    }

    /**
     * 我审批的各个状态未读的数量
     *
     * @param request
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myExamineCount")
    public ApiResult myExamineCount(HttpServletRequest request, ExamineSearchParamVO searchParamVO) {
        Map<String, Object> map = new HashMap<>(10);
        // 待审批数量
        map.put("noExamineCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .meetingType(searchParamVO.getMeetingType())
                .examineStatus(1)
                .build()));
        // 待审批未读的数量
        map.put("noExamineNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .meetingType(searchParamVO.getMeetingType())
                .examineStatus(1)
                .readStatus(0)
                .build()));
        // 已通过未读的数量
        map.put("passNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(3)
                .readStatus(0)
                .build()));
        // 已驳回未读的数量
        map.put("rejectNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(4)
                .readStatus(0)
                .build()));
        // 审批中数量
        map.put("examineCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .meetingType(searchParamVO.getMeetingType())
                .examineStatus(2)
                .build()));
        // 审批中未读的数量
        map.put("examineNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .meetingType(searchParamVO.getMeetingType())
                .examineStatus(2)
                .readStatus(0)
                .build()));
        return ok(map);
    }


    /**
     * 我审批的审批详情
     *
     * @param request
     * @param flowAccountId 用户审批流程ID
     * @param tag           0:查询个人，1：查询全部
     * @return
     */
    @GetMapping("/examineDetail")
    public ApiResult examineDetail(HttpServletRequest request, @RequestParam(defaultValue = "0") String flowAccountId,
                                   @RequestParam(defaultValue = "0") int tag) {
        String accountId = tokenService.getAccountId(request);
        if (tag == 1) {
            accountId = null;
        }
        Map<String, Object> examineInitiate = meetingFlowAccountService.selectMyExamineDetail(accountId, flowAccountId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }
        List<MeetingTrainingItem> meetingTrainingItemList = meetingTrainingItemService.selectMeetingTrainingItemList(MeetingTrainingItem
                .builder()
                .meetingTrainingId(examineInitiate.get("meetingTrainingId").toString())
                .build());
        examineInitiate.put("meetingTrainingItemList", meetingTrainingItemList);
        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(examineInitiate.get("meetingTrainingId").toString());
        examineInitiate.put("meetingTraining", meetingTraining);

        // 审批类型ID  24：会议训练审核 25：会议训练评价
        if (Convert.toInt(examineInitiate.get("examineModuleId")) == 24) {
            List<MeetingExamineInitiate> initiateList = meetingInitiateService.selectExamineInitiateApiList(MeetingExamineInitiate.builder().parentId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
            initiateList.forEach((MeetingExamineInitiate initiate) -> {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiate.getExamineInitiateId()).build());
                initiate.setFileList(fileList);
            });
            examineInitiate.put("initiateDetailList", initiateList);
        } else {
            if (Convert.toInt(examineInitiate.get("examineModuleId")) == 25) {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
                examineInitiate.put("fileList", fileList);
            }
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("examineInitiate", examineInitiate);
        // 将信息改为已读
        meetingFlowAccountService.updateMeetingExamineFlowAccount(MeetingExamineFlowAccount.builder()
                .examineFlowAccountId(flowAccountId)
                .readStatus(1)
                .readTag(1)
                .build());
        return ok(map);
    }


    /**
     * 我审批的审批提交 通过/拒绝
     *
     * @param request
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/examineSub")
    public ApiResult examineSub(HttpServletRequest request, @Validated ExamineSubVO examineSubVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> map = meetingFlowAccountService.selectMyExamineDetail(accountId, examineSubVO.getFlowAccountId());
        if (map == null) {
            return fail("该详情不存在！");
        }
        // 查询同等审批的是否都通过，通过的情况下进行下一个审批
        // 查询同等级审批的相关信息
        List<MeetingExamineFlowAccount> examineFlowAccountList = meetingFlowAccountService.selectMeetingExamineFlowAccountList(MeetingExamineFlowAccount.builder()
                .examineInitiateId(map.get("examineInitiateId").toString())
                .examineSequence(Integer.parseInt(map.get("examineSequence").toString()))
                .examineTag(Convert.toStr(map.get("examineTag")))
                .build());
        for (MeetingExamineFlowAccount examineFlowAccount : examineFlowAccountList) {
            // 查询最新的审批状态
            MeetingExamineFlowAccount examineFlowAccount1 = meetingFlowAccountService.selectNearLimitExamineFlowAccount(MeetingExamineFlowAccount.builder()
                    .accountId(examineFlowAccount.getAccountId())
                    .examineInitiateId(examineFlowAccount.getExamineInitiateId())
                    .examineSequence(examineFlowAccount.getExamineSequence())
                    .examineTag(Convert.toStr(map.get("examineTag")))
                    .build());
            if (examineFlowAccount1 != null && examineFlowAccount1.getExamineStatus() == 4) {
                return fail("该发起记录有审批人员已驳回，暂不能通过或拒绝，需等该条记录的发起者重新修改才能提交！");
            }
        }
        int count = meetingFlowAccountService.updateMeetingExamineFlowAccount(MeetingExamineFlowAccount.builder()
                .examineFlowAccountId(examineSubVO.getFlowAccountId())
                .examineStatus(examineSubVO.getExamineStatus())
                .rejectContent(examineSubVO.getContent())
                .integral(examineSubVO.getIntegral())
                .pics(examineSubVO.getPics())
                .readStatus(1)
                .build());
        if (count <= 0) {
            return fail("审批失败！");
        }
        // 异步操作
        AsyncManager.me().execute(taskExamineFlowAccountService.editExamineInitiate(examineSubVO, map));
        return okMsg("提交成功");
    }


    /**
     * 审批流程人员列表(除员工第一次发起的除外)
     *
     * @param initiateId 审批发起ID
     * @return
     */
    @PassToken
    @GetMapping("/examineFlowAccountList")
    public ApiResult examineFlowAccountList(@RequestParam(defaultValue = "0") String initiateId) {
        MeetingExamineInitiate examineInitiate = meetingInitiateService.selectMeetingExamineInitiateById(initiateId);
        if (examineInitiate == null) {
            return fail("该信息不存在！");
        }
        // 查询审批流程
        MeetingExamineFlow examineFlow = MeetingExamineFlow.builder()
                .examineModuleId(Long.valueOf(examineInitiate.getExamineModuleId()))
                .companyId(examineInitiate.getCompanyId())
                .build();
        // 查询员工发起的审批管理人员列表
        List<ExamineFlowResultVO> examineFlowResultVOList = meetingFlowService.selectStaffInitiateExaminePeopleList(examineFlow, initiateId,examineInitiate.getExamineTag());
        return ok(examineFlowResultVOList);
    }


    /**
     * 审批  删除
     *
     * @param examineInitiateId 审批发起者Id
     * @return
     */
    @PostMapping("/del")
    public ApiResult del(HttpServletRequest request, @RequestParam(defaultValue = "0") String examineInitiateId) {
        MeetingExamineInitiate initiate = meetingInitiateService.selectMeetingExamineInitiateById(examineInitiateId);
        if (initiate == null) {
            return fail("审批不存在！");
        }
        if (initiate.getExamineStatus() != 4) {
            return fail("只有已驳回的才能删除！");
        }
        if (!initiate.getAccountId().equals(tokenService.getAccountId(request))) {
            return fail("只有审批发起者才能删除！");
        }
        // 查询审批发起信息
        int count = meetingInitiateService.deleteMeetingExamineInitiateById(examineInitiateId);
        if (count <= 0) {
            return fail("删除失败！");
        }
        return okMsg("已删除！");
    }

    /**
     * 审批首页的发起/审批数量
     *
     * @param request
     * @return
     */
    @GetMapping("/examineCount")
    public ApiResult examineCount(HttpServletRequest request) {
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> map = new HashMap<>(2);
        // 我发起的未读的数量
        map.put("initiateNoReadCount", meetingInitiateService.selectExamineInitiateApiCount(MeetingExamineInitiate.builder().readStatus(0).accountId(accountId).build()));
        // 我审批的未读的数量
        map.put("examineNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder().accountId(accountId).readStatus(0).build()));
        return ok(map);
    }

    /**
     * 我发起的审批撤回（只有在待审批的状态下才能撤回）
     *
     * @param request
     * @param initiateId 发起者审批ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/initiateRevocation")
    public ApiResult initiateRevocation(HttpServletRequest request, @RequestParam(defaultValue = "0") String initiateId) {
        String accountId = tokenService.getAccountId(request);
        MeetingExamineInitiate examineInitiate = meetingInitiateService.selectMeetingExamineInitiateById(initiateId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }
        if (!accountId.equals(examineInitiate.getAccountId())) {
            return fail("您发起的详情不存在！");
        }
        if (examineInitiate.getExamineStatus() != 1) {
            return fail("只有在待审批的状态下才能撤回！");
        }
        // 查询申批人员表，将其删除
        int count = meetingFlowAccountService.deleteMeetingExamineFlowAccountByInitiateId(initiateId);
        if (count <= 0) {
            throw new ApiException("删除审批人员失败！");
        }
        int result = meetingInitiateService.updateMeetingExamineInitiate(MeetingExamineInitiate.builder()
                .examineInitiateId(initiateId)
                .examineStatus(5)
                .build());
        if (result <= 0) {
            throw new ApiException("撤回失败！");
        }
        return okMsg("已撤回！");
    }


    /**
     * 修改 会议提交（只有待审批的和已拒绝和已撤回的才能修改提交）
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editLeaveSub")
    public ApiResult editLeaveSub(HttpServletRequest request, @RequestBody @Validated MeetingParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        MeetingExamineInitiate initiate = meetingInitiateService.selectMeetingExamineInitiateById(params.getExamineInitiateId());
        if (initiate == null) {
            return fail("该详情不存在！");
        }
        if (!account.getAccountId().equals(initiate.getAccountId())) {
            return fail("您发起的详情不存在！");
        }
        // 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
        Integer status = initiate.getExamineStatus();
        if (status != 1 && status != 4 && status != 5) {
            return fail("只有在待审批或者已驳回或者已撤回的状态下才能修改！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        MeetingExamineFlow examineFlow = meetingFlowService.selectMeetingExamineFlow(MeetingExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(24L)
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }


        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(params.getMeetingTrainingId());
        if (meetingTraining == null) {
            return ApiResult.fail("会议不存在！");
        }

        initiate.setCompanyId(account.getCompanyId());
        initiate.setDeptId(account.getDeptId());
        initiate.setAccountId(account.getAccountId());
        initiate.setAccountName(account.getNickName());
        initiate.setAccountPhone(account.getPhone());
        initiate.setAccountHead(account.getHeadPortrait());
        initiate.setExamineModuleId(24);

        initiate.setTitle(meetingTraining.getTitle());
        initiate.setMeetintBeginTime(meetingTraining.getBeginTime());
        initiate.setMeetintEndTime(meetingTraining.getEndTime());
        initiate.setAddress(meetingTraining.getAddress());
        initiate.setMeetingStatus(meetingTraining.getMeetingStatus());
        initiate.setJoinAccountDeptId(meetingTraining.getJoinAccountDeptId());
        initiate.setJoinAccountDeptName(meetingTraining.getJoinAccountDeptName());
        initiate.setJoinAccountId(meetingTraining.getJoinAccountId());
        initiate.setJoinAccountNickName(meetingTraining.getJoinAccountNickName());
        initiate.setSynopsis(meetingTraining.getSynopsis());
        initiate.setOrganizerAccountId(meetingTraining.getOrganizerAccountId());
        initiate.setOrganizerHeadPortrait(meetingTraining.getOrganizerHeadPortrait());
        initiate.setOrganizerNickName(meetingTraining.getOrganizerNickName());
        initiate.setMeetingType(meetingTraining.getMeetingType());

        initiate.setExamineStatus(1);
        String examineTag = IdUtil.getSnowflake(1, 1).nextIdStr();
        initiate.setExamineTag(examineTag);
        int count = meetingInitiateService.updateMeetingExamineInitiate(initiate);
        if (count <= 0) {
            throw new ApiException("更新失败");
        }
        // 0:直接走后台设置的审批流 1：先部门领导审批，然后再走后台设置的审批流
        ApiAccount deptAccount = null;
        Integer examineFlowTag = 0;
        // 第一级审批都是自己部门的领导审批，如果是本部门领导提交,进入审批流程
        // 查询本部门的领导
        ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(account.getDeptId()).positionType("1").build());
        if (apiAccount != null) {
            examineFlowTag = 1;
            deptAccount = apiAccount;
        }
        if (examineFlowTag == 1 && deptAccount != null) {
            // 部门领导审批
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(deptAccount, initiate.getExamineInitiateId(), examineTag));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            MeetingExamineFlow examineFlow1 = MeetingExamineFlow.builder()
                    .examineModuleId(24L)
                    .companyId(account.getCompanyId())
                    .build();
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, initiate.getExamineInitiateId(),examineTag));
        }

        return okMsg("提交成功,请耐心等待审核！");
    }

    /**
     * 审批流程人员列表(进行中和未开始的除外)
     *
     * @param examineTag  审批唯一标识
     * @return
     */
    @GetMapping("/disposeExamineFlowAccountList")
    public ApiResult disposeExamineFlowAccountList(@RequestParam(defaultValue = "0") String examineTag) {
        // 查询审批流程人员列表
        List<MeetingExamineFlowAccount> examineFlowResultVOList = meetingFlowAccountService.selectNearExamineFlowAccountList(MeetingExamineFlowAccount.builder()
                .examineTag(examineTag)
                .build());
        return ok(examineFlowResultVOList);
    }
}
