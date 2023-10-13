package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.Questionnaire;

/**
 * 内部调研问卷Mapper接口
 *
 * @author web
 * @date 2023-05-09
 */
public interface QuestionnaireMapper {
    /**
     * 查询内部调研问卷
     *
     * @param questionnaireId 内部调研问卷ID
     * @return 内部调研问卷
     */
    public Questionnaire selectQuestionnaireById(String questionnaireId);

    /**
     * 查询内部调研问卷列表
     *
     * @param questionnaire 内部调研问卷
     * @return 内部调研问卷集合
     */
    public List<Questionnaire> selectQuestionnaireList(Questionnaire questionnaire);

    /**
     * 新增内部调研问卷
     *
     * @param questionnaire 内部调研问卷
     * @return 结果
     */
    public int insertQuestionnaire(Questionnaire questionnaire);

    /**
     * 修改内部调研问卷
     *
     * @param questionnaire 内部调研问卷
     * @return 结果
     */
    public int updateQuestionnaire(Questionnaire questionnaire);

    /**
     * 删除内部调研问卷
     *
     * @param questionnaireId 内部调研问卷ID
     * @return 结果
     */
    public int deleteQuestionnaireById(String questionnaireId);

    /**
     * 批量删除内部调研问卷
     *
     * @param questionnaireIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionnaireByIds(String[] questionnaireIds);
}
