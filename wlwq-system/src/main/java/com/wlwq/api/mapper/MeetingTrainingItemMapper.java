package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.MeetingTrainingItem;

/**
 * 会议训练流程事项Mapper接口
 *
 * @author wwb
 * @date 2023-05-29
 */
public interface MeetingTrainingItemMapper {
    /**
     * 查询会议训练流程事项
     *
     * @param meetingTrainingItemId 会议训练流程事项ID
     * @return 会议训练流程事项
     */
    public MeetingTrainingItem selectMeetingTrainingItemById(String meetingTrainingItemId);

    /**
     * 查询会议训练流程事项列表
     *
     * @param meetingTrainingItem 会议训练流程事项
     * @return 会议训练流程事项集合
     */
    public List<MeetingTrainingItem> selectMeetingTrainingItemList(MeetingTrainingItem meetingTrainingItem);

    /**
     * 新增会议训练流程事项
     *
     * @param meetingTrainingItem 会议训练流程事项
     * @return 结果
     */
    public int insertMeetingTrainingItem(MeetingTrainingItem meetingTrainingItem);

    /**
     * 修改会议训练流程事项
     *
     * @param meetingTrainingItem 会议训练流程事项
     * @return 结果
     */
    public int updateMeetingTrainingItem(MeetingTrainingItem meetingTrainingItem);

    /**
     * 删除会议训练流程事项
     *
     * @param meetingTrainingItemId 会议训练流程事项ID
     * @return 结果
     */
    public int deleteMeetingTrainingItemById(String meetingTrainingItemId);

    /**
     * 批量删除会议训练流程事项
     *
     * @param meetingTrainingItemIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMeetingTrainingItemByIds(String[] meetingTrainingItemIds);
    /**
     * 删除会议训练流程事项信息
     *
     * @param meetingTrainingId 会议训练流程事项ID
     * @return 结果
     */
    int deleteMeetingTrainingItemByMeetingTrainingId(String meetingTrainingId);
}
