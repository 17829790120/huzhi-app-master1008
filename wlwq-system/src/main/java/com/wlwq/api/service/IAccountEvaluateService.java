package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.AccountEvaluate;

/**
 * 用户评价Service接口
 *
 * @author gaoce
 * @date 2023-06-02
 */
public interface IAccountEvaluateService {
    /**
     * 查询用户评价
     *
     * @param accountEvaluateId 用户评价ID
     * @return 用户评价
     */
    public AccountEvaluate selectAccountEvaluateById(String accountEvaluateId);

    /**
     * 查询用户评价列表
     *
     * @param accountEvaluate 用户评价
     * @return 用户评价集合
     */
    public List<AccountEvaluate> selectAccountEvaluateList(AccountEvaluate accountEvaluate);

    /**
     * 新增用户评价
     *
     * @param accountEvaluate 用户评价
     * @return 结果
     */
    public int insertAccountEvaluate(AccountEvaluate accountEvaluate);

    /**
     * 修改用户评价
     *
     * @param accountEvaluate 用户评价
     * @return 结果
     */
    public int updateAccountEvaluate(AccountEvaluate accountEvaluate);

    /**
     * 批量删除用户评价
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountEvaluateByIds(String ids);

    /**
     * 删除用户评价信息
     *
     * @param accountEvaluateId 用户评价ID
     * @return 结果
     */
    public int deleteAccountEvaluateById(String accountEvaluateId);

    /**
     * api评价列表
     * @param accountEvaluate
     * @return
     */
    public List<AccountEvaluate> selectApiAccountEvaluateList(AccountEvaluate accountEvaluate);
}
