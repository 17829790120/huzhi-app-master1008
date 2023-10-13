package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.MeetingTraining;

/**
 * 会议训练Service接口
 *
 * @author wwb
 * @date 2023-05-29
 */
public interface IMeetingTrainingService {
    /**
     * 查询会议训练
     *
     * @param meetingTrainingId 会议训练ID
     * @return 会议训练
     */
    public MeetingTraining selectMeetingTrainingById(String meetingTrainingId);

    /**
     * 查询会议训练列表
     *
     * @param meetingTraining 会议训练
     * @return 会议训练集合
     */
    public List<MeetingTraining> selectMeetingTrainingList(MeetingTraining meetingTraining);

    /**
     * 新增会议训练
     *
     * @param meetingTraining 会议训练
     * @return 结果
     */
    public int insertMeetingTraining(MeetingTraining meetingTraining);

    /**
     * 修改会议训练
     *
     * @param meetingTraining 会议训练
     * @return 结果
     */
    public int updateMeetingTraining(MeetingTraining meetingTraining);

    /**
     * 批量删除会议训练
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMeetingTrainingByIds(String ids);

    /**
     * 删除会议训练信息
     *
     * @param meetingTrainingId 会议训练ID
     * @return 结果
     */
    public int deleteMeetingTrainingById(String meetingTrainingId);

    /**
     * 申请转训信息查询（转训信息对应的是会议训练信息）
     * @param meetingTraining
     * @return
     */
    MeetingTraining selectMeetingTrainingByTransferTraining(MeetingTraining meetingTraining);
}
