package com.wlwq.api.service;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.ScoreFlowAccount;

/**
 * 用户积分审批流程Service接口
 *
 * @author gaoce
 * @date 2023-06-07
 */
public interface IScoreFlowAccountService {
    /**
     * 查询用户积分审批流程
     *
     * @param scoreFlowAccountId 用户积分审批流程ID
     * @return 用户积分审批流程
     */
    public ScoreFlowAccount selectScoreFlowAccountById(String scoreFlowAccountId);

    /**
     * 查询用户积分审批流程列表
     *
     * @param scoreFlowAccount 用户积分审批流程
     * @return 用户积分审批流程集合
     */
    public List<ScoreFlowAccount> selectScoreFlowAccountList(ScoreFlowAccount scoreFlowAccount);

    /**
     * 新增用户积分审批流程
     *
     * @param scoreFlowAccount 用户积分审批流程
     * @return 结果
     */
    public int insertScoreFlowAccount(ScoreFlowAccount scoreFlowAccount);

    /**
     * 修改用户积分审批流程
     *
     * @param scoreFlowAccount 用户积分审批流程
     * @return 结果
     */
    public int updateScoreFlowAccount(ScoreFlowAccount scoreFlowAccount);

    /**
     * 批量删除用户积分审批流程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScoreFlowAccountByIds(String ids);

    /**
     * 删除用户积分审批流程信息
     *
     * @param scoreFlowAccountId 用户积分审批流程ID
     * @return 结果
     */
    public int deleteScoreFlowAccountById(String scoreFlowAccountId);

    /**
     * 只查询最新的一条
     * @param scoreFlowAccount
     * @return
     */
    public ScoreFlowAccount selectNearLimitScoreFlowAccount(ScoreFlowAccount scoreFlowAccount);

    /**
     * 我审核的审核列表
     * @param scoreFlowAccount
     * @return
     */
    public List<Map<String,Object>> selectMyScoreFlowAccountList(ScoreFlowAccount scoreFlowAccount);

    /**
     * 根据唯一标识查询审批人员信息
     * @param scoreFlowAccount
     * @return
     */
    public List<ScoreFlowAccount> selectNearScoreFlowAccountList(ScoreFlowAccount scoreFlowAccount);
}
