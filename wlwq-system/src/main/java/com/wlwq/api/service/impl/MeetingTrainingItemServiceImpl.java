package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.MeetingTrainingItemMapper;
import com.wlwq.api.domain.MeetingTrainingItem;
import com.wlwq.api.service.IMeetingTrainingItemService;
import com.wlwq.common.core.text.Convert;

/**
 * 会议训练流程事项Service业务层处理
 *
 * @author wwb
 * @date 2023-05-29
 */
@Service
public class MeetingTrainingItemServiceImpl implements IMeetingTrainingItemService {

    @Autowired
    private MeetingTrainingItemMapper meetingTrainingItemMapper;

    /**
     * 查询会议训练流程事项
     *
     * @param meetingTrainingItemId 会议训练流程事项ID
     * @return 会议训练流程事项
     */
    @Override
    public MeetingTrainingItem selectMeetingTrainingItemById(String meetingTrainingItemId) {
        return meetingTrainingItemMapper.selectMeetingTrainingItemById(meetingTrainingItemId);
    }

    /**
     * 查询会议训练流程事项列表
     *
     * @param meetingTrainingItem 会议训练流程事项
     * @return 会议训练流程事项
     */
    @Override
    public List<MeetingTrainingItem> selectMeetingTrainingItemList(MeetingTrainingItem meetingTrainingItem) {
        return meetingTrainingItemMapper.selectMeetingTrainingItemList(meetingTrainingItem);
    }

    /**
     * 新增会议训练流程事项
     *
     * @param meetingTrainingItem 会议训练流程事项
     * @return 结果
     */
    @Override
    public int insertMeetingTrainingItem(MeetingTrainingItem meetingTrainingItem) {
        meetingTrainingItem.setMeetingTrainingItemId(IdUtil.getSnowflake(1, 1).nextIdStr());
        meetingTrainingItem.setCreateTime(DateUtils.getNowDate());
        return meetingTrainingItemMapper.insertMeetingTrainingItem(meetingTrainingItem);
    }

    /**
     * 修改会议训练流程事项
     *
     * @param meetingTrainingItem 会议训练流程事项
     * @return 结果
     */
    @Override
    public int updateMeetingTrainingItem(MeetingTrainingItem meetingTrainingItem) {
        meetingTrainingItem.setUpdateTime(DateUtils.getNowDate());
        return meetingTrainingItemMapper.updateMeetingTrainingItem(meetingTrainingItem);
    }

    /**
     * 删除会议训练流程事项对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMeetingTrainingItemByIds(String ids) {
        return meetingTrainingItemMapper.deleteMeetingTrainingItemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除会议训练流程事项信息
     *
     * @param meetingTrainingItemId 会议训练流程事项ID
     * @return 结果
     */
    @Override
    public int deleteMeetingTrainingItemById(String meetingTrainingItemId) {
        return meetingTrainingItemMapper.deleteMeetingTrainingItemById(meetingTrainingItemId);
    }
    /**
     * 删除会议训练流程事项信息
     *
     * @param meetingTrainingId 会议训练流程事项ID
     * @return 结果
     */
    @Override
    public int deleteMeetingTrainingItemByMeetingTrainingId(String meetingTrainingId) {
        return meetingTrainingItemMapper.deleteMeetingTrainingItemByMeetingTrainingId(meetingTrainingId);
    }
}
