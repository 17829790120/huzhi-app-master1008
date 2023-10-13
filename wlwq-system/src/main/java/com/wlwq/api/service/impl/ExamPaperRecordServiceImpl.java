package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamPaperRecordMapper;
import com.wlwq.api.domain.ExamPaperRecord;
import com.wlwq.api.service.IExamPaperRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 考试试卷记录Service业务层处理
 *
 * @author wwb
 * @date 2023-05-26
 */
@Service
public class ExamPaperRecordServiceImpl implements IExamPaperRecordService {

    @Autowired
    private ExamPaperRecordMapper examPaperRecordMapper;

    /**
     * 查询考试试卷记录
     *
     * @param examPaperRecordId 考试试卷记录ID
     * @return 考试试卷记录
     */
    @Override
    public ExamPaperRecord selectExamPaperRecordById(String examPaperRecordId) {
        return examPaperRecordMapper.selectExamPaperRecordById(examPaperRecordId);
    }

    /**
     * 查询考试试卷记录列表
     *
     * @param examPaperRecord 考试试卷记录
     * @return 考试试卷记录
     */
    @Override
    public List<ExamPaperRecord> selectExamPaperRecordList(ExamPaperRecord examPaperRecord) {
        return examPaperRecordMapper.selectExamPaperRecordList(examPaperRecord);
    }

    /**
     * 新增考试试卷记录
     *
     * @param examPaperRecord 考试试卷记录
     * @return 结果
     */
    @Override
    public int insertExamPaperRecord(ExamPaperRecord examPaperRecord) {
        examPaperRecord.setExamPaperRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examPaperRecord.setCreateTime(DateUtils.getNowDate());
        return examPaperRecordMapper.insertExamPaperRecord(examPaperRecord);
    }

    /**
     * 修改考试试卷记录
     *
     * @param examPaperRecord 考试试卷记录
     * @return 结果
     */
    @Override
    public int updateExamPaperRecord(ExamPaperRecord examPaperRecord) {
        examPaperRecord.setUpdateTime(DateUtils.getNowDate());
        return examPaperRecordMapper.updateExamPaperRecord(examPaperRecord);
    }

    /**
     * 删除考试试卷记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamPaperRecordByIds(String ids) {
        return examPaperRecordMapper.deleteExamPaperRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除考试试卷记录信息
     *
     * @param examPaperRecordId 考试试卷记录ID
     * @return 结果
     */
    @Override
    public int deleteExamPaperRecordById(String examPaperRecordId) {
        return examPaperRecordMapper.deleteExamPaperRecordById(examPaperRecordId);
    }
}
