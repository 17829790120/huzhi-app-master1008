package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.AccountLike;

/**
 * 用户点赞Service接口
 * 
 * @author gaoce
 * @date 2023-06-02
 */
public interface IAccountLikeService {
    /**
     * 查询用户点赞
     * 
     * @param accountLikeId 用户点赞ID
     * @return 用户点赞
     */
    public AccountLike selectAccountLikeById(String accountLikeId);

    /**
     * 查询用户点赞列表
     * 
     * @param accountLike 用户点赞
     * @return 用户点赞集合
     */
    public List<AccountLike> selectAccountLikeList(AccountLike accountLike);

    /**
     * 新增用户点赞
     * 
     * @param accountLike 用户点赞
     * @return 结果
     */
    public int insertAccountLike(AccountLike accountLike);

    /**
     * 修改用户点赞
     * 
     * @param accountLike 用户点赞
     * @return 结果
     */
    public int updateAccountLike(AccountLike accountLike);

    /**
     * 批量删除用户点赞
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountLikeByIds(String ids);

    /**
     * 删除用户点赞信息
     * 
     * @param accountLikeId 用户点赞ID
     * @return 结果
     */
    public int deleteAccountLikeById(String accountLikeId);

    /**
     * 给很具条件查询一条数据
     * @param accountLike
     * @return
     */
    public AccountLike selectAccountLike(AccountLike accountLike);

    /**
     * 查询点赞数量
     * @param accountLike
     * @return
     */
    public int selectAccountLikeCount(AccountLike accountLike);
}
