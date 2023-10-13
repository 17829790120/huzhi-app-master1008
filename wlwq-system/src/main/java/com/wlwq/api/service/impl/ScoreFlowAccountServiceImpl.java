package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ScoreFlowAccountMapper;
import com.wlwq.api.domain.ScoreFlowAccount;
import com.wlwq.api.service.IScoreFlowAccountService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户积分审批流程Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-07
 */
@Service
public class ScoreFlowAccountServiceImpl implements IScoreFlowAccountService {

    @Autowired
    private ScoreFlowAccountMapper scoreFlowAccountMapper;

    /**
     * 查询用户积分审批流程
     *
     * @param scoreFlowAccountId 用户积分审批流程ID
     * @return 用户积分审批流程
     */
    @Override
    public ScoreFlowAccount selectScoreFlowAccountById(String scoreFlowAccountId) {
        return scoreFlowAccountMapper.selectScoreFlowAccountById(scoreFlowAccountId);
    }

    /**
     * 查询用户积分审批流程列表
     *
     * @param scoreFlowAccount 用户积分审批流程
     * @return 用户积分审批流程
     */
    @Override
    public List<ScoreFlowAccount> selectScoreFlowAccountList(ScoreFlowAccount scoreFlowAccount) {
        return scoreFlowAccountMapper.selectScoreFlowAccountList(scoreFlowAccount);
    }

    /**
     * 新增用户积分审批流程
     *
     * @param scoreFlowAccount 用户积分审批流程
     * @return 结果
     */
    @Override
    public int insertScoreFlowAccount(ScoreFlowAccount scoreFlowAccount) {
        scoreFlowAccount.setScoreFlowAccountId(IdUtil.getSnowflake(1, 1).nextIdStr());
        scoreFlowAccount.setCreateTime(DateUtils.getNowDate());
        return scoreFlowAccountMapper.insertScoreFlowAccount(scoreFlowAccount);
    }

    /**
     * 修改用户积分审批流程
     *
     * @param scoreFlowAccount 用户积分审批流程
     * @return 结果
     */
    @Override
    public int updateScoreFlowAccount(ScoreFlowAccount scoreFlowAccount) {
        // 更新已读状态不更新时间
        if(scoreFlowAccount.getReadTag() == null){
            scoreFlowAccount.setUpdateTime(DateUtils.getNowDate());
        }
        return scoreFlowAccountMapper.updateScoreFlowAccount(scoreFlowAccount);
    }

    /**
     * 删除用户积分审批流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScoreFlowAccountByIds(String ids) {
        return scoreFlowAccountMapper.deleteScoreFlowAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户积分审批流程信息
     *
     * @param scoreFlowAccountId 用户积分审批流程ID
     * @return 结果
     */
    @Override
    public int deleteScoreFlowAccountById(String scoreFlowAccountId) {
        return scoreFlowAccountMapper.deleteScoreFlowAccountById(scoreFlowAccountId);
    }

    /**
     * 只查询最新的一条
     * @param scoreFlowAccount
     * @return
     */
    @Override
    public ScoreFlowAccount selectNearLimitScoreFlowAccount(ScoreFlowAccount scoreFlowAccount){
        return scoreFlowAccountMapper.selectNearLimitScoreFlowAccount(scoreFlowAccount);
    }

    /**
     * 我审核的审核列表
     * @param scoreFlowAccount
     * @return
     */
    @Override
    public List<Map<String,Object>> selectMyScoreFlowAccountList(ScoreFlowAccount scoreFlowAccount){
        return scoreFlowAccountMapper.selectMyScoreFlowAccountList(scoreFlowAccount);
    }

    /**
     * 根据唯一标识查询审批人员信息
     * @param scoreFlowAccount
     * @return
     */
    @Override
    public List<ScoreFlowAccount> selectNearScoreFlowAccountList(ScoreFlowAccount scoreFlowAccount){
        return scoreFlowAccountMapper.selectNearScoreFlowAccountList(scoreFlowAccount);
    }
}
