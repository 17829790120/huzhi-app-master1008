package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.QuestionnaireDistributeRecordMapper;
import com.wlwq.api.domain.QuestionnaireDistributeRecord;
import com.wlwq.api.service.IQuestionnaireDistributeRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 问卷发放记录Service业务层处理
 * 
 * @author wwb
 * @date 2023-05-12
 */
@Service
public class QuestionnaireDistributeRecordServiceImpl implements IQuestionnaireDistributeRecordService {

    @Autowired
    private QuestionnaireDistributeRecordMapper questionnaireDistributeRecordMapper;

    /**
     * 查询问卷发放记录
     * 
     * @param questionnaireDistributeRecordId 问卷发放记录ID
     * @return 问卷发放记录
     */
    @Override
    public QuestionnaireDistributeRecord selectQuestionnaireDistributeRecordById(String questionnaireDistributeRecordId) {
        return questionnaireDistributeRecordMapper.selectQuestionnaireDistributeRecordById(questionnaireDistributeRecordId);
    }

    /**
     * 查询问卷发放记录列表
     * 
     * @param questionnaireDistributeRecord 问卷发放记录
     * @return 问卷发放记录
     */
    @Override
    public List<QuestionnaireDistributeRecord> selectQuestionnaireDistributeRecordList(QuestionnaireDistributeRecord questionnaireDistributeRecord) {
        return questionnaireDistributeRecordMapper.selectQuestionnaireDistributeRecordList(questionnaireDistributeRecord);
    }

    /**
     * 新增问卷发放记录
     * 
     * @param questionnaireDistributeRecord 问卷发放记录
     * @return 结果
     */
    @Override
    public int insertQuestionnaireDistributeRecord(QuestionnaireDistributeRecord questionnaireDistributeRecord) {
        if(StringUtil.isEmpty(questionnaireDistributeRecord.getQuestionnaireDistributeRecordId())){
            questionnaireDistributeRecord.setQuestionnaireDistributeRecordId(IdUtil.getSnowflake(1,1).nextIdStr());
        }
        questionnaireDistributeRecord.setCreateTime(DateUtils.getNowDate());
        return questionnaireDistributeRecordMapper.insertQuestionnaireDistributeRecord(questionnaireDistributeRecord);
    }

    /**
     * 修改问卷发放记录
     * 
     * @param questionnaireDistributeRecord 问卷发放记录
     * @return 结果
     */
    @Override
    public int updateQuestionnaireDistributeRecord(QuestionnaireDistributeRecord questionnaireDistributeRecord) {
        return questionnaireDistributeRecordMapper.updateQuestionnaireDistributeRecord(questionnaireDistributeRecord);
    }

    /**
     * 删除问卷发放记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireDistributeRecordByIds(String ids) {
        return questionnaireDistributeRecordMapper.deleteQuestionnaireDistributeRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除问卷发放记录信息
     * 
     * @param questionnaireDistributeRecordId 问卷发放记录ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireDistributeRecordById(String questionnaireDistributeRecordId) {
        return questionnaireDistributeRecordMapper.deleteQuestionnaireDistributeRecordById(questionnaireDistributeRecordId);
    }
}
