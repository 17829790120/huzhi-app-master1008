package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ExamRecord;

/**
 * 考试记录列表Mapper接口
 *
 * @author wwb
 * @date 2023-04-23
 */
public interface ExamRecordMapper {
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
     * 删除考试记录列表
     *
     * @param examRecordId 考试记录列表ID
     * @return 结果
     */
    public int deleteExamRecordById(String examRecordId);

    /**
     * 批量删除考试记录列表
     *
     * @param examRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamRecordByIds(String[] examRecordIds);
}
