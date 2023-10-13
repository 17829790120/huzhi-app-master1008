package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.ReportTrainingClassify;

/**
 * 汇报训练分类Service接口
 * 
 * @author gaoce
 * @date 2023-06-01
 */
public interface IReportTrainingClassifyService {
    /**
     * 查询汇报训练分类
     * 
     * @param reportTrainingClassifyId 汇报训练分类ID
     * @return 汇报训练分类
     */
    public ReportTrainingClassify selectReportTrainingClassifyById(Long reportTrainingClassifyId);

    /**
     * 查询汇报训练分类列表
     * 
     * @param reportTrainingClassify 汇报训练分类
     * @return 汇报训练分类集合
     */
    public List<ReportTrainingClassify> selectReportTrainingClassifyList(ReportTrainingClassify reportTrainingClassify);

    /**
     * 新增汇报训练分类
     * 
     * @param reportTrainingClassify 汇报训练分类
     * @return 结果
     */
    public int insertReportTrainingClassify(ReportTrainingClassify reportTrainingClassify);

    /**
     * 修改汇报训练分类
     * 
     * @param reportTrainingClassify 汇报训练分类
     * @return 结果
     */
    public int updateReportTrainingClassify(ReportTrainingClassify reportTrainingClassify);

    /**
     * 批量删除汇报训练分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteReportTrainingClassifyByIds(String ids);

    /**
     * 删除汇报训练分类信息
     * 
     * @param reportTrainingClassifyId 汇报训练分类ID
     * @return 结果
     */
    public int deleteReportTrainingClassifyById(Long reportTrainingClassifyId);
}
