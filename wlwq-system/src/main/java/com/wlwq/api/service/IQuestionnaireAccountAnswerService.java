package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.QuestionnaireAccountAnswer;

/**
 * 问卷记录答题Service接口
 *
 * @author wwb
 * @date 2023-05-11
 */
public interface IQuestionnaireAccountAnswerService {
    /**
     * 查询问卷记录答题
     *
     * @param questionnaireAccountAnswerId 问卷记录答题ID
     * @return 问卷记录答题
     */
    public QuestionnaireAccountAnswer selectQuestionnaireAccountAnswerById(String questionnaireAccountAnswerId);

    /**
     * 查询问卷记录答题列表
     *
     * @param questionnaireAccountAnswer 问卷记录答题
     * @return 问卷记录答题集合
     */
    public List<QuestionnaireAccountAnswer> selectQuestionnaireAccountAnswerList(QuestionnaireAccountAnswer questionnaireAccountAnswer);

    /**
     * 新增问卷记录答题
     *
     * @param questionnaireAccountAnswer 问卷记录答题
     * @return 结果
     */
    public int insertQuestionnaireAccountAnswer(QuestionnaireAccountAnswer questionnaireAccountAnswer);

    /**
     * 修改问卷记录答题
     *
     * @param questionnaireAccountAnswer 问卷记录答题
     * @return 结果
     */
    public int updateQuestionnaireAccountAnswer(QuestionnaireAccountAnswer questionnaireAccountAnswer);

    /**
     * 批量删除问卷记录答题
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionnaireAccountAnswerByIds(String ids);

    /**
     * 删除问卷记录答题信息
     *
     * @param questionnaireAccountAnswerId 问卷记录答题ID
     * @return 结果
     */
    public int deleteQuestionnaireAccountAnswerById(String questionnaireAccountAnswerId);
}
