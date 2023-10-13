package com.wlwq.taskService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.examine.ContractParamVO;
import com.wlwq.params.examine.ExamineSubVO;
import com.wlwq.params.examine.ExpenseDetailParamVO;
import com.wlwq.params.examine.ExpenseParamVO;
import com.wlwq.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author gaoce
 */
@Component
@AllArgsConstructor
public class TaskExamineFlowAccountService extends ApiController {

    private final IExamineFlowService flowService;

    private final IExamineFlowAccountService flowAccountService;

    private final IExamineFlowCopyAccountService flowCopyAccountService;

    private final IApiAccountService accountService;

    private final IExamineInitiateService initiateService;

    private final IExamineCopyFlowService copyFlowService;

    private final IExamineFileService fileService;

    private final IExamineContractCounterpartyService counterpartyService;

    private final IAccountClockingService clockingService;

    private final TaskScoreService scoreService;


    /**
     * 合同提交
     *
     * @param params
     * @param account 用户
     * @return
     */
    public TimerTask contract(ContractParamVO params, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                ExamineInitiate examineInitiate = ExamineInitiate.builder().build();
                // 将传过来的参数赋值给要存入的实体
                BeanUtil.copyProperties(params, examineInitiate);
                if (StringUtils.isBlank(params.getContractNumber())) {
                    // 合同编号为空的情况下，自动生成编号
                    examineInitiate.setContractNumber("hz-" + DateUtil.format(new Date(), "yyyyMMdd") + IdUtil.getSnowflake(1, 1).nextIdStr());
                }
                examineInitiate.setExamineModuleId(3L);
                examineInitiate.setCompanyId(account.getCompanyId());
                examineInitiate.setDeptId(account.getDeptId());
                examineInitiate.setAccountId(account.getAccountId());
                examineInitiate.setAccountName(account.getNickName());
                examineInitiate.setAccountPhone(account.getPhone());
                examineInitiate.setAccountHead(account.getHeadPortrait());
                examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
                int detailCount = initiateService.insertExamineInitiate(examineInitiate);
                if (detailCount <= 0) {
                    throw new ApiException("添加报销明细失败");
                }
                // 相对方信息
                List<ExamineContractCounterparty> counterpartyList = params.getCounterpartyList();
                counterpartyList.forEach((ExamineContractCounterparty counterparty) -> {
                    if (counterparty != null) {
                        counterparty.setExamineInitiateId(examineInitiate.getExamineInitiateId());
                        counterpartyService.insertExamineContractCounterparty(counterparty);
                    }
                });
                // 判断是否有文件，有的话将文件存储起来
                fileSave(params.getFileList(), examineInitiate.getExamineInitiateId());

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
                    AsyncManager.me().execute(examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

                }
                if(examineFlowTag == 0){
                    // 将审批流程的第一级人员信息存到记录表中
                    // 异步操作
                    ExamineFlow examineFlow1 = ExamineFlow.builder()
                            .examineModuleId(3L)
                            .companyId(account.getCompanyId())
                            .minValue(params.getTotalMoney().doubleValue())
                            .build();
                    AsyncManager.me().execute(examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
                }

            }
        };
    }

    /**
     * 修改 合同提交
     *
     * @param params
     * @param status  0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
     * @param account 用户
     * @return
     */
    public TimerTask editContract(ContractParamVO params, Integer status, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                ExamineInitiate examineInitiate = ExamineInitiate.builder().build();
                // 将传过来的参数赋值给要存入的实体
                BeanUtil.copyProperties(params, examineInitiate);
                if (StringUtils.isBlank(params.getContractNumber())) {
                    // 合同编号为空的情况下，自动生成编号
                    examineInitiate.setContractNumber("hz-" + DateUtil.format(new Date(), "yyyyMMdd") + IdUtil.getSnowflake(1, 1).nextIdStr());
                }
                examineInitiate.setExamineModuleId(3L);
                examineInitiate.setCompanyId(account.getCompanyId());
                examineInitiate.setDeptId(account.getDeptId());
                examineInitiate.setAccountId(account.getAccountId());
                examineInitiate.setAccountName(account.getNickName());
                examineInitiate.setAccountPhone(account.getPhone());
                examineInitiate.setAccountHead(account.getHeadPortrait());
                examineInitiate.setExamineStatus(1);
                examineInitiate.setExamineInitiateId(params.getExamineInitiateId());
                examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
                int detailCount = initiateService.updateExamineInitiate(examineInitiate);
                if (detailCount <= 0) {
                    throw new ApiException("添加报销明细失败");
                }
                // 删除相对方信息
                counterpartyService.deleteExamineContractCounterpartyByExamineInitiateId(params.getExamineInitiateId());
                // 相对方信息
                List<ExamineContractCounterparty> counterpartyList = params.getCounterpartyList();
                counterpartyList.forEach((ExamineContractCounterparty counterparty) -> {
                    if (counterparty != null) {
                        counterparty.setExamineInitiateId(examineInitiate.getExamineInitiateId());
                        counterpartyService.insertExamineContractCounterparty(counterparty);
                    }
                });
                // 删除文件
                // 删除之前的文件
                fileService.deleteExamineBeforeFileByInitiateId(params.getExamineInitiateId());
                // 判断是否有文件，有的话将文件存储起来
                fileSave(params.getFileList(), examineInitiate.getExamineInitiateId());

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
                    AsyncManager.me().execute(examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

                }

                if(examineFlowTag == 0){
                    // 将审批流程的第一级人员信息存到记录表中
                    // 异步操作
                    ExamineFlow examineFlow1 = ExamineFlow.builder()
                            .examineModuleId(3L)
                            .companyId(account.getCompanyId())
                            .minValue(params.getTotalMoney().doubleValue())
                            .build();
                    AsyncManager.me().execute(examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
                }

            }
        };
    }

    /**
     * 报销提交
     *
     * @param params
     * @param account 用户
     * @return
     */
    public TimerTask expense(ExpenseParamVO params, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                ExamineInitiate examineInitiate = ExamineInitiate.builder()
                        .examineModuleId(2L)
                        .totalMoney(params.getTotalMoney())
                        .companyId(account.getCompanyId())
                        .deptId(account.getDeptId())
                        .accountId(account.getAccountId())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .accountHead(account.getHeadPortrait())
                        .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                        .build();
                int count = initiateService.insertExamineInitiate(examineInitiate);
                if (count <= 0) {
                    throw new ApiException("提交失败");
                }
                // 存入报销明细
                List<ExpenseDetailParamVO> detailParamVOList = params.getDetailParamVOList();
                detailParamVOList.forEach((ExpenseDetailParamVO detailParamVO) -> {
                    if (detailParamVO != null) {
                        ExamineInitiate initiateDetail = ExamineInitiate.builder().build();
                        // 将传过来的参数赋值给要存入的实体
                        BeanUtil.copyProperties(detailParamVO, initiateDetail);
                        initiateDetail.setParentId(examineInitiate.getExamineInitiateId());
                        initiateDetail.setExamineModuleId(2L);
                        int detailCount = initiateService.insertExamineInitiate(initiateDetail);
                        if (detailCount <= 0) {
                            throw new ApiException("添加报销明细失败");
                        }
                        // 判断是否有文件，有的话将文件存储起来
                        fileSave(detailParamVO.getFileList(), initiateDetail.getExamineInitiateId());
                    }

                });
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
                    AsyncManager.me().execute(examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

                }
                if(examineFlowTag == 0){
                    // 将审批流程的第一级人员信息存到记录表中
                    // 异步操作
                    ExamineFlow examineFlow1 = ExamineFlow.builder()
                            .examineModuleId(2L)
                            .companyId(account.getCompanyId())
                            .minValue(params.getTotalMoney().doubleValue())
                            .build();
                    AsyncManager.me().execute(examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
                }

            }
        };
    }

    /**
     * 修改 报销提交
     *
     * @param params
     * @param status  0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
     * @param account 用户
     * @return
     */
    public TimerTask editExpense(ExpenseParamVO params, Integer status, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                ExamineInitiate examineInitiate = ExamineInitiate.builder()
                        .examineModuleId(2L)
                        .totalMoney(params.getTotalMoney())
                        .companyId(account.getCompanyId())
                        .deptId(account.getDeptId())
                        .accountId(account.getAccountId())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .accountHead(account.getHeadPortrait())
                        .examineStatus(1)
                        .examineInitiateId(params.getExamineInitiateId())
                        .examineTag(IdUtil.getSnowflake(1, 1).nextIdStr())
                        .build();
                int count = initiateService.updateExamineInitiate(examineInitiate);
                if (count <= 0) {
                    throw new ApiException("提交失败");
                }
                // 删除报销明细
                initiateService.deleteExamineInitiateByParentId(params.getExamineInitiateId());
                // 存入报销明细
                List<ExpenseDetailParamVO> detailParamVOList = params.getDetailParamVOList();
                detailParamVOList.forEach((ExpenseDetailParamVO detailParamVO) -> {
                    if (detailParamVO != null) {
                        ExamineInitiate initiateDetail = ExamineInitiate.builder().build();
                        // 将传过来的参数赋值给要存入的实体
                        BeanUtil.copyProperties(detailParamVO, initiateDetail);
                        initiateDetail.setParentId(examineInitiate.getExamineInitiateId());
                        initiateDetail.setExamineModuleId(2L);
                        int detailCount = initiateService.insertExamineInitiate(initiateDetail);
                        if (detailCount <= 0) {
                            throw new ApiException("添加报销明细失败");
                        }
                        // 判断是否有文件，有的话将文件存储起来
                        fileSave(detailParamVO.getFileList(), initiateDetail.getExamineInitiateId());
                    }

                });

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
                    AsyncManager.me().execute(examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

                }

                if(examineFlowTag == 0){
                    // 将审批流程的第一级人员信息存到记录表中
                    // 异步操作
                    ExamineFlow examineFlow1 = ExamineFlow.builder()
                            .examineModuleId(2L)
                            .companyId(account.getCompanyId())
                            .minValue(params.getTotalMoney().doubleValue())
                            .build();
                    AsyncManager.me().execute(examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));
                }

            }
        };
    }

    /**
     * 将部门领导审批流程的人员信息存到记录表中
     *
     * @param account  部门领导用户
     * @param examineInitiateId 员工审批发起ID
     * @param examineTag        本次审批唯一标识
     * @return
     */
    public TimerTask examineFlowDeptAccount(ApiAccount account, String examineInitiateId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                // 查询设置的审批时间(分钟)
                String configValue = sysConfigService.selectConfigByKey("sys_examine_time");
                Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                int count = flowAccountService.insertExamineFlowAccount(ExamineFlowAccount.builder()
                        .examineInitiateId(examineInitiateId)
                        .accountId(account.getAccountId())
                        .accountHead(account.getHeadPortrait())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .postId(account.getPostId())
                        .examineSequence(0)
                        .deptId(account.getDeptId())
                        .examineEndTime(DateUtils.addMin(min))
                        .examineTag(examineTag)
                        .build());
                if (count <= 0) {
                    throw new ApiException("添加失败！");
                }
            }
        };
    }

    /**
     * 将审批流程的人员信息存到记录表中
     *
     * @param examineFlow
     * @param examineInitiateId 员工审批发起ID
     * @param examineTag        本次审批唯一标识
     * @return
     */
    public TimerTask examineFlowAccount(ExamineFlow examineFlow, String examineInitiateId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                // 根据模块和类型查询统一审批等级查看审批顺序最小值
                ExamineFlowResultVO flowAccountResultVO = flowService.selectMinExamineGroupBySequence(examineFlow);
                if (flowAccountResultVO != null) {
                    // 查询设置的审批时间(分钟)
                    String configValue = sysConfigService.selectConfigByKey("sys_examine_time");
                    Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                    // 将需要审批的第一级存起来
                    // 查询用户信息
                    examineFlow.setExamineSequence(flowAccountResultVO.getExamineSequence());
                    List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                    accountResultVOList.forEach((ExamineFlowAccountResultVO examineFlowAccountResultVO) -> {
                        int count = flowAccountService.insertExamineFlowAccount(ExamineFlowAccount.builder()
                                .examineInitiateId(examineInitiateId)
                                .accountId(examineFlowAccountResultVO.getAccountId())
                                .accountHead(examineFlowAccountResultVO.getAccountHead())
                                .accountName(examineFlowAccountResultVO.getAccountName())
                                .accountPhone(examineFlowAccountResultVO.getPhone())
                                .postId(examineFlowAccountResultVO.getPostId())
                                .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                .deptId(examineFlowAccountResultVO.getDeptId())
                                .examineEndTime(DateUtils.addMin(min))
                                .examineTag(examineTag)
                                .build());
                        if (count <= 0) {
                            throw new ApiException("添加失败！");
                        }
                    });

                }
            }
        };
    }

    private final ISysConfigService sysConfigService;

    /**
     * 审批通过或拒绝之后修改总表的状态
     *
     * @param examineSubVO
     * @param map          审批详情
     * @return
     */
    public TimerTask editExamineInitiate(ExamineSubVO examineSubVO, Map<String, Object> map) {
        return new TimerTask() {
            @Override
            public void run() {
                // 审批通过之后
                if (examineSubVO.getExamineStatus() == 3) {
                    // 查询设置的审批时间(分钟)
                    String configValue = sysConfigService.selectConfigByKey("sys_examine_time");
                    Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                    // 查询同等审批的是否都通过，通过的情况下进行下一个审批
                    // 查询同等级审批的相关信息
                    List<ExamineFlowAccount> examineFlowAccountList = flowAccountService.selectExamineFlowAccountList(ExamineFlowAccount.builder()
                            .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                            .examineSequence(Convert.toInt(map.get("examineSequence")))
                            .examineTag(Convert.toStr(map.get("examineTag")))
                            .build());
                    Integer tag = 0;// 默认都通过
                    for (ExamineFlowAccount examineFlowAccount : examineFlowAccountList) {
                        // 查询最新的审批状态
                        ExamineFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitExamineFlowAccount(ExamineFlowAccount.builder()
                                .accountId(examineFlowAccount.getAccountId())
                                .examineStatus(3)
                                .examineInitiateId(examineFlowAccount.getExamineInitiateId())
                                .examineSequence(examineFlowAccount.getExamineSequence())
                                .examineTag(Convert.toStr(map.get("examineTag")))
                                .build());
                        if (examineFlowAccount1 == null && examineFlowAccount.getExamineStatus() != 3) {
                            tag = 1; // 同等级审批有不通过的
                        }
                    }
                    // 通过的情况下进行下一个审批
                    if (tag == 0) {
                        //
                        ExamineFlow examineFlow = ExamineFlow.builder()
                                .examineSequence(Convert.toInt(map.get("examineSequence")))
                                .examineModuleId(Convert.toLong(map.get("examineModuleId")))
                                .companyId(Convert.toLong(map.get("companyId")))
                                .build();
                        if(Convert.toInt(map.get("examineModuleId")) == 1){
                            examineFlow.setMinValue(Convert.toDouble(map.get("askForLeaveHour")));
                        }
                        if(Convert.toInt(map.get("examineModuleId")) == 2 || Convert.toInt(map.get("examineModuleId")) == 3){
                            examineFlow.setMinValue(Convert.toDouble(map.get("totalMoney")));
                        }
                        // 审批通过，查询他的下一级审批者是否存在
                        ExamineFlowResultVO examineFlowAccountResultVO = flowService.selectNextExamineSequence(examineFlow);
                        if (examineFlowAccountResultVO != null) {
                            examineFlow.setExamineSequence(examineFlowAccountResultVO.getExamineSequence());
                            // 查询同等级审批的相关信息
                            List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                            // 将信息存储起来
                            accountResultVOList.forEach((ExamineFlowAccountResultVO accountResultVO) -> {
                                int countFlag = flowAccountService.insertExamineFlowAccount(ExamineFlowAccount.builder()
                                        .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                                        .accountId(accountResultVO.getAccountId())
                                        .accountHead(accountResultVO.getAccountHead())
                                        .accountName(accountResultVO.getAccountName())
                                        .accountPhone(accountResultVO.getPhone())
                                        .postId(accountResultVO.getPostId())
                                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                        .deptId(accountResultVO.getDeptId())
                                        .examineEndTime(DateUtils.addMin(min))
                                        .examineTag(Convert.toStr(map.get("examineTag")))
                                        .build());
                                if (countFlag <= 0) {
                                    throw new ApiException("添加用户审批流程失败！");
                                }
                            });
                            // 判断同等审批，如果同等审批有一个通过，那么这个发起表（examine_initiate）的总状态（examine_status）就是审批中
                            int countFlag = initiateService.updateExamineInitiate(ExamineInitiate.builder()
                                    .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                                    .examineStatus(2)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }

                        } else {
                            // 判断是不是最后一个审批的，审批通过之后将信息保存到抄送表中
                            // 查询抄送岗位列表
                            List<ExamineCopyFlow> copyFlowList = copyFlowService.selectApiExamineCopyFlowList(ExamineCopyFlow.builder()
                                    .companyId(Convert.toLong(map.get("companyId")))
                                    .examineModuleId(Convert.toLong(map.get("examineModuleId")))
                                    .build());
                            for (ExamineCopyFlow examineCopyFlow : copyFlowList) {
                                List<ApiAccount> accountList = accountService.selectApiAccountListByPostIdAndDeptId(examineCopyFlow.getDeptId(), examineCopyFlow.getPostId());
                                for (ApiAccount account : accountList) {
                                    // 存入抄送记录表
                                    flowCopyAccountService.insertExamineFlowCopyAccount(ExamineFlowCopyAccount.builder()
                                            .copyAccountId(account.getAccountId())
                                            .copyAccountName(account.getNickName())
                                            .copyAccountHead(account.getHeadPortrait())
                                            .copyAccountPhone(account.getPhone())
                                            .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                                            .examineStatus(3)
                                            .examineTag(Convert.toStr(map.get("examineTag")))
                                            .build());
                                }
                            }
                            // 将总状态改为已通过
                            int countFlag = initiateService.updateExamineInitiate(ExamineInitiate.builder()
                                    .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                                    .examineStatus(3)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }
                            // 判断是不是补卡的
                            if ("5".equals(Convert.toStr(map.get("examineModuleId")))) {
                                // 处理考勤数据
                                clocking(map);
                            }
                        }

                    } else {
                        // 有一个审批不通过的，将总状态改为已拒绝
                        int countFlag = initiateService.updateExamineInitiate(ExamineInitiate.builder()
                                .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                                .examineStatus(4)
                                .readStatus(0)
                                .build());
                        if (countFlag <= 0) {
                            throw new ApiException("修改审批发起失败！");
                        }
                    }
                } else {
                    // 将总状态改为已拒绝
                    int countFlag = initiateService.updateExamineInitiate(ExamineInitiate.builder()
                            .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                            .examineStatus(4)
                            .readStatus(0)
                            .build());
                    if (countFlag <= 0) {
                        throw new ApiException("修改审批发起失败！");
                    }
                }

                // 未按时审核扣除积分
                ExamineFlowAccount flowAccount = flowAccountService.selectExamineFlowAccountById(examineSubVO.getFlowAccountId());
                if (flowAccount != null && System.currentTimeMillis() > flowAccount.getExamineEndTime().getTime()) {
                    // 扣除用户积分
                    // 异步操作，扣除积分
                    AsyncManager.me().execute(scoreService.oaExamineScore(flowAccount.getExamineFlowAccountId(), accountService.selectApiAccountById(flowAccount.getAccountId()), 20));
                }

            }
        };
    }

    /**
     * 处理考勤数据
     *
     * @param map
     */
    public void clocking(Map<String, Object> map) {
        ApiAccount account = accountService.selectApiAccountById(Convert.toStr(map.get("accountId")));
        if (account != null) {
            // 查询是否为上下班 1：上班打卡 2：下班打卡
            Integer clockStatus = Convert.toInt(map.get("clockingStatus"));
            // 判断给定的日期及是否存在
            // 查询今天打过上班卡
            AccountClocking clocking = clockingService.selectAccountClocking(AccountClocking.builder().accountId(account.getAccountId()).clockingDate(Convert.toDate(map.get("reissueClockingDate"))).build());
            if (clocking != null) {
                // 1：上班打卡 2：下班打卡
                if (clockStatus == 1) {
                    clocking.setClockingType(1);
                    clocking.setClockingStatus(6);
                    clocking.setOnWorkTime(Convert.toDate(map.get("reissueClockingTime")));
                    clocking.setPics(Convert.toStr(map.get("pics")));
                    clocking.setRemark(Convert.toStr(map.get("reason")));
                } else {
                    clocking.setOffClockingType(1);
                    clocking.setOffClockingStatus(6);
                    clocking.setOffWorkTime(Convert.toDate(map.get("reissueClockingTime")));
                    clocking.setOffClockingPics(Convert.toStr(map.get("pics")));
                    clocking.setOffClockingRemark(Convert.toStr(map.get("reason")));
                }
                clockingService.updateAccountClocking(clocking);
            } else {
                AccountClocking accountClocking = AccountClocking.builder()
                        .accountId(account.getAccountId())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .accountHead(account.getHeadPortrait())
                        .clockingDate(Convert.toDate(map.get("reissueClockingDate")))
                        .deptId(account.getDeptId())
                        .postIds(account.getPostIds())
                        .companyId(account.getCompanyId())
                        .build();
                // 1：上班打卡 2：下班打卡
                if (clockStatus == 1) {
                    accountClocking.setClockingType(1);
                    accountClocking.setClockingStatus(6);
                    accountClocking.setOnWorkTime(Convert.toDate(map.get("reissueClockingTime")));
                    accountClocking.setPics(Convert.toStr(map.get("pics")));
                    accountClocking.setRemark(Convert.toStr(map.get("reason")));
                } else {
                    accountClocking.setOffClockingType(1);
                    accountClocking.setOffClockingStatus(6);
                    accountClocking.setOffWorkTime(Convert.toDate(map.get("reissueClockingTime")));
                    accountClocking.setOffClockingPics(Convert.toStr(map.get("pics")));
                    accountClocking.setOffClockingRemark(Convert.toStr(map.get("reason")));
                }
                clockingService.insertAccountClocking(accountClocking);
            }
        }
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
}
