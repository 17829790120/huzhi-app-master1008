package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
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
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.examine.ExamineEvaluateSubVO;
import com.wlwq.params.examine.ExamineSearchParamVO;
import com.wlwq.params.examine.MeetingEvaluateParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskMeetingExamineFlowAccountService;
import com.wlwq.taskService.TaskScoreService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wlwq.common.apiResult.ApiResult.okMsg;

/**
 * @author wwb
 * 会议训练领导评价流程
 */
@RestController
@RequestMapping(value = "/api/meetingExamineMinutes")
@AllArgsConstructor
public class MeetingExamineEvaluateApiController extends ApiController {

    private final IExamineModuleService examineModuleService;

    private final TokenService tokenService;

    private final IMeetingExamineInitiateService meetingInitiateService;

    private final IMeetingExamineFlowService meetingFlowService;

    private final IMeetingExamineFlowAccountService meetingFlowAccountService;

    private final IApiAccountService accountService;

    private final TaskMeetingExamineFlowAccountService taskExamineFlowAccountService;

    private final IMeetingTrainingService meetingTrainingService;

    private final IMessageRemindService messageRemindService;

    private final TaskScoreService taskScoreService;

    private final IAccountScoreService accountScoreService;


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

//    /**
//     * 会议训练领导评价提交
//     *
//     * @param request
//     * @param params
//     * @param bindingResult
//     * @return
//     */
//    @RepeatSubmit
//    @Transactional(rollbackFor = Exception.class)
//    @PostMapping("/meetingEvaluateSub")
//    public ApiResult meetingEvaluateSub(HttpServletRequest request, @RequestBody @Validated MeetingEvaluateParamVO params, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//        }
//        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
//        if (account == null) {
//            return ApiResult.fail("该用户不存在！");
//        }
//
//        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(params.getMeetingTrainingId());
//        if (meetingTraining == null) {
//            return ApiResult.fail("会议不存在！");
//        }
//        // 查询用户所在的部门的某一各类型是否添加审批流程
//        MeetingExamineFlow examineFlow = meetingFlowService.selectMeetingExamineFlow(MeetingExamineFlow.builder()
//                .companyId(account.getCompanyId())
//                .examineModuleId(25L)
//                .build());
//        if (examineFlow == null) {
//            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
//        }
//
//        MeetingExamineInitiate examineInitiate = MeetingExamineInitiate.builder().build();
//        // 将传过来的参数赋值给要存入的实体
//        BeanUtil.copyProperties(params, examineInitiate);
//        examineInitiate.setCompanyId(account.getCompanyId());
//        examineInitiate.setDeptId(account.getDeptId());
//        examineInitiate.setAccountId(account.getAccountId());
//        examineInitiate.setAccountName(account.getNickName());
//        examineInitiate.setAccountPhone(account.getPhone());
//        examineInitiate.setAccountHead(account.getHeadPortrait());
//        examineInitiate.setExamineModuleId(25);
//
//        examineInitiate.setTitle(meetingTraining.getTitle());
//        examineInitiate.setMeetintBeginTime(meetingTraining.getBeginTime());
//        examineInitiate.setMeetintEndTime(meetingTraining.getEndTime());
//        examineInitiate.setAddress(meetingTraining.getAddress());
//        examineInitiate.setMeetingStatus(meetingTraining.getMeetingStatus());
//        examineInitiate.setJoinAccountDeptId(meetingTraining.getJoinAccountDeptId());
//        examineInitiate.setJoinAccountDeptName(meetingTraining.getJoinAccountDeptName());
//        examineInitiate.setJoinAccountId(meetingTraining.getJoinAccountId());
//        examineInitiate.setJoinAccountNickName(meetingTraining.getJoinAccountNickName());
//        examineInitiate.setSynopsis(meetingTraining.getSynopsis());
//        examineInitiate.setOrganizerAccountId(meetingTraining.getOrganizerAccountId());
//        examineInitiate.setOrganizerHeadPortrait(meetingTraining.getOrganizerHeadPortrait());
//        examineInitiate.setOrganizerNickName(meetingTraining.getOrganizerNickName());
//        examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
//        int count = meetingInitiateService.insertMeetingExamineInitiate(examineInitiate);
//        if (count <= 0) {
//            throw new ApiException("提交失败");
//        }
//        // 将审批流程的第一级人员信息存到记录表中
//        // 异步操作
//        MeetingExamineFlow examineFlow1 = MeetingExamineFlow.builder()
//                .examineModuleId(25L)
//                .companyId(account.getCompanyId())
//                .build();
//        AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
//
//        return okMsg("提交成功,请耐心等待审核！");
//    }

