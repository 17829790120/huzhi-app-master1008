package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.MeetingExamineFlow;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;

/**
 * 会议审批流程Mapper接口
 *
 * @author wlwq
 * @date 2023-05-29
 */
public interface MeetingExamineFlowMapper {
    /**
     * 查询会议审批流程
     *
     * @param meetingExamineFlowId 会议审批流程ID
     * @return 会议审批流程
     */
    public MeetingExamineFlow selectMeetingExamineFlowById(String meetingExamineFlowId);

    /**
     * 查询会议审批流程列表
     *
     * @param meetingExamineFlow 会议审批流程
     * @return 会议审批流程集合
     */
    public List<MeetingExamineFlow> selectMeetingExamineFlowList(MeetingExamineFlow meetingExamineFlow);

    /**
     * 新增会议审批流程
     *
     * @param meetingExamineFlow 会议审批流程
     * @return 结果
     */
    public int insertMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow);

    /**
     * 修改会议审批流程
     *
     * @param meetingExamineFlow 会议审批流程
     * @return 结果
     */
    public int updateMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow);

    /**
     * 删除会议审批流程
     *
     * @param meetingExamineFlowId 会议审批流程ID
     * @return 结果
     */
    public int deleteMeetingExamineFlowById(String meetingExamineFlowId);

    /**
     * 批量删除会议审批流程
     *
     * @param meetingExamineFlowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMeetingExamineFlowByIds(String[] meetingExamineFlowIds);
    /**
     * 查询某一条数据是否存在
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    MeetingExamineFlow selectMeetingExamineFlow(MeetingExamineFlow meetingExamineFlow);
    /**
     * 根据模块和类型查询统一审批等级查看审批顺序最小值
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    ExamineFlowResultVO selectMinExamineGroupBySequence(MeetingExamineFlow meetingExamineFlow);
    /**
     * app查询发起的审批列表
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    List<ExamineFlowAccountResultVO> selectAppExamineFlowList(MeetingExamineFlow meetingExamineFlow);
    /**
     * 根据模块和类型和等级查询下一个等级
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    ExamineFlowResultVO selectNextExamineSequence(MeetingExamineFlow meetingExamineFlow);
    /**
     * 根据模块和类型查询统一审批等级查看的人数及审批顺序
     *
     * @param meetingExamineFlow 审批流程
     * @return 结果
     */
    List<ExamineFlowResultVO> selectExamineListGroupBySequence(MeetingExamineFlow meetingExamineFlow);
}
