package com.wlwq.api.service.impl;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.ReportRecord;
import com.wlwq.api.mapper.ReportRecordMapper;
import com.wlwq.api.service.IReportRecordService;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 举报记录Service业务层处理
 *
 * @author wwb
 * @date 2023-03-17
 */
@Service
public class ReportRecordServiceImpl implements IReportRecordService {

    @Autowired
    private ReportRecordMapper reportRecordMapper;

    /**
     * 查询举报记录
     *
     * @param reportRecordId 举报记录ID
     * @return 举报记录
     */
    @Override
    public ReportRecord selectReportRecordById(String reportRecordId) {
        return reportRecordMapper.selectReportRecordById(reportRecordId);
    }

    /**
     * 查询举报记录列表
     *
     * @param reportRecord 举报记录
     * @return 举报记录
     */
    @Override
    public List<ReportRecord> selectReportRecordList(ReportRecord reportRecord) {
        return reportRecordMapper.selectReportRecordList(reportRecord);
    }

    /**
     * 新增举报记录
     *
     * @param reportRecord 举报记录
     * @return 结果
     */
    @Override
    public int insertReportRecord(ReportRecord reportRecord) {
        reportRecord.setReportRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        reportRecord.setCreateTime(DateUtils.getNowDate());
        return reportRecordMapper.insertReportRecord(reportRecord);
    }

    /**
     * 修改举报记录
     *
     * @param reportRecord 举报记录
     * @return 结果
     */
    @Override
    public int updateReportRecord(ReportRecord reportRecord) {
        reportRecord.setUpdateTime(DateUtils.getNowDate());
        return reportRecordMapper.updateReportRecord(reportRecord);
    }

    /**
     * 删除举报记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteReportRecordByIds(String ids) {
        return reportRecordMapper.deleteReportRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除举报记录信息
     *
     * @param reportRecordId 举报记录ID
     * @return 结果
     */
    @Override
    public int deleteReportRecordById(String reportRecordId) {
        return reportRecordMapper.deleteReportRecordById(reportRecordId);
    }
}
