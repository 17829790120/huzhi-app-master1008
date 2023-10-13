package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.MeetingExamineInitiateMapper;
import com.wlwq.api.domain.MeetingExamineInitiate;
import com.wlwq.api.service.IMeetingExamineInitiateService;
import com.wlwq.common.core.text.Convert;

/**
 * 会议审批发起Service业务层处理
 *
 * @author wwb
 * @date 2023-05-29
 */
@Service
public class MeetingExamineInitiateServiceImpl implements IMeetingExamineInitiateService {

    @Autowired
    private MeetingExamineInitiateMapper meetingExamineInitiateMapper;

    /**
     * 查询审批发起
     *
     * @param examineInitiateId 审批发起ID
     * @return 审批发起
     */
    @Override
    public MeetingExamineInitiate selectMeetingExamineInitiateById(String examineInitiateId) {
        return meetingExamineInitiateMapper.selectMeetingExamineInitiateById(examineInitiateId);
    }

    /**
     * 查询审批发起列表
     *
     * @param meetingExamineInitiate 审批发起
     * @return 审批发起
     */
    @Override
    public List<MeetingExamineInitiate> selectMeetingExamineInitiateList(MeetingExamineInitiate meetingExamineInitiate) {
        return meetingExamineInitiateMapper.selectMeetingExamineInitiateList(meetingExamineInitiate);
    }

    /**
     * 新增审批发起
     *
     * @param meetingExamineInitiate 审批发起
     * @return 结果
     */
    @Override
    public int insertMeetingExamineInitiate(MeetingExamineInitiate meetingExamineInitiate) {
        meetingExamineInitiate.setExamineInitiateId(IdUtil.getSnowflake(1, 1).nextIdStr());
        meetingExamineInitiate.setCreateTime(DateUtils.getNowDate());
        return meetingExamineInitiateMapper.insertMeetingExamineInitiate(meetingExamineInitiate);
    }

    /**
     * 修改审批发起
     *
     * @param meetingExamineInitiate 审批发起
     * @return 结果
     */
    @Override
    public int updateMeetingExamineInitiate(MeetingExamineInitiate meetingExamineInitiate) {
        meetingExamineInitiate.setUpdateTime(DateUtils.getNowDate());
        return meetingExamineInitiateMapper.updateMeetingExamineInitiate(meetingExamineInitiate);
    }

    /**
     * 删除审批发起对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMeetingExamineInitiateByIds(String ids) {
        return meetingExamineInitiateMapper.deleteMeetingExamineInitiateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审批发起信息
     *
     * @param examineInitiateId 审批发起ID
     * @return 结果
     */
    @Override
    public int deleteMeetingExamineInitiateById(String examineInitiateId) {
        return meetingExamineInitiateMapper.deleteMeetingExamineInitiateById(examineInitiateId);
    }

    /**
     * App查询审批发起列表
     *
     * @param meetingExamineInitiate 审批发起
     * @return 审批发起集合
     */
    @Override
    public List<MeetingExamineInitiate> selectExamineInitiateApiList(MeetingExamineInitiate meetingExamineInitiate){
        return meetingExamineInitiateMapper.selectExamineInitiateApiList(meetingExamineInitiate);
    }

    /**
     * App查询审批发起数量
     *
     * @param examineInitiate 审批发起
     * @return 审批发起数量
     */
    @Override
    public Integer selectExamineInitiateApiCount(MeetingExamineInitiate examineInitiate){
        return meetingExamineInitiateMapper.selectExamineInitiateApiCount(examineInitiate);
    }
    /**
     * 按id获取未评价的审核流程，审核类型为24
     * @param meetingTrainingId
     * @return
     */
    @Override
    public MeetingExamineInitiate selectInitiateByMeetingTrainingId(String meetingTrainingId) {
        return meetingExamineInitiateMapper.selectInitiateByMeetingTrainingId(meetingTrainingId);
    }
    /**
     * 申请转训信息状态查询（转训审核信息对应的是会议训练审核信息）
     * @param meetingTrainingId
     * @param examineModuleId  审批类型ID  24：会议训练审核 25：会议训练评价
     * @return
     */
    @Override
    public MeetingExamineInitiate selectMeetingExamineInitiateByTransferTraining(String meetingTrainingId, int examineModuleId) {
        return meetingExamineInitiateMapper.selectMeetingExamineInitiateByTransferTraining(meetingTrainingId,examineModuleId);
    }

}
