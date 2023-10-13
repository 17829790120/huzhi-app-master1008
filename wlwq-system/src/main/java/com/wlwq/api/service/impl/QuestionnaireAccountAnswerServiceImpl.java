package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.QuestionnaireAccountAnswerMapper;
import com.wlwq.api.domain.QuestionnaireAccountAnswer;
import com.wlwq.api.service.IQuestionnaireAccountAnswerService;
import com.wlwq.common.core.text.Convert;

/**
 * 问卷记录答题Service业务层处理
 *
 * @author wwb
 * @date 2023-05-11
 */
@Service
public class QuestionnaireAccountAnswerServiceImpl implements IQuestionnaireAccountAnswerService {

    @Autowired
    private QuestionnaireAccountAnswerMapper questionnaireAccountAnswerMapper;

    /**
     * 查询问卷记录答题
     *
     * @param questionnaireAccountAnswerId 问卷记录答题ID
     * @return 问卷记录答题
     */
    @Override
    public QuestionnaireAccountAnswer selectQuestionnaireAccountAnswerById(String questionnaireAccountAnswerId) {
        return questionnaireAccountAnswerMapper.selectQuestionnaireAccountAnswerById(questionnaireAccountAnswerId);
    }

    /**
     * 查询问卷记录答题列表
     *
     * @param questionnaireAccountAnswer 问卷记录答题
     * @return 问卷记录答题
     */
    @Override
    public List<QuestionnaireAccountAnswer> selectQuestionnaireAccountAnswerList(QuestionnaireAccountAnswer questionnaireAccountAnswer) {
        return questionnaireAccountAnswerMapper.selectQuestionnaireAccountAnswerList(questionnaireAccountAnswer);
    }

    /**
     * 新增问卷记录答题
     *
     * @param questionnaireAccountAnswer 问卷记录答题
     * @return 结果
     */
    @Override
    public int insertQuestionnaireAccountAnswer(QuestionnaireAccountAnswer questionnaireAccountAnswer) {
        if(StringUtils.isEmpty(questionnaireAccountAnswer.getQuestionnaireAccountAnswerId())){
            questionnaireAccountAnswer.setQuestionnaireAccountAnswerId(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
        questionnaireAccountAnswer.setCreateTime(DateUtils.getNowDate());
        return questionnaireAccountAnswerMapper.insertQuestionnaireAccountAnswer(questionnaireAccountAnswer);
    }

    /**
     * 修改问卷记录答题
     *
     * @param questionnaireAccountAnswer 问卷记录答题
     * @return 结果
     */
    @Override
    public int updateQuestionnaireAccountAnswer(QuestionnaireAccountAnswer questionnaireAccountAnswer) {
        return questionnaireAccountAnswerMapper.updateQuestionnaireAccountAnswer(questionnaireAccountAnswer);
    }

    /**
     * 删除问卷记录答题对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireAccountAnswerByIds(String ids) {
        return questionnaireAccountAnswerMapper.deleteQuestionnaireAccountAnswerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除问卷记录答题信息
     *
     * @param questionnaireAccountAnswerId 问卷记录答题ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireAccountAnswerById(String questionnaireAccountAnswerId) {
        return questionnaireAccountAnswerMapper.deleteQuestionnaireAccountAnswerById(questionnaireAccountAnswerId);
    }
}
