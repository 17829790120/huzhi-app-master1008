package com.wlwq.taskService;

import cn.hutool.core.convert.Convert;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.examine.ExamineSubVO;
import com.wlwq.params.task.TaskFinishPassParamVO;
import com.wlwq.params.task.TaskOverduePassParamVO;
import com.wlwq.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author gaoce
 */
@Component
@AllArgsConstructor
public class TaskService extends ApiController {

    private final IExamineFlowService flowService;

    private final ITaskFlowAccountService flowAccountService;

    private final ISysConfigService sysConfigService;

    private final ITaskInitiateService initiateService;

    private final ITaskReceiveService receiveService;

    private final IApiAccountService accountService;

    private final IAccountScoreService accountScoreService;

    private final IAccountMedalRecordService accountMedalRecordService;

    private final IMessageRemindService messageRemindService;

    private final TaskScoreService taskScoreService;


    /**
     * 任务创建提交 给接收着发消息
     *
     * @param params  任务信息
     * @return
     */
    public TimerTask message(TaskInitiate params) {
        return new TimerTask() {
            @Override
            public void run() {
                // 执行者IDS(可以是多个)
                String executeAccountIds = params.getExecuteAccountIds();
                if(StringUtils.isNotBlank(executeAccountIds)){
                    String[] executeAccountIdArr = executeAccountIds.split(",");
                    for(int i = 0;i < executeAccountIdArr.length;i++){
                        // 发送系统消息
                        // 查询消息是否存在
                        messageRemindService.insertMessageRemind(MessageRemind.builder()
                                .title("任务消息")
                                .brief("您有一条任务<" + params.getTaskTitle() + ">代办,点击查看")
                                .modelStatus(1)
                                .jumpType(-3)
                                .modelId(params.getTaskInitiateId())
                                .accountId(executeAccountIdArr[i])
                                .build());
                    }
                }

                // 协助者IDS(可以是多个)
                String assistAccountIds = params.getAssistAccountIds();
                if(StringUtils.isNotBlank(assistAccountIds)){
                    String[] assistAccountIdArr = assistAccountIds.split(",");
                    for(int i = 0;i < assistAccountIdArr.length;i++){
                        // 发送系统消息
                        // 查询消息是否存在
                        messageRemindService.insertMessageRemind(MessageRemind.builder()
                                .title("任务消息")
                                .brief("您有一条任务<" + params.getTaskTitle() + ">需协助,点击查看")
                                .modelStatus(1)
                                .jumpType(-3)
                                .modelId(params.getTaskInitiateId())
                                .accountId(assistAccountIdArr[i])
                                .build());
                    }
                }
            }
        };
    }


