package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.QuestionnaireRecord;

/**
 * 内部调研问卷记录Service接口
 * 
 * @author wwb
 * @date 2023-05-09
 */
public interface IQuestionnaireRecordService {
    /**
     * 查询内部调研问卷记录
     * 
     * @param questionnaireRecordId 内部调研问卷记录ID
     * @return 内部调研问卷记录
     */
    public QuestionnaireRecord selectQuestionnaireRecordById(String questionnaireRecordId);

    /**
     * 查询内部调研问卷记录列表
     * 
     * @param questionnaireRecord 内部调研问卷记录
     * @return 内部调研问卷记录集合
     */
    public List<QuestionnaireRecord> selectQuestionnaireRecordList(QuestionnaireRecord questionnaireRecord);

    /**
     * 新增内部调研问卷记录
     * 
     * @param questionnaireRecord 内部调研问卷记录
     * @return 结果
     */
    public int insertQuestionnaireRecord(QuestionnaireRecord questionnaireRecord);

    /**
     * 修改内部调研问卷记录
     * 
     * @param questionnaireRecord 内部调研问卷记录
     * @return 结果
     */
    public int updateQuestionnaireRecord(QuestionnaireRecord questionnaireRecord);

    /**
     * 批量删除内部调研问卷记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionnaireRecordByIds(String ids);

    /**
     * 删除内部调研问卷记录信息
     * 
     * @param questionnaireRecordId 内部调研问卷记录ID
     * @return 结果
     */
    public int deleteQuestionnaireRecordById(String questionnaireRecordId);
}
