package com.wlwq.api.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.MeetingExamineFlowMapper;
import com.wlwq.common.core.text.Convert;

/**
 * 会议审批流程Service业务层处理
 *
 * @author wlwq
 * @date 2023-05-29
 */
@Service
public class MeetingExamineFlowServiceImpl implements IMeetingExamineFlowService {

    @Autowired
    private MeetingExamineFlowMapper meetingExamineFlowMapper;

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private IExamineModuleService examineModuleService;

    @Autowired
    private IMeetingExamineFlowAccountService flowAccountService;
    @Autowired
    private IMeetingExamineInitiateService meetingInitiateService;

    /**
     * 查询审批流程
     *
     * @param meetingExamineFlowId 审批流程ID
     * @return 审批流程
     */
    @Override
    public MeetingExamineFlow selectMeetingExamineFlowById(String meetingExamineFlowId) {
        return meetingExamineFlowMapper.selectMeetingExamineFlowById(meetingExamineFlowId);
    }

    /**
     * 查询审批流程列表
     *
     * @param meetingExamineFlow 审批流程
     * @return 审批流程
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<MeetingExamineFlow> selectMeetingExamineFlowList(MeetingExamineFlow meetingExamineFlow) {
        List<MeetingExamineFlow> examineFlowList = meetingExamineFlowMapper.selectMeetingExamineFlowList(meetingExamineFlow);
        examineFlowList.forEach((MeetingExamineFlow examineFlow1) -> {
            ApiAccount apiAccount = accountService.selectApiAccountLimitByPostIdAndDeptId(examineFlow1.getDeptId(), examineFlow1.getPostId());
            examineFlow1.setAccountName(apiAccount == null ? "" : apiAccount.getNickName());
            // 审批模块
            ExamineModule examineModule = examineModuleService.selectExamineModuleById(examineFlow1.getExamineModuleId());
            examineFlow1.setExamineModuleName(examineModule == null ? "" : examineModule.getModuleName());
        });
        return examineFlowList;
    }

    /**
     * 新增审批流程
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    @Override
    public int insertMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow) {
        meetingExamineFlow.setMeetingExamineFlowId(IdUtil.getSnowflake(1, 1).nextIdStr());
        meetingExamineFlow.setCreateTime(DateUtils.getNowDate());
        return meetingExamineFlowMapper.insertMeetingExamineFlow(meetingExamineFlow);
    }

    /**
     * 修改审批流程
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    @Override
    public int updateMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow) {
        meetingExamineFlow.setUpdateTime(DateUtils.getNowDate());
        return meetingExamineFlowMapper.updateMeetingExamineFlow(meetingExamineFlow);
    }

    /**
     * 删除审批流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMeetingExamineFlowByIds(String ids) {
        return meetingExamineFlowMapper.deleteMeetingExamineFlowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审批流程信息
     *
     * @param meetingExamineFlowId 审批流程ID
     * @return 结果
     */
    @Override
    public int deleteMeetingExamineFlowById(String meetingExamineFlowId) {
        return meetingExamineFlowMapper.deleteMeetingExamineFlowById(meetingExamineFlowId);
    }


    /**
     * 查询某一条数据是否存在
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    @Override
    public MeetingExamineFlow selectMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow) {
        return meetingExamineFlowMapper.selectMeetingExamineFlow(meetingExamineFlow);
    }

    /**
     * 根据模块和类型查询统一审批等级查看审批顺序最小值
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    @Override
    public ExamineFlowResultVO selectMinExamineGroupBySequence(MeetingExamineFlow meetingExamineFlow) {
        return meetingExamineFlowMapper.selectMinExamineGroupBySequence(meetingExamineFlow);
    }

    /**
     * app查询发起的审批列表
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    @Override
    public List<ExamineFlowAccountResultVO> selectAppExamineFlowList(MeetingExamineFlow meetingExamineFlow) {
        return meetingExamineFlowMapper.selectAppExamineFlowList(meetingExamineFlow);
    }

    /**
     * 根据模块和类型和等级查询下一个等级
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    @Override
    public ExamineFlowResultVO selectNextExamineSequence(MeetingExamineFlow meetingExamineFlow) {
        return meetingExamineFlowMapper.selectNextExamineSequence(meetingExamineFlow);
    }

    /**
     * 查询员工第一次发起的审批管理人员列表
     *
     * @param meetingExamineFlow
     * @return
     */
    @Override
    public List<ExamineFlowResultVO> selectStaffInitiateExaminePeopleList(MeetingExamineFlow meetingExamineFlow) {
        // 根据模块和类型查询统一审批等级查看的人数及审批顺序
        List<ExamineFlowResultVO> examineFlowResultVOList = meetingExamineFlowMapper.selectExamineListGroupBySequence(meetingExamineFlow);
        for (ExamineFlowResultVO examineFlowResultVO : examineFlowResultVOList) {
            // 查询用户信息
            meetingExamineFlow.setExamineSequence(examineFlowResultVO.getExamineSequence());
            List<ExamineFlowAccountResultVO> accountResultVOList = meetingExamineFlowMapper.selectAppExamineFlowList(meetingExamineFlow);
            examineFlowResultVO.setAccountResultVOList(accountResultVOList);
        }
        return examineFlowResultVOList;
    }

