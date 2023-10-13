package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.MeetingTrainingMapper;
import com.wlwq.api.domain.MeetingTraining;
import com.wlwq.api.service.IMeetingTrainingService;
import com.wlwq.common.core.text.Convert;

/**
 * 会议训练Service业务层处理
 *
 * @author wwb
 * @date 2023-05-29
 */
@Service
public class MeetingTrainingServiceImpl implements IMeetingTrainingService {

    @Autowired
    private MeetingTrainingMapper meetingTrainingMapper;

    /**
     * 查询会议训练
     *
     * @param meetingTrainingId 会议训练ID
     * @return 会议训练
     */
    @Override
    public MeetingTraining selectMeetingTrainingById(String meetingTrainingId) {
        return meetingTrainingMapper.selectMeetingTrainingById(meetingTrainingId);
    }

    /**
     * 查询会议训练列表
     *
     * @param meetingTraining 会议训练
     * @return 会议训练
     */
    @Override
    public List<MeetingTraining> selectMeetingTrainingList(MeetingTraining meetingTraining) {
        return meetingTrainingMapper.selectMeetingTrainingList(meetingTraining);
    }

    /**
     * 新增会议训练
     *
     * @param meetingTraining 会议训练
     * @return 结果
     */
    @Override
    public int insertMeetingTraining(MeetingTraining meetingTraining) {
        if(StringUtils.isEmpty(meetingTraining.getMeetingTrainingId())){
            meetingTraining.setMeetingTrainingId(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
        meetingTraining.setCreateTime(DateUtils.getNowDate());
        return meetingTrainingMapper.insertMeetingTraining(meetingTraining);
    }

    /**
     * 修改会议训练
     *
     * @param meetingTraining 会议训练
     * @return 结果
     */
    @Override
    public int updateMeetingTraining(MeetingTraining meetingTraining) {
        meetingTraining.setUpdateTime(DateUtils.getNowDate());
        return meetingTrainingMapper.updateMeetingTraining(meetingTraining);
    }

    /**
     * 删除会议训练对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMeetingTrainingByIds(String ids) {
        return meetingTrainingMapper.deleteMeetingTrainingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除会议训练信息
     *
     * @param meetingTrainingId 会议训练ID
     * @return 结果
     */
    @Override
    public int deleteMeetingTrainingById(String meetingTrainingId) {
        return meetingTrainingMapper.deleteMeetingTrainingById(meetingTrainingId);
    }
    /**
     * 申请转训信息查询（转训信息对应的是会议训练信息）
     * @param meetingTraining
     * @return
     */
    @Override
    public MeetingTraining selectMeetingTrainingByTransferTraining(MeetingTraining meetingTraining) {
        return meetingTrainingMapper.selectMeetingTrainingByTransferTraining(meetingTraining);
    }
}
