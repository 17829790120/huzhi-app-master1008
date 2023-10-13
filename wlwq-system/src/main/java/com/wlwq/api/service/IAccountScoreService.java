package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.AccountScore;

/**
 * 用户积分Service接口
 * 
 * @author gaoce
 * @date 2023-06-06
 */
public interface IAccountScoreService {
    /**
     * 查询用户积分
     * 
     * @param accountScoreId 用户积分ID
     * @return 用户积分
     */
    public AccountScore selectAccountScoreById(String accountScoreId);

    /**
     * 查询用户积分列表
     * 
     * @param accountScore 用户积分
     * @return 用户积分集合
     */
    public List<AccountScore> selectAccountScoreList(AccountScore accountScore);

    /**
     * 新增用户积分
     * 
     * @param accountScore 用户积分
     * @return 结果
     */
    public int insertAccountScore(AccountScore accountScore);

    /**
     * 修改用户积分
     * 
     * @param accountScore 用户积分
     * @return 结果
     */
    public int updateAccountScore(AccountScore accountScore);

    /**
     * 批量删除用户积分
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountScoreByIds(String ids);

    /**
     * 删除用户积分信息
     * 
     * @param accountScoreId 用户积分ID
     * @return 结果
     */
    public int deleteAccountScoreById(String accountScoreId);

    /**
     * api查询积分列表
     * @param accountScore
     * @return
     */
    public List<AccountScore> selectApiAccountScoreList(AccountScore accountScore);

    /**
     * 查询考试对应的获得积分数据
     * @param accountScore
     * @return
     */
    AccountScore selectScoreByAccountIdAndTargetId(AccountScore accountScore);

    /**
     * 查询课程对应的获得积分数据
     * @param accountScore
     * @return
     */
    Integer selectCourseScore(AccountScore accountScore);



    /**
     * 根据accountId和targetId查询AccountScoreId
     */

    AccountScore selectAccountScoreId(String accountId, String targetId);
}



