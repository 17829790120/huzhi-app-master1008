package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.QuestionnaireMapper;
import com.wlwq.api.domain.Questionnaire;
import com.wlwq.api.service.IQuestionnaireService;
import com.wlwq.common.core.text.Convert;

/**
 * 内部调研问卷Service业务层处理
 *
 * @author web
 * @date 2023-05-09
 */
@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    /**
     * 查询内部调研问卷
     *
     * @param questionnaireId 内部调研问卷ID
     * @return 内部调研问卷
     */
    @Override
    public Questionnaire selectQuestionnaireById(String questionnaireId) {
        return questionnaireMapper.selectQuestionnaireById(questionnaireId);
    }

    /**
     * 查询内部调研问卷列表
     *
     * @param questionnaire 内部调研问卷
     * @return 内部调研问卷
     */
    @Override
    public List<Questionnaire> selectQuestionnaireList(Questionnaire questionnaire) {
        return questionnaireMapper.selectQuestionnaireList(questionnaire);
    }

    /**
     * 新增内部调研问卷
     *
     * @param questionnaire 内部调研问卷
     * @return 结果
     */
    @Override
    public int insertQuestionnaire(Questionnaire questionnaire) {
        questionnaire.setQuestionnaireId(IdUtil.getSnowflake(1, 1).nextIdStr());
        questionnaire.setCreateTime(DateUtils.getNowDate());
        return questionnaireMapper.insertQuestionnaire(questionnaire);
    }

    /**
     * 修改内部调研问卷
     *
     * @param questionnaire 内部调研问卷
     * @return 结果
     */
    @Override
    public int updateQuestionnaire(Questionnaire questionnaire) {
        return questionnaireMapper.updateQuestionnaire(questionnaire);
    }

    /**
     * 删除内部调研问卷对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireByIds(String ids) {
        return questionnaireMapper.deleteQuestionnaireByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内部调研问卷信息
     *
     * @param questionnaireId 内部调研问卷ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireById(String questionnaireId) {
        return questionnaireMapper.deleteQuestionnaireById(questionnaireId);
    }
}
