package com.wlwq.api.service;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.WinningTrainingRecord;

/**
 * 人与人pkService接口
 *
 * @author wwb
 * @date 2023-06-07
 */
public interface IWinningTrainingRecordService {
    /**
     * 查询人与人pk
     *
     * @param winningTrainingRecordId 人与人pkID
     * @return 人与人pk
     */
    public WinningTrainingRecord selectWinningTrainingRecordById(String winningTrainingRecordId);

    /**
     * 查询人与人pk列表
     *
     * @param winningTrainingRecord 人与人pk
     * @return 人与人pk集合
     */
    public List<WinningTrainingRecord> selectWinningTrainingRecordList(WinningTrainingRecord winningTrainingRecord);

    /**
     * 新增人与人pk
     *
     * @param winningTrainingRecord 人与人pk
     * @return 结果
     */
    public int insertWinningTrainingRecord(WinningTrainingRecord winningTrainingRecord);

    /**
     * 修改人与人pk
     *
     * @param winningTrainingRecord 人与人pk
     * @return 结果
     */
    public int updateWinningTrainingRecord(WinningTrainingRecord winningTrainingRecord);

    /**
     * 批量删除人与人pk
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWinningTrainingRecordByIds(String ids);

    /**
     * 删除人与人pk信息
     *
     * @param winningTrainingRecordId 人与人pkID
     * @return 结果
     */
    public int deleteWinningTrainingRecordById(String winningTrainingRecordId);
    /**
     * 查询人与人pk列表
     *
     * @param winningTrainingRecord
     * @return 汇报训练集合
     */
    List<Map<String, Object>> selectWebWinningTrainingList(WinningTrainingRecord winningTrainingRecord);
}
