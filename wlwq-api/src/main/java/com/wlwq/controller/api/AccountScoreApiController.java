package com.wlwq.controller.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.examine.ExamineSearchParamVO;
import com.wlwq.params.examine.ExamineSubVO;
import com.wlwq.params.examine.OtherParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskScoreService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.wlwq.common.apiResult.ApiResult.okMsg;

/**
 * @author gaoce
 * 用户积分
 */
@RestController
@RequestMapping(value = "/api/accountScore")
@AllArgsConstructor
public class AccountScoreApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountScoreService accountScoreService;

    private final IExamineFlowService flowService;

    private final IExamineCopyFlowService copyFlowService;

    private final TaskScoreService taskScoreService;

    private final IScoreFlowAccountService flowAccountService;

    private final IScoreFlowCopyAccountService copyAccountService;

    /**
     * 积分列表
     *
     * @param request
     * @param pageParam   分页
     * @param month       月份筛选，格式为2023-05
     * @param scoreStatus 1:获得 2:支出
     * @return
     */
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request,
                          PageParam pageParam,
                          String month,
                          @RequestParam(defaultValue = "1") Integer scoreStatus) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountScore> scoreList = accountScoreService.selectApiAccountScoreList(AccountScore.builder()
                .scoreStatus(scoreStatus)
                .accountId(tokenService.getAccountId(request))
                .month(month)
                .build());
        PageInfo<AccountScore> pageInfo = new PageInfo<>(scoreList);
        return ok(pageInfo);
    }

    /**
     * 积分兑换提交
     *
     * @param request
     * @param score   积分
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/exchangeSub")
    public ApiResult exchangeSub(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer score) {
        if (score <= 0) {
            return ApiResult.fail("请输入大于0的积分！");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(30L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }

        if (score > account.getSurplusScore()) {
            return ApiResult.fail("您的账户积分不足！");
        }
        // 更新用户信息
        int updateCount = accountService.updateApiAccount(ApiAccount.builder()
                .accountId(account.getAccountId())
                .surplusScore(account.getSurplusScore() - score)
                .build());
        if (updateCount <= 0) {
            throw new ApiException("更新账户失败！");
        }
        AccountScore accountScore = AccountScore.builder()
                .accountId(account.getAccountId())
                .targetId("0")
                .scoreType(-1)
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .scoreSource("积分兑换")
                .scoreStatus(2)
                .score(score)
                .examineStatus(1)
                .deptId(account.getDeptId())
                .companyId(account.getCompanyId())
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = accountScoreService.insertAccountScore(accountScore);
        if (count <= 0) {
            throw new ApiException("积分存入失败！");
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
            AsyncManager.me().execute(taskScoreService.examineFlowDeptAccount(deptAccount, accountScore.getAccountScoreId(), accountScore.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            AsyncManager.me().execute(taskScoreService.examineFlowAccount(examineFlow1, accountScore.getAccountScoreId(), accountScore.getExamineTag()));
        }
        return ApiResult.okMsg("提交成功，请耐心等待审核！");
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
        ScoreFlowAccount flowAccount = flowAccountService.selectScoreFlowAccountById(examineSubVO.getFlowAccountId());
        if (flowAccount == null) {
            return fail("该详情不存在！");
        }
        // 查询同等审批的是否都通过，通过的情况下进行下一个审批
        // 查询同等级审批的相关信息
        List<ScoreFlowAccount> examineFlowAccountList = flowAccountService.selectScoreFlowAccountList(ScoreFlowAccount.builder()
                .accountScoreId(flowAccount.getAccountScoreId())
                .examineSequence(flowAccount.getExamineSequence())
                .examineTag(flowAccount.getExamineTag())
                .build());
        for (ScoreFlowAccount examineFlowAccount : examineFlowAccountList) {
            // 查询最新的审批状态
            ScoreFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitScoreFlowAccount(ScoreFlowAccount.builder()
                    .accountId(examineFlowAccount.getAccountId())
                    .accountScoreId(examineFlowAccount.getAccountScoreId())
                    .examineSequence(examineFlowAccount.getExamineSequence())
                    .examineTag(flowAccount.getExamineTag())
                    .build());
            if (examineFlowAccount1 != null && examineFlowAccount1.getExamineStatus() == 4) {
                return fail("该发起记录有审批人员已驳回，暂不能通过或拒绝，需等该条记录的发起者重新修改才能提交！");
            }
        }
        int count = flowAccountService.updateScoreFlowAccount(ScoreFlowAccount.builder()
                .scoreFlowAccountId(examineSubVO.getFlowAccountId())
                .examineStatus(examineSubVO.getExamineStatus())
                .rejectContent(examineSubVO.getContent())
                .pics(examineSubVO.getPics())
                .readStatus(1)
                .build());
        if (count <= 0) {
            return fail("审批失败！");
        }
        // 异步操作
        AsyncManager.me().execute(taskScoreService.editAccountScore(examineSubVO, flowAccount));
        return okMsg("提交成功");
    }

    /**
     * 我审批的审批列表
     *
     * @param request
     * @param pageParam     分页
     * @param examineStatus 1：待处理 2：已处理(包含已通过和已驳回)
     * @param keyword       提交人名称筛选
     * @return
     */
    @GetMapping("/myExamineList")
    public ApiResult myExamineList(HttpServletRequest request, PageParam pageParam, @RequestParam(defaultValue = "1") Integer examineStatus, String keyword) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> examineList = flowAccountService.selectMyScoreFlowAccountList(ScoreFlowAccount.builder()
                .accountName(keyword)
                .examineStatus(examineStatus)
                .accountId(tokenService.getAccountId(request))
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(examineList);
        return ok(pageInfo);
    }


    /**
     * 抄送我的列表
     *
     * @param request
     * @param pageParam 分页
     * @param keyword   提交人名称筛选
     * @return
     */
    @GetMapping("/copyMyExamineList")
    public ApiResult myExamineList(HttpServletRequest request, PageParam pageParam, String keyword) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> examineList = copyAccountService.selectCopyMyScoreFlowAccountList(ScoreFlowCopyAccount.builder()
                .accountName(keyword)
                .copyAccountId(tokenService.getAccountId(request))
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(examineList);
        pageInfo.getList().forEach(e -> {
            // 查询已经通过的审批人列表
            List<ScoreFlowAccount> flowAccountList = flowAccountService.selectScoreFlowAccountList(ScoreFlowAccount.builder().accountScoreId(Convert.toStr(e.get("accountScoreId"))).examineStatus(3).build());
            e.put("flowAccountList", flowAccountList);
        });
        return ok(pageInfo);
    }


    /**
     * 积分提现记录
     *
     * @param request
     * @param pageParam     分页
     * @param month         月份筛选，格式为2023-05
     * @param examineStatus 审批状态 1：(待审批和审核中)  3：已通过 4：已驳回
     * @return
     */
    @GetMapping("/withdrawList")
    public ApiResult withdrawList(HttpServletRequest request,
                                  PageParam pageParam,
                                  String month,
                                  @RequestParam(defaultValue = "1") Integer examineStatus) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountScore> scoreList = accountScoreService.selectApiAccountScoreList(AccountScore.builder()
                .scoreStatus(2)
                .examineStatus(examineStatus)
                .accountId(tokenService.getAccountId(request))
                .month(month)
                .build());
        PageInfo<AccountScore> pageInfo = new PageInfo<>(scoreList);
        return ok(pageInfo);
    }

    /**
     * 积分提现详情
     *
     * @param accountScoreId 用户积分ID
     * @return
     */
    @GetMapping("/withdrawDetail")
    public ApiResult withdrawDetail(@RequestParam(defaultValue = "0") String accountScoreId) {
        AccountScore accountScore = accountScoreService.selectAccountScoreById(accountScoreId);
        return ok(accountScore);
    }

    /**
     * 审批流程人员列表
     *
     * @param accountScoreId 用户积分ID
     * @return
     */
    @PassToken
    @GetMapping("/examineFlowAccountList")
    public ApiResult examineFlowAccountList(@RequestParam(defaultValue = "0") String accountScoreId) {
        AccountScore accountScore = accountScoreService.selectAccountScoreById(accountScoreId);
        if (accountScore == null) {
            return fail("该信息不存在！");
        }
        // 查询审批流程
        ExamineFlow examineFlow = ExamineFlow.builder()
                .examineModuleId(30L)
                .companyId(accountScore.getCompanyId())
                .minValue(accountScore.getScore().doubleValue())
                .build();
        // 查询员工发起的审批管理人员列表
        List<ExamineFlowResultVO> examineFlowResultVOList = flowService.selectAccountScoreExaminePeopleList(examineFlow, accountScoreId);
        return ok(examineFlowResultVOList);
    }

    /**
     * 查询抄送岗位列表
     *
     * @return
     */
    @GetMapping("/copyAccountList")
    public ApiResult copyAccountList(HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询抄送人的信息
        List<ApiAccount> copyAccountList = new ArrayList<>();
        // 查询抄送岗位列表
        List<ExamineCopyFlow> copyFlowList = copyFlowService.selectExamineCopyFlowList(ExamineCopyFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(30L)
                .build());
        for (ExamineCopyFlow examineCopyFlow : copyFlowList) {
            List<ApiAccount> accountList = accountService.selectApiAccountListByPostIdAndDeptId(examineCopyFlow.getDeptId(), examineCopyFlow.getPostId());
            copyAccountList.addAll(accountList);
        }
        return ok(copyAccountList);
    }

    /**
     * 抄送者处理提交
     *
     * @param request
     * @param flowCopyAccountId 抄送ID
     * @param pics              处理图片
     * @param remark            处理备注
     * @return
     */
    @RepeatSubmit
    @PostMapping("/disposeSub")
    public ApiResult disposeSub(HttpServletRequest request,
                                @RequestParam(defaultValue = "0") String flowCopyAccountId,
                                String pics,
                                String remark) {
        ScoreFlowCopyAccount account = copyAccountService.selectScoreFlowCopyAccountById(flowCopyAccountId);
        if (account == null) {
            return ApiResult.fail("该抄送信息不存在！");
        }
        if (!account.getCopyAccountId().equals(tokenService.getAccountId(request))) {
            return ApiResult.fail("只能处理自己提交的信息！");
        }
        if (account.getDisposeStatus() == 1) {
            return ApiResult.fail("已处理过了，请勿重复处理！");
        }
        int count = copyAccountService.updateScoreFlowCopyAccount(ScoreFlowCopyAccount.builder()
                .scoreFlowCopyAccountId(flowCopyAccountId)
                .disposeStatus(1)
                .disposePics(pics)
                .disposeRemark(remark)
                .build());
        if (count <= 0) {
            return ApiResult.fail("提交失败！");
        }
        return ApiResult.okMsg("已处理！");
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
        List<ScoreFlowAccount> examineFlowResultVOList = flowAccountService.selectNearScoreFlowAccountList(ScoreFlowAccount.builder()
                .examineTag(examineTag)
                .build());
        return ok(examineFlowResultVOList);
    }
}
