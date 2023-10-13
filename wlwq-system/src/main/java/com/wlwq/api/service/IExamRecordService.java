package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.ExamRecord;
import com.wlwq.common.core.domain.AjaxResult;

/**
 * 考试记录列表Service接口
 *
 * @author wwb
 * @date 2023-04-23
 */
public interface IExamRecordService {
    /**
     * 查询考试记录列表
     *
     * @param examRecordId 考试记录列表ID
     * @return 考试记录列表
     */
    public ExamRecord selectExamRecordById(String examRecordId);

    /**
     * 查询考试记录列表列表
     *
     * @param examRecord 考试记录列表
     * @return 考试记录列表集合
     */
    public List<ExamRecord> selectExamRecordList(ExamRecord examRecord);

    /**
     * 新增考试记录列表
     *
     * @param examRecord 考试记录列表
     * @return 结果
     */
    public int insertExamRecord(ExamRecord examRecord);

    /**
     * 修改考试记录列表
     *
     * @param examRecord 考试记录列表
     * @return 结果
     */
    public int updateExamRecord(ExamRecord examRecord);

    /**
     * 批量删除考试记录列表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamRecordByIds(String ids);

    /**
     * 删除考试记录列表信息
     *
     * @param examRecordId 考试记录列表ID
     * @return 结果
     */
    public int deleteExamRecordById(String examRecordId);

    /**
     * 考试打分
     *
     * @param examRecord 考试答题记录
     * @param score 得分
     * @return 结果
     */
    public AjaxResult examScoring(ExamRecord examRecord, Double score, ApiAccount account);
}