    /**
     * 会议训练领导评价提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/meetingEvaluateSub")
    public ApiResult meetingEvaluateSub(HttpServletRequest request, @RequestBody @Validated MeetingEvaluateParamVO params, BindingResult bindingResult) {
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


        MeetingExamineInitiate examineInitiate = MeetingExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        examineInitiate.setExamineModuleId(25);

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
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
        int count = meetingInitiateService.insertMeetingExamineInitiate(examineInitiate);
        if (count <= 0) {
            throw new ApiException("提交失败");
        }

        // 第一级审批都是自己部门的领导审批，如果是本部门领导提交,进入审批流程
        // 查询本部门的领导
        ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(account.getDeptId()).positionType("1").build());
        if (apiAccount == null) {
            throw new ApiException("没有查询到本部门的领导，请联系管理员！");
        }
        // 部门领导审批
        AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(apiAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
        return okMsg("提交成功,请耐心等待审核！");
    }

    /**
     * 我评价的评价列表
     *
     * @param request
     * @param pageParam     分页
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myMeetingEvaluateList")
    public ApiResult myMeetingEvaluateList(HttpServletRequest request, PageParam pageParam, ExamineSearchParamVO searchParamVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> initiateList = meetingFlowAccountService.selectMyExamineList(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr("25")
                .accountId(tokenService.getAccountId(request))
                .searchTimeTag(searchParamVO.getSearchTimeTag())
                .evaluateStatus(searchParamVO.getEvaluateStatus())
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(initiateList);
        return ok(pageInfo);
    }

    /**
     * 评价提交
     *
     * @param request
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/myMeetingEvaluateSub")
    public ApiResult myMeetingEvaluateSub(HttpServletRequest request, @RequestBody @Validated ExamineEvaluateSubVO examineSubVO, BindingResult bindingResult) {
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
        if (examineFlowAccountList != null && examineFlowAccountList.size() > 0) {
            List<MeetingExamineFlowAccount> yiPinJiaList = examineFlowAccountList.stream().filter(obj -> obj.getEvaluateStatus() == 1).collect(Collectors.toList());
            if (yiPinJiaList == null || yiPinJiaList.size() == 0) {
                MeetingExamineInitiate initiate = meetingInitiateService.selectMeetingExamineInitiateById(map.get("examineInitiateId").toString());
                int count = meetingInitiateService.updateMeetingExamineInitiate(MeetingExamineInitiate
                        .builder()
                        .examineInitiateId(initiate.getExamineInitiateId())
                        .evaluate(examineSubVO.getEvaluate())
                        .evaluateStatus(1)
                        .improvement(examineSubVO.getImprovement())
                        .meetingBenefits(examineSubVO.getMeetingBenefits())
                        .meetingDisadvantages(examineSubVO.getMeetingDisadvantages())
                        .build());
                if (count <= 0) {
                    throw new ApiException("评价失败");
                }
            }
            int count = meetingFlowAccountService.updateMeetingExamineFlowAccount(MeetingExamineFlowAccount.builder()
                    .examineFlowAccountId(examineSubVO.getFlowAccountId())
                    .evaluate(examineSubVO.getEvaluate())
                    .evaluateStatus(1)
                    .improvement(examineSubVO.getImprovement())
                    .meetingBenefits(examineSubVO.getMeetingBenefits())
                    .meetingDisadvantages(examineSubVO.getMeetingDisadvantages())
                    .readStatus(1)
                    .build());
            if (count <= 0) {
                return fail("评价失败！");
            }
            // 给发起人积分
            ApiAccount account = accountService.selectApiAccountById(Convert.toStr(map.get("accountId")));
            if (account != null) {
                int score = taskScoreService.selectScore(account, 31);
                // 更新用户信息
                accountService.updateApiAccount(ApiAccount.builder()
                        .accountId(account.getAccountId())
                        .surplusScore(account.getSurplusScore() + score)
                        .totalScore(account.getTotalScore() + score)
                        .build());
                // 用户积分存入记录
                // 赠送用户积分
                accountScoreService.insertAccountScore(AccountScore.builder()
                        .accountId(account.getAccountId())
                        .targetId(Convert.toStr(map.get("examineInitiateId")))
                        .scoreType(-8)
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .accountHead(account.getHeadPortrait())
                        .scoreSource("会议评价获得积分")
                        .scoreStatus(1)
                        .score(score)
                        .build());
                // 发送系统消息
                // 查询消息是否存在
                messageRemindService.insertMessageRemind(MessageRemind.builder()
                        .title("积分变动")
                        .brief("会议评价获得" + score + "积分,点击查看")
                        .modelStatus(2)
                        .jumpType(-2)
                        .modelId(Convert.toStr(map.get("examineInitiateId")))
                        .accountId(account.getAccountId())
                        .build());
            }
            return okMsg("评价成功");
        }
        return okMsg("评价失败");
    }

}
