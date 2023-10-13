package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.TargetTraining;

/**
 * 目标训练管理Service接口
 *
 * @author wwb
 * @date 2023-06-01
 */
public interface ITargetTrainingService {
    /**
     * 查询目标训练管理
     *
     * @param targetTrainingId 目标训练管理ID
     * @return 目标训练管理
     */
    public TargetTraining selectTargetTrainingById(String targetTrainingId);

    /**
     * 查询目标训练管理列表
     *
     * @param targetTraining 目标训练管理
     * @return 目标训练管理集合
     */
    public List<TargetTraining> selectTargetTrainingList(TargetTraining targetTraining);

    /**
     * 新增目标训练管理
     *
     * @param targetTraining 目标训练管理
     * @return 结果
     */
    public int insertTargetTraining(TargetTraining targetTraining);

    /**
     * 修改目标训练管理
     *
     * @param targetTraining 目标训练管理
     * @return 结果
     */
    public int updateTargetTraining(TargetTraining targetTraining);

    /**
     * 批量删除目标训练管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTargetTrainingByIds(String ids);

    /**
     * 删除目标训练管理信息
     *
     * @param targetTrainingId 目标训练管理ID
     * @return 结果
     */
    public int deleteTargetTrainingById(String targetTrainingId);
}
