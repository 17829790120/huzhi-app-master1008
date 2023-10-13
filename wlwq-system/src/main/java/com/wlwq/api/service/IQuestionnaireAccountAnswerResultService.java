package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.QuestionnaireAccountAnswerResult;

/**
 * 问卷记录答案Service接口
 *
 * @author wlwq
 * @date 2023-05-11
 */
public interface IQuestionnaireAccountAnswerResultService {
    /**
     * 查询问卷记录答案
     *
     * @param questionnaireAccountAnswerResultId 问卷记录答案ID
     * @return 问卷记录答案
     */
    public QuestionnaireAccountAnswerResult selectQuestionnaireAccountAnswerResultById(String questionnaireAccountAnswerResultId);

    /**
     * 查询问卷记录答案列表
     *
     * @param questionnaireAccountAnswerResult 问卷记录答案
     * @return 问卷记录答案集合
     */
    public List<QuestionnaireAccountAnswerResult> selectQuestionnaireAccountAnswerResultList(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult);

    /**
     * 新增问卷记录答案
     *
     * @param questionnaireAccountAnswerResult 问卷记录答案
     * @return 结果
     */
    public int insertQuestionnaireAccountAnswerResult(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult);

    /**
     * 修改问卷记录答案
     *
     * @param questionnaireAccountAnswerResult 问卷记录答案
     * @return 结果
     */
    public int updateQuestionnaireAccountAnswerResult(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult);

    /**
     * 批量删除问卷记录答案
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionnaireAccountAnswerResultByIds(String ids);

    /**
     * 删除问卷记录答案信息
     *
     * @param questionnaireAccountAnswerResultId 问卷记录答案ID
     * @return 结果
     */
    public int deleteQuestionnaireAccountAnswerResultById(String questionnaireAccountAnswerResultId);
}
