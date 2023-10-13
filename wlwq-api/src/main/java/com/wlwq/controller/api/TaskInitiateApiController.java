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
import com.wlwq.params.examine.LeaveParamVO;
import com.wlwq.params.examine.OtherParamVO;
import com.wlwq.params.task.TaskFinishParamVO;
import com.wlwq.params.task.TaskFinishPassParamVO;
import com.wlwq.params.task.TaskOverdueParamVO;
import com.wlwq.params.task.TaskOverduePassParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.wlwq.common.apiResult.ApiResult.okMsg;

/**
 * 任务
 *
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/taskInitiate")
@AllArgsConstructor
public class TaskInitiateApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ITaskInitiateService initiateService;

    private final ITaskFileService fileService;

    private final ITaskReceiveService receiveService;

    private final IExamineFlowService flowService;

    private final TaskService taskService;

    private final ITaskFlowAccountService flowAccountService;

    /**
     * 任务创建提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/create")
    public ApiResult create(HttpServletRequest request, @RequestBody @Validated TaskInitiate params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        params.setAccountId(account.getAccountId());
        params.setAccountName(account.getNickName());
        params.setAccountPhone(account.getPhone());
        params.setAccountHead(account.getHeadPortrait());
        params.setDeptId(account.getDeptId());
        params.setCompanyId(account.getCompanyId());
        int count = initiateService.insertTaskInitiate(params);
        if (count <= 0) {
            return ApiResult.fail("创建失败！");
        }
        // 判断是否有文件，有的话将文件存储起来
        fileSave(params.getFileList(), params.getTaskInitiateId());
        // 异步请求
        AsyncManager.me().execute(taskService.message(params));
        return okMsg("提交成功！");
    }


    /**
     * 待接收的任务列表
     *
     * @param request
     * @param keyword
     * @return
     */
    @GetMapping("/waitReceivingTaskList")
    public ApiResult waitReceivingTaskList(HttpServletRequest request, PageParam pageParam, String keyword) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<TaskInitiate> initiateList = initiateService.selectApiTaskInitiateList(TaskInitiate.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskTitle(keyword)
                .build());
        PageInfo<TaskInitiate> pageInfo = new PageInfo<>(initiateList);
        return ok(pageInfo);
    }

    /**
     * 待接收任务详情
     *
     * @param request
     * @param taskInitiateId 任务ID
     * @return
     */
    @GetMapping("/waitReceivingTaskDetail")
    public ApiResult waitReceivingTaskDetail(HttpServletRequest request, @RequestParam(defaultValue = "0") String taskInitiateId) {
        // 查询详情
        TaskInitiate initiate = initiateService.selectApiTaskInitiateDetail(TaskInitiate.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskInitiateId(taskInitiateId)
                .build());
        if (initiate == null) {
            return ApiResult.fail("该详情不存在！");
        }
        // 查询文件列表
        initiate.setFileList(fileService.selectTaskFileList(TaskFile.builder().taskInitiateId(taskInitiateId).build()));
        return ok(initiate);
    }


    /**
     * 点击 接收任务
     *
     * @param request
     * @param taskInitiateId 任务ID
     * @return
     */
    @RepeatSubmit
    @PostMapping("/receiving")
    public ApiResult receiving(HttpServletRequest request, @RequestParam(defaultValue = "0") String taskInitiateId) {
        TaskInitiate taskInitiate = initiateService.selectTaskInitiateById(taskInitiateId);
        if (taskInitiate == null) {
            return ApiResult.fail("该任务不存在!");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        int count = receiveService.insertTaskReceive(TaskReceive.builder()
                .taskInitiateId(taskInitiateId)
                .accountId(account.getAccountId())
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .deptId(account.getDeptId())
                .companyId(account.getCompanyId())
                .taskStartTime(taskInitiate.getTaskStartTime())
                .taskEndTime(taskInitiate.getTaskEndTime())
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build());
        if (count <= 0) {
            return ApiResult.fail("接收失败！");
        }
        return okMsg("成功！");
    }


    /**
     * 已接收任务列表/已逾期任务列表/已完成任务列表
     *
     * @param request
     * @param keyword 关键字搜索
     * @param status  0：查询已接收的 1：查询已逾期的 2：查询已完成的
     * @return
     */
    @GetMapping("/taskList")
    public ApiResult taskList(HttpServletRequest request,
                              PageParam pageParam,
                              @RequestParam(defaultValue = "0") Integer status,
                              String keyword) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<TaskInitiate> initiateList = initiateService.selectApiTaskReceiveList(TaskInitiate.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskTitle(keyword)
                .tag(status)
                .build());
        PageInfo<TaskInitiate> pageInfo = new PageInfo<>(initiateList);
        return ok(pageInfo);
    }

    /**
     * 已接收任务详情/已逾期任务详情/已完成任务详情
     *
     * @param request
     * @param taskReceiveId  任务接收ID
     * @param taskInitiateId 任务发起ID
     * @return
     */
    @GetMapping("/taskDetail")
    public ApiResult taskDetail(HttpServletRequest request,
                                @RequestParam(defaultValue = "0") String taskReceiveId,
                                @RequestParam(defaultValue = "0") String taskInitiateId) {
        // 查询发起详情
        TaskInitiate initiate = initiateService.selectApiTaskInitiateDetail(TaskInitiate.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskInitiateId(taskInitiateId)
                .build());
        if (initiate == null) {
            return ApiResult.fail("该详情不存在！");
        }
        // 查询文件列表
        List<TaskFile> fileList = fileService.selectTaskFileList(TaskFile.builder().taskInitiateId(taskInitiateId).build());
        initiate.setFileList(fileList);
        Map<String, Object> map = new HashMap<>(3);
        map.put("initiate", initiate);
        // 接收详情
        map.put("receive", receiveService.selectTaskReceiveById(taskReceiveId));
        return ok(map);
    }


    /**
     * 任务延期提交
     *
     * @param request
     * @param params  相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/overdueSub")
    public ApiResult overdueSub(HttpServletRequest request, @Validated TaskOverdueParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(params.getTaskReceiveId());
        if (taskReceive == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(26L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 更新审批状态
        TaskReceive taskReceive1 = TaskReceive.builder()
                .taskReceiveId(params.getTaskReceiveId())
                .remark(params.getRemark())
                .taskOverdueTime(params.getTaskOverdueTime())
                .examineStatus(1)
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = receiveService.updateTaskReceive(taskReceive1);
        if (count <= 0) {
            return fail("更新审批状态失败！");
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
        if(examineFlowTag == 1 && deptAccount != null){
            // 部门领导审批
            AsyncManager.me().execute(taskService.overdueFlowDeptAccount(deptAccount, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));

        }
        if(examineFlowTag == 0){
            // 异步请求
            AsyncManager.me().execute(taskService.overdueFlowAccount(examineFlow1, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));
        }
        return okMsg("提交成功，请耐心等待审核！");
    }

    /**
     * 任务延期审核通过/拒绝
     *
     * @param params 相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/overduePassSub")
    public ApiResult overdueSub(@Validated TaskOverduePassParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        TaskFlowAccount flowAccount = flowAccountService.selectTaskFlowAccountById(params.getTaskFlowAccountId());
        if (flowAccount == null) {
            return ApiResult.fail("该延期的任务不存在!");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(flowAccount.getTaskReceiveId());
        if (flowAccount == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        TaskInitiate taskInitiate = initiateService.selectTaskInitiateById(taskReceive.getTaskInitiateId());
        if (taskInitiate == null) {
            return ApiResult.fail("该发起的任务不存在!");
        }
        // 查询同等审批的是否都通过，通过的情况下进行下一个审批
        // 查询同等级审批的相关信息
        List<TaskFlowAccount> taskFlowAccountList = flowAccountService.selectTaskFlowAccountList(TaskFlowAccount.builder()
                .taskReceiveId(flowAccount.getTaskReceiveId())
                .examineType(1)
                .examineSequence(flowAccount.getExamineSequence())
                .examineTag(flowAccount.getExamineTag())
                .build());
        for (TaskFlowAccount taskFlowAccount : taskFlowAccountList) {
            // 查询最新的审批状态
            TaskFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                    .accountId(taskFlowAccount.getAccountId())
                    .taskReceiveId(taskFlowAccount.getTaskReceiveId())
                    .examineType(1)
                    .examineSequence(taskFlowAccount.getExamineSequence())
                    .examineTag(flowAccount.getExamineTag())
                    .build());
            if (examineFlowAccount1 != null && examineFlowAccount1.getExamineStatus() == 4) {
                return fail("该发起记录有审批人员已驳回，暂不能通过或拒绝，需等该条记录的发起者重新修改才能提交！");
            }
        }
        int count = flowAccountService.updateTaskFlowAccount(TaskFlowAccount.builder()
                .taskFlowAccountId(params.getTaskFlowAccountId())
                .examineStatus(params.getExamineStatus())
                .rejectContent(params.getContent())
                .pics(params.getPics())
                .readStatus(1)
                .build());
        if (count <= 0) {
            return fail("审批失败！");
        }
        // 异步操作
        AsyncManager.me().execute(taskService.editTaskReceive(params, flowAccount, taskInitiate));
        return okMsg("提交成功");
    }

    /**
     * 任务延期 提交撤回（只有在待审批的状态下才能撤回）
     *
     * @param request
     * @param taskReceiveId 任务接收ID
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/overdueRevocation")
    public ApiResult overdueRevocation(HttpServletRequest request, @RequestParam(defaultValue = "0") String taskReceiveId) {
        String accountId = tokenService.getAccountId(request);
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(taskReceiveId);
        if (taskReceive == null) {
            return fail("该详情不存在！");
        }
        if (!accountId.equals(taskReceive.getAccountId())) {
            return fail("您接收的详情不存在！");
        }
        if (taskReceive.getExamineStatus() != 1) {
            return fail("只有在待审批的状态下才能撤回！");
        }
        // 查询申批人员表，将其删除
        int count = flowAccountService.deleteTaskFlowAccountByReceiveId(taskReceiveId, 1);
        if (count <= 0) {
            throw new ApiException("删除审批人员失败！");
        }
        int result = receiveService.updateTaskReceive(TaskReceive.builder()
                .taskReceiveId(taskReceiveId)
                .examineStatus(5)
                .build());
        if (result <= 0) {
            throw new ApiException("撤回失败！");
        }
        return okMsg("已撤回！");
    }

    /**
     * 修改任务延期提交
     *
     * @param request
     * @param params  相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/editOverdueSub")
    public ApiResult editOverdueSub(HttpServletRequest request, @Validated TaskOverdueParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(params.getTaskReceiveId());
        if (taskReceive == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        // 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
        Integer status = taskReceive.getExamineStatus();
        if (status != 1 && status != 4 && status != 5) {
            return fail("只有在待审批或者已驳回或者已撤回的状态下才能修改！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(26L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 更新审批状态
        TaskReceive taskReceive1 = TaskReceive.builder()
                .taskReceiveId(params.getTaskReceiveId())
                .remark(params.getRemark())
                .taskOverdueTime(params.getTaskOverdueTime())
                .examineStatus(1)
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = receiveService.updateTaskReceive(taskReceive1);
        if (count <= 0) {
            return fail("更新审批状态失败！");
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
        if(examineFlowTag == 1 && deptAccount != null){
            // 部门领导审批
            AsyncManager.me().execute(taskService.overdueFlowDeptAccount(deptAccount, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));

        }
        if(examineFlowTag == 0){
            // 将审批流程的第一级人员信息存到记录表中
            // 异步请求
            AsyncManager.me().execute(taskService.overdueFlowAccount(examineFlow1, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));
        }
        return okMsg("提交成功，请耐心等待审核！");
    }


    /**
     * 任务完成提交
     *
     * @param request
     * @param params  相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/finishSub")
    public ApiResult finishSub(HttpServletRequest request, @Validated TaskFinishParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(params.getTaskReceiveId());
        if (taskReceive == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(27L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 更新审批状态
        TaskReceive taskReceive1 = TaskReceive.builder()
                .taskReceiveId(params.getTaskReceiveId())
                .remark(params.getRemark())
                .examineStatus(6)
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = receiveService.updateTaskReceive(taskReceive1);
        if (count <= 0) {
            return fail("更新审批状态失败！");
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
            // 异步请求
            AsyncManager.me().execute(taskService.finishFlowDeptAccount(deptAccount, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 异步请求
            AsyncManager.me().execute(taskService.finishFlowAccount(examineFlow1, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));
        }

        return okMsg("提交成功，请耐心等待审核！");
    }

    /**
     * 任务完成审核通过/拒绝
     *
     * @param params 相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/finishPassSub")
    public ApiResult finishPassSub(@Validated TaskFinishPassParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        TaskFlowAccount flowAccount = flowAccountService.selectTaskFlowAccountById(params.getTaskFlowAccountId());
        if (flowAccount == null) {
            return ApiResult.fail("该延期的任务不存在!");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(flowAccount.getTaskReceiveId());
        if (flowAccount == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        TaskInitiate taskInitiate = initiateService.selectTaskInitiateById(taskReceive.getTaskInitiateId());
        if (taskInitiate == null) {
            return ApiResult.fail("该发起的任务不存在!");
        }
        // 查询同等审批的是否都通过，通过的情况下进行下一个审批
        // 查询同等级审批的相关信息
        List<TaskFlowAccount> taskFlowAccountList = flowAccountService.selectTaskFlowAccountList(TaskFlowAccount.builder()
                .taskReceiveId(flowAccount.getTaskReceiveId())
                .examineType(2)
                .examineSequence(flowAccount.getExamineSequence())
                .examineTag(flowAccount.getExamineTag())
                .build());
        for (TaskFlowAccount taskFlowAccount : taskFlowAccountList) {
            // 查询最新的审批状态
            TaskFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                    .accountId(taskFlowAccount.getAccountId())
                    .taskReceiveId(taskFlowAccount.getTaskReceiveId())
                    .examineType(2)
                    .examineSequence(taskFlowAccount.getExamineSequence())
                    .examineTag(flowAccount.getExamineTag())
                    .build());
            if (examineFlowAccount1 != null && examineFlowAccount1.getExamineStatus() == 9) {
                return fail("该发起记录有审批人员已驳回，暂不能通过或拒绝，需等该条记录的发起者重新修改才能提交！");
            }
        }
        int count = flowAccountService.updateTaskFlowAccount(TaskFlowAccount.builder()
                .taskFlowAccountId(params.getTaskFlowAccountId())
                .examineStatus(params.getExamineStatus())
                .rejectContent(params.getContent())
                .pics(params.getPics())
                .readStatus(1)
                .build());
        if (count <= 0) {
            return fail("审批失败！");
        }
        // 异步操作
        AsyncManager.me().execute(taskService.editFinishTaskReceive(params, flowAccount, taskInitiate));
        return okMsg("提交成功");
    }

    /**
     * 任务完成 提交撤回（只有在待审批的状态下才能撤回）
     *
     * @param request
     * @param taskReceiveId 任务接收ID
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/finishRevocation")
    public ApiResult finishRevocation(HttpServletRequest request, @RequestParam(defaultValue = "0") String taskReceiveId) {
        String accountId = tokenService.getAccountId(request);
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(taskReceiveId);
        if (taskReceive == null) {
            return fail("该详情不存在！");
        }
        if (!accountId.equals(taskReceive.getAccountId())) {
            return fail("您接收的详情不存在！");
        }
        if (taskReceive.getExamineStatus() != 6) {
            return fail("只有在待审批的状态下才能撤回！");
        }
        // 查询申批人员表，将其删除
        int count = flowAccountService.deleteTaskFlowAccountByReceiveId(taskReceiveId, 2);
        if (count <= 0) {
            throw new ApiException("删除审批人员失败！");
        }
        int result = receiveService.updateTaskReceive(TaskReceive.builder()
                .taskReceiveId(taskReceiveId)
                .examineStatus(10)
                .build());
        if (result <= 0) {
            throw new ApiException("撤回失败！");
        }
        return okMsg("已撤回！");
    }


    /**
     * 修改任务完成提交
     *
     * @param request
     * @param params  相关参数
     * @return
     */
    @PostMapping("/editFinishSub")
    public ApiResult editFinishSub(HttpServletRequest request, @Validated TaskFinishParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(params.getTaskReceiveId());
        if (taskReceive == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        // 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
        Integer status = taskReceive.getExamineStatus();
        if (status != 6 && status != 9 && status != 10) {
            return fail("只有在待审批或者已驳回或者已撤回的状态下才能修改！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(27L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 更新审批状态
        TaskReceive taskReceive1 = TaskReceive.builder()
                .taskReceiveId(params.getTaskReceiveId())
                .remark(params.getRemark())
                .examineStatus(6)
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = receiveService.updateTaskReceive(taskReceive1);
        if (count <= 0) {
            return fail("更新审批状态失败！");
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
            // 异步请求
            AsyncManager.me().execute(taskService.finishFlowDeptAccount(deptAccount, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 异步请求
            AsyncManager.me().execute(taskService.finishFlowAccount(examineFlow1, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));
        }
        return okMsg("提交成功，请耐心等待审核！");
    }


    /**
     * 我审核的审核列表
     *
     * @param request
     * @param keyword 关键字搜索
     * @param status  0:待审核 1：已通过 2：已驳回
     * @return
     */
    @GetMapping("/myAuditList")
    public ApiResult myAuditList(HttpServletRequest request,
                                 PageParam pageParam,
                                 @RequestParam(defaultValue = "0") Integer status,
                                 String keyword) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> initiateList = flowAccountService.selectApiTaskFlowAccountList(TaskFlowAccount.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskTitle(keyword)
                .tag(status)
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(initiateList);
        return ok(pageInfo);
    }


    /**
     * 我审核的审核详情
     *
     * @param request
     * @param taskFlowAccountId 用户审批流程ID
     * @return
     */
    @GetMapping("/myAuditDetail")
    public ApiResult myAuditDetail(HttpServletRequest request,
                                   @RequestParam(defaultValue = "0") String taskFlowAccountId) {
        // 查询详情
        Map<String, Object> detail = flowAccountService.selectApiTaskFlowAccountDetail(TaskFlowAccount.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskFlowAccountId(taskFlowAccountId)
                .build());
        if(detail == null){
            return ApiResult.fail("该详情不存在！");
        }
        // 查询文件列表
        List<TaskFile> fileList = fileService.selectTaskFileList(TaskFile.builder().taskInitiateId(Convert.toStr(detail.get("taskInitiateId"))).build());
        detail.put("fileList",fileList);
        return ok(detail);
    }

    /**
     * 我的任务的相关数量
     *
     * @param request
     * @param keyword 关键词搜索
     * @return
     */
    @GetMapping("/myTaskCount")
    public ApiResult myTaskCount(HttpServletRequest request, String keyword) {
        Map<String, Object> map = new HashMap<>(5);
        // 待接收数量
        map.put("noReceiveCount", initiateService.selectApiTaskInitiateCount(TaskInitiate.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskTitle(keyword)
                .build()));
        // 已接收数量
        map.put("receiveCount", initiateService.selectApiTaskReceiveCount(TaskInitiate.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskTitle(keyword)
                .tag(0)
                .build()));
        // 已逾期数量
        map.put("overdueCount", initiateService.selectApiTaskReceiveCount(TaskInitiate.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskTitle(keyword)
                .tag(1)
                .build()));
        // 已完成数量
        map.put("finishCount", initiateService.selectApiTaskReceiveCount(TaskInitiate.builder()
                .accountId(tokenService.getNoAccountId(request))
                .taskTitle(keyword)
                .tag(2)
                .build()));
        return ok(map);
    }

    /**
     * 任务审批流程人员列表
     *
     * @param taskReceiveId 审批接收ID
     * @param status        1:延期审批 2:完成审批
     * @return
     */
    @PassToken
    @GetMapping("/taskFlowAccountList")
    public ApiResult taskFlowAccountList(@RequestParam(defaultValue = "0") String taskReceiveId,
                                         @RequestParam(defaultValue = "1") Integer status) {
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(taskReceiveId);
        if (taskReceive == null) {
            return fail("该信息不存在！");
        }
        TaskInitiate taskInitiate = initiateService.selectTaskInitiateById(taskReceive.getTaskInitiateId());
        if (taskInitiate == null) {
            return ApiResult.fail("该发起的任务不存在!");
        }
        // 查询审批流程
        ExamineFlow examineFlow = ExamineFlow.builder()
                .examineModuleId(status == 1 ? 26L : 27L)
                .companyId(taskReceive.getCompanyId())
                .minValue(taskInitiate.getTaskScore().doubleValue())
                .build();
        // 查询员工发起的审批管理人员列表
        List<ExamineFlowResultVO> examineFlowResultVOList = flowService.selectTaskExaminePeopleList(examineFlow,taskInitiate, taskReceiveId, status);
        return ok(examineFlowResultVOList);
    }

    /**
     * 逾期任务完成提交
     *
     * @param request
     * @param params  相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/overdueFinishSub")
    public ApiResult overdueFinishSub(HttpServletRequest request, @Validated TaskFinishParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(params.getTaskReceiveId());
        if (taskReceive == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(27L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 更新审批状态
        TaskReceive taskReceive1 = TaskReceive.builder()
                .taskReceiveId(params.getTaskReceiveId())
                .remark(params.getRemark())
                .examineStatus(11)
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = receiveService.updateTaskReceive(taskReceive1);
        if (count <= 0) {
            return fail("更新审批状态失败！");
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
            AsyncManager.me().execute(taskService.overdueFinishFlowDeptAccount(deptAccount, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 异步请求
            AsyncManager.me().execute(taskService.overdueFinishFlowAccount(examineFlow1, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));
        }

        return okMsg("提交成功，请耐心等待审核！");
    }


    /**
     * 逾期任务完成审核通过/拒绝
     *
     * @param params 相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/overdueFinishPassSub")
    public ApiResult overdueFinishPassSub(@Validated TaskFinishPassParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        TaskFlowAccount flowAccount = flowAccountService.selectTaskFlowAccountById(params.getTaskFlowAccountId());
        if (flowAccount == null) {
            return ApiResult.fail("该逾期的任务不存在!");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(flowAccount.getTaskReceiveId());
        if (flowAccount == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        TaskInitiate taskInitiate = initiateService.selectTaskInitiateById(taskReceive.getTaskInitiateId());
        if (taskInitiate == null) {
            return ApiResult.fail("该发起的任务不存在!");
        }
        // 查询同等审批的是否都通过，通过的情况下进行下一个审批
        // 查询同等级审批的相关信息
        List<TaskFlowAccount> taskFlowAccountList = flowAccountService.selectTaskFlowAccountList(TaskFlowAccount.builder()
                .taskReceiveId(flowAccount.getTaskReceiveId())
                .examineType(2)
                .examineSequence(flowAccount.getExamineSequence())
                .examineTag(flowAccount.getExamineTag())
                .build());
        for (TaskFlowAccount taskFlowAccount : taskFlowAccountList) {
            // 查询最新的审批状态
            TaskFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                    .accountId(taskFlowAccount.getAccountId())
                    .taskReceiveId(taskFlowAccount.getTaskReceiveId())
                    .examineType(2)
                    .examineSequence(taskFlowAccount.getExamineSequence())
                    .examineTag(flowAccount.getExamineTag())
                    .build());
            if (examineFlowAccount1 != null && examineFlowAccount1.getExamineStatus() == 13) {
                return fail("该发起记录有审批人员已驳回，暂不能通过或拒绝，需等该条记录的发起者重新修改才能提交！");
            }
        }
        int count = flowAccountService.updateTaskFlowAccount(TaskFlowAccount.builder()
                .taskFlowAccountId(params.getTaskFlowAccountId())
                .examineStatus(params.getExamineStatus())
                .rejectContent(params.getContent())
                .pics(params.getPics())
                .readStatus(1)
                .build());
        if (count <= 0) {
            return fail("审批失败！");
        }
        // 异步操作
        AsyncManager.me().execute(taskService.editOverdueFinishTaskReceive(params, flowAccount, taskInitiate));
        return okMsg("提交成功");
    }


    /**
     * 逾期任务完成 提交撤回（只有在待审批的状态下才能撤回）
     *
     * @param request
     * @param taskReceiveId 任务接收ID
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/overdueFinishRevocation")
    public ApiResult overdueFinishRevocation(HttpServletRequest request, @RequestParam(defaultValue = "0") String taskReceiveId) {
        String accountId = tokenService.getAccountId(request);
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(taskReceiveId);
        if (taskReceive == null) {
            return fail("该详情不存在！");
        }
        if (!accountId.equals(taskReceive.getAccountId())) {
            return fail("您接收的详情不存在！");
        }
        if (taskReceive.getExamineStatus() != 11) {
            return fail("只有在待审批的状态下才能撤回！");
        }
        // 查询申批人员表，将其删除
        int count = flowAccountService.deleteTaskFlowAccountByReceiveId(taskReceiveId, 2);
        if (count <= 0) {
            throw new ApiException("删除审批人员失败！");
        }
        int result = receiveService.updateTaskReceive(TaskReceive.builder()
                .taskReceiveId(taskReceiveId)
                .examineStatus(13)
                .build());
        if (result <= 0) {
            throw new ApiException("撤回失败！");
        }
        return okMsg("已撤回！");
    }

    /**
     * 修改逾期任务完成提交
     *
     * @param request
     * @param params  相关参数
     * @return
     */
    @PostMapping("/editOverdueFinishSub")
    public ApiResult editOverdueFinishSub(HttpServletRequest request, @Validated TaskFinishParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(params.getTaskReceiveId());
        if (taskReceive == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        // 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
        Integer status = taskReceive.getExamineStatus();
        if (status != 11 && status != 13 && status != 15) {
            return fail("只有在待审批或者已驳回或者已撤回的状态下才能修改！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(27L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 更新审批状态
        TaskReceive taskReceive1 = TaskReceive.builder()
                .taskReceiveId(params.getTaskReceiveId())
                .remark(params.getRemark())
                .examineStatus(11)
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = receiveService.updateTaskReceive(taskReceive1);
        if (count <= 0) {
            return fail("更新审批状态失败！");
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
            AsyncManager.me().execute(taskService.overdueFinishFlowDeptAccount(deptAccount, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步请求
            AsyncManager.me().execute(taskService.overdueFinishFlowAccount(examineFlow1, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));
        }
        return okMsg("提交成功，请耐心等待审核！");
    }


    /**
     * 延期任务完成提交
     *
     * @param request
     * @param params  相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/postponeFinishSub")
    public ApiResult postponeFinishSub(HttpServletRequest request, @Validated TaskFinishParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(params.getTaskReceiveId());
        if (taskReceive == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(27L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 更新审批状态
        TaskReceive taskReceive1 = TaskReceive.builder()
                .taskReceiveId(params.getTaskReceiveId())
                .remark(params.getRemark())
                .examineStatus(16)
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = receiveService.updateTaskReceive(taskReceive1);
        if (count <= 0) {
            return fail("更新审批状态失败！");
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
            AsyncManager.me().execute(taskService.postponeFinishFlowDeptAccount(deptAccount, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 异步请求
            AsyncManager.me().execute(taskService.postponeFinishFlowAccount(examineFlow1, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));
        }
        return okMsg("提交成功，请耐心等待审核！");
    }


    /**
     * 延期任务完成审核通过/拒绝
     *
     * @param params 相关参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/postponeFinishPassSub")
    public ApiResult postponeFinishPassSub(@Validated TaskFinishPassParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        TaskFlowAccount flowAccount = flowAccountService.selectTaskFlowAccountById(params.getTaskFlowAccountId());
        if (flowAccount == null) {
            return ApiResult.fail("该延期的任务不存在!");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(flowAccount.getTaskReceiveId());
        if (flowAccount == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        TaskInitiate taskInitiate = initiateService.selectTaskInitiateById(taskReceive.getTaskInitiateId());
        if (taskInitiate == null) {
            return ApiResult.fail("该发起的任务不存在!");
        }
        // 查询同等审批的是否都通过，通过的情况下进行下一个审批
        // 查询同等级审批的相关信息
        List<TaskFlowAccount> taskFlowAccountList = flowAccountService.selectTaskFlowAccountList(TaskFlowAccount.builder()
                .taskReceiveId(flowAccount.getTaskReceiveId())
                .examineType(2)
                .examineSequence(flowAccount.getExamineSequence())
                .examineTag(flowAccount.getExamineTag())
                .build());
        for (TaskFlowAccount taskFlowAccount : taskFlowAccountList) {
            // 查询最新的审批状态
            TaskFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                    .accountId(taskFlowAccount.getAccountId())
                    .taskReceiveId(taskFlowAccount.getTaskReceiveId())
                    .examineType(2)
                    .examineSequence(taskFlowAccount.getExamineSequence())
                    .examineTag(flowAccount.getExamineTag())
                    .build());
            if (examineFlowAccount1 != null && examineFlowAccount1.getExamineStatus() == 18) {
                return fail("该发起记录有审批人员已驳回，暂不能通过或拒绝，需等该条记录的发起者重新修改才能提交！");
            }
        }
        int count = flowAccountService.updateTaskFlowAccount(TaskFlowAccount.builder()
                .taskFlowAccountId(params.getTaskFlowAccountId())
                .examineStatus(params.getExamineStatus())
                .rejectContent(params.getContent())
                .pics(params.getPics())
                .readStatus(1)
                .build());
        if (count <= 0) {
            return fail("审批失败！");
        }
        // 异步操作
        AsyncManager.me().execute(taskService.editPostponeFinishTaskReceive(params, flowAccount, taskInitiate));
        return okMsg("提交成功");
    }

    /**
     * 延期任务完成 提交撤回（只有在待审批的状态下才能撤回）
     *
     * @param request
     * @param taskReceiveId 任务接收ID
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/postponeFinishRevocation")
    public ApiResult postponeFinishRevocation(HttpServletRequest request, @RequestParam(defaultValue = "0") String taskReceiveId) {
        String accountId = tokenService.getAccountId(request);
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(taskReceiveId);
        if (taskReceive == null) {
            return fail("该详情不存在！");
        }
        if (!accountId.equals(taskReceive.getAccountId())) {
            return fail("您接收的详情不存在！");
        }
        if (taskReceive.getExamineStatus() != 16) {
            return fail("只有在待审批的状态下才能撤回！");
        }
        // 查询申批人员表，将其删除
        int count = flowAccountService.deleteTaskFlowAccountByReceiveId(taskReceiveId, 2);
        if (count <= 0) {
            throw new ApiException("删除审批人员失败！");
        }
        int result = receiveService.updateTaskReceive(TaskReceive.builder()
                .taskReceiveId(taskReceiveId)
                .examineStatus(19)
                .build());
        if (result <= 0) {
            throw new ApiException("撤回失败！");
        }
        return okMsg("已撤回！");
    }

    /**
     * 修改延期任务完成提交
     *
     * @param request
     * @param params  相关参数
     * @return
     */
    @PostMapping("/editPostponeFinishSub")
    public ApiResult editPostponeFinishSub(HttpServletRequest request, @Validated TaskFinishParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        TaskReceive taskReceive = receiveService.selectTaskReceiveById(params.getTaskReceiveId());
        if (taskReceive == null) {
            return ApiResult.fail("该接收的任务不存在!");
        }
        // 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
        Integer status = taskReceive.getExamineStatus();
        if (status != 16 && status != 18 && status != 19) {
            return fail("只有在待审批或者已驳回或者已撤回的状态下才能修改！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(27L)
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 更新审批状态
        TaskReceive taskReceive1 = TaskReceive.builder()
                .taskReceiveId(params.getTaskReceiveId())
                .remark(params.getRemark())
                .examineStatus(16)
                .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                .build();
        int count = receiveService.updateTaskReceive(taskReceive1);
        if (count <= 0) {
            return fail("更新审批状态失败！");
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
            AsyncManager.me().execute(taskService.postponeFinishFlowDeptAccount(deptAccount, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            AsyncManager.me().execute(taskService.postponeFinishFlowAccount(examineFlow1, taskReceive.getTaskReceiveId(), taskReceive1.getExamineTag()));
        }
        return okMsg("提交成功，请耐心等待审核！");
    }

    /**
     * 审批流程人员列表(进行中和未开始的除外)
     *
     * @param examineTag  审批唯一标识
     * @param examineType 1:延期审批 2:完成审批
     * @return
     */
    @GetMapping("/disposeExamineFlowAccountList")
    public ApiResult disposeExamineFlowAccountList(@RequestParam(defaultValue = "0") String examineTag, @RequestParam(defaultValue = "1") Integer examineType) {
        // 查询审批流程人员列表
        List<TaskFlowAccount> examineFlowResultVOList = flowAccountService.selectNearTaskFlowAccountList(TaskFlowAccount.builder()
                .examineTag(examineTag)
                .examineType(examineType)
                .build());
        return ok(examineFlowResultVOList);
    }

    /**
     * 文件存储
     *
     * @param fileList
     */
    public void fileSave(List<TaskFile> fileList, String taskInitiateId) {
        if (fileList != null && fileList.size() > 0) {
            fileList.forEach((TaskFile file) -> {
                if (file.getTaskFileType() != null
                        && StringUtils.isNotBlank(file.getTaskFileName())
                        && StringUtils.isNotBlank(file.getTaskFileUrl())) {
                    // 计算文件的大小
                    try {
                        file.setExamineFileSize(fileSize(file.getTaskFileUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 将文件存储起来
                    file.setTaskInitiateId(taskInitiateId);
                    int countFlag = fileService.insertTaskFile(file);
                    if (countFlag <= 0) {
                        throw new ApiException("文件保存失败！");
                    }
                }
            });
        }
    }
}
