package com.wlwq.api.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.WinningTrainingRecord;

/**
 * 人与人pkMapper接口
 *
 * @author wwb
 * @date 2023-06-07
 */
public interface WinningTrainingRecordMapper {
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
     * 删除人与人pk
     *
     * @param winningTrainingRecordId 人与人pkID
     * @return 结果
     */
    public int deleteWinningTrainingRecordById(String winningTrainingRecordId);

    /**
     * 批量删除人与人pk
     *
     * @param winningTrainingRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteWinningTrainingRecordByIds(String[] winningTrainingRecordIds);
    /**
     * 查询人与人pk列表
     *
     * @param winningTrainingRecord
     * @return 汇报训练集合
     */
    List<Map<String, Object>> selectWebWinningTrainingList(WinningTrainingRecord winningTrainingRecord);
}
