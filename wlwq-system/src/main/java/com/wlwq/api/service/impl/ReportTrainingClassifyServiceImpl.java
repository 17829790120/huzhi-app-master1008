package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.api.mapper.ReportTrainingClassifyMapper;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.domain.ReportTrainingClassify;
import com.wlwq.api.service.IReportTrainingClassifyService;
import com.wlwq.common.core.text.Convert;

/**
 * 汇报训练分类Service业务层处理
 * 
 * @author gaoce
 * @date 2023-06-01
 */
@Service
public class ReportTrainingClassifyServiceImpl implements IReportTrainingClassifyService {

    @Autowired
    private ReportTrainingClassifyMapper reportTrainingClassifyMapper;

    /**
     * 查询汇报训练分类
     * 
     * @param reportTrainingClassifyId 汇报训练分类ID
     * @return 汇报训练分类
     */
    @Override
    public ReportTrainingClassify selectReportTrainingClassifyById(Long reportTrainingClassifyId) {
        return reportTrainingClassifyMapper.selectReportTrainingClassifyById(reportTrainingClassifyId);
    }

    /**
     * 查询汇报训练分类列表
     * 
     * @param reportTrainingClassify 汇报训练分类
     * @return 汇报训练分类
     */
    @Override
    public List<ReportTrainingClassify> selectReportTrainingClassifyList(ReportTrainingClassify reportTrainingClassify) {
        return reportTrainingClassifyMapper.selectReportTrainingClassifyList(reportTrainingClassify);
    }

    /**
     * 新增汇报训练分类
     * 
     * @param reportTrainingClassify 汇报训练分类
     * @return 结果
     */
    @Override
    public int insertReportTrainingClassify(ReportTrainingClassify reportTrainingClassify) {
        reportTrainingClassify.setCreateTime(DateUtils.getNowDate());
        return reportTrainingClassifyMapper.insertReportTrainingClassify(reportTrainingClassify);
    }

    /**
     * 修改汇报训练分类
     * 
     * @param reportTrainingClassify 汇报训练分类
     * @return 结果
     */
    @Override
    public int updateReportTrainingClassify(ReportTrainingClassify reportTrainingClassify) {
        reportTrainingClassify.setUpdateTime(DateUtils.getNowDate());
        return reportTrainingClassifyMapper.updateReportTrainingClassify(reportTrainingClassify);
    }

    /**
     * 删除汇报训练分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteReportTrainingClassifyByIds(String ids) {
        return reportTrainingClassifyMapper.deleteReportTrainingClassifyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除汇报训练分类信息
     * 
     * @param reportTrainingClassifyId 汇报训练分类ID
     * @return 结果
     */
    @Override
    public int deleteReportTrainingClassifyById(Long reportTrainingClassifyId) {
        return reportTrainingClassifyMapper.deleteReportTrainingClassifyById(reportTrainingClassifyId);
    }
}
