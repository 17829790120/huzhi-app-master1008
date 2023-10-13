package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountEvaluateMapper;
import com.wlwq.api.domain.AccountEvaluate;
import com.wlwq.api.service.IAccountEvaluateService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户评价Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-02
 */
@Service
public class AccountEvaluateServiceImpl implements IAccountEvaluateService {

    @Autowired
    private AccountEvaluateMapper accountEvaluateMapper;

    /**
     * 查询用户评价
     *
     * @param accountEvaluateId 用户评价ID
     * @return 用户评价
     */
    @Override
    public AccountEvaluate selectAccountEvaluateById(String accountEvaluateId) {
        return accountEvaluateMapper.selectAccountEvaluateById(accountEvaluateId);
    }

    /**
     * 查询用户评价列表
     *
     * @param accountEvaluate 用户评价
     * @return 用户评价
     */
    @Override
    public List<AccountEvaluate> selectAccountEvaluateList(AccountEvaluate accountEvaluate) {
        return accountEvaluateMapper.selectAccountEvaluateList(accountEvaluate);
    }

    /**
     * 新增用户评价
     *
     * @param accountEvaluate 用户评价
     * @return 结果
     */
    @Override
    public int insertAccountEvaluate(AccountEvaluate accountEvaluate) {
        accountEvaluate.setAccountEvaluateId(IdUtil.getSnowflake(1, 1).nextIdStr());
        accountEvaluate.setCreateTime(DateUtils.getNowDate());
        return accountEvaluateMapper.insertAccountEvaluate(accountEvaluate);
    }

    /**
     * 修改用户评价
     *
     * @param accountEvaluate 用户评价
     * @return 结果
     */
    @Override
    public int updateAccountEvaluate(AccountEvaluate accountEvaluate) {
        return accountEvaluateMapper.updateAccountEvaluate(accountEvaluate);
    }

    /**
     * 删除用户评价对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountEvaluateByIds(String ids) {
        return accountEvaluateMapper.deleteAccountEvaluateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户评价信息
     *
     * @param accountEvaluateId 用户评价ID
     * @return 结果
     */
    @Override
    public int deleteAccountEvaluateById(String accountEvaluateId) {
        return accountEvaluateMapper.deleteAccountEvaluateById(accountEvaluateId);
    }

    /**
     * api评价列表
     * @param accountEvaluate
     * @return
     */
    @Override
    public List<AccountEvaluate> selectApiAccountEvaluateList(AccountEvaluate accountEvaluate){
        return accountEvaluateMapper.selectApiAccountEvaluateList(accountEvaluate);
    }
}
