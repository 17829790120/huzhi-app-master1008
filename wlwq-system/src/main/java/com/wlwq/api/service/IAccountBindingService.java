package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.AccountBinding;

/**
 * 用户绑定Service接口
 * 
 * @author gaoce
 * @date 2023-07-26
 */
public interface IAccountBindingService {
    /**
     * 查询用户绑定
     * 
     * @param accountBindingId 用户绑定ID
     * @return 用户绑定
     */
    public AccountBinding selectAccountBindingById(String accountBindingId);

    /**
     * 查询用户绑定列表
     * 
     * @param accountBinding 用户绑定
     * @return 用户绑定集合
     */
    public List<AccountBinding> selectAccountBindingList(AccountBinding accountBinding);

    /**
     * 新增用户绑定
     * 
     * @param accountBinding 用户绑定
     * @return 结果
     */
    public int insertAccountBinding(AccountBinding accountBinding);

    /**
     * 修改用户绑定
     * 
     * @param accountBinding 用户绑定
     * @return 结果
     */
    public int updateAccountBinding(AccountBinding accountBinding);

    /**
     * 批量删除用户绑定
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountBindingByIds(String ids);

    /**
     * 删除用户绑定信息
     * 
     * @param accountBindingId 用户绑定ID
     * @return 结果
     */
    public int deleteAccountBindingById(String accountBindingId);

    /**
     * 根据条件查询一条记录
     * @param accountBinding
     * @return
     */
    public AccountBinding selectAccountBinding(AccountBinding accountBinding);
}
