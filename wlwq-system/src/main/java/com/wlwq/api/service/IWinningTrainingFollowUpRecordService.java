package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.WinningTrainingFollowUpRecord;

/**
 * 训练跟进记录Service接口
 *
 * @author wwb
 * @date 2023-06-08
 */
public interface IWinningTrainingFollowUpRecordService {
    /**
     * 查询训练跟进记录
     *
     * @param winningTrainingFollowUpRecordId 训练跟进记录ID
     * @return 训练跟进记录
     */
    public WinningTrainingFollowUpRecord selectWinningTrainingFollowUpRecordById(String winningTrainingFollowUpRecordId);

    /**
     * 查询训练跟进记录列表
     *
     * @param winningTrainingFollowUpRecord 训练跟进记录
     * @return 训练跟进记录集合
     */
    public List<WinningTrainingFollowUpRecord> selectWinningTrainingFollowUpRecordList(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord);

    /**
     * 新增训练跟进记录
     *
     * @param winningTrainingFollowUpRecord 训练跟进记录
     * @return 结果
     */
    public int insertWinningTrainingFollowUpRecord(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord);

    /**
     * 修改训练跟进记录
     *
     * @param winningTrainingFollowUpRecord 训练跟进记录
     * @return 结果
     */
    public int updateWinningTrainingFollowUpRecord(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord);

    /**
     * 批量删除训练跟进记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWinningTrainingFollowUpRecordByIds(String ids);

    /**
     * 删除训练跟进记录信息
     *
     * @param winningTrainingFollowUpRecordId 训练跟进记录ID
     * @return 结果
     */
    public int deleteWinningTrainingFollowUpRecordById(String winningTrainingFollowUpRecordId);
}
