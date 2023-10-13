package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.MeetingTrainingMinutes;

/**
 * 会议训练纪要Service接口
 *
 * @author wwb
 * @date 2023-05-31
 */
public interface IMeetingTrainingMinutesService {
    /**
     * 查询会议训练纪要
     *
     * @param meetingTrainingMinutesId 会议训练纪要ID
     * @return 会议训练纪要
     */
    public MeetingTrainingMinutes selectMeetingTrainingMinutesById(String meetingTrainingMinutesId);

    /**
     * 查询会议训练纪要列表
     *
     * @param meetingTrainingMinutes 会议训练纪要
     * @return 会议训练纪要集合
     */
    public List<MeetingTrainingMinutes> selectMeetingTrainingMinutesList(MeetingTrainingMinutes meetingTrainingMinutes);

    /**
     * 新增会议训练纪要
     *
     * @param meetingTrainingMinutes 会议训练纪要
     * @return 结果
     */
    public int insertMeetingTrainingMinutes(MeetingTrainingMinutes meetingTrainingMinutes);

    /**
     * 修改会议训练纪要
     *
     * @param meetingTrainingMinutes 会议训练纪要
     * @return 结果
     */
    public int updateMeetingTrainingMinutes(MeetingTrainingMinutes meetingTrainingMinutes);

    /**
     * 批量删除会议训练纪要
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMeetingTrainingMinutesByIds(String ids);

    /**
     * 删除会议训练纪要信息
     *
     * @param meetingTrainingMinutesId 会议训练纪要ID
     * @return 结果
     */
    public int deleteMeetingTrainingMinutesById(String meetingTrainingMinutesId);
}
