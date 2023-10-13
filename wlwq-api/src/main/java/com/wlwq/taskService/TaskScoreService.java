package com.wlwq.taskService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.examine.ExamineSubVO;
import com.wlwq.system.service.ISysConfigService;
import com.wlwq.system.service.ISysDictDataService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * 赠送积分
 *
 * @author gaoce
 */
@Component
@AllArgsConstructor
public class TaskScoreService extends ApiController {

    private final IApiAccountService accountService;
    private final IAccountScoreService accountScoreService;
    private final ISysDeptScoreService deptScoreService;
    private final IReportTrainingService reportTrainingService;
    private final ISysDictDataService dictDataService;

    private final IExamineFlowService flowService;

    private final ISysConfigService sysConfigService;

    private final IScoreFlowAccountService flowAccountService;

    private final IScoreFlowCopyAccountService flowCopyAccountService;

    private final IExamineCopyFlowService copyFlowService;

    private final IAccountMedalRecordService accountMedalRecordService;

    private final IMessageRemindService messageRemindService;

    /**
     * 后台更新勋章奖励记录
     *
     * @param accountMedalRecord 勋章记录
     * @return
     */
    public TimerTask managerMedal(AccountMedalRecord accountMedalRecord, AccountMedal accountMedal) {
        return new TimerTask() {
            @Override
            public void run() {
                String accountId = accountMedalRecord.getAccountId();
                if (com.wlwq.common.utils.StringUtils.isNotBlank(accountId)) {
                    String[] arr = accountId.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        // 更新接收者的余额
                        ApiAccount apiAccount = accountService.selectApiAccountById(arr[i]);
                        if (apiAccount != null) {
                            // 赠送用户积分
                            int count = accountScoreService.insertAccountScore(AccountScore.builder()
                                    .accountId(apiAccount.getAccountId())
                                    .targetId("0")
                                    .scoreType(0)
                                    .accountName(apiAccount.getNickName())
                                    .accountPhone(apiAccount.getPhone())
                                    .accountHead(apiAccount.getHeadPortrait())
                                    .scoreSource(accountMedalRecord.getRemark())
                                    .scoreStatus(1)
                                    .score(accountMedalRecord.getScore())
                                    .deptId(apiAccount.getDeptId())
                                    .companyId(apiAccount.getCompanyId())
                                    .build());
                            if (count <= 0) {
                                throw new ApiException("积分存入失败！");
                            }
                            accountService.updateApiAccount(ApiAccount.builder()
                                    .accountId(apiAccount.getAccountId())
                                    .totalScore(apiAccount.getTotalScore() + accountMedalRecord.getScore())
                                    .surplusScore(apiAccount.getSurplusScore() + accountMedalRecord.getScore())
                                    .build());
                            // 发送系统消息
                            // 查询消息是否存在
                            messageRemindService.insertMessageRemind(MessageRemind.builder()
                                    .title("积分变动")
                                    .brief("恭喜您【" + accountMedalRecord.getRemark() + "】获得" + accountMedalRecord.getScore() + "积分,点击查看")
                                    .modelStatus(2)
                                    .jumpType(-2)
                                    .modelId("0")
                                    .accountId(apiAccount.getAccountId())
                                    .build());
                            // 查看是否满足勋章条件并更新勋章
                            accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(apiAccount.getAccountId()), accountMedalRecord.getScore(), accountMedalRecord.getRemark(), accountMedal);
                        }
                    }
                }
            }
        };
    }


    /**
     * 后台更新积分奖励记录
     *
     * @param accountScore 积分记录
     * @return
     */
    public TimerTask managerScore(AccountScore accountScore) {
        return new TimerTask() {
            @Override
            public void run() {
                String accountId = accountScore.getAccountId();
                if (com.wlwq.common.utils.StringUtils.isNotBlank(accountId)) {
                    String[] arr = accountId.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        // 更新接收者的余额
                        ApiAccount apiAccount = accountService.selectApiAccountById(arr[i]);
                        if (apiAccount != null) {
                            // 1:获得 2:支出
                            if (accountScore.getScoreStatus() == 1) {
                                accountService.updateApiAccount(ApiAccount.builder()
                                        .accountId(apiAccount.getAccountId())
                                        .totalScore(apiAccount.getTotalScore() + accountScore.getScore())
                                        .surplusScore(apiAccount.getSurplusScore() + accountScore.getScore())
                                        .build());
                                // 发送系统消息
                                // 查询消息是否存在
                                messageRemindService.insertMessageRemind(MessageRemind.builder()
                                        .title("积分变动")
                                        .brief("恭喜您获得【" + accountScore.getScoreSource() + "】" + accountScore.getScore() + "积分,点击查看")
                                        .modelStatus(2)
                                        .jumpType(-2)
                                        .modelId("0")
                                        .accountId(apiAccount.getAccountId())
                                        .build());
//                                // 查看是否满足勋章条件并更新勋章
//                                accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(apiAccount.getAccountId()), accountScore.getScore(), accountScore.getScoreSource());
                            }
                            if (accountScore.getScoreStatus() == 2) {
                                accountService.updateApiAccount(ApiAccount.builder()
                                        .accountId(apiAccount.getAccountId())
                                        .surplusScore(apiAccount.getSurplusScore() - accountScore.getScore())
                                        .build());
                                // 发送系统消息
                                // 查询消息是否存在
                                messageRemindService.insertMessageRemind(MessageRemind.builder()
                                        .title("积分变动")
                                        .brief("您【" + accountScore.getScoreSource() + "】扣除" + accountScore.getScore() + "积分,点击查看")
                                        .modelStatus(2)
                                        .jumpType(-2)
                                        .modelId("0")
                                        .accountId(apiAccount.getAccountId())
                                        .build());
                            }
                            // 赠送/扣除用户积分
                            int count = accountScoreService.insertAccountScore(AccountScore.builder()
                                    .accountId(apiAccount.getAccountId())
                                    .targetId("0")
                                    .scoreType(0)
                                    .accountName(apiAccount.getNickName())
                                    .accountPhone(apiAccount.getPhone())
                                    .accountHead(apiAccount.getHeadPortrait())
                                    .scoreSource(accountScore.getScoreSource())
                                    .scoreStatus(accountScore.getScoreStatus())
                                    .score(accountScore.getScore())
                                    .deptId(apiAccount.getDeptId())
                                    .companyId(apiAccount.getCompanyId())
                                    .build());
                            if (count <= 0) {
                                throw new ApiException("积分存入失败！");
                            }

                        }
                    }
                }
            }
        };
    }

    /**
     * 审批通过或拒绝之后修改总表的状态
     *
     * @param examineSubVO
     * @param flowAccount  审批详情
     * @return
     */
    public TimerTask editAccountScore(ExamineSubVO examineSubVO, ScoreFlowAccount flowAccount) {
        return new TimerTask() {
            @Override
            public void run() {
                // 审批通过之后
                if (examineSubVO.getExamineStatus() == 3) {
                    // 查询设置的审批时间(分钟)
                    String configValue = sysConfigService.selectConfigByKey("sys_examine_score_time");
                    Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                    // 查询同等审批的是否都通过，通过的情况下进行下一个审批
                    // 查询同等级审批的相关信息
                    List<ScoreFlowAccount> examineFlowAccountList = flowAccountService.selectScoreFlowAccountList(ScoreFlowAccount.builder()
                            .accountScoreId(flowAccount.getAccountScoreId())
                            .examineSequence(flowAccount.getExamineSequence())
                            .examineTag(flowAccount.getExamineTag())
                            .build());
                    Integer tag = 0;// 默认都通过
                    for (ScoreFlowAccount examineFlowAccount : examineFlowAccountList) {
                        // 查询最新的审批状态
                        ScoreFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitScoreFlowAccount(ScoreFlowAccount.builder()
                                .accountId(examineFlowAccount.getAccountId())
                                .examineStatus(3)
                                .accountScoreId(examineFlowAccount.getAccountScoreId())
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
                                .examineModuleId(30L)
                                .companyId(flowAccount.getCompanyId())
                                .build();
                        ScoreFlowAccount flowAccount = flowAccountService.selectScoreFlowAccountById(examineSubVO.getFlowAccountId());
                        if (flowAccount != null) {
                            AccountScore accountScore = accountScoreService.selectAccountScoreById(flowAccount.getAccountScoreId());
                            examineFlow.setMinValue(accountScore == null ? 0 : accountScore.getScore().doubleValue());
                        }
                        // 审批通过，查询他的下一级审批者是否存在
                        ExamineFlowResultVO examineFlowAccountResultVO = flowService.selectNextExamineSequence(examineFlow);
                        if (examineFlowAccountResultVO != null) {
                            examineFlow.setExamineSequence(examineFlowAccountResultVO.getExamineSequence());
                            // 查询同等级审批的相关信息
                            List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                            // 将信息存储起来
                            accountResultVOList.forEach((ExamineFlowAccountResultVO accountResultVO) -> {
                                int countFlag = flowAccountService.insertScoreFlowAccount(ScoreFlowAccount.builder()
                                        .accountScoreId(flowAccount.getAccountScoreId())
                                        .accountId(accountResultVO.getAccountId())
                                        .accountHead(accountResultVO.getAccountHead())
                                        .accountName(accountResultVO.getAccountName())
                                        .accountPhone(accountResultVO.getPhone())
                                        .postId(accountResultVO.getPostId())
                                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                        .deptId(accountResultVO.getDeptId())
                                        .companyId(accountResultVO.getCompanyId())
                                        .examineEndTime(DateUtils.addMin(min))
                                        .examineTag(flowAccount.getExamineTag())
                                        .build());
                                if (countFlag <= 0) {
                                    throw new ApiException("添加用户审批流程失败！");
                                }
                            });
                            // 判断同等审批，如果同等审批有一个通过，那么这个发起表（account_score）的总状态（examine_status）就是审批中
                            int countFlag = accountScoreService.updateAccountScore(AccountScore.builder()
                                    .accountScoreId(flowAccount.getAccountScoreId())
                                    .examineStatus(2)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }

                        } else {
                            // 判断是不是最后一个审批的，审批通过之后将信息保存到抄送表中
                            // 查询抄送岗位列表
                            List<ExamineCopyFlow> copyFlowList = copyFlowService.selectApiExamineCopyFlowList(ExamineCopyFlow.builder()
                                    .companyId(flowAccount.getCompanyId())
                                    .examineModuleId(30L)
                                    .build());
                            for (ExamineCopyFlow examineCopyFlow : copyFlowList) {
                                List<ApiAccount> accountList = accountService.selectApiAccountListByPostIdAndDeptId(examineCopyFlow.getDeptId(), examineCopyFlow.getPostId());
                                for (ApiAccount account : accountList) {
                                    // 存入抄送记录表
                                    flowCopyAccountService.insertScoreFlowCopyAccount(ScoreFlowCopyAccount.builder()
                                            .copyAccountId(account.getAccountId())
                                            .copyAccountName(account.getNickName())
                                            .copyAccountHead(account.getHeadPortrait())
                                            .copyAccountPhone(account.getPhone())
                                            .accountScoreId(flowAccount.getAccountScoreId())
                                            .examineStatus(3)
                                            .examineTag(flowAccount.getExamineTag())
                                            .build());
                                }
                            }
                            // 将总状态改为已通过
                            int countFlag = accountScoreService.updateAccountScore(AccountScore.builder()
                                    .accountScoreId(flowAccount.getAccountScoreId())
                                    .examineStatus(3)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }

                        }

                    } else {
                        // 有一个未完成，将总状态改为已拒绝
                        int countFlag = accountScoreService.updateAccountScore(AccountScore.builder()
                                .accountScoreId(flowAccount.getAccountScoreId())
                                .examineStatus(4)
                                .build());
                        if (countFlag <= 0) {
                            throw new ApiException("修改审批发起失败！");
                        }
                    }
                } else {
                    // 将总状态改为已拒绝
                    int countFlag = accountScoreService.updateAccountScore(AccountScore.builder()
                            .accountScoreId(flowAccount.getAccountScoreId())
                            .examineStatus(4)
                            .rejectContent(examineSubVO.getContent())
                            .pics(examineSubVO.getPics())
                            .build());
                    if (countFlag <= 0) {
                        throw new ApiException("修改审批发起失败！");
                    }
                    // 生成一条退回的记录
                    AccountScore accountScore = accountScoreService.selectAccountScoreById(flowAccount.getAccountScoreId());
                    if (accountScore != null) {
                        ApiAccount account = accountService.selectApiAccountById(accountScore.getAccountId());
                        if (account != null) {
                            // 赠送用户积分
                            int count = accountScoreService.insertAccountScore(AccountScore.builder()
                                    .accountId(accountScore.getAccountId())
                                    .targetId(accountScore.getTargetId())
                                    .scoreType(-1)
                                    .accountName(account.getNickName())
                                    .accountPhone(account.getPhone())
                                    .accountHead(account.getHeadPortrait())
                                    .scoreSource("积分兑换拒绝后退回积分")
                                    .scoreStatus(1)
                                    .score(accountScore.getScore())
                                    .deptId(account.getDeptId())
                                    .companyId(account.getCompanyId())
                                    .build());
                            if (count <= 0) {
                                throw new ApiException("积分存入失败！");
                            }
                            // 更新用户信息
                            accountService.updateApiAccount(ApiAccount.builder()
                                    .accountId(account.getAccountId())
                                    .surplusScore(account.getSurplusScore() + accountScore.getScore())
                                    .build());
                            // 发送系统消息
                            // 查询消息是否存在
                            messageRemindService.insertMessageRemind(MessageRemind.builder()
                                    .title("积分变动")
                                    .brief("积分兑换拒绝后退回" + accountScore.getScore() + "积分,点击查看")
                                    .modelStatus(2)
                                    .jumpType(-2)
                                    .modelId("0")
                                    .accountId(account.getAccountId())
                                    .build());
                        }
                    }


                }

                // 未按时审核扣除积分
                ScoreFlowAccount flowAccount = flowAccountService.selectScoreFlowAccountById(examineSubVO.getFlowAccountId());
                if (flowAccount != null && System.currentTimeMillis() > flowAccount.getExamineEndTime().getTime()) {
                    // 扣除用户积分
                    // 异步操作，扣除积分
                    AsyncManager.me().execute(oaExamineScore(flowAccount.getScoreFlowAccountId(), accountService.selectApiAccountById(flowAccount.getAccountId()), 22));
                }

            }
        };
    }


    /**
     * 将部门领导审批流程的人员信息存到记录表中
     *
     * @param account
     * @param accountScoreId 积分记录ID
     * @param examineTag     审批唯一标识
     * @return
     */
    public TimerTask examineFlowDeptAccount(ApiAccount account, String accountScoreId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {

                // 查询设置的审批时间(分钟)
                String configValue = sysConfigService.selectConfigByKey("sys_examine_score_time");
                Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                int count = flowAccountService.insertScoreFlowAccount(ScoreFlowAccount.builder()
                        .accountScoreId(accountScoreId)
                        .accountId(account.getAccountId())
                        .accountHead(account.getHeadPortrait())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .postId(account.getPostId())
                        .examineSequence(0)
                        .deptId(account.getDeptId())
                        .companyId(account.getCompanyId())
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
     * @param accountScoreId 积分记录ID
     * @param examineTag     审批唯一标识
     * @return
     */
    public TimerTask examineFlowAccount(ExamineFlow examineFlow, String accountScoreId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                // 根据模块和类型查询统一审批等级查看审批顺序最小值
                ExamineFlowResultVO flowAccountResultVO = flowService.selectMinExamineGroupBySequence(examineFlow);
                if (flowAccountResultVO != null) {
                    // 查询设置的审批时间(分钟)
                    String configValue = sysConfigService.selectConfigByKey("sys_examine_score_time");
                    Integer min = com.wlwq.common.utils.StringUtils.isNotEmpty(configValue) ? Convert.toInt(configValue) : 0;
                    // 将需要审批的第一级存起来
                    // 查询用户信息
                    examineFlow.setExamineSequence(flowAccountResultVO.getExamineSequence());
                    List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                    accountResultVOList.forEach((ExamineFlowAccountResultVO examineFlowAccountResultVO) -> {
                        int count = flowAccountService.insertScoreFlowAccount(ScoreFlowAccount.builder()
                                .accountScoreId(accountScoreId)
                                .accountId(examineFlowAccountResultVO.getAccountId())
                                .accountHead(examineFlowAccountResultVO.getAccountHead())
                                .accountName(examineFlowAccountResultVO.getAccountName())
                                .accountPhone(examineFlowAccountResultVO.getPhone())
                                .postId(examineFlowAccountResultVO.getPostId())
                                .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                .deptId(examineFlowAccountResultVO.getDeptId())
                                .companyId(examineFlowAccountResultVO.getCompanyId())
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


    /**
     * 未在规定的时间内审批扣除积分（OA 及 积分兑换）
     *
     * @param flowerAccountId 用户审批ID
     * @param account         用户
     * @param type            sys_set_score字典表的字典值
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask oaExamineScore(String flowerAccountId, ApiAccount account, Integer type) {
        return new TimerTask() {
            @Override
            public void run() {
                // 查询在规定的时间内审批扣除积分
                int selectScore = selectScore(account, type);
                if (selectScore > 0) {
                    ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
                    if (account1 != null) {
                        // 更新用户信息
                        accountService.updateApiAccount(ApiAccount.builder()
                                .accountId(account1.getAccountId())
                                .surplusScore(account1.getSurplusScore() - selectScore)
                                .build());
                        // 用户积分存入记录
                        insertScore(flowerAccountId, account1, scoreSource(type), selectScore, 2, type, "您未在规定的时间内审批【" + scoreSource(type) + "】,扣除" + selectScore + "积分，点击查看");
                    }


                }
            }
        };
    }


    /**
     * 考勤打卡赠送积分
     *
     * @param clockingType      0：未打卡 1：正常打卡/外勤打卡 2：外出签到
     * @param clockingStatus    0：未打卡 1：正常 2：迟到 3：早退 4：缺卡 5：旷工 6：补卡
     * @param accountClockingId 用户签到ID
     * @param account           用户
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask clockingScore(Integer clockingType, Integer clockingStatus, String accountClockingId, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                if (clockingStatus == 1) {
                    Integer score = 0;
                    // 正常打卡送积分
                    if (clockingType == 1) {
                        // 每次提交都赠送积分
                        int selectScore = selectScore(account, 18);
                        if (selectScore > 0) {
                            // 用户积分存入记录
                            insertScore(accountClockingId, account, scoreSource(18), selectScore, 1, 18, "恭喜您打卡成功，获得" + selectScore + "积分,点击查看");
                            // 赠送用户积分
                            score += selectScore;
                        }

                    }
                    if (score > 0) {
                        ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
                        if (account1 != null) {
                            // 更新用户信息
                            accountService.updateApiAccount(ApiAccount.builder()
                                    .accountId(account1.getAccountId())
                                    .totalScore(account1.getTotalScore() + score)
                                    .surplusScore(account1.getSurplusScore() + score)
                                    .build());
//                            // 查看是否满足勋章条件并更新勋章
//                            accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "考勤打卡赠送积分");
                        }

                    }
                }

            }
        };
    }


    /**
     * 签到打卡赠送积分
     *
     * @param accountSignRecordId 用户签到ID
     * @param account             用户
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask signScore(String accountSignRecordId, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                Integer score = 0;
                // 每次提交都赠送积分
                int selectScore = selectScore(account, 19);
                if (selectScore > 0) {
                    // 用户积分存入记录
                    insertScore(accountSignRecordId, account, scoreSource(19), selectScore, 1, 19, "恭喜您签到成功，获得" + selectScore + "积分,点击查看");
                    // 赠送用户积分
                    score += selectScore;
                }
                if (score > 0) {
                    ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
                    if (account1 != null) {
                        // 更新用户信息
                        accountService.updateApiAccount(ApiAccount.builder()
                                .accountId(account1.getAccountId())
                                .totalScore(account1.getTotalScore() + score)
                                .surplusScore(account1.getSurplusScore() + score)
                                .build());
//                            // 查看是否满足勋章条件并更新勋章
//                            accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "考勤打卡赠送积分");
                    }

                }
            }
        };
    }

    /**
     * 汇报训练赠送积分
     *
     * @param params  汇报训练
     * @param account 用户
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask reportTrainingScore(ReportTraining params, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                // 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
                Integer templateType = params.getTemplateType();
                if (templateType == 1) {
                    reportTrainingScoreRecord(params, account, 1, 2);
                }
                if (templateType == 2) {
                    reportTrainingScoreRecord(params, account, 4, 5);
                }
                if (templateType == 3) {
                    reportTrainingScoreRecord(params, account, 6, 7);
                }
                if (templateType == 4) {
                    reportTrainingScoreRecord(params, account, 8, 9);
                }
                if (templateType == 5) {
                    reportTrainingScoreRecord(params, account, 10, 11);
                }
                if (templateType == 6) {
                    reportTrainingScoreRecord(params, account, 12, 13);
                }
                if (templateType == 7) {
                    reportTrainingScoreRecord(params, account, 14, 15);
                }
                if (templateType == 8) {
                    reportTrainingScoreRecord(params, account, 16, 17);
                }
            }
        };
    }

    /**
     * 汇报训练删除积分
     *
     * @param training 汇报训练
     * @param account  用户
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask reportTrainingDeleteScore(ReportTraining training, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                // 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
                Integer templateType = training.getTemplateType();
                if (templateType == 1) {
                    reportTrainingDeleteScoreRecord(training, account, 1, 2);
                }
                if (templateType == 2) {
                    reportTrainingDeleteScoreRecord(training, account, 4, 5);
                }
                if (templateType == 3) {
                    reportTrainingDeleteScoreRecord(training, account, 6, 7);
                }
                if (templateType == 4) {
                    reportTrainingDeleteScoreRecord(training, account, 8, 9);
                }
                if (templateType == 5) {
                    reportTrainingDeleteScoreRecord(training, account, 10, 11);
                }
                if (templateType == 6) {
                    reportTrainingDeleteScoreRecord(training, account, 12, 13);
                }
                if (templateType == 7) {
                    reportTrainingDeleteScoreRecord(training, account, 14, 15);
                }
                if (templateType == 8) {
                    reportTrainingDeleteScoreRecord(training, account, 16, 17);
                }
            }
        };
    }

    /**
     * 汇报训练积分记录()删除
     *
     * @param training
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public void reportTrainingDeleteScoreRecord(ReportTraining training, ApiAccount account, Integer subType, Integer fileType) {
        // 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
        Integer templateType = training.getTemplateType();
        // 赠送积分
        Integer score = 0;
        // 每次提交都赠送积分
        int selectScore1 = selectScore(account, subType);
        if (selectScore1 > 0) {
            // 用户积分存入记录
            insertScore(training.getReportTrainingId(), account, scoreSource(subType), selectScore1, 2, subType, "您【" + scoreSource(subType) + "】" + selectScore1 + "积分已失效");
            // 赠送用户积分
            score -= selectScore1;
        }
        // 判断是否上传文件 0：否 1:是
        if (training.getFileTag() != null && training.getFileTag() == 1) {
            int selectScore = selectScore(account, fileType);
            if (selectScore > 0) {
                // 用户积分存入记录
                insertScore(training.getReportTrainingId(), account, scoreSource(fileType), selectScore, 2, fileType, "您【" + scoreSource(subType) + "】" + selectScore + "积分已失效");
                // 赠送用户积分
                score -= selectScore;
            }
        }
        if (templateType == 1) {
            // 日精进判断是否满勤
            // 查询当月的天数
            String month = DateUtil.format(new Date(), "yyyy-MM");
            int day = DateUtils.day(month);
            int reportCount = reportTrainingService.selectApiReportTrainingCount(ReportTraining.builder()
                    .accountId(account.getAccountId())
                    .templateType(1)
                    .month(month)
                    .build());
            if (reportCount >= day) {
                // 日精进满勤赠送积分
                int selectScore = selectScore(account, 3);
                if (selectScore > 0) {
                    // 用户积分存入记录
                    insertScore(training.getReportTrainingId(), account, scoreSource(3), selectScore, 2, 3, "恭喜您【" + scoreSource(3) + "】获得" + selectScore + "积分,点击查看");
                    // 赠送用户积分
                    score -= selectScore;
                }
            }
        }

        ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
        if (account1 != null) {
            // 更新用户信息
            accountService.updateApiAccount(ApiAccount.builder()
                    .accountId(account1.getAccountId())
                    .totalScore(account1.getTotalScore() + score)
                    .surplusScore(account1.getSurplusScore() + score)
                    .build());
//                // 查看是否满足勋章条件并更新勋章
//                accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "汇报训练积分");
        }
    }

    /**
     * 汇报训练积分记录
     *
     * @param params
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public void reportTrainingScoreRecord(ReportTraining params, ApiAccount account, Integer subType, Integer fileType) {
        // 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
        Integer templateType = params.getTemplateType();
        // 赠送积分
        Integer score = 0;
        // 每次提交都赠送积分
        int selectScore1 = selectScore(account, subType);
        if (selectScore1 > 0) {
            // 用户积分存入记录
            insertScore(params.getReportTrainingId(), account, scoreSource(subType), selectScore1, 1, subType, "恭喜您【" + scoreSource(subType) + "】获得" + selectScore1 + "积分,点击查看");
            // 赠送用户积分
            score += selectScore1;
        }
        // 判断是否上传文件 0：否 1:是
        if (params.getFileTag() != null && params.getFileTag() == 1) {
            int selectScore = selectScore(account, fileType);
            if (selectScore > 0) {
                // 用户积分存入记录
                insertScore(params.getReportTrainingId(), account, scoreSource(fileType), selectScore, 1, fileType, "恭喜您【" + scoreSource(subType) + "】获得" + selectScore + "积分,点击查看");
                // 赠送用户积分
                score += selectScore;
            }
        }
        if (templateType == 1) {
            // 日精进判断是否满勤
            // 查询当月的天数
            String month = DateUtil.format(new Date(), "yyyy-MM");
            int day = DateUtils.day(month);
            int reportCount = reportTrainingService.selectApiReportTrainingCount(ReportTraining.builder()
                    .accountId(account.getAccountId())
                    .templateType(1)
                    .month(month)
                    .build());
            if (reportCount >= day) {
                // 日精进满勤赠送积分
                int selectScore = selectScore(account, 3);
                if (selectScore > 0) {
                    // 用户积分存入记录
                    insertScore(params.getReportTrainingId(), account, scoreSource(3), selectScore, 1, 3, "恭喜您【" + scoreSource(3) + "】获得" + selectScore + "积分,点击查看");
                    // 赠送用户积分
                    score -= selectScore;
                }
            }
        }
        ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
        if (score > 0) {
            if (account1 != null) {
                // 更新用户信息
                accountService.updateApiAccount(ApiAccount.builder()
                        .accountId(account1.getAccountId())
                        .totalScore(account1.getTotalScore() + score)
                        .surplusScore(account1.getSurplusScore() + score)
                        .build());
//                // 查看是否满足勋章条件并更新勋章
//                accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "汇报训练积分");
            }
        }

    }


    /**
     * 查询积分来源
     * type 积分类型
     *
     * @return
     */
    public String scoreSource(Integer type) {
        // 根据库条件查询名称
        String label = dictDataService.selectDictLabel("sys_set_score", Convert.toStr(type));
        return StringUtils.isBlank(label) ? "积分收入" : label;
    }

    /**
     * @param account
     * @param type    积分类型
     * @return
     */
    public int selectScore(ApiAccount account, Integer type) {
        SysDeptScore sysDeptScore = deptScoreService.selectSysDeptScore(SysDeptScore.builder()
                .deptId(account.getCompanyId())
                .sysSetScore(type)
                .build());
        return sysDeptScore == null ? 0 : (sysDeptScore.getScore() < 0 ? 0 : sysDeptScore.getScore());
    }

    /**
     * 积分存入
     * targetId 相关ID
     * type 积分来源
     * scoreSource 积分来源名称
     * score 积分
     * scoreStatus 1:获得 2:支出
     * messageContent 消息内容
     */
    public void insertScore(String targetId, ApiAccount account, String scoreSource, Integer score, Integer scoreStatus, Integer type, String messageContent) {
        AccountScore accountScore = AccountScore.builder()
                .accountId(account.getAccountId())
                .targetId(targetId)
                .scoreType(type)
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .scoreSource(scoreSource)
                .scoreStatus(scoreStatus)
                .score(score)
                .deptId(account.getDeptId())
                .companyId(account.getCompanyId())
                .build();
        if (scoreStatus == 2) {
            if (type == 1 || type == 2 || type == 4 || type == 5 || type == 6 || type == 7 || type == 8 || type == 9 || type == 10 || type == 11 || type == 12 || type == 13 || type == 14 || type == 15 || type == 16 || type == 17 || type == 34 || type == 35) {
                accountScore.setExamineStatus(3);
            }
        }
        // 赠送用户积分
        int count = accountScoreService.insertAccountScore(accountScore);
        if (count <= 0) {
            throw new ApiException("积分存入失败！");
        }
        // 发送系统消息
        // 查询消息是否存在
        messageRemindService.insertMessageRemind(MessageRemind.builder()
                .title("积分变动")
                .brief(messageContent)
                .modelStatus(2)
                .jumpType(-2)
                .modelId(targetId)
                .accountId(account.getAccountId())
                .build());
    }

    /**
     * 添加积分
     *
     * @param account
     * @param accountScore
     * @param messageRemind
     */
    public void insertIntegralScore(ApiAccount account, AccountScore accountScore, MessageRemind messageRemind) {
        accountService.updateApiAccount(account);
        // 查看是否满足勋章条件并更新勋章
        //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "转训审核通过赠送积分");
        // 用户积分存入记录
        // 赠送用户积分
        accountScoreService.insertAccountScore(accountScore);
        // 发送系统消息
        // 查询消息是否存在
        messageRemindService.insertMessageRemind(messageRemind);
    }

    /**
     * 六大架构提交
     *
     * @param params
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask sixStructuresScore(SixStructures params, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                sixStructuresScoreRecord(params, account, 34, 35);
            }
        };
    }

    /**
     * 汇报训练积分记录
     *
     * @param params
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public void sixStructuresScoreRecord(SixStructures params, ApiAccount account, Integer subType, Integer fileType) {
        // 赠送积分
        Integer score = 0;
        // 每次提交都赠送积分
        int selectScore1 = selectScore(account, subType);
        if (selectScore1 > 0) {
            // 用户积分存入记录
            insertScore(params.getSixStructuresId(), account, scoreSource(subType), selectScore1, 1, subType, "恭喜您【" + scoreSource(subType) + "】获得" + selectScore1 + "积分,点击查看");
            // 赠送用户积分
            score += selectScore1;
        }
        // 判断是否上传文件 0：否 1:是
        if (params.getFileTag() != null && params.getFileTag() == 1) {
            int selectScore = selectScore(account, fileType);
            if (selectScore > 0) {
                // 用户积分存入记录
                insertScore(params.getSixStructuresId(), account, scoreSource(fileType), selectScore, 1, fileType, "恭喜您【" + scoreSource(subType) + "】获得" + selectScore + "积分,点击查看");
                // 赠送用户积分
                score += selectScore;
            }
        }
        ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
        if (score > 0) {
            if (account1 != null) {
                // 更新用户信息
                accountService.updateApiAccount(ApiAccount.builder()
                        .accountId(account1.getAccountId())
                        .totalScore(account1.getTotalScore() + score)
                        .surplusScore(account1.getSurplusScore() + score)
                        .build());
//                // 查看是否满足勋章条件并更新勋章
//                accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "汇报训练积分");
            }
        }

    }

    /**
     * 六大架构删除
     *
     * @param sixStructures
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask reportSixStructuresDeleteScore(SixStructures sixStructures, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                sixStructuresScoreDeleteRecord(sixStructures, account, 34, 35);
            }
        };
    }

    /**
     * 汇报训练积分记录()删除
     *
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public void sixStructuresScoreDeleteRecord(SixStructures sixStructures, ApiAccount account, Integer subType, Integer fileType) {
        // 赠送积分
        Integer score = 0;
        // 每次提交都赠送积分
        int selectScore1 = selectScore(account, subType);
        if (selectScore1 > 0) {
            // 用户积分存入记录
            insertScore(sixStructures.getSixStructuresId(), account, scoreSource(subType), selectScore1, 2, subType, "您【" + scoreSource(subType) + "】" + selectScore1 + "积分已失效");
            // 赠送用户积分
            score -= selectScore1;
        }
        // 判断是否上传文件 0：否 1:是
        if (sixStructures.getFileTag() != null && sixStructures.getFileTag() == 1) {
            int selectScore = selectScore(account, fileType);
            if (selectScore > 0) {
                // 用户积分存入记录
                insertScore(sixStructures.getSixStructuresId(), account, scoreSource(fileType), selectScore, 2, fileType, "您【" + scoreSource(subType) + "】" + selectScore + "积分已失效");
                // 赠送用户积分
                score -= selectScore;
            }
        }

        ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
        if (account1 != null) {
            // 更新用户信息
            accountService.updateApiAccount(ApiAccount.builder()
                    .accountId(account1.getAccountId())
                    .totalScore(account1.getTotalScore() + score)
                    .surplusScore(account1.getSurplusScore() + score)
                    .build());
//                // 查看是否满足勋章条件并更新勋章
//                accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "汇报训练积分");
        }
    }





    ///////
    //////
    /////
    ////
    ///
    //

    /**
     * @author dxy
     */

    /**
     * 四类关系提交
     *
     * @param params
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask fourRelationshipsScore(FourRelationships params, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                fourRelationshipsScoreRecord(params, account, 34, 35);
            }
        };
    }

    /**
     * 汇报训练积分记录
     *
     * @param params
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public void fourRelationshipsScoreRecord(FourRelationships params, ApiAccount account, Integer subType, Integer fileType) {
        // 赠送积分
        Integer score = 0;
        // 每次提交都赠送积分
        int selectScore1 = selectScore(account, subType);
        if (selectScore1 > 0) {
            // 用户积分存入记录
            insertScore(params.getFourRelationshipsId(), account, scoreSource(subType), selectScore1, 1, subType, "恭喜您【" + scoreSource(subType) + "】获得" + selectScore1 + "积分,点击查看");
            // 赠送用户积分
            score += selectScore1;
        }
        // 判断是否上传文件 0：否 1:是
        if (params.getFileTag() != null && params.getFileTag() == 1) {
            int selectScore = selectScore(account, fileType);
            if (selectScore > 0) {
                // 用户积分存入记录
                insertScore(params.getFourRelationshipsId(), account, scoreSource(fileType), selectScore, 1, fileType, "恭喜您【" + scoreSource(subType) + "】获得" + selectScore + "积分,点击查看");
                // 赠送用户积分
                score += selectScore;
            }
        }
        ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
        if (score > 0) {
            if (account1 != null) {
                // 更新用户信息
                accountService.updateApiAccount(ApiAccount.builder()
                        .accountId(account1.getAccountId())
                        .totalScore(account1.getTotalScore() + score)
                        .surplusScore(account1.getSurplusScore() + score)
                        .build());
//                // 查看是否满足勋章条件并更新勋章
//                accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "汇报训练积分");
            }
        }

    }

    /**
     * 四类关系删除
     *
     * @param fourRelationships
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TimerTask reportFourRelationshipsDeleteScore(FourRelationships fourRelationships, ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                fourRelationshipsScoreDeleteRecord(fourRelationships, account, 34, 35);
            }
        };
    }

    /**
     * 汇报训练积分记录()删除
     *
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public void fourRelationshipsScoreDeleteRecord(FourRelationships fourRelationships, ApiAccount account, Integer subType, Integer fileType) {
        // 赠送积分
        Integer score = 0;
        // 每次提交都赠送积分
        int selectScore1 = selectScore(account, subType);
        if (selectScore1 > 0) {
            // 用户积分存入记录
            insertScore(fourRelationships.getFourRelationshipsId(), account, scoreSource(subType), selectScore1, 2, subType, "您【" + scoreSource(subType) + "】" + selectScore1 + "积分已失效");
            // 赠送用户积分
            score -= selectScore1;
        }
        // 判断是否上传文件 0：否 1:是
        if (fourRelationships.getFileTag() != null && fourRelationships.getFileTag() == 1) {
            int selectScore = selectScore(account, fileType);
            if (selectScore > 0) {
                // 用户积分存入记录
                insertScore(fourRelationships.getFourRelationshipsId(), account, scoreSource(fileType), selectScore, 2, fileType, "您【" + scoreSource(subType) + "】" + selectScore + "积分已失效");
                // 赠送用户积分
                score -= selectScore;
            }
        }

        ApiAccount account1 = accountService.selectApiAccountById(account.getAccountId());
        if (account1 != null) {
            // 更新用户信息
            accountService.updateApiAccount(ApiAccount.builder()
                    .accountId(account1.getAccountId())
                    .totalScore(account1.getTotalScore() + score)
                    .surplusScore(account1.getSurplusScore() + score)
                    .build());
//                // 查看是否满足勋章条件并更新勋章
//                accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "汇报训练积分");
        }
    }

}

