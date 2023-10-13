package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.AccountFlowerRecord;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.resultVO.flower.AccountFlowerResultVO;

/**
 * 红花记录Mapper接口
 *
 * @author gaoce
 * @date 2023-05-26
 */
public interface AccountFlowerRecordMapper {
    /**
     * 查询红花记录
     *
     * @param accountFlowerRecordId 红花记录ID
     * @return 红花记录
     */
    public AccountFlowerRecord selectAccountFlowerRecordById(String accountFlowerRecordId);

    /**
     * 查询红花记录列表
     *
     * @param accountFlowerRecord 红花记录
     * @return 红花记录集合
     */
    public List<AccountFlowerRecord> selectAccountFlowerRecordList(AccountFlowerRecord accountFlowerRecord);

    /**
     * 新增红花记录
     *
     * @param accountFlowerRecord 红花记录
     * @return 结果
     */
    public int insertAccountFlowerRecord(AccountFlowerRecord accountFlowerRecord);

    /**
     * 修改红花记录
     *
     * @param accountFlowerRecord 红花记录
     * @return 结果
     */
    public int updateAccountFlowerRecord(AccountFlowerRecord accountFlowerRecord);

    /**
     * 删除红花记录
     *
     * @param accountFlowerRecordId 红花记录ID
     * @return 结果
     */
    public int deleteAccountFlowerRecordById(String accountFlowerRecordId);

    /**
     * 批量删除红花记录
     *
     * @param accountFlowerRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountFlowerRecordByIds(String[] accountFlowerRecordIds);

    /**
     * 红花排行榜
     * @param account
     * @return
     */
    public List<AccountFlowerResultVO> selectApiFlowerAccountList(ApiAccount account);

    /**
     * 查询某个用户某个时间段的红花统计
     * @param account
     * @return
     */
    public AccountFlowerResultVO selectApiFlowerAccount(ApiAccount account);

    /**
     * 排行榜红花收入消耗列表
     * @param accountFlowerRecord
     * @return
     */
    List<AccountFlowerRecord> selectAccountFlowerRecordTotalList(AccountFlowerRecord accountFlowerRecord);
}
