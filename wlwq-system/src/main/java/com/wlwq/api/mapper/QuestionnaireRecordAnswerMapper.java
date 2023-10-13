package com.wlwq.api.mapper;

import java.util.List;
import com.wlwq.api.domain.QuestionnaireRecordAnswer;

/**
 * 问卷记录答案Mapper接口
 * 
 * @author wwb
 * @date 2023-05-09
 */
public interface QuestionnaireRecordAnswerMapper {
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
     * 删除问卷记录答案
     * 
     * @param questionnaireRecordAnswerId 问卷记录答案ID
     * @return 结果
     */
    public int deleteQuestionnaireRecordAnswerById(String questionnaireRecordAnswerId);

    /**
     * 批量删除问卷记录答案
     * 
     * @param questionnaireRecordAnswerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionnaireRecordAnswerByIds(String[] questionnaireRecordAnswerIds);
}
