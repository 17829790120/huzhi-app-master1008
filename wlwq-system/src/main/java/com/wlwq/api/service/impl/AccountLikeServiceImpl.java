package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountLikeMapper;
import com.wlwq.api.domain.AccountLike;
import com.wlwq.api.service.IAccountLikeService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户点赞Service业务层处理
 * 
 * @author gaoce
 * @date 2023-06-02
 */
@Service
public class AccountLikeServiceImpl implements IAccountLikeService {

    @Autowired
    private AccountLikeMapper accountLikeMapper;

    /**
     * 查询用户点赞
     * 
     * @param accountLikeId 用户点赞ID
     * @return 用户点赞
     */
    @Override
    public AccountLike selectAccountLikeById(String accountLikeId) {
        return accountLikeMapper.selectAccountLikeById(accountLikeId);
    }

    /**
     * 查询用户点赞列表
     * 
     * @param accountLike 用户点赞
     * @return 用户点赞
     */
    @Override
    public List<AccountLike> selectAccountLikeList(AccountLike accountLike) {
        return accountLikeMapper.selectAccountLikeList(accountLike);
    }

    /**
     * 新增用户点赞
     * 
     * @param accountLike 用户点赞
     * @return 结果
     */
    @Override
    public int insertAccountLike(AccountLike accountLike) {
        accountLike.setAccountLikeId(IdUtil.getSnowflake(1,1).nextIdStr());
        accountLike.setCreateTime(DateUtils.getNowDate());
        return accountLikeMapper.insertAccountLike(accountLike);
    }

    /**
     * 修改用户点赞
     * 
     * @param accountLike 用户点赞
     * @return 结果
     */
    @Override
    public int updateAccountLike(AccountLike accountLike) {
        return accountLikeMapper.updateAccountLike(accountLike);
    }

    /**
     * 删除用户点赞对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountLikeByIds(String ids) {
        return accountLikeMapper.deleteAccountLikeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户点赞信息
     * 
     * @param accountLikeId 用户点赞ID
     * @return 结果
     */
    @Override
    public int deleteAccountLikeById(String accountLikeId) {
        return accountLikeMapper.deleteAccountLikeById(accountLikeId);
    }

    /**
     * 给很具条件查询一条数据
     * @param accountLike
     * @return
     */
    @Override
    public AccountLike selectAccountLike(AccountLike accountLike){
        return accountLikeMapper.selectAccountLike(accountLike);
    }

    /**
     * 查询点赞数量
     * @param accountLike
     * @return
     */
    @Override
    public int selectAccountLikeCount(AccountLike accountLike){
        return accountLikeMapper.selectAccountLikeCount(accountLike);
    }
}
