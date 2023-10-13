package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountAppellationMapper;
import com.wlwq.api.domain.AccountAppellation;
import com.wlwq.api.service.IAccountAppellationService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户称谓Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-08
 */
@Service
public class AccountAppellationServiceImpl implements IAccountAppellationService {

    @Autowired
    private AccountAppellationMapper accountAppellationMapper;

    /**
     * 查询用户称谓
     *
     * @param accountAppellationId 用户称谓ID
     * @return 用户称谓
     */
    @Override
    public AccountAppellation selectAccountAppellationById(String accountAppellationId) {
        return accountAppellationMapper.selectAccountAppellationById(accountAppellationId);
    }

    /**
     * 查询用户称谓列表
     *
     * @param accountAppellation 用户称谓
     * @return 用户称谓
     */
    @Override
    public List<AccountAppellation> selectAccountAppellationList(AccountAppellation accountAppellation) {
        return accountAppellationMapper.selectAccountAppellationList(accountAppellation);
    }

    /**
     * 新增用户称谓
     *
     * @param accountAppellation 用户称谓
     * @return 结果
     */
    @Override
    public int insertAccountAppellation(AccountAppellation accountAppellation) {
        accountAppellation.setAccountAppellationId(IdUtil.getSnowflake(1, 1).nextIdStr());
        accountAppellation.setCreateTime(DateUtils.getNowDate());
        return accountAppellationMapper.insertAccountAppellation(accountAppellation);
    }

    /**
     * 修改用户称谓
     *
     * @param accountAppellation 用户称谓
     * @return 结果
     */
    @Override
    public int updateAccountAppellation(AccountAppellation accountAppellation) {
        accountAppellation.setUpdateTime(DateUtils.getNowDate());
        return accountAppellationMapper.updateAccountAppellation(accountAppellation);
    }

    /**
     * 删除用户称谓对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountAppellationByIds(String ids) {
        return accountAppellationMapper.deleteAccountAppellationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户称谓信息
     *
     * @param accountAppellationId 用户称谓ID
     * @return 结果
     */
    @Override
    public int deleteAccountAppellationById(String accountAppellationId) {
        return accountAppellationMapper.deleteAccountAppellationById(accountAppellationId);
    }

    /**
     * 根据积分值查询名称
     * @param accountAppellation
     * @return
     */
    @Override
    public String selectAccountAppellationName(AccountAppellation accountAppellation){
        return accountAppellationMapper.selectAccountAppellationName(accountAppellation);
    }
}
