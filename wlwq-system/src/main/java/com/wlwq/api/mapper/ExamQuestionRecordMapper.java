package com.wlwq.api.mapper;

import java.util.List;
import com.wlwq.api.domain.ExamQuestionRecord;

/**
 * 考试答题记录Mapper接口
 * 
 * @author wwb
 * @date 2023-04-24
 */
public interface ExamQuestionRecordMapper {
    /**
     * 查询考试答题记录
     * 
     * @param examQuestionRecordId 考试答题记录ID
     * @return 考试答题记录
     */
    public ExamQuestionRecord selectExamQuestionRecordById(String examQuestionRecordId);

    /**
     * 查询考试答题记录列表
     * 
     * @param examQuestionRecord 考试答题记录
     * @return 考试答题记录集合
     */
    public List<ExamQuestionRecord> selectExamQuestionRecordList(ExamQuestionRecord examQuestionRecord);

    /**
     * 新增考试答题记录
     * 
     * @param examQuestionRecord 考试答题记录
     * @return 结果
     */
    public int insertExamQuestionRecord(ExamQuestionRecord examQuestionRecord);

    /**
     * 修改考试答题记录
     * 
     * @param examQuestionRecord 考试答题记录
     * @return 结果
     */
    public int updateExamQuestionRecord(ExamQuestionRecord examQuestionRecord);

    /**
     * 删除考试答题记录
     * 
     * @param examQuestionRecordId 考试答题记录ID
     * @return 结果
     */
    public int deleteExamQuestionRecordById(String examQuestionRecordId);

    /**
     * 批量删除考试答题记录
     * 
     * @param examQuestionRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamQuestionRecordByIds(String[] examQuestionRecordIds);
    /**
     * 获取考试得分
     *
     * @param examRecordId 考试记录ID
     * @return 结果
     */
    Double getScore(String examRecordId);
}
