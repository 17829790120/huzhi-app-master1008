package com.wlwq.api.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.wlwq.common.core.domain.Ztree;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountMedalMapper;
import com.wlwq.api.domain.AccountMedal;
import com.wlwq.api.service.IAccountMedalService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户勋章Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-08
 */
@Service
public class AccountMedalServiceImpl implements IAccountMedalService {

    @Autowired
    private AccountMedalMapper accountMedalMapper;

    /**
     * 查询用户勋章
     *
     * @param accountMedalId 用户勋章ID
     * @return 用户勋章
     */
    @Override
    public AccountMedal selectAccountMedalById(Long accountMedalId) {
        return accountMedalMapper.selectAccountMedalById(accountMedalId);
    }

    /**
     * 查询用户勋章列表
     *
     * @param accountMedal 用户勋章
     * @return 用户勋章
     */
    @Override
    public List<AccountMedal> selectAccountMedalList(AccountMedal accountMedal) {
        return accountMedalMapper.selectAccountMedalList(accountMedal);
    }

    /**
     * 新增用户勋章
     *
     * @param accountMedal 用户勋章
     * @return 结果
     */
    @Override
    public int insertAccountMedal(AccountMedal accountMedal) {
        accountMedal.setCreateTime(DateUtils.getNowDate());
        return accountMedalMapper.insertAccountMedal(accountMedal);
    }

    /**
     * 修改用户勋章
     *
     * @param accountMedal 用户勋章
     * @return 结果
     */
    @Override
    public int updateAccountMedal(AccountMedal accountMedal) {
        accountMedal.setUpdateTime(DateUtils.getNowDate());
        return accountMedalMapper.updateAccountMedal(accountMedal);
    }

    /**
     * 删除用户勋章对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountMedalByIds(String ids) {
        return accountMedalMapper.deleteAccountMedalByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户勋章信息
     *
     * @param accountMedalId 用户勋章ID
     * @return 结果
     */
    @Override
    public int deleteAccountMedalById(Long accountMedalId) {
        return accountMedalMapper.deleteAccountMedalById(accountMedalId);
    }

    /**
     * 查询用户勋章树列表
     *
     * @return 所有用户勋章信息
     */
    @Override
    public List<Ztree> selectAccountMedalTree() {
        List<AccountMedal> accountMedalList = accountMedalMapper.selectAccountMedalList(new AccountMedal());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (AccountMedal accountMedal : accountMedalList) {
            Ztree ztree = new Ztree();
            ztree.setId(accountMedal.getAccountMedalId());
            ztree.setpId(accountMedal.getParentId());
            ztree.setName(accountMedal.getMedalName());
            ztree.setTitle(accountMedal.getMedalName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
     *  查询用户满足条件未领取的勋章列表
     * @param accountMedal
     * @return
     */
    @Override
    public List<AccountMedal> selectApiAccountNoMedalList(AccountMedal accountMedal){
        return accountMedalMapper.selectApiAccountNoMedalList(accountMedal);
    }

    /**
     * 查询勋章数量
     * @param accountMedal
     * @return
     */
    @Override
    public int selectAccountMedalCount(AccountMedal accountMedal){
        return accountMedalMapper.selectAccountMedalCount(accountMedal);
    }

    /**
     * api查询勋章列表
     * @param accountMedal
     * @return
     */
    @Override
    public List<AccountMedal> selectApiAccountMedalList(AccountMedal accountMedal){
        return accountMedalMapper.selectApiAccountMedalList(accountMedal);
    }
}
