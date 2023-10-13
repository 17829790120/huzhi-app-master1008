package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.ExamineFlow;
import com.wlwq.api.domain.MeetingExamineFlow;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;

/**
 * 会议审批流程Service接口
 *
 * @author wlwq
 * @date 2023-05-29
 */
public interface IMeetingExamineFlowService {
    /**
     * 查询审批流程
     *
     * @param meetingExamineFlowId 审批流程ID
     * @return 审批流程
     */
    public MeetingExamineFlow selectMeetingExamineFlowById(String meetingExamineFlowId);

    /**
     * 查询审批流程列表
     *
     * @param meetingExamineFlow 审批流程
     * @return 审批流程集合
     */
    public List<MeetingExamineFlow> selectMeetingExamineFlowList(MeetingExamineFlow meetingExamineFlow);

    /**
     * 新增审批流程
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    public int insertMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow);

    /**
     * 修改审批流程
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    public int updateMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow);

    /**
     * 批量删除审批流程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMeetingExamineFlowByIds(String ids);

    /**
     * 删除审批流程信息
     *
     * @param examineFlowId 审批流程ID
     * @return 结果
     */
    public int deleteMeetingExamineFlowById(String examineFlowId);


    /**
     * 查询某一条数据是否存在
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    public MeetingExamineFlow selectMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow);

    /**
     * 根据模块和类型查询统一审批等级查看审批顺序最小值
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    public ExamineFlowResultVO selectMinExamineGroupBySequence(MeetingExamineFlow meetingExamineFlow);

    /**
     * app查询发起的审批列表
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    public List<ExamineFlowAccountResultVO> selectAppExamineFlowList(MeetingExamineFlow meetingExamineFlow);

    /**
     * 根据模块和类型和等级查询下一个等级
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    public ExamineFlowResultVO selectNextExamineSequence(MeetingExamineFlow meetingExamineFlow);

    /**
     * 查询员工第一次发起的审批管理人员列表
     * @param meetingExamineFlow
     * @return
     */
    public List<ExamineFlowResultVO> selectStaffInitiateExaminePeopleList(MeetingExamineFlow meetingExamineFlow);



    /**
     * 根据模块和类型查询统一审批等级查看的人数及审批顺序
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    public List<ExamineFlowResultVO> selectExamineListGroupBySequence(MeetingExamineFlow meetingExamineFlow);

    /**
     * 查询审批管理人员列表（除查询员工第一次发起外）
     * @param meetingExamineFlow
     * @param initiateId 审批发起ID
     * @return
     */
    public List<ExamineFlowResultVO> selectStaffInitiateExaminePeopleList(MeetingExamineFlow meetingExamineFlow,String initiateId,String examineTag);

}
