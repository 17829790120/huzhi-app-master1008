package com.wlwq.api.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.MeetingExamineFlowAccount;
import com.wlwq.api.domain.MeetingExamineInitiate;
import org.apache.ibatis.annotations.Param;

/**
 * 用户审批流程Mapper接口
 * 
 * @author wwb
 * @date 2023-05-29
 */
public interface MeetingExamineFlowAccountMapper {
    /**
     * 查询用户审批流程
     * 
     * @param examineFlowAccountId 用户审批流程ID
     * @return 用户审批流程
     */
    public MeetingExamineFlowAccount selectMeetingExamineFlowAccountById(String examineFlowAccountId);

    /**
     * 查询用户审批流程列表
     * 
     * @param meetingExamineFlowAccount 用户审批流程
     * @return 用户审批流程集合
     */
    public List<MeetingExamineFlowAccount> selectMeetingExamineFlowAccountList(MeetingExamineFlowAccount meetingExamineFlowAccount);

    /**
     * 新增用户审批流程
     * 
     * @param meetingExamineFlowAccount 用户审批流程
     * @return 结果
     */
    public int insertMeetingExamineFlowAccount(MeetingExamineFlowAccount meetingExamineFlowAccount);

    /**
     * 修改用户审批流程
     * 
     * @param meetingExamineFlowAccount 用户审批流程
     * @return 结果
     */
    public int updateMeetingExamineFlowAccount(MeetingExamineFlowAccount meetingExamineFlowAccount);

    /**
     * 删除用户审批流程
     * 
     * @param examineFlowAccountId 用户审批流程ID
     * @return 结果
     */
    public int deleteMeetingExamineFlowAccountById(String examineFlowAccountId);

    /**
     * 批量删除用户审批流程
     * 
     * @param examineFlowAccountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMeetingExamineFlowAccountByIds(String[] examineFlowAccountIds);
    /**
     * 我审批的审批详情
     * @param accountId 账号ID
     * @param flowAccountId 审批ID
     * @return
     */
    Map<String, Object> selectMyExamineDetail(@Param("accountId") String accountId, @Param("flowAccountId")  String flowAccountId);
    /**
     * 只查询就近的一条
     *
     * @param meetingExamineFlowAccount 用户审批流程
     * @return 用户审批流程集合
     */
    MeetingExamineFlowAccount selectNearLimitExamineFlowAccount(MeetingExamineFlowAccount meetingExamineFlowAccount);
    /**
     * 我审批的审批列表
     * @param meetingExamineInitiate
     * @return
     */
    List<Map<String, Object>> selectMyExamineList(MeetingExamineInitiate meetingExamineInitiate);
    /**
     * 我审批的审批数量
     * @param meetingExamineInitiate
     * @return
     */
    Integer selectMyExamineCount(MeetingExamineInitiate meetingExamineInitiate);
    /**
     *  根据发起审批ID删除相关数据
     * @param examineInitiateId 发起审批的ID
     * @return
     */
    Integer deleteExamineFlowAccountByInitiateId(String examineInitiateId);
    /**
     * 根据发起者ID修改删除状态
     * @param meetingExamineFlowAccount
     * @return
     */
    int updateExamineFlowAccountByInitiateId(MeetingExamineFlowAccount meetingExamineFlowAccount);
    /**
     * 全部会议列表（审核通过的）
     * @return
     */
    List<Map<String, Object>> selectAllExamineList(MeetingExamineInitiate meetingExamineInitiate);

    /**
     * 根据唯一标识查询审批人员信息
     * @param meetingExamineFlowAccount
     * @return
     */
    public List<MeetingExamineFlowAccount> selectNearExamineFlowAccountList(MeetingExamineFlowAccount meetingExamineFlowAccount);
}
