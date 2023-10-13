package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ReportTrainingReadRecordMapper;
import com.wlwq.api.domain.ReportTrainingReadRecord;
import com.wlwq.api.service.IReportTrainingReadRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 汇报训练已读记录Service业务层处理
 * 
 * @author gaoce
 * @date 2023-07-08
 */
@Service
public class ReportTrainingReadRecordServiceImpl implements IReportTrainingReadRecordService {

    @Autowired
    private ReportTrainingReadRecordMapper reportTrainingReadRecordMapper;

    /**
     * 查询汇报训练已读记录
     * 
     * @param reportTrainingReadRecordId 汇报训练已读记录ID
     * @return 汇报训练已读记录
     */
    @Override
    public ReportTrainingReadRecord selectReportTrainingReadRecordById(String reportTrainingReadRecordId) {
        return reportTrainingReadRecordMapper.selectReportTrainingReadRecordById(reportTrainingReadRecordId);
    }

    /**
     * 查询汇报训练已读记录列表
     * 
     * @param reportTrainingReadRecord 汇报训练已读记录
     * @return 汇报训练已读记录
     */
    @Override
    public List<ReportTrainingReadRecord> selectReportTrainingReadRecordList(ReportTrainingReadRecord reportTrainingReadRecord) {
        return reportTrainingReadRecordMapper.selectReportTrainingReadRecordList(reportTrainingReadRecord);
    }

    /**
     * 新增汇报训练已读记录
     * 
     * @param reportTrainingReadRecord 汇报训练已读记录
     * @return 结果
     */
    @Override
    public int insertReportTrainingReadRecord(ReportTrainingReadRecord reportTrainingReadRecord) {
        reportTrainingReadRecord.setReportTrainingReadRecordId(IdUtil.getSnowflake(1,1).nextIdStr());
        reportTrainingReadRecord.setCreateTime(DateUtils.getNowDate());
        return reportTrainingReadRecordMapper.insertReportTrainingReadRecord(reportTrainingReadRecord);
    }

    /**
     * 修改汇报训练已读记录
     * 
     * @param reportTrainingReadRecord 汇报训练已读记录
     * @return 结果
     */
    @Override
    public int updateReportTrainingReadRecord(ReportTrainingReadRecord reportTrainingReadRecord) {
        reportTrainingReadRecord.setUpdateTime(DateUtils.getNowDate());
        return reportTrainingReadRecordMapper.updateReportTrainingReadRecord(reportTrainingReadRecord);
    }

    /**
     * 删除汇报训练已读记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteReportTrainingReadRecordByIds(String ids) {
        return reportTrainingReadRecordMapper.deleteReportTrainingReadRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除汇报训练已读记录信息
     * 
     * @param reportTrainingReadRecordId 汇报训练已读记录ID
     * @return 结果
     */
    @Override
    public int deleteReportTrainingReadRecordById(String reportTrainingReadRecordId) {
        return reportTrainingReadRecordMapper.deleteReportTrainingReadRecordById(reportTrainingReadRecordId);
    }

    /**
     * 只查询一条记录
     * @param reportTrainingReadRecord
     * @return
     */
    @Override
    public ReportTrainingReadRecord selectReportTrainingReadRecord(ReportTrainingReadRecord reportTrainingReadRecord){
        return reportTrainingReadRecordMapper.selectReportTrainingReadRecord(reportTrainingReadRecord);
    }

    /**
     * 根据条件删除信息
     * @param reportTrainingReadRecord
     * @return
     */
    @Override
    public int delReportTrainingReadRecord(ReportTrainingReadRecord reportTrainingReadRecord){
        return reportTrainingReadRecordMapper.delReportTrainingReadRecord(reportTrainingReadRecord);
    }

    /**
     * 查询数量
     * @param reportTrainingReadRecord
     * @return
     */
    @Override
    public int selectReportTrainingReadRecordCount(ReportTrainingReadRecord reportTrainingReadRecord) {
        return reportTrainingReadRecordMapper.selectReportTrainingReadRecordCount(reportTrainingReadRecord);
    }
}
