package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.ExamineInitiate;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamineFlowAccountMapper;
import com.wlwq.api.domain.ExamineFlowAccount;
import com.wlwq.api.service.IExamineFlowAccountService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户审批流程Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-11
 */
@Service
public class ExamineFlowAccountServiceImpl implements IExamineFlowAccountService {

    @Autowired
    private ExamineFlowAccountMapper examineFlowAccountMapper;

    /**
     * 查询用户审批流程
     *
     * @param examineFlowAccountId 用户审批流程ID
     * @return 用户审批流程
     */
    @Override
    public ExamineFlowAccount selectExamineFlowAccountById(String examineFlowAccountId) {
        return examineFlowAccountMapper.selectExamineFlowAccountById(examineFlowAccountId);
    }

    /**
     * 查询用户审批流程列表
     *
     * @param examineFlowAccount 用户审批流程
     * @return 用户审批流程
     */
    @Override
    public List<ExamineFlowAccount> selectExamineFlowAccountList(ExamineFlowAccount examineFlowAccount) {
        return examineFlowAccountMapper.selectExamineFlowAccountList(examineFlowAccount);
    }

    /**
     * 新增用户审批流程
     *
     * @param examineFlowAccount 用户审批流程
     * @return 结果
     */
    @Override
    public int insertExamineFlowAccount(ExamineFlowAccount examineFlowAccount) {
        examineFlowAccount.setExamineFlowAccountId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examineFlowAccount.setCreateTime(DateUtils.getNowDate());
        return examineFlowAccountMapper.insertExamineFlowAccount(examineFlowAccount);
    }

    /**
     * 修改用户审批流程
     *
     * @param examineFlowAccount 用户审批流程
     * @return 结果
     */
    @Override
    public int updateExamineFlowAccount(ExamineFlowAccount examineFlowAccount) {
        // 更新已读状态不更新时间
        if(examineFlowAccount.getReadTag() == null){
            examineFlowAccount.setUpdateTime(DateUtils.getNowDate());
        }
        return examineFlowAccountMapper.updateExamineFlowAccount(examineFlowAccount);
    }

    /**
     * 删除用户审批流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamineFlowAccountByIds(String ids) {
        return examineFlowAccountMapper.deleteExamineFlowAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户审批流程信息
     *
     * @param examineFlowAccountId 用户审批流程ID
     * @return 结果
     */
    @Override
    public int deleteExamineFlowAccountById(String examineFlowAccountId) {
        return examineFlowAccountMapper.deleteExamineFlowAccountById(examineFlowAccountId);
    }

    /**
     * 我审批的审批详情
     * @param accountId 账号ID
     * @param flowAccountId 审批ID
     * @return
     */
    @Override
    public Map<String,Object> selectMyExamineDetail(String accountId, String flowAccountId){
        return examineFlowAccountMapper.selectMyExamineDetail(accountId, flowAccountId);
    }

    /**
     * 只查询就近的一条
     *
     * @param examineFlowAccount 用户审批流程
     * @return 用户审批流程集合
     */
    @Override
    public ExamineFlowAccount selectNearLimitExamineFlowAccount(ExamineFlowAccount examineFlowAccount){
        return examineFlowAccountMapper.selectNearLimitExamineFlowAccount(examineFlowAccount);
    }


    /**
     * 我审批的审批列表
     * @param examineInitiate
     * @return
     */
    @Override
    public List<Map<String,Object>> selectMyExamineList(ExamineInitiate examineInitiate){
        return examineFlowAccountMapper.selectMyExamineList(examineInitiate);
    }

    /**
     * 我审批的审批数量
     * @param examineInitiate
     * @return
     */
    @Override
    public Integer selectMyExamineCount(ExamineInitiate examineInitiate){
        return examineFlowAccountMapper.selectMyExamineCount(examineInitiate);
    }

    /**
     *  根据发起审批ID删除相关数据
     * @param examineInitiateId 发起审批的ID
     * @return
     */
    @Override
    public Integer deleteExamineFlowAccountByInitiateId(String examineInitiateId){
        return examineFlowAccountMapper.deleteExamineFlowAccountByInitiateId(examineInitiateId);
    }

    /**
     * 根据发起者ID修改删除状态
     * @param examineFlowAccount
     * @return
     */
    @Override
    public int updateExamineFlowAccountByInitiateId(ExamineFlowAccount examineFlowAccount){
        return examineFlowAccountMapper.updateExamineFlowAccountByInitiateId(examineFlowAccount);
    }

    /**
     * 根据唯一标识查询审批人员信息
     * @param examineFlowAccount
     * @return
     */
    @Override
    public List<ExamineFlowAccount> selectNearExamineFlowAccountList(ExamineFlowAccount examineFlowAccount){
        return examineFlowAccountMapper.selectNearExamineFlowAccountList(examineFlowAccount);
    }
}
