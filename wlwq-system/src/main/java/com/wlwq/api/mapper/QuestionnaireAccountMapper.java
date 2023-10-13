package com.wlwq.api.mapper;

import java.util.List;
import com.wlwq.api.domain.QuestionnaireAccount;

/**
 * 问卷发放记录Mapper接口
 * 
 * @author wwb
 * @date 2023-05-11
 */
public interface QuestionnaireAccountMapper {
    /**
     * 查询问卷发放记录
     * 
     * @param questionnaireAccountId 问卷发放记录ID
     * @return 问卷发放记录
     */
    public QuestionnaireAccount selectQuestionnaireAccountById(String questionnaireAccountId);

    /**
     * 查询问卷发放记录列表
     * 
     * @param questionnaireAccount 问卷发放记录
     * @return 问卷发放记录集合
     */
    public List<QuestionnaireAccount> selectQuestionnaireAccountList(QuestionnaireAccount questionnaireAccount);

    /**
     * 新增问卷发放记录
     * 
     * @param questionnaireAccount 问卷发放记录
     * @return 结果
     */
    public int insertQuestionnaireAccount(QuestionnaireAccount questionnaireAccount);

    /**
     * 修改问卷发放记录
     * 
     * @param questionnaireAccount 问卷发放记录
     * @return 结果
     */
    public int updateQuestionnaireAccount(QuestionnaireAccount questionnaireAccount);

    /**
     * 删除问卷发放记录
     * 
     * @param questionnaireAccountId 问卷发放记录ID
     * @return 结果
     */
    public int deleteQuestionnaireAccountById(String questionnaireAccountId);

    /**
     * 批量删除问卷发放记录
     * 
     * @param questionnaireAccountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionnaireAccountByIds(String[] questionnaireAccountIds);
}
