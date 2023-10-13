package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.QuestionnaireRecordAnswer;

/**
 * 问卷记录答案Service接口
 * 
 * @author wwb
 * @date 2023-05-09
 */
public interface IQuestionnaireRecordAnswerService {
    /**
     * 查询问卷记录答案
     * 
     * @param questionnaireRecordAnswerId 问卷记录答案ID
     * @return 问卷记录答案
     */
    public QuestionnaireRecordAnswer selectQuestionnaireRecordAnswerById(String questionnaireRecordAnswerId);

    /**
     * 查询问卷记录答案列表
     * 
     * @param questionnaireRecordAnswer 问卷记录答案
     * @return 问卷记录答案集合
     */
    public List<QuestionnaireRecordAnswer> selectQuestionnaireRecordAnswerList(QuestionnaireRecordAnswer questionnaireRecordAnswer);

    /**
     * 新增问卷记录答案
     * 
     * @param questionnaireRecordAnswer 问卷记录答案
     * @return 结果
     */
    public int insertQuestionnaireRecordAnswer(QuestionnaireRecordAnswer questionnaireRecordAnswer);

    /**
     * 修改问卷记录答案
     * 
     * @param questionnaireRecordAnswer 问卷记录答案
     * @return 结果
     */
    public int updateQuestionnaireRecordAnswer(QuestionnaireRecordAnswer questionnaireRecordAnswer);

    /**
     * 批量删除问卷记录答案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionnaireRecordAnswerByIds(String ids);

    /**
     * 删除问卷记录答案信息
     * 
     * @param questionnaireRecordAnswerId 问卷记录答案ID
     * @return 结果
     */
    public int deleteQuestionnaireRecordAnswerById(String questionnaireRecordAnswerId);
}
