package com.wlwq.api.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.ScoreFlowAccount;

/**
 * 用户积分审批流程Mapper接口
 *
 * @author gaoce
 * @date 2023-06-07
 */
public interface ScoreFlowAccountMapper {
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
     * 删除用户积分审批流程
     *
     * @param scoreFlowAccountId 用户积分审批流程ID
     * @return 结果
     */
    public int deleteScoreFlowAccountById(String scoreFlowAccountId);

    /**
     * 批量删除用户积分审批流程
     *
     * @param scoreFlowAccountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScoreFlowAccountByIds(String[] scoreFlowAccountIds);

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
