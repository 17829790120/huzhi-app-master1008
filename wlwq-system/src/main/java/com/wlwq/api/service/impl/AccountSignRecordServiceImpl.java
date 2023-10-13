package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountSignRecordMapper;
import com.wlwq.api.domain.AccountSignRecord;
import com.wlwq.api.service.IAccountSignRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户签到记录Service业务层处理
 *
 * @author gaoce
 * @date 2023-07-03
 */
@Service
public class AccountSignRecordServiceImpl implements IAccountSignRecordService {

    @Autowired
    private AccountSignRecordMapper accountSignRecordMapper;

    /**
     * 查询用户签到记录
     *
     * @param accountSignRecordId 用户签到记录ID
     * @return 用户签到记录
     */
    @Override
    public AccountSignRecord selectAccountSignRecordById(String accountSignRecordId) {
        return accountSignRecordMapper.selectAccountSignRecordById(accountSignRecordId);
    }

    /**
     * 查询用户签到记录列表
     *
     * @param accountSignRecord 用户签到记录
     * @return 用户签到记录
     */
    @Override
    public List<AccountSignRecord> selectAccountSignRecordList(AccountSignRecord accountSignRecord) {
        return accountSignRecordMapper.selectAccountSignRecordList(accountSignRecord);
    }

    /**
     * 新增用户签到记录
     *
     * @param accountSignRecord 用户签到记录
     * @return 结果
     */
    @Override
    public int insertAccountSignRecord(AccountSignRecord accountSignRecord) {
        accountSignRecord.setAccountSignRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        accountSignRecord.setCreateTime(DateUtils.getNowDate());
        return accountSignRecordMapper.insertAccountSignRecord(accountSignRecord);
    }

    /**
     * 修改用户签到记录
     *
     * @param accountSignRecord 用户签到记录
     * @return 结果
     */
    @Override
    public int updateAccountSignRecord(AccountSignRecord accountSignRecord) {
        accountSignRecord.setUpdateTime(DateUtils.getNowDate());
        return accountSignRecordMapper.updateAccountSignRecord(accountSignRecord);
    }

    /**
     * 删除用户签到记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountSignRecordByIds(String ids) {
        return accountSignRecordMapper.deleteAccountSignRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户签到记录信息
     *
     * @param accountSignRecordId 用户签到记录ID
     * @return 结果
     */
    @Override
    public int deleteAccountSignRecordById(String accountSignRecordId) {
        return accountSignRecordMapper.deleteAccountSignRecordById(accountSignRecordId);
    }

    /**
     * api查询用户的签到记录
     * @param accountSignRecord
     * @return
     */
    @Override
    public List<AccountSignRecord> selectApiAccountSignRecordList(AccountSignRecord accountSignRecord){
        return accountSignRecordMapper.selectApiAccountSignRecordList(accountSignRecord);
    }

    /**
     *  api查询用户的签到数量
     * @param accountSignRecord
     * @return
     */
    @Override
    public int selectApiAccountSignRecordCount(AccountSignRecord accountSignRecord){
        return accountSignRecordMapper.selectApiAccountSignRecordCount(accountSignRecord);
    }

}
