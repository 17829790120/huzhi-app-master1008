package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.AccountMedal;
import com.wlwq.api.domain.AccountMedalRecord;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.resultVO.score.AccountScoreResultVO;

/**
 * 用户勋章记录Service接口
 * 
 * @author gaoce
 * @date 2023-06-09
 */
public interface IAccountMedalRecordService {
    /**
     * 查询用户勋章记录
     * 
     * @param accountMedalRecordId 用户勋章记录ID
     * @return 用户勋章记录
     */
    public AccountMedalRecord selectAccountMedalRecordById(String accountMedalRecordId);

    /**
     * 查询用户勋章记录列表
     * 
     * @param accountMedalRecord 用户勋章记录
     * @return 用户勋章记录集合
     */
    public List<AccountMedalRecord> selectAccountMedalRecordList(AccountMedalRecord accountMedalRecord);

    /**
     * 新增用户勋章记录
     * 
     * @param accountMedalRecord 用户勋章记录
     * @return 结果
     */
    public int insertAccountMedalRecord(AccountMedalRecord accountMedalRecord);

    /**
     * 修改用户勋章记录
     * 
     * @param accountMedalRecord 用户勋章记录
     * @return 结果
     */
    public int updateAccountMedalRecord(AccountMedalRecord accountMedalRecord);

    /**
     * 批量删除用户勋章记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountMedalRecordByIds(String ids);

    /**
     * 删除用户勋章记录信息
     * 
     * @param accountMedalRecordId 用户勋章记录ID
     * @return 结果
     */
    public int deleteAccountMedalRecordById(String accountMedalRecordId);


    /**
     * 更新勋章
     * account 用户信息
     * score 本次更新积分
     * remark 备注
     * @return
     */
    public void updateMedalRecord(ApiAccount account, Integer score, String remark, AccountMedal accountMedal);


    /**
     * 根据条件查询数量
     * @param accountMedalRecord
     * @return
     */
    public int selectAccountMedalRecordCountByParentId(AccountMedalRecord accountMedalRecord);

    /**
     * api查询勋章列表
     * @param accountMedalRecord
     * @return
     */
    public List<AccountMedalRecord> selectApiAccountMedalRecordList(AccountMedalRecord accountMedalRecord);

    /**
     * 查询勋章排行榜
     * @param account
     * @return
     */
    public List<AccountScoreResultVO> selectApiMedalAccountList(ApiAccount account);

    /**
     *  根据条件查询二级勋章数量（去重）
     * @param accountMedalRecord
     * @return
     */
    public int selectDistinctAccountMedalRecordCountByParentId(AccountMedalRecord accountMedalRecord);
}
