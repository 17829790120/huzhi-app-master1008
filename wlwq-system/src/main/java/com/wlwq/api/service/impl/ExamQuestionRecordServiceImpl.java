package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamQuestionRecordMapper;
import com.wlwq.api.domain.ExamQuestionRecord;
import com.wlwq.api.service.IExamQuestionRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 考试答题记录Service业务层处理
 *
 * @author wwb
 * @date 2023-04-24
 */
@Service
public class ExamQuestionRecordServiceImpl implements IExamQuestionRecordService {

    @Autowired
    private ExamQuestionRecordMapper examQuestionRecordMapper;

    /**
     * 查询考试答题记录
     *
     * @param examQuestionRecordId 考试答题记录ID
     * @return 考试答题记录
     */
    @Override
    public ExamQuestionRecord selectExamQuestionRecordById(String examQuestionRecordId) {
        return examQuestionRecordMapper.selectExamQuestionRecordById(examQuestionRecordId);
    }

    /**
     * 查询考试答题记录列表
     *
     * @param examQuestionRecord 考试答题记录
     * @return 考试答题记录
     */
    @Override
    public List<ExamQuestionRecord> selectExamQuestionRecordList(ExamQuestionRecord examQuestionRecord) {
        return examQuestionRecordMapper.selectExamQuestionRecordList(examQuestionRecord);
    }

    /**
     * 新增考试答题记录
     *
     * @param examQuestionRecord 考试答题记录
     * @return 结果
     */
    @Override
    public int insertExamQuestionRecord(ExamQuestionRecord examQuestionRecord) {
        examQuestionRecord.setExamQuestionRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examQuestionRecord.setCreateTime(DateUtils.getNowDate());
        return examQuestionRecordMapper.insertExamQuestionRecord(examQuestionRecord);
    }

    /**
     * 修改考试答题记录
     *
     * @param examQuestionRecord 考试答题记录
     * @return 结果
     */
    @Override
    public int updateExamQuestionRecord(ExamQuestionRecord examQuestionRecord) {
        examQuestionRecord.setUpdateTime(DateUtils.getNowDate());
        return examQuestionRecordMapper.updateExamQuestionRecord(examQuestionRecord);
    }

    /**
     * 删除考试答题记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamQuestionRecordByIds(String ids) {
        return examQuestionRecordMapper.deleteExamQuestionRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除考试答题记录信息
     *
     * @param examQuestionRecordId 考试答题记录ID
     * @return 结果
     */
    @Override
    public int deleteExamQuestionRecordById(String examQuestionRecordId) {
        return examQuestionRecordMapper.deleteExamQuestionRecordById(examQuestionRecordId);
    }
    /**
     * 获取考试得分
     *
     * @param examRecordId 考试记录ID
     * @return 结果
     */
    @Override
    public Double getScore(String examRecordId) {
        return examQuestionRecordMapper.getScore(examRecordId);
    }

}
