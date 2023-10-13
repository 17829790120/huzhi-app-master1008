package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ScoreFlowCopyAccountMapper;
import com.wlwq.api.domain.ScoreFlowCopyAccount;
import com.wlwq.api.service.IScoreFlowCopyAccountService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户积分审批抄送流程Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-07
 */
@Service
public class ScoreFlowCopyAccountServiceImpl implements IScoreFlowCopyAccountService {

    @Autowired
    private ScoreFlowCopyAccountMapper scoreFlowCopyAccountMapper;

    /**
     * 查询用户积分审批抄送流程
     *
     * @param scoreFlowCopyAccountId 用户积分审批抄送流程ID
     * @return 用户积分审批抄送流程
     */
    @Override
    public ScoreFlowCopyAccount selectScoreFlowCopyAccountById(String scoreFlowCopyAccountId) {
        return scoreFlowCopyAccountMapper.selectScoreFlowCopyAccountById(scoreFlowCopyAccountId);
    }

    /**
     * 查询用户积分审批抄送流程列表
     *
     * @param scoreFlowCopyAccount 用户积分审批抄送流程
     * @return 用户积分审批抄送流程
     */
    @Override
    public List<ScoreFlowCopyAccount> selectScoreFlowCopyAccountList(ScoreFlowCopyAccount scoreFlowCopyAccount) {
        return scoreFlowCopyAccountMapper.selectScoreFlowCopyAccountList(scoreFlowCopyAccount);
    }

    /**
     * 新增用户积分审批抄送流程
     *
     * @param scoreFlowCopyAccount 用户积分审批抄送流程
     * @return 结果
     */
    @Override
    public int insertScoreFlowCopyAccount(ScoreFlowCopyAccount scoreFlowCopyAccount) {
        scoreFlowCopyAccount.setScoreFlowCopyAccountId(IdUtil.getSnowflake(1, 1).nextIdStr());
        scoreFlowCopyAccount.setCreateTime(DateUtils.getNowDate());
        return scoreFlowCopyAccountMapper.insertScoreFlowCopyAccount(scoreFlowCopyAccount);
    }

    /**
     * 修改用户积分审批抄送流程
     *
     * @param scoreFlowCopyAccount 用户积分审批抄送流程
     * @return 结果
     */
    @Override
    public int updateScoreFlowCopyAccount(ScoreFlowCopyAccount scoreFlowCopyAccount) {
        scoreFlowCopyAccount.setUpdateTime(DateUtils.getNowDate());
        return scoreFlowCopyAccountMapper.updateScoreFlowCopyAccount(scoreFlowCopyAccount);
    }

    /**
     * 删除用户积分审批抄送流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScoreFlowCopyAccountByIds(String ids) {
        return scoreFlowCopyAccountMapper.deleteScoreFlowCopyAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户积分审批抄送流程信息
     *
     * @param scoreFlowCopyAccountId 用户积分审批抄送流程ID
     * @return 结果
     */
    @Override
    public int deleteScoreFlowCopyAccountById(String scoreFlowCopyAccountId) {
        return scoreFlowCopyAccountMapper.deleteScoreFlowCopyAccountById(scoreFlowCopyAccountId);
    }

    /**
     * 抄送我的列表
     * @param scoreFlowCopyAccount
     * @return
     */
    @Override
    public List<Map<String,Object>> selectCopyMyScoreFlowAccountList(ScoreFlowCopyAccount scoreFlowCopyAccount){
        return scoreFlowCopyAccountMapper.selectCopyMyScoreFlowAccountList(scoreFlowCopyAccount);
    }
}