    /**
     * 完成审批通过或拒绝之后修改总表的状态
     *
     * @param params
     * @param flowAccount  用户审批信息
     * @param taskInitiate 发起信息
     * @return
     */
    public TimerTask editFinishTaskReceive(TaskFinishPassParamVO params, TaskFlowAccount flowAccount, TaskInitiate taskInitiate) {
        return new TimerTask() {
            @Override
            public void run() {
                // 审批通过之后  审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：延期完成待审批 12：延期完成审批中 13：延期完成已驳回 14延期完成已撤回
                if (params.getExamineStatus() == 8) {
                    // 查询设置的审批时间(分钟)
                    String configValue = sysConfigService.selectConfigByKey("sys_examine_time");
                    Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                    // 查询同等审批的是否都通过，通过的情况下进行下一个审批
                    // 查询同等级审批的相关信息
                    List<TaskFlowAccount> examineFlowAccountList = flowAccountService.selectTaskFlowAccountList(TaskFlowAccount.builder()
                            .taskReceiveId(flowAccount.getTaskReceiveId())
                            .examineType(2)
                            .examineSequence(flowAccount.getExamineSequence())
                            .examineTag(flowAccount.getExamineTag())
                            .build());
                    Integer tag = 0;// 默认都通过
                    for (TaskFlowAccount examineFlowAccount : examineFlowAccountList) {
                        // 查询最新的审批状态
                        TaskFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                                .accountId(examineFlowAccount.getAccountId())
                                .examineStatus(8)
                                .examineType(2)
                                .taskReceiveId(examineFlowAccount.getTaskReceiveId())
                                .examineSequence(examineFlowAccount.getExamineSequence())
                                .examineTag(flowAccount.getExamineTag())
                                .build());
                        if (examineFlowAccount1 == null && examineFlowAccount.getExamineStatus() != 8) {
                            tag = 1; // 同等级审批有不通过的
                        }
                    }
                    // 通过的情况下进行下一个审批
                    if (tag == 0) {
                        //
                        ExamineFlow examineFlow = ExamineFlow.builder()
                                .examineSequence(flowAccount.getExamineSequence())
                                .examineModuleId(27L)
                                .companyId(taskInitiate.getCompanyId())
                                .minValue(taskInitiate.getTaskScore().doubleValue())
                                .build();
                        // 审批通过，查询他的下一级审批者是否存在
                        ExamineFlowResultVO examineFlowAccountResultVO = flowService.selectNextExamineSequence(examineFlow);
                        if (examineFlowAccountResultVO != null) {
                            examineFlow.setExamineSequence(examineFlowAccountResultVO.getExamineSequence());
                            // 查询同等级审批的相关信息
                            List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                            // 将信息存储起来
                            accountResultVOList.forEach((ExamineFlowAccountResultVO accountResultVO) -> {
                                int countFlag = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                                        .taskReceiveId(flowAccount.getTaskReceiveId())
                                        .accountId(accountResultVO.getAccountId())
                                        .accountHead(accountResultVO.getAccountHead())
                                        .accountName(accountResultVO.getAccountName())
                                        .accountPhone(accountResultVO.getPhone())
                                        .postId(accountResultVO.getPostId())
                                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                        .deptId(accountResultVO.getDeptId())
                                        .examineEndTime(DateUtils.addMin(min))
                                        .examineStatus(6)
                                        .examineType(2)
                                        .examineTag(flowAccount.getExamineTag())
                                        .build());
                                if (countFlag <= 0) {
                                    throw new ApiException("添加用户审批流程失败！");
                                }
                            });
                            // 判断同等审批，如果同等审批有一个通过，那么这个发起表（examine_initiate）的总状态（examine_status）就是审批中
                            int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                    .taskReceiveId(flowAccount.getTaskReceiveId())
                                    .examineStatus(7)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }

                        } else {
                            // 将总状态改为已通过
                            int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                    .taskReceiveId(flowAccount.getTaskReceiveId())
                                    .examineStatus(8)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }
                            // 赠送积分
                            TaskReceive taskReceive = receiveService.selectTaskReceiveById(flowAccount.getTaskReceiveId());
                            if (taskReceive != null) {
                                TaskInitiate taskInitiate = initiateService.selectTaskInitiateById(taskReceive.getTaskInitiateId());
                                if (taskInitiate != null && taskInitiate.getTaskScore() > 0) {
                                    ApiAccount account = accountService.selectApiAccountById(taskReceive.getAccountId());
                                    if (account != null) {
                                        ApiAccount account1 = ApiAccount.builder()
                                                .accountId(account.getAccountId())
                                                .totalScore(account.getTotalScore() + taskInitiate.getTaskScore())
                                                .surplusScore(account.getSurplusScore() + taskInitiate.getTaskScore())
                                                .build();
                                        // 更新用户信息
                                        accountService.updateApiAccount(account1);
                                        // 用户积分存入记录
                                        // 赠送用户积分
                                        int count = accountScoreService.insertAccountScore(AccountScore.builder()
                                                .accountId(account.getAccountId())
                                                .targetId(taskReceive.getTaskReceiveId())
                                                .scoreType(0)
                                                .accountName(account.getNickName())
                                                .accountPhone(account.getPhone())
                                                .accountHead(account.getHeadPortrait())
                                                .scoreSource("任务完成奖励")
                                                .scoreStatus(1)
                                                .score(taskInitiate.getTaskScore())
                                                .build());
                                        if (count <= 0) {
                                            throw new ApiException("积分存入失败！");
                                        }
                                        // 发送系统消息
                                        // 查询消息是否存在
                                        messageRemindService.insertMessageRemind(MessageRemind.builder()
                                                .title("积分变动")
                                                .brief("任务完成奖励" + taskInitiate.getTaskScore() + "积分,点击查看")
                                                .modelStatus(2)
                                                .jumpType(-2)
                                                .modelId("0")
                                                .accountId(account.getAccountId())
                                                .build());
//                                        // 查看是否满足勋章条件并更新勋章
//                                        accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(taskReceive.getAccountId()), taskInitiate.getTaskScore(), "任务完成");
                                    }
                                }
                            }

                        }

                    } else {
                        // 有一个审批不通过的，将总状态改为已拒绝
                        int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                .taskReceiveId(flowAccount.getTaskReceiveId())
                                .examineStatus(9)
                                .readStatus(0)
                                .build());
                        if (countFlag <= 0) {
                            throw new ApiException("修改审批发起失败！");
                        }
                    }
                } else {
                    // 将总状态改为已拒绝
                    int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                            .taskReceiveId(flowAccount.getTaskReceiveId())
                            .examineStatus(9)
                            .readStatus(0)
                            .build());
                    if (countFlag <= 0) {
                        throw new ApiException("修改审批发起失败！");
                    }
                }

            }
        };
    }


    /**
     * 延期审批通过或拒绝之后修改总表的状态
     *
     * @param params
     * @param flowAccount  用户审批信息
     * @param taskInitiate 发起信息
     * @return
     */
    public TimerTask editTaskReceive(TaskOverduePassParamVO params, TaskFlowAccount flowAccount, TaskInitiate taskInitiate) {
        return new TimerTask() {
            @Override
            public void run() {
                // 审批通过之后
                if (params.getExamineStatus() == 3) {
                    // 查询设置的审批时间(分钟)
                    String configValue = sysConfigService.selectConfigByKey("sys_examine_time");
                    Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                    // 查询同等审批的是否都通过，通过的情况下进行下一个审批
                    // 查询同等级审批的相关信息
                    List<TaskFlowAccount> examineFlowAccountList = flowAccountService.selectTaskFlowAccountList(TaskFlowAccount.builder()
                            .taskReceiveId(flowAccount.getTaskReceiveId())
                            .examineType(1)
                            .examineSequence(flowAccount.getExamineSequence())
                            .examineTag(flowAccount.getExamineTag())
                            .build());
                    Integer tag = 0;// 默认都通过
                    for (TaskFlowAccount examineFlowAccount : examineFlowAccountList) {
                        // 查询最新的审批状态
                        TaskFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                                .accountId(examineFlowAccount.getAccountId())
                                .examineStatus(3)
                                .examineType(1)
                                .taskReceiveId(examineFlowAccount.getTaskReceiveId())
                                .examineSequence(examineFlowAccount.getExamineSequence())
                                .examineTag(flowAccount.getExamineTag())
                                .build());
                        if (examineFlowAccount1 == null && examineFlowAccount.getExamineStatus() != 3) {
                            tag = 1; // 同等级审批有不通过的
                        }
                    }
                    // 通过的情况下进行下一个审批
                    if (tag == 0) {
                        //
                        ExamineFlow examineFlow = ExamineFlow.builder()
                                .examineSequence(flowAccount.getExamineSequence())
                                .examineModuleId(26L)
                                .companyId(taskInitiate.getCompanyId())
                                .minValue(taskInitiate.getTaskScore().doubleValue())
                                .build();
                        // 审批通过，查询他的下一级审批者是否存在
                        ExamineFlowResultVO examineFlowAccountResultVO = flowService.selectNextExamineSequence(examineFlow);
                        if (examineFlowAccountResultVO != null) {
                            examineFlow.setExamineSequence(examineFlowAccountResultVO.getExamineSequence());
                            // 查询同等级审批的相关信息
                            List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                            // 将信息存储起来
                            accountResultVOList.forEach((ExamineFlowAccountResultVO accountResultVO) -> {
                                int countFlag = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                                        .taskReceiveId(flowAccount.getTaskReceiveId())
                                        .accountId(accountResultVO.getAccountId())
                                        .accountHead(accountResultVO.getAccountHead())
                                        .accountName(accountResultVO.getAccountName())
                                        .accountPhone(accountResultVO.getPhone())
                                        .postId(accountResultVO.getPostId())
                                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                        .deptId(accountResultVO.getDeptId())
                                        .examineEndTime(DateUtils.addMin(min))
                                        .examineStatus(1)
                                        .examineType(1)
                                        .examineTag(flowAccount.getExamineTag())
                                        .build());
                                if (countFlag <= 0) {
                                    throw new ApiException("添加用户审批流程失败！");
                                }
                            });
                            // 判断同等审批，如果同等审批有一个通过，那么这个发起表（examine_initiate）的总状态（examine_status）就是审批中
                            int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                    .taskReceiveId(flowAccount.getTaskReceiveId())
                                    .examineStatus(2)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }

                        } else {
                            // 将总状态改为已通过
                            int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                    .taskReceiveId(flowAccount.getTaskReceiveId())
                                    .examineStatus(3)
                                    .taskEndTime(params.getTaskOverdueTime())
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }


                        }

                    } else {
                        //  有一个审批不通过的，将总状态改为已拒绝
                        int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                .taskReceiveId(flowAccount.getTaskReceiveId())
                                .examineStatus(4)
                                .readStatus(0)
                                .build());
                        if (countFlag <= 0) {
                            throw new ApiException("修改审批发起失败！");
                        }
                    }
                } else {
                    // 将总状态改为已拒绝
                    int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                            .taskReceiveId(flowAccount.getTaskReceiveId())
                            .examineStatus(4)
                            .readStatus(0)
                            .build());
                    if (countFlag <= 0) {
                        throw new ApiException("修改审批发起失败！");
                    }
                }

            }
        };
    }

    /**
     * 任务完成审批(将部门领导审批流程的人员信息存到记录表中)
     *
     * @param account
     * @param taskReceiveId 接收ID
     * @param examineTag    审批唯一标识
     * @return
     */
    public TimerTask finishFlowDeptAccount(ApiAccount account, String taskReceiveId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {

                int count = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                        .taskReceiveId(taskReceiveId)
                        .accountId(account.getAccountId())
                        .accountHead(account.getHeadPortrait())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .postId(account.getPostId())
                        .examineSequence(0)
                        .deptId(account.getDeptId())
                        .examineStatus(6)
                        .examineType(2)
                        .examineTag(examineTag)
                        .build());
                if (count <= 0) {
                    throw new ApiException("添加失败！");
                }
            }
        };
    }


    /**
     * 任务完成审批
     *
     * @param examineFlow
     * @param taskReceiveId 接收ID
     * @param examineTag    审批唯一标识
     * @return
     */
    public TimerTask finishFlowAccount(ExamineFlow examineFlow, String taskReceiveId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                // 根据模块和类型查询统一审批等级查看审批顺序最小值
                ExamineFlowResultVO flowAccountResultVO = flowService.selectMinExamineGroupBySequence(examineFlow);
                if (flowAccountResultVO != null) {
                    // 将需要审批的第一级存起来
                    // 查询用户信息
                    examineFlow.setExamineSequence(flowAccountResultVO.getExamineSequence());
                    List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                    accountResultVOList.forEach((ExamineFlowAccountResultVO examineFlowAccountResultVO) -> {
                        int count = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                                .taskReceiveId(taskReceiveId)
                                .accountId(examineFlowAccountResultVO.getAccountId())
                                .accountHead(examineFlowAccountResultVO.getAccountHead())
                                .accountName(examineFlowAccountResultVO.getAccountName())
                                .accountPhone(examineFlowAccountResultVO.getPhone())
                                .postId(examineFlowAccountResultVO.getPostId())
                                .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                .deptId(examineFlowAccountResultVO.getDeptId())
                                .examineStatus(6)
                                .examineType(2)
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

    /**
     * 任务延期审批（将部门领导审批流程的人员信息存到记录表中）
     *
     * @param account
     * @param taskReceiveId 接收ID
     * @param examineTag    审批唯一标识
     * @return
     */
    public TimerTask overdueFlowDeptAccount(ApiAccount account, String taskReceiveId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                int count = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                        .taskReceiveId(taskReceiveId)
                        .accountId(account.getAccountId())
                        .accountHead(account.getHeadPortrait())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .postId(account.getPostId())
                        .examineSequence(0)
                        .deptId(account.getDeptId())
                        .examineStatus(1)
                        .examineType(1)
                        .examineTag(examineTag)
                        .build());
                if (count <= 0) {
                    throw new ApiException("添加失败！");
                }
            }
        };
    }

    /**
     * 任务延期审批
     *
     * @param examineFlow
     * @param taskReceiveId 接收ID
     * @param examineTag    审批唯一标识
     * @return
     */
    public TimerTask overdueFlowAccount(ExamineFlow examineFlow, String taskReceiveId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                // 根据模块和类型查询统一审批等级查看审批顺序最小值
                ExamineFlowResultVO flowAccountResultVO = flowService.selectMinExamineGroupBySequence(examineFlow);
                if (flowAccountResultVO != null) {
                    // 将需要审批的第一级存起来
                    // 查询用户信息
                    examineFlow.setExamineSequence(flowAccountResultVO.getExamineSequence());
                    List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                    accountResultVOList.forEach((ExamineFlowAccountResultVO examineFlowAccountResultVO) -> {
                        int count = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                                .taskReceiveId(taskReceiveId)
                                .accountId(examineFlowAccountResultVO.getAccountId())
                                .accountHead(examineFlowAccountResultVO.getAccountHead())
                                .accountName(examineFlowAccountResultVO.getAccountName())
                                .accountPhone(examineFlowAccountResultVO.getPhone())
                                .postId(examineFlowAccountResultVO.getPostId())
                                .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                .deptId(examineFlowAccountResultVO.getDeptId())
                                .examineStatus(1)
                                .examineType(1)
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

    /**
     * 逾期任务完成审批
     *
     * @param account
     * @param taskReceiveId 接收ID
     * @param examineTag    审批唯一标识
     * @return
     */
    public TimerTask overdueFinishFlowDeptAccount(ApiAccount account, String taskReceiveId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                int count = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                        .taskReceiveId(taskReceiveId)
                        .accountId(account.getAccountId())
                        .accountHead(account.getHeadPortrait())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .postId(account.getPostId())
                        .examineSequence(0)
                        .deptId(account.getDeptId())
                        .examineStatus(11)
                        .examineType(2)
                        .examineTag(examineTag)
                        .build());
                if (count <= 0) {
                    throw new ApiException("添加失败！");
                }
            }
        };
    }

    /**
     * 逾期任务完成审批
     *
     * @param examineFlow
     * @param taskReceiveId 接收ID
     * @param examineTag    审批唯一标识
     * @return
     */
    public TimerTask overdueFinishFlowAccount(ExamineFlow examineFlow, String taskReceiveId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                // 根据模块和类型查询统一审批等级查看审批顺序最小值
                ExamineFlowResultVO flowAccountResultVO = flowService.selectMinExamineGroupBySequence(examineFlow);
                if (flowAccountResultVO != null) {
                    // 将需要审批的第一级存起来
                    // 查询用户信息
                    examineFlow.setExamineSequence(flowAccountResultVO.getExamineSequence());
                    List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                    accountResultVOList.forEach((ExamineFlowAccountResultVO examineFlowAccountResultVO) -> {
                        int count = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                                .taskReceiveId(taskReceiveId)
                                .accountId(examineFlowAccountResultVO.getAccountId())
                                .accountHead(examineFlowAccountResultVO.getAccountHead())
                                .accountName(examineFlowAccountResultVO.getAccountName())
                                .accountPhone(examineFlowAccountResultVO.getPhone())
                                .postId(examineFlowAccountResultVO.getPostId())
                                .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                .deptId(examineFlowAccountResultVO.getDeptId())
                                .examineStatus(11)
                                .examineType(2)
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

    /**
     * 延期任务完成审批
     *
     * @param examineFlow
     * @param taskReceiveId 接收ID
     * @param examineTag    审批唯一标识
     * @return
     */
    public TimerTask postponeFinishFlowDeptAccount(ApiAccount account, String taskReceiveId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                int count = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                        .taskReceiveId(taskReceiveId)
                        .accountId(account.getAccountId())
                        .accountHead(account.getHeadPortrait())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .postId(account.getPostId())
                        .examineSequence(0)
                        .deptId(account.getDeptId())
                        .examineStatus(16)
                        .examineType(2)
                        .examineTag(examineTag)
                        .build());
                if (count <= 0) {
                    throw new ApiException("添加失败！");
                }
            }
        };
    }

    /**
     * 延期任务完成审批
     *
     * @param examineFlow
     * @param taskReceiveId 接收ID
     * @param examineTag    审批唯一标识
     * @return
     */
    public TimerTask postponeFinishFlowAccount(ExamineFlow examineFlow, String taskReceiveId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                // 根据模块和类型查询统一审批等级查看审批顺序最小值
                ExamineFlowResultVO flowAccountResultVO = flowService.selectMinExamineGroupBySequence(examineFlow);
                if (flowAccountResultVO != null) {
                    // 将需要审批的第一级存起来
                    // 查询用户信息
                    examineFlow.setExamineSequence(flowAccountResultVO.getExamineSequence());
                    List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                    accountResultVOList.forEach((ExamineFlowAccountResultVO examineFlowAccountResultVO) -> {
                        int count = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                                .taskReceiveId(taskReceiveId)
                                .accountId(examineFlowAccountResultVO.getAccountId())
                                .accountHead(examineFlowAccountResultVO.getAccountHead())
                                .accountName(examineFlowAccountResultVO.getAccountName())
                                .accountPhone(examineFlowAccountResultVO.getPhone())
                                .postId(examineFlowAccountResultVO.getPostId())
                                .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                .deptId(examineFlowAccountResultVO.getDeptId())
                                .examineStatus(16)
                                .examineType(2)
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

    /**
     * 逾期完成审批通过或拒绝之后修改总表的状态
     *
     * @param params
     * @param flowAccount  用户审批信息
     * @param taskInitiate 发起信息
     * @return
     */
    public TimerTask editOverdueFinishTaskReceive(TaskFinishPassParamVO params, TaskFlowAccount flowAccount, TaskInitiate taskInitiate) {
        return new TimerTask() {
            @Override
            public void run() {
                // 审批通过之后  审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
                if (params.getExamineStatus() == 14) {
                    // 查询设置的审批时间(分钟)
                    String configValue = sysConfigService.selectConfigByKey("sys_examine_time");
                    Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                    // 查询同等审批的是否都通过，通过的情况下进行下一个审批
                    // 查询同等级审批的相关信息
                    List<TaskFlowAccount> examineFlowAccountList = flowAccountService.selectTaskFlowAccountList(TaskFlowAccount.builder()
                            .taskReceiveId(flowAccount.getTaskReceiveId())
                            .examineType(2)
                            .examineSequence(flowAccount.getExamineSequence())
                            .examineTag(flowAccount.getExamineTag())
                            .build());
                    Integer tag = 0;// 默认都通过
                    for (TaskFlowAccount examineFlowAccount : examineFlowAccountList) {
                        // 查询最新的审批状态
                        TaskFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                                .accountId(examineFlowAccount.getAccountId())
                                .examineStatus(14)
                                .examineType(2)
                                .taskReceiveId(examineFlowAccount.getTaskReceiveId())
                                .examineSequence(examineFlowAccount.getExamineSequence())
                                .examineTag(flowAccount.getExamineTag())
                                .build());
                        if (examineFlowAccount1 == null && examineFlowAccount.getExamineStatus() != 14) {
                            tag = 1; // 同等级审批有不通过的
                        }
                    }
                    // 通过的情况下进行下一个审批
                    if (tag == 0) {
                        //
                        ExamineFlow examineFlow = ExamineFlow.builder()
                                .examineSequence(flowAccount.getExamineSequence())
                                .examineModuleId(27L)
                                .companyId(taskInitiate.getCompanyId())
                                .minValue(taskInitiate.getTaskScore().doubleValue())
                                .build();
                        // 审批通过，查询他的下一级审批者是否存在
                        ExamineFlowResultVO examineFlowAccountResultVO = flowService.selectNextExamineSequence(examineFlow);
                        if (examineFlowAccountResultVO != null) {
                            examineFlow.setExamineSequence(examineFlowAccountResultVO.getExamineSequence());
                            // 查询同等级审批的相关信息
                            List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                            // 将信息存储起来
                            accountResultVOList.forEach((ExamineFlowAccountResultVO accountResultVO) -> {
                                int countFlag = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                                        .taskReceiveId(flowAccount.getTaskReceiveId())
                                        .accountId(accountResultVO.getAccountId())
                                        .accountHead(accountResultVO.getAccountHead())
                                        .accountName(accountResultVO.getAccountName())
                                        .accountPhone(accountResultVO.getPhone())
                                        .postId(accountResultVO.getPostId())
                                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                        .deptId(accountResultVO.getDeptId())
                                        .examineEndTime(DateUtils.addMin(min))
                                        .examineStatus(11)
                                        .examineType(2)
                                        .examineTag(flowAccount.getExamineTag())
                                        .build());
                                if (countFlag <= 0) {
                                    throw new ApiException("添加用户审批流程失败！");
                                }
                            });
                            // 判断同等审批，如果同等审批有一个通过，那么这个发起表（examine_initiate）的总状态（examine_status）就是审批中
                            int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                    .taskReceiveId(flowAccount.getTaskReceiveId())
                                    .examineStatus(12)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }

                        } else {
                            // 将总状态改为已通过
                            int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                    .taskReceiveId(flowAccount.getTaskReceiveId())
                                    .examineStatus(14)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }
                            // 逾期审核通过扣除逾期接收者的信息
                            TaskReceive taskReceive = receiveService.selectTaskReceiveById(flowAccount.getTaskReceiveId());
                            if (taskReceive != null) {
                                ApiAccount account = accountService.selectApiAccountById(taskReceive.getAccountId());
                                if (account != null) {
                                    int score = taskScoreService.selectScore(account, 21);
                                    if (score > 0) {
                                        // 更新用户信息
                                        accountService.updateApiAccount(ApiAccount.builder()
                                                .accountId(account.getAccountId())
                                                .surplusScore(account.getSurplusScore() - score)
                                                .build());
                                        // 用户积分存入记录
                                        // 赠送用户积分
                                        int count = accountScoreService.insertAccountScore(AccountScore.builder()
                                                .accountId(account.getAccountId())
                                                .targetId(taskReceive.getTaskReceiveId())
                                                .scoreType(0)
                                                .accountName(account.getNickName())
                                                .accountPhone(account.getPhone())
                                                .accountHead(account.getHeadPortrait())
                                                .scoreSource("任务延期扣除")
                                                .scoreStatus(2)
                                                .score(score)
                                                .build());
                                        if (count <= 0) {
                                            throw new ApiException("积分存入失败！");
                                        }
                                        // 发送系统消息
                                        // 查询消息是否存在
                                        messageRemindService.insertMessageRemind(MessageRemind.builder()
                                                .title("积分变动")
                                                .brief("任务延期扣除" + score + "积分,点击查看")
                                                .modelStatus(2)
                                                .jumpType(-2)
                                                .modelId("0")
                                                .accountId(account.getAccountId())
                                                .build());
                                    }


                                }

                            }
                        }

                    } else {
                        // 只要有一个拒绝的，将状态改为已拒绝
                        int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                .taskReceiveId(flowAccount.getTaskReceiveId())
                                .examineStatus(13)
                                .readStatus(0)
                                .build());
                        if (countFlag <= 0) {
                            throw new ApiException("修改审批发起失败！");
                        }
                    }
                } else {
                    // 将总状态改为已拒绝
                    int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                            .taskReceiveId(flowAccount.getTaskReceiveId())
                            .examineStatus(13)
                            .readStatus(0)
                            .build());
                    if (countFlag <= 0) {
                        throw new ApiException("修改审批发起失败！");
                    }
                }

            }
        };
    }


    /**
     * 延期完成审批通过或拒绝之后修改总表的状态
     *
     * @param params
     * @param flowAccount  用户审批信息
     * @param taskInitiate 发起信息
     * @return
     */
    public TimerTask editPostponeFinishTaskReceive(TaskFinishPassParamVO params, TaskFlowAccount flowAccount, TaskInitiate taskInitiate) {
        return new TimerTask() {
            @Override
            public void run() {
                // 审批通过之后  审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
                if (params.getExamineStatus() == 20) {
                    // 查询设置的审批时间(分钟)
                    String configValue = sysConfigService.selectConfigByKey("sys_examine_time");
                    Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                    // 查询同等审批的是否都通过，通过的情况下进行下一个审批
                    // 查询同等级审批的相关信息
                    List<TaskFlowAccount> examineFlowAccountList = flowAccountService.selectTaskFlowAccountList(TaskFlowAccount.builder()
                            .taskReceiveId(flowAccount.getTaskReceiveId())
                            .examineType(2)
                            .examineSequence(flowAccount.getExamineSequence())
                            .examineTag(flowAccount.getExamineTag())
                            .build());
                    Integer tag = 0;// 默认都通过
                    for (TaskFlowAccount examineFlowAccount : examineFlowAccountList) {
                        // 查询最新的审批状态
                        TaskFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                                .accountId(examineFlowAccount.getAccountId())
                                .examineStatus(20)
                                .examineType(2)
                                .taskReceiveId(examineFlowAccount.getTaskReceiveId())
                                .examineSequence(examineFlowAccount.getExamineSequence())
                                .examineTag(flowAccount.getExamineTag())
                                .build());
                        if (examineFlowAccount1 == null && examineFlowAccount.getExamineStatus() != 20) {
                            tag = 1; // 同等级审批有不通过的
                        }
                    }
                    // 通过的情况下进行下一个审批
                    if (tag == 0) {
                        //
                        ExamineFlow examineFlow = ExamineFlow.builder()
                                .examineSequence(flowAccount.getExamineSequence())
                                .examineModuleId(27L)
                                .companyId(taskInitiate.getCompanyId())
                                .minValue(taskInitiate.getTaskScore().doubleValue())
                                .build();
                        // 审批通过，查询他的下一级审批者是否存在
                        ExamineFlowResultVO examineFlowAccountResultVO = flowService.selectNextExamineSequence(examineFlow);
                        if (examineFlowAccountResultVO != null) {
                            examineFlow.setExamineSequence(examineFlowAccountResultVO.getExamineSequence());
                            // 查询同等级审批的相关信息
                            List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                            // 将信息存储起来
                            accountResultVOList.forEach((ExamineFlowAccountResultVO accountResultVO) -> {
                                int countFlag = flowAccountService.insertTaskFlowAccount(TaskFlowAccount.builder()
                                        .taskReceiveId(flowAccount.getTaskReceiveId())
                                        .accountId(accountResultVO.getAccountId())
                                        .accountHead(accountResultVO.getAccountHead())
                                        .accountName(accountResultVO.getAccountName())
                                        .accountPhone(accountResultVO.getPhone())
                                        .postId(accountResultVO.getPostId())
                                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                        .deptId(accountResultVO.getDeptId())
                                        .examineEndTime(DateUtils.addMin(min))
                                        .examineStatus(16)
                                        .examineType(2)
                                        .examineTag(flowAccount.getExamineTag())
                                        .build());
                                if (countFlag <= 0) {
                                    throw new ApiException("添加用户审批流程失败！");
                                }
                            });
                            // 判断同等审批，如果同等审批有一个通过，那么这个发起表（examine_initiate）的总状态（examine_status）就是审批中
                            int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                    .taskReceiveId(flowAccount.getTaskReceiveId())
                                    .examineStatus(17)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }

                        } else {
                            // 将总状态改为已通过
                            int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                    .taskReceiveId(flowAccount.getTaskReceiveId())
                                    .examineStatus(20)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }
                            // 赠送积分
                            TaskReceive taskReceive = receiveService.selectTaskReceiveById(flowAccount.getTaskReceiveId());
                            if (taskReceive != null) {
                                TaskInitiate taskInitiate = initiateService.selectTaskInitiateById(taskReceive.getTaskInitiateId());
                                if (taskInitiate != null && taskInitiate.getTaskScore() > 0) {
                                    ApiAccount account = accountService.selectApiAccountById(taskReceive.getAccountId());
                                    if (account != null) {
                                        ApiAccount account1 = ApiAccount.builder()
                                                .accountId(account.getAccountId())
                                                .totalScore(account.getTotalScore() + taskInitiate.getTaskScore())
                                                .surplusScore(account.getSurplusScore() + taskInitiate.getTaskScore())
                                                .build();
                                        // 更新用户信息
                                        accountService.updateApiAccount(account1);
                                        // 用户积分存入记录
                                        // 赠送用户积分
                                        int count = accountScoreService.insertAccountScore(AccountScore.builder()
                                                .accountId(account.getAccountId())
                                                .targetId(taskReceive.getTaskReceiveId())
                                                .scoreType(0)
                                                .accountName(account.getNickName())
                                                .accountPhone(account.getPhone())
                                                .accountHead(account.getHeadPortrait())
                                                .scoreSource("任务完成奖励")
                                                .scoreStatus(1)
                                                .score(taskInitiate.getTaskScore())
                                                .build());
                                        if (count <= 0) {
                                            throw new ApiException("积分存入失败！");
                                        }
                                        // 发送系统消息
                                        // 查询消息是否存在
                                        messageRemindService.insertMessageRemind(MessageRemind.builder()
                                                .title("积分变动")
                                                .brief("任务完成奖励" + taskInitiate.getTaskScore() + "积分,点击查看")
                                                .modelStatus(2)
                                                .jumpType(-2)
                                                .modelId("0")
                                                .accountId(account.getAccountId())
                                                .build());
//                                        // 查看是否满足勋章条件并更新勋章
//                                        accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(taskReceive.getAccountId()), taskInitiate.getTaskScore(), "任务完成");
                                    }
                                }
                            }
                        }

                    } else {
                        // 只要是有一个拒绝的，总状态改为已拒绝
                        int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                                .taskReceiveId(flowAccount.getTaskReceiveId())
                                .examineStatus(18)
                                .readStatus(0)
                                .build());
                        if (countFlag <= 0) {
                            throw new ApiException("修改审批发起失败！");
                        }
                    }
                } else {
                    // 将总状态改为已拒绝
                    int countFlag = receiveService.updateTaskReceive(TaskReceive.builder()
                            .taskReceiveId(flowAccount.getTaskReceiveId())
                            .examineStatus(18)
                            .readStatus(0)
                            .build());
                    if (countFlag <= 0) {
                        throw new ApiException("修改审批发起失败！");
                    }
                }

            }
        };
    }

}
