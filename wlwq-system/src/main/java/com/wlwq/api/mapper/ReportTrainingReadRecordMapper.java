package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ReportTrainingReadRecord;

/**
 * 汇报训练已读记录Mapper接口
 *
 * @author gaoce
 * @date 2023-07-08
 */
public interface ReportTrainingReadRecordMapper {
    /**
     * 查询汇报训练已读记录
     *
     * @param reportTrainingReadRecordId 汇报训练已读记录ID
     * @return 汇报训练已读记录
     */
    public ReportTrainingReadRecord selectReportTrainingReadRecordById(String reportTrainingReadRecordId);

    /**
     * 查询汇报训练已读记录列表
     *
     * @param reportTrainingReadRecord 汇报训练已读记录
     * @return 汇报训练已读记录集合
     */
    public List<ReportTrainingReadRecord> selectReportTrainingReadRecordList(ReportTrainingReadRecord reportTrainingReadRecord);

    /**
     * 新增汇报训练已读记录
     *
     * @param reportTrainingReadRecord 汇报训练已读记录
     * @return 结果
     */
    public int insertReportTrainingReadRecord(ReportTrainingReadRecord reportTrainingReadRecord);

    /**
     * 修改汇报训练已读记录
     *
     * @param reportTrainingReadRecord 汇报训练已读记录
     * @return 结果
     */
    public int updateReportTrainingReadRecord(ReportTrainingReadRecord reportTrainingReadRecord);

    /**
     * 删除汇报训练已读记录
     *
     * @param reportTrainingReadRecordId 汇报训练已读记录ID
     * @return 结果
     */
    public int deleteReportTrainingReadRecordById(String reportTrainingReadRecordId);

    /**
     * 批量删除汇报训练已读记录
     *
     * @param reportTrainingReadRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteReportTrainingReadRecordByIds(String[] reportTrainingReadRecordIds);

    /**
     * 只查询一条记录
     * @param reportTrainingReadRecord
     * @return
     */
    public ReportTrainingReadRecord selectReportTrainingReadRecord(ReportTrainingReadRecord reportTrainingReadRecord);

    /**
     * 根据条件删除信息
     * @param reportTrainingReadRecord
     * @return
     */
    public int delReportTrainingReadRecord(ReportTrainingReadRecord reportTrainingReadRecord);

    /**
     * 查询数量
     * @param reportTrainingReadRecord
     * @return
     */
    public int selectReportTrainingReadRecordCount(ReportTrainingReadRecord reportTrainingReadRecord);
}
