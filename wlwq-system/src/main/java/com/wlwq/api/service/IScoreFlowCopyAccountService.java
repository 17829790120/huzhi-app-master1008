package com.wlwq.api.service;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.ScoreFlowCopyAccount;

/**
 * 用户积分审批抄送流程Service接口
 *
 * @author gaoce
 * @date 2023-06-07
 */
public interface IScoreFlowCopyAccountService {
    /**
     * 查询用户积分审批抄送流程
     *
     * @param scoreFlowCopyAccountId 用户积分审批抄送流程ID
     * @return 用户积分审批抄送流程
     */
    public ScoreFlowCopyAccount selectScoreFlowCopyAccountById(String scoreFlowCopyAccountId);

    /**
     * 查询用户积分审批抄送流程列表
     *
     * @param scoreFlowCopyAccount 用户积分审批抄送流程
     * @return 用户积分审批抄送流程集合
     */
    public List<ScoreFlowCopyAccount> selectScoreFlowCopyAccountList(ScoreFlowCopyAccount scoreFlowCopyAccount);

    /**
     * 新增用户积分审批抄送流程
     *
     * @param scoreFlowCopyAccount 用户积分审批抄送流程
     * @return 结果
     */
    public int insertScoreFlowCopyAccount(ScoreFlowCopyAccount scoreFlowCopyAccount);

    /**
     * 修改用户积分审批抄送流程
     *
     * @param scoreFlowCopyAccount 用户积分审批抄送流程
     * @return 结果
     */
    public int updateScoreFlowCopyAccount(ScoreFlowCopyAccount scoreFlowCopyAccount);

    /**
     * 批量删除用户积分审批抄送流程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScoreFlowCopyAccountByIds(String ids);

    /**
     * 删除用户积分审批抄送流程信息
     *
     * @param scoreFlowCopyAccountId 用户积分审批抄送流程ID
     * @return 结果
     */
    public int deleteScoreFlowCopyAccountById(String scoreFlowCopyAccountId);

    /**
     * 抄送我的列表
     * @param scoreFlowCopyAccount
     * @return
     */
    public List<Map<String,Object>> selectCopyMyScoreFlowAccountList(ScoreFlowCopyAccount scoreFlowCopyAccount);
}
