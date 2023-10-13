package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.MeetingTrainingMinutesMapper;
import com.wlwq.api.domain.MeetingTrainingMinutes;
import com.wlwq.api.service.IMeetingTrainingMinutesService;
import com.wlwq.common.core.text.Convert;

/**
 * 会议训练纪要Service业务层处理
 *
 * @author wwb
 * @date 2023-05-31
 */
@Service
public class MeetingTrainingMinutesServiceImpl implements IMeetingTrainingMinutesService {

    @Autowired
    private MeetingTrainingMinutesMapper meetingTrainingMinutesMapper;

    /**
     * 查询会议训练纪要
     *
     * @param meetingTrainingMinutesId 会议训练纪要ID
     * @return 会议训练纪要
     */
    @Override
    public MeetingTrainingMinutes selectMeetingTrainingMinutesById(String meetingTrainingMinutesId) {
        return meetingTrainingMinutesMapper.selectMeetingTrainingMinutesById(meetingTrainingMinutesId);
    }

    /**
     * 查询会议训练纪要列表
     *
     * @param meetingTrainingMinutes 会议训练纪要
     * @return 会议训练纪要
     */
    @Override
    public List<MeetingTrainingMinutes> selectMeetingTrainingMinutesList(MeetingTrainingMinutes meetingTrainingMinutes) {
        return meetingTrainingMinutesMapper.selectMeetingTrainingMinutesList(meetingTrainingMinutes);
    }

    /**
     * 新增会议训练纪要
     *
     * @param meetingTrainingMinutes 会议训练纪要
     * @return 结果
     */
    @Override
    public int insertMeetingTrainingMinutes(MeetingTrainingMinutes meetingTrainingMinutes) {
        meetingTrainingMinutes.setMeetingTrainingMinutesId(IdUtil.getSnowflake(1, 1).nextIdStr());
        meetingTrainingMinutes.setCreateTime(DateUtils.getNowDate());
        return meetingTrainingMinutesMapper.insertMeetingTrainingMinutes(meetingTrainingMinutes);
    }

    /**
     * 修改会议训练纪要
     *
     * @param meetingTrainingMinutes 会议训练纪要
     * @return 结果
     */
    @Override
    public int updateMeetingTrainingMinutes(MeetingTrainingMinutes meetingTrainingMinutes) {
        meetingTrainingMinutes.setUpdateTime(DateUtils.getNowDate());
        return meetingTrainingMinutesMapper.updateMeetingTrainingMinutes(meetingTrainingMinutes);
    }

    /**
     * 删除会议训练纪要对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMeetingTrainingMinutesByIds(String ids) {
        return meetingTrainingMinutesMapper.deleteMeetingTrainingMinutesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除会议训练纪要信息
     *
     * @param meetingTrainingMinutesId 会议训练纪要ID
     * @return 结果
     */
    @Override
    public int deleteMeetingTrainingMinutesById(String meetingTrainingMinutesId) {
        return meetingTrainingMinutesMapper.deleteMeetingTrainingMinutesById(meetingTrainingMinutesId);
    }
}
