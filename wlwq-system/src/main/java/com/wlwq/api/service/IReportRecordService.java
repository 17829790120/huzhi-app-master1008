package com.wlwq.api.service;

import com.wlwq.api.domain.ReportRecord;

import java.util.List;

/**
 * 举报记录Service接口
 *
 * @author wwb
 * @date 2023-03-17
 */
public interface IReportRecordService {
    /**
     * 查询举报记录
     *
     * @param reportRecordId 举报记录ID
     * @return 举报记录
     */
    public ReportRecord selectReportRecordById(String reportRecordId);

    /**
     * 查询举报记录列表
     *
     * @param reportRecord 举报记录
     * @return 举报记录集合
     */
    public List<ReportRecord> selectReportRecordList(ReportRecord reportRecord);

    /**
     * 新增举报记录
     *
     * @param reportRecord 举报记录
     * @return 结果
     */
    public int insertReportRecord(ReportRecord reportRecord);

    /**
     * 修改举报记录
     *
     * @param reportRecord 举报记录
     * @return 结果
     */
    public int updateReportRecord(ReportRecord reportRecord);

    /**
     * 批量删除举报记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteReportRecordByIds(String ids);

    /**
     * 删除举报记录信息
     *
     * @param reportRecordId 举报记录ID
     * @return 结果
     */
    public int deleteReportRecordById(String reportRecordId);
}
