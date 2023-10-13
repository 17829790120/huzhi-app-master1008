package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.resultVO.flower.AccountFlowerResultVO;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountFlowerRecordMapper;
import com.wlwq.api.domain.AccountFlowerRecord;
import com.wlwq.api.service.IAccountFlowerRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 红花记录Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-26
 */
@Service
public class AccountFlowerRecordServiceImpl implements IAccountFlowerRecordService {

    @Autowired
    private AccountFlowerRecordMapper accountFlowerRecordMapper;

    /**
     * 查询红花记录
     *
     * @param accountFlowerRecordId 红花记录ID
     * @return 红花记录
     */
    @Override
    public AccountFlowerRecord selectAccountFlowerRecordById(String accountFlowerRecordId) {
        return accountFlowerRecordMapper.selectAccountFlowerRecordById(accountFlowerRecordId);
    }

    /**
     * 查询红花记录列表
     *
     * @param accountFlowerRecord 红花记录
     * @return 红花记录
     */
    @Override
    public List<AccountFlowerRecord> selectAccountFlowerRecordList(AccountFlowerRecord accountFlowerRecord) {
        return accountFlowerRecordMapper.selectAccountFlowerRecordList(accountFlowerRecord);
    }

    /**
     * 新增红花记录
     *
     * @param accountFlowerRecord 红花记录
     * @return 结果
     */
    @Override
    public int insertAccountFlowerRecord(AccountFlowerRecord accountFlowerRecord) {
        accountFlowerRecord.setAccountFlowerRecordId(IdUtil.getSnowflake(1,1).nextIdStr());
        accountFlowerRecord.setCreateTime(DateUtils.getNowDate());
        return accountFlowerRecordMapper.insertAccountFlowerRecord(accountFlowerRecord);
    }

    /**
     * 修改红花记录
     *
     * @param accountFlowerRecord 红花记录
     * @return 结果
     */
    @Override
    public int updateAccountFlowerRecord(AccountFlowerRecord accountFlowerRecord) {
        return accountFlowerRecordMapper.updateAccountFlowerRecord(accountFlowerRecord);
    }

    /**
     * 删除红花记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountFlowerRecordByIds(String ids) {
        return accountFlowerRecordMapper.deleteAccountFlowerRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除红花记录信息
     *
     * @param accountFlowerRecordId 红花记录ID
     * @return 结果
     */
    @Override
    public int deleteAccountFlowerRecordById(String accountFlowerRecordId) {
        return accountFlowerRecordMapper.deleteAccountFlowerRecordById(accountFlowerRecordId);
    }

    /**
     * 红花排行榜
     * @param account
     * @return
     */
    @Override
    public List<AccountFlowerResultVO> selectApiFlowerAccountList(ApiAccount account){
        return accountFlowerRecordMapper.selectApiFlowerAccountList(account);
    }

    /**
     * 查询某个用户某个时间段的红花统计
     * @param account
     * @return
     */
    @Override
    public AccountFlowerResultVO selectApiFlowerAccount(ApiAccount account){
        return accountFlowerRecordMapper.selectApiFlowerAccount(account);
    }

    /**
     * 排行榜红花收入消耗列表
     * @param accountFlowerRecord
     * @return
     */
    @Override
    public List<AccountFlowerRecord> selectAccountFlowerRecordTotalList(AccountFlowerRecord accountFlowerRecord) {
        return accountFlowerRecordMapper.selectAccountFlowerRecordTotalList(accountFlowerRecord);
    }
}
