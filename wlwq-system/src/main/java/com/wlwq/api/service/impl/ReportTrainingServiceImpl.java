package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ReportTrainingMapper;
import com.wlwq.api.domain.ReportTraining;
import com.wlwq.api.service.IReportTrainingService;
import com.wlwq.common.core.text.Convert;

/**
 * 汇报训练Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-05
 */
@Service
public class ReportTrainingServiceImpl implements IReportTrainingService {

    @Autowired
    private ReportTrainingMapper reportTrainingMapper;

    /**
     * 查询汇报训练
     *
     * @param reportTrainingId 汇报训练ID
     * @return 汇报训练
     */
    @Override
    public ReportTraining selectReportTrainingById(String reportTrainingId) {
        return reportTrainingMapper.selectReportTrainingById(reportTrainingId);
    }

    /**
     * 查询汇报训练列表
     *
     * @param reportTraining 汇报训练
     * @return 汇报训练集合
     */
    @Override
    public List<ReportTraining> selectReportTrainingList(ReportTraining reportTraining){
        return reportTrainingMapper.selectReportTrainingList(reportTraining);
    }

    /**
     * 查询汇报训练列表
     *
     * @param reportTraining 汇报训练
     * @return 汇报训练
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<Map<String,Object>> selectWebReportTrainingList(ReportTraining reportTraining) {
        return reportTrainingMapper.selectWebReportTrainingList(reportTraining);
    }

    /**
     * 新增汇报训练
     *
     * @param reportTraining 汇报训练
     * @return 结果
     */
    @Override
    public int insertReportTraining(ReportTraining reportTraining) {
        reportTraining.setReportTrainingId(IdUtil.getSnowflake(1, 1).nextIdStr());
        reportTraining.setCreateTime(DateUtils.getNowDate());
        return reportTrainingMapper.insertReportTraining(reportTraining);
    }

    /**
     * 修改汇报训练
     *
     * @param reportTraining 汇报训练
     * @return 结果
     */
    @Override
    public int updateReportTraining(ReportTraining reportTraining) {
        reportTraining.setUpdateTime(DateUtils.getNowDate());
        return reportTrainingMapper.updateReportTraining(reportTraining);
    }

    /**
     * 删除汇报训练对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteReportTrainingByIds(String ids) {
        return reportTrainingMapper.deleteReportTrainingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除汇报训练信息
     *
     * @param reportTrainingId 汇报训练ID
     * @return 结果
     */
    @Override
    public int deleteReportTrainingById(String reportTrainingId) {
        return reportTrainingMapper.deleteReportTrainingById(reportTrainingId);
    }

    /**
     * api查询列表
     * @param reportTraining
     * @return
     */
    @Override
    public List<ReportTraining> selectApiReportTrainingList(ReportTraining reportTraining){
        return reportTrainingMapper.selectApiReportTrainingList(reportTraining);
    }

    /**
     *  api查询数量
     * @param reportTraining
     * @return
     */
    @Override
    public Integer selectApiReportTrainingCount(ReportTraining reportTraining){
        return reportTrainingMapper.selectApiReportTrainingCount(reportTraining);
    }

    /**
     * 查询提交的人数
     * @param reportTraining
     * @return
     */
    @Override
    public Integer selectApiReportTrainingAccountCount(ReportTraining reportTraining){
        return reportTrainingMapper.selectApiReportTrainingAccountCount(reportTraining);
    }

}
