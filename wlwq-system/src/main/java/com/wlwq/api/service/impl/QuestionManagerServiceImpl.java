package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.QuestionManagerMapper;
import com.wlwq.api.domain.QuestionManager;
import com.wlwq.api.service.IQuestionManagerService;
import com.wlwq.common.core.text.Convert;

/**
 * 试题列表Service业务层处理
 *
 * @author wwb
 * @date 2023-04-23
 */
@Service
public class QuestionManagerServiceImpl implements IQuestionManagerService {

    @Autowired
    private QuestionManagerMapper questionManagerMapper;

    /**
     * 查询试题列表
     *
     * @param questionManagerId 试题列表ID
     * @return 试题列表
     */
    @Override
    public QuestionManager selectQuestionManagerById(String questionManagerId) {
        return questionManagerMapper.selectQuestionManagerById(questionManagerId);
    }

    /**
     * 查询试题列表列表
     *
     * @param questionManager 试题列表
     * @return 试题列表
     */
    @Override
    public List<QuestionManager> selectQuestionManagerList(QuestionManager questionManager) {
        return questionManagerMapper.selectQuestionManagerList(questionManager);
    }

    /**
     * 新增试题列表
     *
     * @param questionManager 试题列表
     * @return 结果
     */
    @Override
    public int insertQuestionManager(QuestionManager questionManager) {
        questionManager.setQuestionManagerId(IdUtil.getSnowflake(1,1).nextIdStr());
        questionManager.setCreateTime(DateUtils.getNowDate());
        return questionManagerMapper.insertQuestionManager(questionManager);
    }

    /**
     * 修改试题列表
     *
     * @param questionManager 试题列表
     * @return 结果
     */
    @Override
    public int updateQuestionManager(QuestionManager questionManager) {
        questionManager.setUpdateTime(DateUtils.getNowDate());
        return questionManagerMapper.updateQuestionManager(questionManager);
    }

    /**
     * 删除试题列表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQuestionManagerByIds(String ids) {
        return questionManagerMapper.deleteQuestionManagerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除试题列表信息
     *
     * @param questionManagerId 试题列表ID
     * @return 结果
     */
    @Override
    public int deleteQuestionManagerById(Long questionManagerId) {
        return questionManagerMapper.deleteQuestionManagerById(questionManagerId);
    }
    /**
     * 查询未添加到考卷的题库试题
     * @param questionManager
     * @return
     */
    @Override
    public List<QuestionManager> selectNoQuestionManagerList(QuestionManager questionManager) {
        return questionManagerMapper.selectNoQuestionManagerList(questionManager);
    }
    /**
     * 根据课程章节id查询对应的试题数量
     * @param chapterId 章节id
     * @return
     */
    @Override
    public int selectQuestionManagerCountByChapterId(Long chapterId) {
        return questionManagerMapper.selectQuestionManagerCountByChapterId(chapterId);
    }
}
