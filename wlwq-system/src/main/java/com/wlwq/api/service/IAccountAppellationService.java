package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.AccountAppellation;

/**
 * 用户称谓Service接口
 *
 * @author gaoce
 * @date 2023-06-08
 */
public interface IAccountAppellationService {
    /**
     * 查询用户称谓
     *
     * @param accountAppellationId 用户称谓ID
     * @return 用户称谓
     */
    public AccountAppellation selectAccountAppellationById(String accountAppellationId);

    /**
     * 查询用户称谓列表
     *
     * @param accountAppellation 用户称谓
     * @return 用户称谓集合
     */
    public List<AccountAppellation> selectAccountAppellationList(AccountAppellation accountAppellation);

    /**
     * 新增用户称谓
     *
     * @param accountAppellation 用户称谓
     * @return 结果
     */
    public int insertAccountAppellation(AccountAppellation accountAppellation);

    /**
     * 修改用户称谓
     *
     * @param accountAppellation 用户称谓
     * @return 结果
     */
    public int updateAccountAppellation(AccountAppellation accountAppellation);

    /**
     * 批量删除用户称谓
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountAppellationByIds(String ids);

    /**
     * 删除用户称谓信息
     *
     * @param accountAppellationId 用户称谓ID
     * @return 结果
     */
    public int deleteAccountAppellationById(String accountAppellationId);

    /**
     * 根据积分值查询名称
     * @param accountAppellation
     * @return
     */
    public String selectAccountAppellationName(AccountAppellation accountAppellation);
}
