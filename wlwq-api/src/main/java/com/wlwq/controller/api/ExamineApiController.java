package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
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
import com.wlwq.common.utils.DateUtils;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.examine.*;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysConfigService;
import com.wlwq.taskService.TaskExamineFlowAccountService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static com.wlwq.common.apiResult.ApiResult.okMsg;

/**
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/examine")
@AllArgsConstructor
public class ExamineApiController extends ApiController {

    private final IExamineModuleService examineModuleService;

    private final TokenService tokenService;

    private final IExamineInitiateService initiateService;

    private final IExamineFlowService flowService;

    private final IExamineFlowAccountService flowAccountService;

    private final IApiAccountService accountService;

    private final TaskExamineFlowAccountService taskExamineFlowAccountService;

    private final IExamineFileService fileService;

    private final IExamineCopyFlowService copyFlowService;

    private final IExamineContractCounterpartyService counterpartyService;

    private final IExamineFlowCopyAccountService flowCopyAccountService;

    private final ISysConfigService sysConfigService;


    /**
     * 审批类型列表（一级）
     *
     * @return
     */
    @GetMapping(value = "/oneList")
    @PassToken
    public ApiResult oneList() {
        List<ExamineModule> classList = examineModuleService.selectExamineModuleList(ExamineModule.builder().parentId(0L).showStatus(1).build());
        return ok(classList);
    }

    /**
     * 审批类型列表（三级）
     *
     * @param examineModuleId 二级分类ID（金额类型：9 请假类型：10 报销类型：11 合同期限类型：12 印章类型：13）
     * @param keyword         关键词搜索
     * @return
     */
    @GetMapping(value = "/threeList")
    @PassToken
    public ApiResult threeList(@RequestParam(defaultValue = "0") Long examineModuleId, String keyword) {
        List<ExamineModule> classList = examineModuleService.selectExamineModuleList(ExamineModule.builder().parentId(examineModuleId).showStatus(1).moduleName(keyword).build());
        return ok(classList);
    }


    /**
     * 请假提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/leaveSub")
    public ApiResult leaveSub(HttpServletRequest request, @RequestBody @Validated LeaveParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
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
            ExamineFlow examineFlow = flowService.selectExamineFlow(ExamineFlow.builder()
                    .companyId(account.getCompanyId())
                    .examineModuleId(1L)
                    .build());
            if (examineFlow == null) {
                return fail("该部门下该模块没有添加审批流程，请联系管理员！");
            }
        }
        ExamineInitiate examineInitiate = ExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        examineInitiate.setExamineModuleId(1L);
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
        int count = initiateService.insertExamineInitiate(examineInitiate);
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
            ExamineFlow examineFlow1 = ExamineFlow.builder()
                    .examineModuleId(1L)
                    .companyId(account.getCompanyId())
                    .minValue(params.getAskForLeaveHour())
                    .build();
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
        }
        return okMsg("提交成功,请耐心等待审核！");
    }


    /**
     * 报销提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/expenseSub")
    public ApiResult expenseSub(HttpServletRequest request, @RequestBody @Validated ExpenseParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询用户所在的公司的某一各类型是否添加审批流程
        ExamineFlow examineFlow = flowService.selectExamineFlow(ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(2L)
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 异步操作
        AsyncManager.me().execute(taskExamineFlowAccountService.expense(params, account));
        return okMsg("提交成功,请耐心等待审核！");
    }


    /**
     * 合同提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/contractSub")
    public ApiResult contractSub(HttpServletRequest request, @RequestBody @Validated ContractParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow = flowService.selectExamineFlow(ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(3L)
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 异步操作
        AsyncManager.me().execute(taskExamineFlowAccountService.contract(params, account));
        return okMsg("提交成功,请耐心等待审核！");
    }


    /**
     * 其他提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/otherSub")
    public ApiResult otherSub(HttpServletRequest request, @RequestBody @Validated OtherParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow = flowService.selectExamineFlow(ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(4L)
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        ExamineInitiate examineInitiate = ExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        examineInitiate.setExamineModuleId(4L);
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
        int count = initiateService.insertExamineInitiate(examineInitiate);
        if (count <= 0) {
            throw new ApiException("提交失败");
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
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            ExamineFlow examineFlow1 = ExamineFlow.builder()
                    .examineModuleId(4L)
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
        List<ExamineInitiate> initiateList = initiateService.selectExamineInitiateApiList(ExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .searchTimeTag(searchParamVO.getSearchTimeTag() == null ? 1 : searchParamVO.getSearchTimeTag())
                .parentId("0")
                .build());
        PageInfo<ExamineInitiate> pageInfo = new PageInfo<>(initiateList);
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
        ExamineInitiate examineInitiate = ExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .parentId("0")
                .readStatus(0)
                .build();
        Map<String, Object> map = new HashMap<>(10);
        // 待审批未读数量
        examineInitiate.setExamineStatus(1);
        map.put("stayExamineNoReadyCount", initiateService.selectExamineInitiateApiCount(examineInitiate));
        // 审批中未读数量
        examineInitiate.setExamineStatus(2);
        map.put("examineIngNoReadyCount", initiateService.selectExamineInitiateApiCount(examineInitiate));
        // 已通过未读数量
        examineInitiate.setExamineStatus(3);
        map.put("passExamineNoReadyCount", initiateService.selectExamineInitiateApiCount(examineInitiate));
        // 已驳回未读数量
        examineInitiate.setExamineStatus(4);
        map.put("refuseExamineNoReadyCount", initiateService.selectExamineInitiateApiCount(examineInitiate));
        return ok(map);
    }


    /**
     * 我发起的审批详情
     *
     * @param request
     * @param initiateId 审批发起ID
     * @return
     */
    @GetMapping("/initiateDetail")
    public ApiResult initiateDetail(HttpServletRequest request, @RequestParam(defaultValue = "0") String initiateId) {
        ExamineInitiate examineInitiate = initiateService.selectExamineInitiateById(initiateId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }
        // 1：请假 2：报销 3：合同 4：其他 5：补卡
        if (examineInitiate.getExamineModuleId() == 2) {
            List<ExamineInitiate> initiateList = initiateService.selectExamineInitiateApiList(ExamineInitiate.builder().parentId(examineInitiate.getExamineInitiateId()).build());
            initiateList.forEach((ExamineInitiate initiate) -> {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiate.getExamineInitiateId()).build());
                initiate.setFileList(fileList);
            });
            examineInitiate.setInitiateDetailList(initiateList);
        } else {
            if (examineInitiate.getExamineModuleId() == 3) {
                // 查询相对方信息
                List<ExamineContractCounterparty> counterpartyList = counterpartyService.selectExamineContractCounterpartyList(ExamineContractCounterparty.builder().examineInitiateId(initiateId).build());
                examineInitiate.setCounterpartyList(counterpartyList);
            }
            // 查询文件
            List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiateId).build());
            examineInitiate.setFileList(fileList);
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("examineInitiate", examineInitiate);
        // 将信息改为已读
        initiateService.updateExamineInitiate(ExamineInitiate.builder()
                .examineInitiateId(initiateId)
                .readStatus(1)
                .build());
        return ok(map);
    }

    /**
     * 查询抄送岗位列表
     *
     * @param examineModuleId 审批类型ID  1：请假 2：报销 3：合同 4：其他 5：补卡
     * @return
     */
    @GetMapping("/copyAccountList")
    public ApiResult copyAccountList(HttpServletRequest request, @RequestParam(defaultValue = "0") Long examineModuleId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询抄送人的信息
        List<ApiAccount> copyAccountList = new ArrayList<>();
        // 查询抄送岗位列表
        List<ExamineCopyFlow> copyFlowList = copyFlowService.selectApiExamineCopyFlowList(ExamineCopyFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(examineModuleId)
                .build());
        for (ExamineCopyFlow examineCopyFlow : copyFlowList) {
            List<ApiAccount> accountList = accountService.selectApiAccountListByPostIdAndDeptId(examineCopyFlow.getDeptId(), examineCopyFlow.getPostId());
            copyAccountList.addAll(accountList);
        }
        return ok(copyAccountList);
    }

    /**
     * 查询员工第一次发起的审批管理人员列表
     *
     * @param examineModuleId 审批类型ID  1：请假 2：报销 3：合同 4：其他 5：补卡
     * @return
     */
    @GetMapping("/examineFlowList")
    public ApiResult examineFlowList(HttpServletRequest request, @RequestParam(defaultValue = "0") Long examineModuleId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询审批流程
        ExamineFlow examineFlow = ExamineFlow.builder()
                .examineModuleId(examineModuleId)
                .companyId(account.getCompanyId())
                .build();
        // 查询员工发起的审批管理人员列表
        List<ExamineFlowResultVO> examineFlowResultVOList = flowService.selectStaffInitiateExaminePeopleList(examineFlow);
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
        List<Map<String, Object>> initiateList = flowAccountService.selectMyExamineList(ExamineInitiate.builder()
                .accountName(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .searchTimeTag(searchParamVO.getSearchTimeTag() == null ? 1 : searchParamVO.getSearchTimeTag())
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(initiateList);
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
        map.put("noExamineCount", flowAccountService.selectMyExamineCount(ExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(1)
                .build()));
        // 待审批未读的数量
        map.put("noExamineNoReadCount", flowAccountService.selectMyExamineCount(ExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(1)
                .readStatus(0)
                .build()));

        // 审批中数量
        map.put("examineCount", flowAccountService.selectMyExamineCount(ExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(2)
                .build()));
        // 审批中未读的数量
        map.put("examineNoReadCount", flowAccountService.selectMyExamineCount(ExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
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
     * @return
     */
    @GetMapping("/examineDetail")
    public ApiResult examineDetail(HttpServletRequest request, @RequestParam(defaultValue = "0") String flowAccountId) {
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> examineInitiate = flowAccountService.selectMyExamineDetail(accountId, flowAccountId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }
        // 1：请假 2：报销 3：合同 4：其他 5：补卡
        if (Convert.toInt(examineInitiate.get("examineModuleId")) == 2) {
            List<ExamineInitiate> initiateList = initiateService.selectExamineInitiateApiList(ExamineInitiate.builder().parentId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
            initiateList.forEach((ExamineInitiate initiate) -> {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiate.getExamineInitiateId()).build());
                initiate.setFileList(fileList);
            });
            examineInitiate.put("initiateDetailList", initiateList);
        } else {
            if (Convert.toInt(examineInitiate.get("examineModuleId")) == 3) {
                // 查询相对方信息
                List<ExamineContractCounterparty> counterpartyList = counterpartyService.selectExamineContractCounterpartyList(ExamineContractCounterparty.builder().examineInitiateId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
                examineInitiate.put("counterpartyList", counterpartyList);
            }
            // 查询文件
            List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
            examineInitiate.put("fileList", fileList);
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("examineInitiate", examineInitiate);
        // 将信息改为已读
        flowAccountService.updateExamineFlowAccount(ExamineFlowAccount.builder()
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
        Map<String, Object> map = flowAccountService.selectMyExamineDetail(accountId, examineSubVO.getFlowAccountId());
        if (map == null) {
            return fail("该详情不存在！");
        }
        // 查询同等审批的是否都通过，通过的情况下进行下一个审批
        // 查询同等级审批的相关信息
        List<ExamineFlowAccount> examineFlowAccountList = flowAccountService.selectExamineFlowAccountList(ExamineFlowAccount.builder()
                .examineInitiateId(map.get("examineInitiateId").toString())
                .examineSequence(Integer.parseInt(map.get("examineSequence").toString()))
                .examineTag(Convert.toStr(map.get("examineTag")))
                .build());
        for (ExamineFlowAccount examineFlowAccount : examineFlowAccountList) {
            // 查询最新的审批状态
            ExamineFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitExamineFlowAccount(ExamineFlowAccount.builder()
                    .accountId(examineFlowAccount.getAccountId())
                    .examineInitiateId(examineFlowAccount.getExamineInitiateId())
                    .examineSequence(examineFlowAccount.getExamineSequence())
                    .examineTag(Convert.toStr(map.get("examineTag")))
                    .build());
            if (examineFlowAccount1 != null && examineFlowAccount1.getExamineStatus() == 4) {
                return fail("该发起记录有审批人员已驳回，暂不能通过或拒绝，需等该条记录的发起者重新修改才能提交！");
            }
        }
        int count = flowAccountService.updateExamineFlowAccount(ExamineFlowAccount.builder()
                .examineFlowAccountId(examineSubVO.getFlowAccountId())
                .examineStatus(examineSubVO.getExamineStatus())
                .rejectContent(examineSubVO.getContent())
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
        ExamineInitiate examineInitiate = initiateService.selectExamineInitiateById(initiateId);
        if (examineInitiate == null) {
            return fail("该信息不存在！");
        }
        // 查询审批流程
        ExamineFlow examineFlow = ExamineFlow.builder()
                .examineModuleId(examineInitiate.getExamineModuleId())
                .companyId(examineInitiate.getCompanyId())
                .build();
        if(examineInitiate.getExamineModuleId() == 1){
            examineFlow.setMinValue(examineInitiate.getAskForLeaveHour().doubleValue());
        }
        if(examineInitiate.getExamineModuleId() == 2 || examineInitiate.getExamineModuleId() == 3){
            examineFlow.setMinValue(examineInitiate.getTotalMoney().doubleValue());
        }
        // 查询员工发起的审批管理人员列表
        List<ExamineFlowResultVO> examineFlowResultVOList = flowService.selectStaffInitiateExaminePeopleList(examineFlow, initiateId, examineInitiate.getExamineTag());
        return ok(examineFlowResultVOList);
    }


    /**
     * 审批流程人员列表(已驳回，已撤回，已抄送)
     *
     * @param examineTag 审批唯一标识
     * @return
     */
    @GetMapping("/disposeExamineFlowAccountList")
    public ApiResult disposeExamineFlowAccountList(@RequestParam(defaultValue = "0") String examineTag) {
        // 查询审批流程人员列表
        List<ExamineFlowAccount> examineFlowResultVOList = flowAccountService.selectNearExamineFlowAccountList(ExamineFlowAccount.builder().examineTag(examineTag).build());
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
        ExamineInitiate initiate = initiateService.selectExamineInitiateById(examineInitiateId);
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
        int count = initiateService.deleteExamineInitiateById(examineInitiateId);
        if (count <= 0) {
            return fail("删除失败！");
        }
        return okMsg("已删除！");
    }


    /**
     * 抄送我的列表
     *
     * @param request
     * @param pageParam     分页
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/copyMyExamineList")
    public ApiResult copyMyExamineList(HttpServletRequest request, PageParam pageParam, ExamineSearchParamVO searchParamVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> copyMyExamineList = flowCopyAccountService.selectMyCopyExamineList(ExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .searchTimeTag(searchParamVO.getSearchTimeTag() == null ? 1 : searchParamVO.getSearchTimeTag())
                .readStatus(searchParamVO.getReadStatus())
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(copyMyExamineList);
        return ok(pageInfo);
    }


    /**
     * 更新抄送列表的已读状态
     *
     * @param request
     * @return
     */
    @PostMapping("/updateReadStatusByCopyAccountById")
    public ApiResult updateReadStatusByCopyAccountById(HttpServletRequest request) {
        int count = flowCopyAccountService.updateReadStatusByCopyAccountById(tokenService.getAccountId(request));
        if (count <= 0) {
            return okMsg("标记失败！");
        }
        return okMsg("已标记！");
    }


    /**
     * 抄送我的审批详情
     *
     * @param request
     * @param flowCopyAccountId 抄送ID
     * @return
     */
    @GetMapping("/copyExamineDetail")
    public ApiResult copyExamineDetail(HttpServletRequest request, @RequestParam(defaultValue = "0") String flowCopyAccountId) {
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> examineInitiate = flowCopyAccountService.selectCopyMyExamineDetail(accountId, flowCopyAccountId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }
        // 1：请假 2：报销 3：合同 4：其他 5：补卡
        if (Convert.toInt(examineInitiate.get("examineModuleId")) == 2) {
            List<ExamineInitiate> initiateList = initiateService.selectExamineInitiateApiList(ExamineInitiate.builder().parentId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
            initiateList.forEach((ExamineInitiate initiate) -> {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiate.getExamineInitiateId()).build());
                initiate.setFileList(fileList);
            });
            examineInitiate.put("initiateDetailList", initiateList);
        } else {
            if (Convert.toInt(examineInitiate.get("examineModuleId")) == 3) {
                // 查询相对方信息
                List<ExamineContractCounterparty> counterpartyList = counterpartyService.selectExamineContractCounterpartyList(ExamineContractCounterparty.builder().examineInitiateId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
                examineInitiate.put("counterpartyList", counterpartyList);
            }
            // 查询文件
            List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
            examineInitiate.put("fileList", fileList);
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("examineInitiate", examineInitiate);
        // 将信息改为已读
        flowCopyAccountService.updateExamineFlowCopyAccount(ExamineFlowCopyAccount.builder()
                .examineFlowCopyAccountId(flowCopyAccountId)
                .readStatus(1)
                .build());
        return ok(map);
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
        Map<String, Object> map = new HashMap<>(3);
        // 我发起的未读的数量
        map.put("initiateNoReadCount", initiateService.selectExamineInitiateApiCount(ExamineInitiate.builder().readStatus(0).accountId(accountId).build()));
        // 我审批的未读的数量
        map.put("examineNoReadCount", flowAccountService.selectMyExamineCount(ExamineInitiate.builder().accountId(accountId).readStatus(0).build()));
        // 抄送我的未读的数量
        map.put("copyNoReadCount", flowCopyAccountService.selectExamineFlowCopyAccountCount(ExamineFlowCopyAccount.builder().copyAccountId(accountId).readStatus(0).build()));
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
        ExamineInitiate examineInitiate = initiateService.selectExamineInitiateById(initiateId);
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
        int count = flowAccountService.deleteExamineFlowAccountByInitiateId(initiateId);
        if (count <= 0) {
            throw new ApiException("删除审批人员失败！");
        }
        int result = initiateService.updateExamineInitiate(ExamineInitiate.builder()
                .examineInitiateId(initiateId)
                .examineStatus(5)
                .build());
        if (result <= 0) {
            throw new ApiException("撤回失败！");
        }
        return okMsg("已撤回！");
    }


    /**
     * 修改 请假提交（只有待审批的和已拒绝和已撤回的才能修改提交）
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editLeaveSub")
    public ApiResult editLeaveSub(HttpServletRequest request, @RequestBody @Validated LeaveParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        ExamineInitiate initiate = initiateService.selectExamineInitiateById(params.getExamineInitiateId());
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
        // 0:直接走后台设置的审批流 1：先部门领导审批，然后再走后台设置的审批流
        ApiAccount deptAccount = null;
        Integer examineFlowTag = 0;
        // 第一级审批都是自己部门的领导审批，如果是本部门领导提交,进入审批流程
        // 查询本部门的领导
        ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(account.getDeptId()).positionType("1").build());
        if(apiAccount != null){
            examineFlowTag = 1;
            deptAccount = apiAccount;
        }
        if(examineFlowTag == 0){
            // 查询用户所在的部门的某一各类型是否添加审批流程
            ExamineFlow examineFlow = flowService.selectExamineFlow(ExamineFlow.builder()
                    .companyId(account.getCompanyId())
                    .examineModuleId(1L)
                    .build());
            if (examineFlow == null) {
                return fail("该部门下该模块没有添加审批流程，请联系管理员！");
            }
        }
        ExamineInitiate examineInitiate = ExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        // // 默认声明状态为待审批
        examineInitiate.setExamineStatus(1);
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
        int count = initiateService.updateExamineInitiate(examineInitiate);
        if (count <= 0) {
            throw new ApiException("更新失败");
        }
        if(examineFlowTag == 1 && deptAccount != null){
            // 部门领导审批
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

        }
        if(examineFlowTag == 0){
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            ExamineFlow examineFlow1 = ExamineFlow.builder()
                    .examineModuleId(1L)
                    .companyId(account.getCompanyId())
                    .minValue(params.getAskForLeaveHour())
                    .build();
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
        }
        return okMsg("提交成功,请耐心等待审核！");
    }


    /**
     * 修改 报销提交 （只有待审批的和已拒绝和已撤回的才能修改提交）
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editExpenseSub")
    public ApiResult editExpenseSub(HttpServletRequest request, @RequestBody @Validated ExpenseParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        ExamineInitiate initiate = initiateService.selectExamineInitiateById(params.getExamineInitiateId());
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
        // 查询用户所在的公司的某一各类型是否添加审批流程
        ExamineFlow examineFlow = flowService.selectExamineFlow(ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(2L)
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 异步操作
        AsyncManager.me().execute(taskExamineFlowAccountService.editExpense(params, status, account));
        return okMsg("提交成功,请耐心等待审核！");
    }


    /**
     * 修改 合同提交 （只有待审批的和已拒绝和已撤回的才能修改提交）
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editContractSub")
    public ApiResult editContractSub(HttpServletRequest request, @RequestBody @Validated ContractParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        ExamineInitiate initiate = initiateService.selectExamineInitiateById(params.getExamineInitiateId());
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
        ExamineFlow examineFlow = flowService.selectExamineFlow(ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(3L)
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 异步操作
        AsyncManager.me().execute(taskExamineFlowAccountService.editContract(params, status, account));
        return okMsg("提交成功,请耐心等待审核！");
    }

    /**
     * 修改 其他提交 （只有待审批的和已拒绝和已撤回的才能修改提交）
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editOtherSub")
    public ApiResult editOtherSub(HttpServletRequest request, @RequestBody @Validated OtherParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        ExamineInitiate initiate = initiateService.selectExamineInitiateById(params.getExamineInitiateId());
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
        ExamineFlow examineFlow = flowService.selectExamineFlow(ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(4L)
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        ExamineInitiate examineInitiate = ExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        examineInitiate.setExamineInitiateId(params.getExamineInitiateId());
        examineInitiate.setExamineStatus(1);
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
        int count = initiateService.updateExamineInitiate(examineInitiate);
        if (count <= 0) {
            throw new ApiException("提交失败");
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
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            ExamineFlow examineFlow1 = ExamineFlow.builder()
                    .examineModuleId(4L)
                    .companyId(account.getCompanyId())
                    .build();
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
        }
        return okMsg("提交成功,请耐心等待审核！");
    }

    @PassToken
    @GetMapping("/get")
    public ApiResult get() {
        List<Map<String, Object>> list = initiateService.selectList();
        return ok(list);
    }
}
