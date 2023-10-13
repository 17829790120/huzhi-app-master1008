package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.QuestionnaireDistributeRecord;

/**
 * 问卷发放记录Service接口
 * 
 * @author wwb
 * @date 2023-05-12
 */
public interface IQuestionnaireDistributeRecordService {
    /**
     * 查询问卷发放记录
     * 
     * @param questionnaireDistributeRecordId 问卷发放记录ID
     * @return 问卷发放记录
     */
    public QuestionnaireDistributeRecord selectQuestionnaireDistributeRecordById(String questionnaireDistributeRecordId);

    /**
     * 查询问卷发放记录列表
     * 
     * @param questionnaireDistributeRecord 问卷发放记录
     * @return 问卷发放记录集合
     */
    public List<QuestionnaireDistributeRecord> selectQuestionnaireDistributeRecordList(QuestionnaireDistributeRecord questionnaireDistributeRecord);

    /**
     * 新增问卷发放记录
     * 
     * @param questionnaireDistributeRecord 问卷发放记录
     * @return 结果
     */
    public int insertQuestionnaireDistributeRecord(QuestionnaireDistributeRecord questionnaireDistributeRecord);

    /**
     * 修改问卷发放记录
     * 
     * @param questionnaireDistributeRecord 问卷发放记录
     * @return 结果
     */
    public int updateQuestionnaireDistributeRecord(QuestionnaireDistributeRecord questionnaireDistributeRecord);

    /**
     * 批量删除问卷发放记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionnaireDistributeRecordByIds(String ids);

    /**
     * 删除问卷发放记录信息
     * 
     * @param questionnaireDistributeRecordId 问卷发放记录ID
     * @return 结果
     */
    public int deleteQuestionnaireDistributeRecordById(String questionnaireDistributeRecordId);
}
