package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;
import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.MeetingExamineInitiate;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.MeetingExamineFlowAccountMapper;
import com.wlwq.api.domain.MeetingExamineFlowAccount;
import com.wlwq.api.service.IMeetingExamineFlowAccountService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户审批流程Service业务层处理
 * 
 * @author wwb
 * @date 2023-05-29
 */
@Service
public class MeetingExamineFlowAccountServiceImpl implements IMeetingExamineFlowAccountService {

    @Autowired
    private MeetingExamineFlowAccountMapper meetingExamineFlowAccountMapper;

    /**
     * 查询用户审批流程
     *
     * @param examineFlowAccountId 用户审批流程ID
     * @return 用户审批流程
     */
    @Override
    public MeetingExamineFlowAccount selectMeetingExamineFlowAccountById(String examineFlowAccountId) {
        return meetingExamineFlowAccountMapper.selectMeetingExamineFlowAccountById(examineFlowAccountId);
    }

    /**
     * 查询用户审批流程列表
     *
     * @param meetingExamineFlowAccount 用户审批流程
     * @return 用户审批流程
     */
    @Override
    public List<MeetingExamineFlowAccount> selectMeetingExamineFlowAccountList(MeetingExamineFlowAccount meetingExamineFlowAccount) {
        return meetingExamineFlowAccountMapper.selectMeetingExamineFlowAccountList(meetingExamineFlowAccount);
    }

    /**
     * 新增用户审批流程
     *
     * @param meetingExamineFlowAccount 用户审批流程
     * @return 结果
     */
    @Override
    public int insertMeetingExamineFlowAccount(MeetingExamineFlowAccount meetingExamineFlowAccount) {
        meetingExamineFlowAccount.setExamineFlowAccountId(IdUtil.getSnowflake(1, 1).nextIdStr());
        meetingExamineFlowAccount.setCreateTime(DateUtils.getNowDate());
        return meetingExamineFlowAccountMapper.insertMeetingExamineFlowAccount(meetingExamineFlowAccount);
    }

    /**
     * 修改用户审批流程
     *
     * @param meetingExamineFlowAccount 用户审批流程
     * @return 结果
     */
    @Override
    public int updateMeetingExamineFlowAccount(MeetingExamineFlowAccount meetingExamineFlowAccount) {
        // 更新已读状态不更新时间
        if(meetingExamineFlowAccount.getReadTag() == null){
            meetingExamineFlowAccount.setUpdateTime(DateUtils.getNowDate());
        }
        return meetingExamineFlowAccountMapper.updateMeetingExamineFlowAccount(meetingExamineFlowAccount);
    }

    /**
     * 删除用户审批流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMeetingExamineFlowAccountByIds(String ids) {
        return meetingExamineFlowAccountMapper.deleteMeetingExamineFlowAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户审批流程信息
     *
     * @param examineFlowAccountId 用户审批流程ID
     * @return 结果
     */
    @Override
    public int deleteMeetingExamineFlowAccountById(String examineFlowAccountId) {
        return meetingExamineFlowAccountMapper.deleteMeetingExamineFlowAccountById(examineFlowAccountId);
    }

    /**
     * 我审批的审批详情
     * @param accountId 账号ID
     * @param flowAccountId 审批ID
     * @return
     */
    @Override
    public Map<String,Object> selectMyExamineDetail(String accountId, String flowAccountId){
        return meetingExamineFlowAccountMapper.selectMyExamineDetail(accountId, flowAccountId);
    }

    /**
     * 只查询就近的一条
     *
     * @param meetingExamineFlowAccount 用户审批流程
     * @return 用户审批流程集合
     */
    @Override
    public MeetingExamineFlowAccount selectNearLimitExamineFlowAccount(MeetingExamineFlowAccount meetingExamineFlowAccount){
        return meetingExamineFlowAccountMapper.selectNearLimitExamineFlowAccount(meetingExamineFlowAccount);
    }


    /**
     * 我审批的审批列表
     * @param meetingExamineInitiate
     * @return
     */
    @Override
    public List<Map<String,Object>> selectMyExamineList(MeetingExamineInitiate meetingExamineInitiate){
        return meetingExamineFlowAccountMapper.selectMyExamineList(meetingExamineInitiate);
    }

    /**
     * 我审批的审批数量
     * @param meetingExamineInitiate
     * @return
     */
    @Override
    public Integer selectMyExamineCount(MeetingExamineInitiate meetingExamineInitiate){
        return meetingExamineFlowAccountMapper.selectMyExamineCount(meetingExamineInitiate);
    }

    /**
     *  根据发起审批ID删除相关数据
     * @param examineInitiateId 发起审批的ID
     * @return
     */
    @Override
    public Integer deleteMeetingExamineFlowAccountByInitiateId(String examineInitiateId){
        return meetingExamineFlowAccountMapper.deleteExamineFlowAccountByInitiateId(examineInitiateId);
    }

    /**
     * 根据发起者ID修改删除状态
     * @param meetingExamineFlowAccount
     * @return
     */
    @Override
    public int updateMeetingExamineFlowAccountByInitiateId(MeetingExamineFlowAccount meetingExamineFlowAccount){
        return meetingExamineFlowAccountMapper.updateExamineFlowAccountByInitiateId(meetingExamineFlowAccount);
    }
    /**
     * 全部会议列表（审核通过的）
     * @return
     */
    @Override
    public List<Map<String, Object>> selectAllExamineList(MeetingExamineInitiate meetingExamineInitiate) {
        return meetingExamineFlowAccountMapper.selectAllExamineList(meetingExamineInitiate);
    }


    /**
     * 根据唯一标识查询审批人员信息
     * @param meetingExamineFlowAccount
     * @return
     */
    @Override
    public List<MeetingExamineFlowAccount> selectNearExamineFlowAccountList(MeetingExamineFlowAccount meetingExamineFlowAccount){
        return meetingExamineFlowAccountMapper.selectNearExamineFlowAccountList(meetingExamineFlowAccount);
    }
}
