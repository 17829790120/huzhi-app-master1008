package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountScoreMapper;
import com.wlwq.api.domain.AccountScore;
import com.wlwq.api.service.IAccountScoreService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户积分Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-06
 */
@Service
public class AccountScoreServiceImpl implements IAccountScoreService {

    @Autowired
    private AccountScoreMapper accountScoreMapper;

    /**
     * 查询用户积分
     *
     * @param accountScoreId 用户积分ID
     * @return 用户积分
     */
    @Override
    public AccountScore selectAccountScoreById(String accountScoreId) {
        return accountScoreMapper.selectAccountScoreById(accountScoreId);
    }

    /**
     * 查询用户积分列表
     *
     * @param accountScore 用户积分
     * @return 用户积分
     */
    @Override
    public List<AccountScore> selectAccountScoreList(AccountScore accountScore) {
        return accountScoreMapper.selectAccountScoreList(accountScore);
    }

    /**
     * 新增用户积分
     *
     * @param accountScore 用户积分
     * @return 结果
     */
    @Override
    public int insertAccountScore(AccountScore accountScore) {
        accountScore.setAccountScoreId(IdUtil.getSnowflake(1, 1).nextIdStr());
        accountScore.setCreateTime(DateUtils.getNowDate());
        return accountScoreMapper.insertAccountScore(accountScore);
    }

    /**
     * 修改用户积分
     *
     * @param accountScore 用户积分
     * @return 结果
     */
    @Override
    public int updateAccountScore(AccountScore accountScore) {
        return accountScoreMapper.updateAccountScore(accountScore);
    }

    /**
     * 删除用户积分对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountScoreByIds(String ids) {
        return accountScoreMapper.deleteAccountScoreByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户积分信息
     *
     * @param accountScoreId 用户积分ID
     * @return 结果
     */
    @Override
    public int deleteAccountScoreById(String accountScoreId) {
        return accountScoreMapper.deleteAccountScoreById(accountScoreId);
    }

    /**
     * api查询积分列表
     *
     * @param accountScore
     * @return
     */
    @Override
    public List<AccountScore> selectApiAccountScoreList(AccountScore accountScore) {
        return accountScoreMapper.selectApiAccountScoreList(accountScore);
    }

    /**
     * 查询考试对应的获得积分数据
     *
     * @param accountScore
     * @return
     */
    @Override
    public AccountScore selectScoreByAccountIdAndTargetId(AccountScore accountScore) {
        return accountScoreMapper.selectScoreByAccountIdAndTargetId(accountScore);
    }

    /**
     * 查询课程对应的获得积分数据
     *
     * @param accountScore
     * @return
     */
    @Override
    public Integer selectCourseScore(AccountScore accountScore) {
        return accountScoreMapper.selectCourseScore(accountScore);
    }


    /**
     * 根据accountId和targetId查询AccountScoreId
     */
    @Override
   public AccountScore selectAccountScoreId(String accountId, String targetId) {
        return accountScoreMapper.selectAccountScoreId(accountId, targetId);
    }
}
