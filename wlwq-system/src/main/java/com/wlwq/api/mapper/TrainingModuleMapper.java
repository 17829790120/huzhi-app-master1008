package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.TrainingModule;

/**
 * 人员训练模块Mapper接口
 *
 * @author gaoce
 * @date 2023-07-31
 */
public interface TrainingModuleMapper {
    /**
     * 查询人员训练模块
     *
     * @param trainingModuleId 人员训练模块ID
     * @return 人员训练模块
     */
    public TrainingModule selectTrainingModuleById(String trainingModuleId);

    /**
     * 查询人员训练模块列表
     *
     * @param trainingModule 人员训练模块
     * @return 人员训练模块集合
     */
    public List<TrainingModule> selectTrainingModuleList(TrainingModule trainingModule);



    /**
     * 新增人员训练模块
     *
     * @param trainingModule 人员训练模块
     * @return 结果
     */
    public int insertTrainingModule(TrainingModule trainingModule);

    /**
     * 修改人员训练模块
     *
     * @param trainingModule 人员训练模块
     * @return 结果
     */
    public int updateTrainingModule(TrainingModule trainingModule);

    /**
     * 删除人员训练模块
     *
     * @param trainingModuleId 人员训练模块ID
     * @return 结果
     */
    public int deleteTrainingModuleById(String trainingModuleId);

    /**
     * 批量删除人员训练模块
     *
     * @param trainingModuleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrainingModuleByIds(String[] trainingModuleIds);


    /**
     * 查询人员训练模块列表
     *
     * @param trainingModule 人员训练模块
     * @return 人员训练模块集合
     */
    public List<TrainingModule> selectApiTrainingModuleList(TrainingModule trainingModule);
}
