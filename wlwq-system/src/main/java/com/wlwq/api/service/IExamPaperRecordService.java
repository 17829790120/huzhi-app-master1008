package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.ExamPaperRecord;

/**
 * 考试试卷记录Service接口
 *
 * @author wwb
 * @date 2023-05-26
 */
public interface IExamPaperRecordService {
    /**
     * 查询考试试卷记录
     *
     * @param examPaperRecordId 考试试卷记录ID
     * @return 考试试卷记录
     */
    public ExamPaperRecord selectExamPaperRecordById(String examPaperRecordId);

    /**
     * 查询考试试卷记录列表
     *
     * @param examPaperRecord 考试试卷记录
     * @return 考试试卷记录集合
     */
    public List<ExamPaperRecord> selectExamPaperRecordList(ExamPaperRecord examPaperRecord);

    /**
     * 新增考试试卷记录
     *
     * @param examPaperRecord 考试试卷记录
     * @return 结果
     */
    public int insertExamPaperRecord(ExamPaperRecord examPaperRecord);

    /**
     * 修改考试试卷记录
     *
     * @param examPaperRecord 考试试卷记录
     * @return 结果
     */
    public int updateExamPaperRecord(ExamPaperRecord examPaperRecord);

    /**
     * 批量删除考试试卷记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamPaperRecordByIds(String ids);

    /**
     * 删除考试试卷记录信息
     *
     * @param examPaperRecordId 考试试卷记录ID
     * @return 结果
     */
    public int deleteExamPaperRecordById(String examPaperRecordId);
}
