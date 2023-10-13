package com.wlwq.taskService;

import cn.hutool.core.convert.Convert;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.params.examine.ExamineSubVO;
import com.wlwq.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author gaoce
 */
@Component
@AllArgsConstructor
public class TaskMeetingExamineFlowAccountService extends ApiController {

    private final IMeetingExamineFlowService flowService;

    private final IMeetingExamineFlowAccountService flowAccountService;

    private final IApiAccountService accountService;

    private final IMeetingExamineInitiateService initiateService;

    private final IExamineFileService fileService;

    private final ITargetTrainingRecordService targetTrainingRecordService;

    private final IMessageRemindService messageRemindService;

    private final TaskScoreService taskScoreService;

    private final IAccountScoreService accountScoreService;

    private final ICustomUserClaimService customUserClaimService;

    private final IMeetingTrainingService meetingTrainingService;

    /**
     * 将部门领导审批流程的人员信息存到记录表中
     *
     * @param account
     * @param examineInitiateId 员工审批发起ID
     * @param examineTag        审批唯一标识
     * @return
     */
    public TimerTask examineFlowDeptAccount(ApiAccount account, String examineInitiateId, String examineTag) {
        return new TimerTask() {
            @Override
            public void run() {
                int count = flowAccountService.insertMeetingExamineFlowAccount(MeetingExamineFlowAccount.builder()
                        .examineInitiateId(examineInitiateId)
                        .accountId(account.getAccountId())
                        .accountHead(account.getHeadPortrait())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .postId(account.getPostId())
                        .examineSequence(0)
                        .deptId(account.getDeptId())
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
     * @param examineTag        审批唯一标识
     * @return
     */
    public TimerTask examineFlowAccount(MeetingExamineFlow examineFlow, String examineInitiateId, String examineTag) {
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
                        int count = flowAccountService.insertMeetingExamineFlowAccount(MeetingExamineFlowAccount.builder()
                                .examineInitiateId(examineInitiateId)
                                .accountId(examineFlowAccountResultVO.getAccountId())
                                .accountHead(examineFlowAccountResultVO.getAccountHead())
                                .accountName(examineFlowAccountResultVO.getAccountName())
                                .accountPhone(examineFlowAccountResultVO.getPhone())
                                .postId(examineFlowAccountResultVO.getPostId())
                                .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                                .deptId(examineFlowAccountResultVO.getDeptId())
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
                    List<MeetingExamineFlowAccount> examineFlowAccountList = flowAccountService.selectMeetingExamineFlowAccountList(MeetingExamineFlowAccount.builder()
                            .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                            .examineSequence(Convert.toInt(map.get("examineSequence")))
                            .examineTag(Convert.toStr(map.get("examineTag")))
                            .build());
                    Integer tag = 0;// 默认都通过
                    for (MeetingExamineFlowAccount examineFlowAccount : examineFlowAccountList) {
                        // 查询最新的审批状态
                        MeetingExamineFlowAccount examineFlowAccount1 = flowAccountService.selectNearLimitExamineFlowAccount(MeetingExamineFlowAccount.builder()
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
                        MeetingExamineFlow examineFlow = MeetingExamineFlow.builder()
                                .examineSequence(Convert.toInt(map.get("examineSequence")))
                                .examineModuleId(Convert.toLong(map.get("examineModuleId")))
                                .companyId(Convert.toLong(map.get("companyId")))
                                .minValue(Convert.toBigDecimal(map.get("customeStrikeMoney")))
                                .build();
                        // 审批通过，查询他的下一级审批者是否存在
                        ExamineFlowResultVO examineFlowAccountResultVO = flowService.selectNextExamineSequence(examineFlow);
                        if (examineFlowAccountResultVO != null) {
                            examineFlow.setExamineSequence(examineFlowAccountResultVO.getExamineSequence());
                            // 查询同等级审批的相关信息
                            List<ExamineFlowAccountResultVO> accountResultVOList = flowService.selectAppExamineFlowList(examineFlow);
                            // 将信息存储起来
                            accountResultVOList.forEach((ExamineFlowAccountResultVO accountResultVO) -> {
                                int countFlag = flowAccountService.insertMeetingExamineFlowAccount(MeetingExamineFlowAccount.builder()
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
                            int countFlag = initiateService.updateMeetingExamineInitiate(MeetingExamineInitiate.builder()
                                    .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                                    .examineStatus(2)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }
                        } else {
                            // 将总状态改为已通过
                            int countFlag = initiateService.updateMeetingExamineInitiate(MeetingExamineInitiate.builder()
                                    .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                                    .examineStatus(3)
                                    .readStatus(0)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }

                            MeetingExamineInitiate initiate = initiateService.selectMeetingExamineInitiateById(Convert.toStr(map.get("examineInitiateId")));
                            if (initiate != null) {
                                ApiAccount account = accountService.selectApiAccountById(initiate.getAccountId());
//                                if (initiate.getMeetingType() == 1 && map.get("examineModuleId").toString().equals("24")) {
                                if ((map.get("examineModuleId").toString().equals("24"))) {
                                    //转训审核通过给积分
                                    TargetTrainingRecord targetTrainingRecord = targetTrainingRecordService.selectTargetTrainingRecordById(map.get("meetingTrainingId").toString());
                                    if (targetTrainingRecord != null) {
                                        countFlag = targetTrainingRecordService.updateTargetTrainingRecord(TargetTrainingRecord
                                                .builder()
                                                .targetTrainingRecordId(targetTrainingRecord.getTargetTrainingRecordId())
                                                .auditStatus(3)
                                                .build());
                                        if (countFlag <= 0) {
                                            throw new ApiException("修改审批发起失败！");
                                        }
                                    }

                                    if (account != null) {
                                        //获取转训记录对应的会议对象，再获取对应的课程id；
                                        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(initiate.getMeetingTrainingId());
                                        int score = taskScoreService.selectScore(account, 29);
                                        // 更新用户信息
                                        accountService.updateApiAccount(ApiAccount.builder()
                                                .accountId(account.getAccountId())
                                                .surplusScore(account.getSurplusScore() + score)
                                                .totalScore(account.getTotalScore() + score)
                                                .build());
                                        // 查看是否满足勋章条件并更新勋章
                                        //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "转训审核通过赠送积分");
                                        // 用户积分存入记录
                                        // 赠送用户积分
                                        accountScoreService.insertAccountScore(AccountScore.builder()
                                                .accountId(account.getAccountId())
                                                .targetId(initiate.getExamineInitiateId())
                                                .scoreType(-7)
                                                .accountName(account.getNickName())
                                                .accountPhone(account.getPhone())
                                                .accountHead(account.getHeadPortrait())
                                                .scoreSource(taskScoreService.scoreSource(29))
                                                .scoreStatus(1)
                                                .score(score)
//                                                .courseId(meetingTraining == null ? 0L : meetingTraining.getCourseId())
                                                .build());
                                        // 发送系统消息
                                        // 查询消息是否存在
                                        messageRemindService.insertMessageRemind(MessageRemind.builder()
                                                .title("积分变动")
                                                .brief("会议训练获得" + score + "积分,点击查看")
                                                .modelStatus(2)
                                                .jumpType(-2)
                                                .modelId("24")
                                                .accountId(account.getAccountId())
                                                .build());
                                    }
                                }
                                else if (map.get("examineModuleId").toString().equals("28")) {
                                    //人生目标审核
                                    TargetTrainingRecord targetTrainingRecord = targetTrainingRecordService.selectTargetTrainingRecordById(map.get("meetingTrainingId").toString());
                                    if (targetTrainingRecord != null) {
                                        countFlag = targetTrainingRecordService.updateTargetTrainingRecord(TargetTrainingRecord
                                                .builder()
                                                .targetTrainingRecordId(targetTrainingRecord.getTargetTrainingRecordId())
                                                .auditStatus(3)
                                                .build());
                                        if (countFlag <= 0) {
                                            throw new ApiException("修改审批发起失败！");
                                        }
                                    }

                                    if (account != null) {
                                        int score = taskScoreService.selectScore(account, 23);
                                        // 更新用户信息
                                        accountService.updateApiAccount(ApiAccount.builder()
                                                .accountId(account.getAccountId())
                                                .surplusScore(account.getSurplusScore() + score)
                                                .totalScore(account.getTotalScore() + score)
                                                .build());
                                        // 查看是否满足勋章条件并更新勋章
                                        //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "人生目标审核通过赠送积分");
                                        // 用户积分存入记录
                                        // 赠送用户积分
                                        accountScoreService.insertAccountScore(AccountScore.builder()
                                                .accountId(account.getAccountId())
                                                .targetId(initiate.getExamineInitiateId())
                                                .scoreType(0)
                                                .accountName(account.getNickName())
                                                .accountPhone(account.getPhone())
                                                .accountHead(account.getHeadPortrait())
                                                .scoreSource(taskScoreService.scoreSource(23))
                                                .scoreStatus(1)
                                                .score(score)
                                                .build());
                                        // 发送系统消息
                                        // 查询消息是否存在
                                        messageRemindService.insertMessageRemind(MessageRemind.builder()
                                                .title("积分变动")
                                                .brief("人生目标审核通过,获得" + score + "积分,点击查看")
                                                .modelStatus(2)
                                                .jumpType(-2)
                                                .modelId("28")
                                                .accountId(account.getAccountId())
                                                .build());
                                    }
                                } else if (map.get("examineModuleId").toString().equals("29") && account != null) {
                                    int score = taskScoreService.selectScore(account, 24);
                                    //人生目标实现情况审核
                                    // 更新用户信息
                                    accountService.updateApiAccount(ApiAccount.builder()
                                            .accountId(account.getAccountId())
                                            .surplusScore(account.getSurplusScore() + score)
                                            .totalScore(account.getTotalScore() + score)
                                            .build());
                                    // 查看是否满足勋章条件并更新勋章
                                    //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "人生目标实现赠送积分");
                                    // 用户积分存入记录
                                    // 赠送用户积分
                                    accountScoreService.insertAccountScore(AccountScore.builder()
                                            .accountId(account.getAccountId())
                                            .targetId(initiate.getExamineInitiateId())
                                            .scoreType(0)
                                            .accountName(account.getNickName())
                                            .accountPhone(account.getPhone())
                                            .accountHead(account.getHeadPortrait())
                                            .scoreSource(taskScoreService.scoreSource(24))
                                            .scoreStatus(1)
                                            .score(score)
                                            .build());
                                    // 发送系统消息
                                    // 查询消息是否存在
                                    messageRemindService.insertMessageRemind(MessageRemind.builder()
                                            .title("积分变动")
                                            .brief("人生目标实现审核通过,获得" + score + "积分,点击查看")
                                            .modelStatus(2)
                                            .jumpType(-2)
                                            .modelId("29")
                                            .accountId(account.getAccountId())
                                            .build());
                                } else if (map.get("examineModuleId").toString().equals("31") && account != null) {
                                    customUserClaimService.updateCustomUserClaimStatus(map.get("customId").toString(), initiate.getMeetingTrainingId());
                                    //int score = taskScoreService.selectScore(account, 25);
                                    int score = 0;
                                    if(examineSubVO.getIntegral() != null){
                                        score = examineSubVO.getIntegral();
                                    }
                                    //客户成交
                                    // 更新用户信息
                                    accountService.updateApiAccount(ApiAccount.builder()
                                            .accountId(account.getAccountId())
                                            .surplusScore(account.getSurplusScore() + score)
                                            .totalScore(account.getTotalScore() + score)
                                            .build());
                                    // 查看是否满足勋章条件并更新勋章
                                    //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "客户成交赠送积分");
                                    // 用户积分存入记录
                                    // 赠送用户积分
                                    accountScoreService.insertAccountScore(AccountScore.builder()
                                            .accountId(account.getAccountId())
                                            .targetId(initiate.getExamineInitiateId())
                                            .scoreType(0)
                                            .accountName(account.getNickName())
                                            .accountPhone(account.getPhone())
                                            .accountHead(account.getHeadPortrait())
                                            .scoreSource(taskScoreService.scoreSource(25))
                                            .scoreStatus(1)
                                            .score(score)
                                            .build());
                                    // 发送系统消息
                                    // 查询消息是否存在
                                    messageRemindService.insertMessageRemind(MessageRemind.builder()
                                            .title("积分变动")
                                            .brief("客户成交审核通过,获得" + score + "积分,点击查看")
                                            .modelStatus(2)
                                            .jumpType(-2)
                                            .modelId("31")
                                            .accountId(account.getAccountId())
                                            .build());
                                }
                            }
                        }
                    } else {
                        // 有一个拒绝，将总状态改为已拒绝
                        int countFlag = initiateService.updateMeetingExamineInitiate(MeetingExamineInitiate.builder()
                                .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                                .examineStatus(4)
                                .readStatus(0)
                                .build());
                        if (countFlag <= 0) {
                            throw new ApiException("修改审批发起失败！");
                        }

                        if (map.get("examineModuleId").toString().equals("28")) {
                            TargetTrainingRecord targetTrainingRecord = targetTrainingRecordService.selectTargetTrainingRecordById(map.get("meetingTrainingId").toString());
                            if (targetTrainingRecord != null) {
                                countFlag = targetTrainingRecordService.updateTargetTrainingRecord(TargetTrainingRecord
                                        .builder()
                                        .targetTrainingRecordId(targetTrainingRecord.getTargetTrainingRecordId())
                                        .auditStatus(4)
                                        .build());
                                if (countFlag <= 0) {
                                    throw new ApiException("修改审批发起失败！");
                                }
                            }
                        }
                    }
                } else {

                    // 将总状态改为已拒绝
                    int countFlag = initiateService.updateMeetingExamineInitiate(MeetingExamineInitiate.builder()
                            .examineInitiateId(Convert.toStr(map.get("examineInitiateId")))
                            .examineStatus(4)
                            .content(examineSubVO.getContent())
                            .readStatus(0)
                            .build());
                    if (countFlag <= 0) {
                        throw new ApiException("修改审批发起失败！");
                    }

                    if (map.get("examineModuleId").toString().equals("28")) {
                        TargetTrainingRecord targetTrainingRecord = targetTrainingRecordService.selectTargetTrainingRecordById(map.get("meetingTrainingId").toString());
                        if (targetTrainingRecord != null) {
                            countFlag = targetTrainingRecordService.updateTargetTrainingRecord(TargetTrainingRecord
                                    .builder()
                                    .targetTrainingRecordId(targetTrainingRecord.getTargetTrainingRecordId())
                                    .auditStatus(4)
                                    .build());
                            if (countFlag <= 0) {
                                throw new ApiException("修改审批发起失败！");
                            }
                        }
                    }

                    MeetingExamineInitiate initiate = initiateService.selectMeetingExamineInitiateById(Convert.toStr(map.get("examineInitiateId")));
                    if (initiate != null) {
                        ApiAccount account = accountService.selectApiAccountById(initiate.getAccountId());
                        if (map.get("examineModuleId").toString().equals("28")) {
                            //人生目标审核
                            if (account != null) {
                                // 发送系统消息
                                // 查询消息是否存在
                                messageRemindService.insertMessageRemind(MessageRemind.builder()
                                        .title("审核流程消息")
                                        .brief("人生目标审核未通过。")
                                        .modelStatus(1)
                                        .jumpType(23)
                                        .modelId("28")
                                        .accountId(account.getAccountId())
                                        .build());
                            }
                        } else if (map.get("examineModuleId").toString().equals("29") && account != null) {
                            //人生目标实现情况审核
                            // 发送系统消息
                            // 查询消息是否存在
                            messageRemindService.insertMessageRemind(MessageRemind.builder()
                                    .title("审核流程消息")
                                    .brief("人生目标审核未通过,获得。")
                                    .modelStatus(1)
                                    .jumpType(0)
                                    .modelId("29")
                                    .accountId(account.getAccountId())
                                    .build());
                        } else if (map.get("examineModuleId").toString().equals("31") && account != null) {
                            //客户成交
                            // 发送系统消息
                            // 查询消息是否存在
                            messageRemindService.insertMessageRemind(MessageRemind.builder()
                                    .title("审核流程消息")
                                    .brief("客户成交审核未通过")
                                    .modelStatus(1)
                                    .jumpType(25)
                                    .modelId("31")
                                    .accountId(account.getAccountId())
                                    .build());
                        }
                    }
                }
            }
        };
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
