package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.AccountSignRecord;

/**
 * 用户签到记录Service接口
 * 
 * @author gaoce
 * @date 2023-07-03
 */
public interface IAccountSignRecordService {
    /**
     * 查询用户签到记录
     * 
     * @param accountSignRecordId 用户签到记录ID
     * @return 用户签到记录
     */
    public AccountSignRecord selectAccountSignRecordById(String accountSignRecordId);

    /**
     * 查询用户签到记录列表
     * 
     * @param accountSignRecord 用户签到记录
     * @return 用户签到记录集合
     */
    public List<AccountSignRecord> selectAccountSignRecordList(AccountSignRecord accountSignRecord);

    /**
     * 新增用户签到记录
     * 
     * @param accountSignRecord 用户签到记录
     * @return 结果
     */
    public int insertAccountSignRecord(AccountSignRecord accountSignRecord);

    /**
     * 修改用户签到记录
     * 
     * @param accountSignRecord 用户签到记录
     * @return 结果
     */
    public int updateAccountSignRecord(AccountSignRecord accountSignRecord);

    /**
     * 批量删除用户签到记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountSignRecordByIds(String ids);

    /**
     * 删除用户签到记录信息
     * 
     * @param accountSignRecordId 用户签到记录ID
     * @return 结果
     */
    public int deleteAccountSignRecordById(String accountSignRecordId);

    /**
     * api查询用户的签到记录
     * @param accountSignRecord
     * @return
     */
    public List<AccountSignRecord> selectApiAccountSignRecordList(AccountSignRecord accountSignRecord);

    /**
     *  api查询用户的签到数量
     * @param accountSignRecord
     * @return
     */
    public int selectApiAccountSignRecordCount(AccountSignRecord accountSignRecord);
}
