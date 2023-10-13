package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.QuestionnaireRecordMapper;
import com.wlwq.api.domain.QuestionnaireRecord;
import com.wlwq.api.service.IQuestionnaireRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 内部调研问卷记录Service业务层处理
 * 
 * @author wwb
 * @date 2023-05-09
 */
@Service
public class QuestionnaireRecordServiceImpl implements IQuestionnaireRecordService {

    @Autowired
    private QuestionnaireRecordMapper questionnaireRecordMapper;

    /**
     * 查询内部调研问卷记录
     * 
     * @param questionnaireRecordId 内部调研问卷记录ID
     * @return 内部调研问卷记录
     */
    @Override
    public QuestionnaireRecord selectQuestionnaireRecordById(String questionnaireRecordId) {
        return questionnaireRecordMapper.selectQuestionnaireRecordById(questionnaireRecordId);
    }

    /**
     * 查询内部调研问卷记录列表
     * 
     * @param questionnaireRecord 内部调研问卷记录
     * @return 内部调研问卷记录
     */
    @Override
    public List<QuestionnaireRecord> selectQuestionnaireRecordList(QuestionnaireRecord questionnaireRecord) {
        return questionnaireRecordMapper.selectQuestionnaireRecordList(questionnaireRecord);
    }

    /**
     * 新增内部调研问卷记录
     * 
     * @param questionnaireRecord 内部调研问卷记录
     * @return 结果
     */
    @Override
    public int insertQuestionnaireRecord(QuestionnaireRecord questionnaireRecord) {
        questionnaireRecord.setQuestionnaireRecordId(IdUtil.getSnowflake(1,1).nextIdStr());
        questionnaireRecord.setCreateTime(DateUtils.getNowDate());
        return questionnaireRecordMapper.insertQuestionnaireRecord(questionnaireRecord);
    }

    /**
     * 修改内部调研问卷记录
     * 
     * @param questionnaireRecord 内部调研问卷记录
     * @return 结果
     */
    @Override
    public int updateQuestionnaireRecord(QuestionnaireRecord questionnaireRecord) {
        return questionnaireRecordMapper.updateQuestionnaireRecord(questionnaireRecord);
    }

    /**
     * 删除内部调研问卷记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireRecordByIds(String ids) {
        return questionnaireRecordMapper.deleteQuestionnaireRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内部调研问卷记录信息
     * 
     * @param questionnaireRecordId 内部调研问卷记录ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireRecordById(String questionnaireRecordId) {
        return questionnaireRecordMapper.deleteQuestionnaireRecordById(questionnaireRecordId);
    }
}
