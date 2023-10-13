package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.QuestionnaireRecordAnswerMapper;
import com.wlwq.api.domain.QuestionnaireRecordAnswer;
import com.wlwq.api.service.IQuestionnaireRecordAnswerService;
import com.wlwq.common.core.text.Convert;

/**
 * 问卷记录答案Service业务层处理
 * 
 * @author wwb
 * @date 2023-05-09
 */
@Service
public class QuestionnaireRecordAnswerServiceImpl implements IQuestionnaireRecordAnswerService {

    @Autowired
    private QuestionnaireRecordAnswerMapper questionnaireRecordAnswerMapper;

    /**
     * 查询问卷记录答案
     * 
     * @param questionnaireRecordAnswerId 问卷记录答案ID
     * @return 问卷记录答案
     */
    @Override
    public QuestionnaireRecordAnswer selectQuestionnaireRecordAnswerById(String questionnaireRecordAnswerId) {
        return questionnaireRecordAnswerMapper.selectQuestionnaireRecordAnswerById(questionnaireRecordAnswerId);
    }

    /**
     * 查询问卷记录答案列表
     * 
     * @param questionnaireRecordAnswer 问卷记录答案
     * @return 问卷记录答案
     */
    @Override
    public List<QuestionnaireRecordAnswer> selectQuestionnaireRecordAnswerList(QuestionnaireRecordAnswer questionnaireRecordAnswer) {
        return questionnaireRecordAnswerMapper.selectQuestionnaireRecordAnswerList(questionnaireRecordAnswer);
    }

    /**
     * 新增问卷记录答案
     * 
     * @param questionnaireRecordAnswer 问卷记录答案
     * @return 结果
     */
    @Override
    public int insertQuestionnaireRecordAnswer(QuestionnaireRecordAnswer questionnaireRecordAnswer) {
        questionnaireRecordAnswer.setQuestionnaireRecordAnswerId(IdUtil.getSnowflake(1,1).nextIdStr());
        questionnaireRecordAnswer.setCreateTime(DateUtils.getNowDate());
        return questionnaireRecordAnswerMapper.insertQuestionnaireRecordAnswer(questionnaireRecordAnswer);
    }

    /**
     * 修改问卷记录答案
     * 
     * @param questionnaireRecordAnswer 问卷记录答案
     * @return 结果
     */
    @Override
    public int updateQuestionnaireRecordAnswer(QuestionnaireRecordAnswer questionnaireRecordAnswer) {
        return questionnaireRecordAnswerMapper.updateQuestionnaireRecordAnswer(questionnaireRecordAnswer);
    }

    /**
     * 删除问卷记录答案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireRecordAnswerByIds(String ids) {
        return questionnaireRecordAnswerMapper.deleteQuestionnaireRecordAnswerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除问卷记录答案信息
     * 
     * @param questionnaireRecordAnswerId 问卷记录答案ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireRecordAnswerById(String questionnaireRecordAnswerId) {
        return questionnaireRecordAnswerMapper.deleteQuestionnaireRecordAnswerById(questionnaireRecordAnswerId);
    }
}
