package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.QuestionnaireAccountAnswerResultMapper;
import com.wlwq.api.domain.QuestionnaireAccountAnswerResult;
import com.wlwq.api.service.IQuestionnaireAccountAnswerResultService;
import com.wlwq.common.core.text.Convert;

/**
 * 问卷记录答案Service业务层处理
 *
 * @author wlwq
 * @date 2023-05-11
 */
@Service
public class QuestionnaireAccountAnswerResultServiceImpl implements IQuestionnaireAccountAnswerResultService {

    @Autowired
    private QuestionnaireAccountAnswerResultMapper questionnaireAccountAnswerResultMapper;

    /**
     * 查询问卷记录答案
     *
     * @param questionnaireAccountAnswerResultId 问卷记录答案ID
     * @return 问卷记录答案
     */
    @Override
    public QuestionnaireAccountAnswerResult selectQuestionnaireAccountAnswerResultById(String questionnaireAccountAnswerResultId) {
        return questionnaireAccountAnswerResultMapper.selectQuestionnaireAccountAnswerResultById(questionnaireAccountAnswerResultId);
    }

    /**
     * 查询问卷记录答案列表
     *
     * @param questionnaireAccountAnswerResult 问卷记录答案
     * @return 问卷记录答案
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<QuestionnaireAccountAnswerResult> selectQuestionnaireAccountAnswerResultList(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult) {
        return questionnaireAccountAnswerResultMapper.selectQuestionnaireAccountAnswerResultList(questionnaireAccountAnswerResult);
    }

    /**
     * 新增问卷记录答案
     *
     * @param questionnaireAccountAnswerResult 问卷记录答案
     * @return 结果
     */
    @Override
    public int insertQuestionnaireAccountAnswerResult(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult) {
        questionnaireAccountAnswerResult.setQuestionnaireAccountAnswerResultId(IdUtil.getSnowflake(1, 1).nextIdStr());
        questionnaireAccountAnswerResult.setCreateTime(DateUtils.getNowDate());
        return questionnaireAccountAnswerResultMapper.insertQuestionnaireAccountAnswerResult(questionnaireAccountAnswerResult);
    }

    /**
     * 修改问卷记录答案
     *
     * @param questionnaireAccountAnswerResult 问卷记录答案
     * @return 结果
     */
    @Override
    public int updateQuestionnaireAccountAnswerResult(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult) {
        return questionnaireAccountAnswerResultMapper.updateQuestionnaireAccountAnswerResult(questionnaireAccountAnswerResult);
    }

    /**
     * 删除问卷记录答案对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireAccountAnswerResultByIds(String ids) {
        return questionnaireAccountAnswerResultMapper.deleteQuestionnaireAccountAnswerResultByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除问卷记录答案信息
     *
     * @param questionnaireAccountAnswerResultId 问卷记录答案ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireAccountAnswerResultById(String questionnaireAccountAnswerResultId) {
        return questionnaireAccountAnswerResultMapper.deleteQuestionnaireAccountAnswerResultById(questionnaireAccountAnswerResultId);
    }
}
