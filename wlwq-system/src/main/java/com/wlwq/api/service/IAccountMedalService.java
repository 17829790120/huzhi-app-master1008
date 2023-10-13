package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.AccountMedal;
import com.wlwq.common.core.domain.Ztree;

/**
 * 用户勋章Service接口
 *
 * @author gaoce
 * @date 2023-06-08
 */
public interface IAccountMedalService {
    /**
     * 查询用户勋章
     *
     * @param accountMedalId 用户勋章ID
     * @return 用户勋章
     */
    public AccountMedal selectAccountMedalById(Long accountMedalId);

    /**
     * 查询用户勋章列表
     *
     * @param accountMedal 用户勋章
     * @return 用户勋章集合
     */
    public List<AccountMedal> selectAccountMedalList(AccountMedal accountMedal);

    /**
     * 新增用户勋章
     *
     * @param accountMedal 用户勋章
     * @return 结果
     */
    public int insertAccountMedal(AccountMedal accountMedal);

    /**
     * 修改用户勋章
     *
     * @param accountMedal 用户勋章
     * @return 结果
     */
    public int updateAccountMedal(AccountMedal accountMedal);

    /**
     * 批量删除用户勋章
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountMedalByIds(String ids);

    /**
     * 删除用户勋章信息
     *
     * @param accountMedalId 用户勋章ID
     * @return 结果
     */
    public int deleteAccountMedalById(Long accountMedalId);

    /**
     * 查询用户勋章树列表
     *
     * @return 所有用户勋章信息
     */
    public List<Ztree> selectAccountMedalTree();

    /**
     *  查询用户满足条件未领取的勋章列表
     * @param accountMedal
     * @return
     */
    public List<AccountMedal> selectApiAccountNoMedalList(AccountMedal accountMedal);

    /**
     * 查询勋章数量
     * @param accountMedal
     * @return
     */
    public int selectAccountMedalCount(AccountMedal accountMedal);

    /**
     * api查询勋章列表
     * @param accountMedal
     * @return
     */
    public List<AccountMedal> selectApiAccountMedalList(AccountMedal accountMedal);
}
