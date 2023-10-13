package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountBindingMapper;
import com.wlwq.api.domain.AccountBinding;
import com.wlwq.api.service.IAccountBindingService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户绑定Service业务层处理
 * 
 * @author gaoce
 * @date 2023-07-26
 */
@Service
public class AccountBindingServiceImpl implements IAccountBindingService {

    @Autowired
    private AccountBindingMapper accountBindingMapper;

    /**
     * 查询用户绑定
     * 
     * @param accountBindingId 用户绑定ID
     * @return 用户绑定
     */
    @Override
    public AccountBinding selectAccountBindingById(String accountBindingId) {
        return accountBindingMapper.selectAccountBindingById(accountBindingId);
    }

    /**
     * 查询用户绑定列表
     * 
     * @param accountBinding 用户绑定
     * @return 用户绑定
     */
    @Override
    public List<AccountBinding> selectAccountBindingList(AccountBinding accountBinding) {
        return accountBindingMapper.selectAccountBindingList(accountBinding);
    }

    /**
     * 新增用户绑定
     * 
     * @param accountBinding 用户绑定
     * @return 结果
     */
    @Override
    public int insertAccountBinding(AccountBinding accountBinding) {
        accountBinding.setAccountBindingId(IdUtil.getSnowflake(1,1).nextIdStr());
        accountBinding.setCreateTime(DateUtils.getNowDate());
        return accountBindingMapper.insertAccountBinding(accountBinding);
    }

    /**
     * 修改用户绑定
     * 
     * @param accountBinding 用户绑定
     * @return 结果
     */
    @Override
    public int updateAccountBinding(AccountBinding accountBinding) {
        accountBinding.setUpdateTime(DateUtils.getNowDate());
        return accountBindingMapper.updateAccountBinding(accountBinding);
    }

    /**
     * 删除用户绑定对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountBindingByIds(String ids) {
        return accountBindingMapper.deleteAccountBindingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户绑定信息
     * 
     * @param accountBindingId 用户绑定ID
     * @return 结果
     */
    @Override
    public int deleteAccountBindingById(String accountBindingId) {
        return accountBindingMapper.deleteAccountBindingById(accountBindingId);
    }

    /**
     * 根据条件查询一条记录
     * @param accountBinding
     * @return
     */
    @Override
    public AccountBinding selectAccountBinding(AccountBinding accountBinding){
        return accountBindingMapper.selectAccountBinding(accountBinding);
    }
}
