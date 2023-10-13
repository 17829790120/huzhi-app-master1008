package com.wlwq.api.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.ExamineFlowAccount;
import com.wlwq.api.domain.ExamineInitiate;
import org.apache.ibatis.annotations.Param;

/**
 * 用户审批流程Mapper接口
 *
 * @author gaoce
 * @date 2023-05-11
 */
public interface ExamineFlowAccountMapper {
    /**
     * 查询用户审批流程
     *
     * @param examineFlowAccountId 用户审批流程ID
     * @return 用户审批流程
     */
    public ExamineFlowAccount selectExamineFlowAccountById(String examineFlowAccountId);

    /**
     * 查询用户审批流程列表
     *
     * @param examineFlowAccount 用户审批流程
     * @return 用户审批流程集合
     */
    public List<ExamineFlowAccount> selectExamineFlowAccountList(ExamineFlowAccount examineFlowAccount);

    /**
     * 新增用户审批流程
     *
     * @param examineFlowAccount 用户审批流程
     * @return 结果
     */
    public int insertExamineFlowAccount(ExamineFlowAccount examineFlowAccount);

    /**
     * 修改用户审批流程
     *
     * @param examineFlowAccount 用户审批流程
     * @return 结果
     */
    public int updateExamineFlowAccount(ExamineFlowAccount examineFlowAccount);

    /**
     * 删除用户审批流程
     *
     * @param examineFlowAccountId 用户审批流程ID
     * @return 结果
     */
    public int deleteExamineFlowAccountById(String examineFlowAccountId);

    /**
     * 批量删除用户审批流程
     *
     * @param examineFlowAccountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineFlowAccountByIds(String[] examineFlowAccountIds);

    /**
     * 我审批的审批详情
     *
     * @param accountId     账号ID
     * @param flowAccountId 审批ID
     * @return
     */
    public Map<String, Object> selectMyExamineDetail(@Param("accountId") String accountId, @Param("flowAccountId") String flowAccountId);

    /**
     * 只查询就近的一条
     *
     * @param examineFlowAccount 用户审批流程
     * @return 用户审批流程集合
     */
    public ExamineFlowAccount selectNearLimitExamineFlowAccount(ExamineFlowAccount examineFlowAccount);

    /**
     * 我审批的审批列表
     * @param examineInitiate
     * @return
     */
    public List<Map<String,Object>> selectMyExamineList(ExamineInitiate examineInitiate);

    /**
     * 我审批的审批数量
     * @param examineInitiate
     * @return
     */
    public Integer selectMyExamineCount(ExamineInitiate examineInitiate);

    /**
     *  根据发起审批ID删除相关数据
     * @param examineInitiateId 发起审批的ID
     * @return
     */
    public Integer deleteExamineFlowAccountByInitiateId(String examineInitiateId);

    /**
     * 根据发起者ID修改删除状态
     * @param examineFlowAccount
     * @return
     */
    public int updateExamineFlowAccountByInitiateId(ExamineFlowAccount examineFlowAccount);

    /**
     * 根据唯一标识查询审批人员信息
     * @param examineFlowAccount
     * @return
     */
    public List<ExamineFlowAccount> selectNearExamineFlowAccountList(ExamineFlowAccount examineFlowAccount);
}
