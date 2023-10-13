package com.wlwq.api.service;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.ReportTraining;

/**
 * 汇报训练Service接口
 *
 * @author gaoce
 * @date 2023-06-05
 */
public interface IReportTrainingService {
    /**
     * 查询汇报训练
     *
     * @param reportTrainingId 汇报训练ID
     * @return 汇报训练
     */
    public ReportTraining selectReportTrainingById(String reportTrainingId);

    /**
     * 查询汇报训练列表
     *
     * @param reportTraining 汇报训练
     * @return 汇报训练集合
     */
    public List<ReportTraining> selectReportTrainingList(ReportTraining reportTraining);

    /**
     * 查询汇报训练列表
     *
     * @param reportTraining 汇报训练
     * @return 汇报训练集合
     */
    public List<Map<String,Object>> selectWebReportTrainingList(ReportTraining reportTraining);

    /**
     * 新增汇报训练
     *
     * @param reportTraining 汇报训练
     * @return 结果
     */
    public int insertReportTraining(ReportTraining reportTraining);

    /**
     * 修改汇报训练
     *
     * @param reportTraining 汇报训练
     * @return 结果
     */
    public int updateReportTraining(ReportTraining reportTraining);

    /**
     * 批量删除汇报训练
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteReportTrainingByIds(String ids);

    /**
     * 删除汇报训练信息
     *
     * @param reportTrainingId 汇报训练ID
     * @return 结果
     */
    public int deleteReportTrainingById(String reportTrainingId);

    /**
     * api查询列表
     * @param reportTraining
     * @return
     */
    public List<ReportTraining> selectApiReportTrainingList(ReportTraining reportTraining);

    /**
     *  api查询数量
     * @param reportTraining
     * @return
     */
    public Integer selectApiReportTrainingCount(ReportTraining reportTraining);

    /**
     * 查询提交的人数
     * @param reportTraining
     * @return
     */
    public Integer selectApiReportTrainingAccountCount(ReportTraining reportTraining);


}
