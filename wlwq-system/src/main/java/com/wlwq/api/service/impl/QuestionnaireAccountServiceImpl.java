package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.QuestionnaireAccountMapper;
import com.wlwq.api.domain.QuestionnaireAccount;
import com.wlwq.api.service.IQuestionnaireAccountService;
import com.wlwq.common.core.text.Convert;

/**
 * 问卷发放记录Service业务层处理
 * 
 * @author wwb
 * @date 2023-05-11
 */
@Service
public class QuestionnaireAccountServiceImpl implements IQuestionnaireAccountService {

    @Autowired
    private QuestionnaireAccountMapper questionnaireAccountMapper;

    /**
     * 查询问卷发放记录
     * 
     * @param questionnaireAccountId 问卷发放记录ID
     * @return 问卷发放记录
     */
    @Override
    public QuestionnaireAccount selectQuestionnaireAccountById(String questionnaireAccountId) {
        return questionnaireAccountMapper.selectQuestionnaireAccountById(questionnaireAccountId);
    }

    /**
     * 查询问卷发放记录列表
     * 
     * @param questionnaireAccount 问卷发放记录
     * @return 问卷发放记录
     */
    @Override
    public List<QuestionnaireAccount> selectQuestionnaireAccountList(QuestionnaireAccount questionnaireAccount) {
        return questionnaireAccountMapper.selectQuestionnaireAccountList(questionnaireAccount);
    }

    /**
     * 新增问卷发放记录
     * 
     * @param questionnaireAccount 问卷发放记录
     * @return 结果
     */
    @Override
    public int insertQuestionnaireAccount(QuestionnaireAccount questionnaireAccount) {
        questionnaireAccount.setQuestionnaireAccountId(IdUtil.getSnowflake(1,1).nextIdStr());
        questionnaireAccount.setCreateTime(DateUtils.getNowDate());
        return questionnaireAccountMapper.insertQuestionnaireAccount(questionnaireAccount);
    }

    /**
     * 修改问卷发放记录
     * 
     * @param questionnaireAccount 问卷发放记录
     * @return 结果
     */
    @Override
    public int updateQuestionnaireAccount(QuestionnaireAccount questionnaireAccount) {
        return questionnaireAccountMapper.updateQuestionnaireAccount(questionnaireAccount);
    }

    /**
     * 删除问卷发放记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireAccountByIds(String ids) {
        return questionnaireAccountMapper.deleteQuestionnaireAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除问卷发放记录信息
     * 
     * @param questionnaireAccountId 问卷发放记录ID
     * @return 结果
     */
    @Override
    public int deleteQuestionnaireAccountById(String questionnaireAccountId) {
        return questionnaireAccountMapper.deleteQuestionnaireAccountById(questionnaireAccountId);
    }
}
