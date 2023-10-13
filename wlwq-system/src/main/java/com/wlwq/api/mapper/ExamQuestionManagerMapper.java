package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ExamQuestionManager;

/**
 * 考试记录试题Mapper接口
 *
 * @author wwb
 * @date 2023-05-26
 */
public interface ExamQuestionManagerMapper {
    /**
     * 查询考试记录试题
     *
     * @param examQuestionManagerId 考试记录试题ID
     * @return 考试记录试题
     */
    public ExamQuestionManager selectExamQuestionManagerById(String examQuestionManagerId);

    /**
     * 查询考试记录试题列表
     *
     * @param examQuestionManager 考试记录试题
     * @return 考试记录试题集合
     */
    public List<ExamQuestionManager> selectExamQuestionManagerList(ExamQuestionManager examQuestionManager);

    /**
     * 新增考试记录试题
     *
     * @param examQuestionManager 考试记录试题
     * @return 结果
     */
    public int insertExamQuestionManager(ExamQuestionManager examQuestionManager);

    /**
     * 修改考试记录试题
     *
     * @param examQuestionManager 考试记录试题
     * @return 结果
     */
    public int updateExamQuestionManager(ExamQuestionManager examQuestionManager);

    /**
     * 删除考试记录试题
     *
     * @param examQuestionManagerId 考试记录试题ID
     * @return 结果
     */
    public int deleteExamQuestionManagerById(String examQuestionManagerId);

    /**
     * 批量删除考试记录试题
     *
     * @param examQuestionManagerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamQuestionManagerByIds(String[] examQuestionManagerIds);
}