    /**
     * 查询审批管理人员列表（除查询员工第一次发起外）
     *
     * @param meetingExamineFlow
     * @param initiateId  审批发起ID
     * @param examineTag  审批唯一标识
     * @return
     */
    @Override
    public List<ExamineFlowResultVO> selectStaffInitiateExaminePeopleList(MeetingExamineFlow meetingExamineFlow, String initiateId,String examineTag) {
        MeetingExamineInitiate examineInitiate = meetingInitiateService.selectMeetingExamineInitiateById(initiateId);
        // 根据模块和类型查询同一审批等级查看的人数及审批顺序
        List<ExamineFlowResultVO> examineFlowResultVOList = meetingExamineFlowMapper.selectExamineListGroupBySequence(meetingExamineFlow);
        examineFlowResultVOList.add(ExamineFlowResultVO.builder()
                .examineSequence(0)
                .examinePeopleCount(1)
                .build());
        // 排序，examineSequence从小到大
        Collections.sort(examineFlowResultVOList, Comparator.comparingInt(ExamineFlowResultVO::getExamineSequence));
        for (ExamineFlowResultVO examineFlowResultVO : examineFlowResultVOList) {
            // 查询用户信息
            meetingExamineFlow.setExamineSequence(examineFlowResultVO.getExamineSequence());
            List<ExamineFlowAccountResultVO> accountResultVOList = meetingExamineFlowMapper.selectAppExamineFlowList(meetingExamineFlow);
            if(examineFlowResultVO.getExamineSequence() == 0 && examineInitiate != null){
                // 默认第一级是本部门的领导
                // 查询本部门的领导
                ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(examineInitiate.getCompanyId()).deptId(examineInitiate.getDeptId()).positionType("1").build());
                if (apiAccount != null) {
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
                }
            }
            for (ExamineFlowAccountResultVO examineFlowAccountResultVO : accountResultVOList) {
                // 查询用户的审批状态
                MeetingExamineFlowAccount examineFlowAccount = flowAccountService.selectNearLimitExamineFlowAccount(MeetingExamineFlowAccount.builder()
                        .examineInitiateId(initiateId)
                        .accountId(examineFlowAccountResultVO.getAccountId())
                        .postId(examineFlowAccountResultVO.getPostId())
                        .deptId(examineFlowAccountResultVO.getDeptId())
                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                        .examineTag(examineTag)
                        .build());
                // 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
                examineFlowAccountResultVO.setExamineStatus(examineFlowAccount == null ? 0 : examineFlowAccount.getExamineStatus());
                Integer examineStatus = examineFlowAccountResultVO.getExamineStatus();
                if (examineStatus == 4) {
                    examineFlowResultVO.setExamineFlowStatus(4);
                    break;
                } else if (examineStatus == 3) {
                    examineFlowResultVO.setExamineFlowStatus(3);
                } else {
                    examineFlowResultVO.setExamineFlowStatus(0);
                }
                if(examineFlowAccount != null && examineFlowAccount.getUpdateTime() != null){
                    examineFlowAccountResultVO.setExamineTime(examineFlowAccount.getUpdateTime());
                }
            }
            examineFlowResultVO.setAccountResultVOList(accountResultVOList);
        }
        return examineFlowResultVOList;
    }

    /**
     * 根据模块和类型查询统一审批等级查看的人数及审批顺序
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    @Override
    public List<ExamineFlowResultVO> selectExamineListGroupBySequence(MeetingExamineFlow meetingExamineFlow) {
        return meetingExamineFlowMapper.selectExamineListGroupBySequence(meetingExamineFlow);
    }
}
