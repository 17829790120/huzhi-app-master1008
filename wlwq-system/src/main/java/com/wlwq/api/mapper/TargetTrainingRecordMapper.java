package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.TargetTrainingRecord;

/**
 * 目标训练记录Mapper接口
 *
 * @author wwb
 * @date 2023-06-05
 */
public interface TargetTrainingRecordMapper {
    /**
     * 查询目标训练记录
     *
     * @param targetTrainingRecordId 目标训练记录ID
     * @return 目标训练记录
     */
    public TargetTrainingRecord selectTargetTrainingRecordById(String targetTrainingRecordId);

    /**
     * 查询目标训练记录列表
     *
     * @param targetTrainingRecord 目标训练记录
     * @return 目标训练记录集合
     */
    public List<TargetTrainingRecord> selectTargetTrainingRecordList(TargetTrainingRecord targetTrainingRecord);

    /**
     * 新增目标训练记录
     *
     * @param targetTrainingRecord 目标训练记录
     * @return 结果
     */
    public int insertTargetTrainingRecord(TargetTrainingRecord targetTrainingRecord);

    /**
     * 修改目标训练记录
     *
     * @param targetTrainingRecord 目标训练记录
     * @return 结果
     */
    public int updateTargetTrainingRecord(TargetTrainingRecord targetTrainingRecord);

    /**
     * 删除目标训练记录
     *
     * @param targetTrainingRecordId 目标训练记录ID
     * @return 结果
     */
    public int deleteTargetTrainingRecordById(String targetTrainingRecordId);

    /**
     * 批量删除目标训练记录
     *
     * @param targetTrainingRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTargetTrainingRecordByIds(String[] targetTrainingRecordIds);
}
