package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.MeetingExamineInitiate;
import org.apache.ibatis.annotations.Param;

/**
 * 会议审批发起Mapper接口
 *
 * @author wwb
 * @date 2023-05-29
 */
public interface MeetingExamineInitiateMapper {
    /**
     * 查询会议审批发起
     *
     * @param examineInitiateId 会议审批发起ID
     * @return 会议审批发起
     */
    public MeetingExamineInitiate selectMeetingExamineInitiateById(String examineInitiateId);

    /**
     * 查询会议审批发起列表
     *
     * @param meetingExamineInitiate 会议审批发起
     * @return 会议审批发起集合
     */
    public List<MeetingExamineInitiate> selectMeetingExamineInitiateList(MeetingExamineInitiate meetingExamineInitiate);

    /**
     * 新增会议审批发起
     *
     * @param meetingExamineInitiate 会议审批发起
     * @return 结果
     */
    public int insertMeetingExamineInitiate(MeetingExamineInitiate meetingExamineInitiate);

    /**
     * 修改会议审批发起
     *
     * @param meetingExamineInitiate 会议审批发起
     * @return 结果
     */
    public int updateMeetingExamineInitiate(MeetingExamineInitiate meetingExamineInitiate);

    /**
     * 删除会议审批发起
     *
     * @param examineInitiateId 会议审批发起ID
     * @return 结果
     */
    public int deleteMeetingExamineInitiateById(String examineInitiateId);

    /**
     * 批量删除会议审批发起
     *
     * @param examineInitiateIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMeetingExamineInitiateByIds(String[] examineInitiateIds);
    /**
     * App查询审批发起列表
     *
     * @param meetingExamineInitiate 审批发起
     * @return 审批发起集合
     */
    List<MeetingExamineInitiate> selectExamineInitiateApiList(MeetingExamineInitiate meetingExamineInitiate);
    /**
     * App查询审批发起数量
     *
     * @param examineInitiate 审批发起
     * @return 审批发起数量
     */
    Integer selectExamineInitiateApiCount(MeetingExamineInitiate examineInitiate);
    /**
     * 按id获取未评价的审核流程，审核类型为24
     * @param meetingTrainingId
     * @return
     */
    MeetingExamineInitiate selectInitiateByMeetingTrainingId(String meetingTrainingId);
    /**
     * 申请转训信息状态查询（转训审核信息对应的是会议训练审核信息）
     * @param meetingTrainingId
     * @param examineModuleId  审批类型ID  24：会议训练审核 25：会议训练评价
     * @return
     */
    MeetingExamineInitiate selectMeetingExamineInitiateByTransferTraining(@Param("meetingTrainingId")String meetingTrainingId,
                                                                          @Param("examineModuleId") int examineModuleId);
}
