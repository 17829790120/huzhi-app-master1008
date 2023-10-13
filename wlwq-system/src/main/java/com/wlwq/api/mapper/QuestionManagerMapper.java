package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.QuestionManager;

/**
 * 试题列表Mapper接口
 *
 * @author wwb
 * @date 2023-04-23
 */
public interface QuestionManagerMapper {
    /**
     * 查询试题列表
     *
     * @param questionManagerId 试题列表ID
     * @return 试题列表
     */
    public QuestionManager selectQuestionManagerById(String questionManagerId);

    /**
     * 查询试题列表列表
     *
     * @param questionManager 试题列表
     * @return 试题列表集合
     */
    public List<QuestionManager> selectQuestionManagerList(QuestionManager questionManager);

    /**
     * 新增试题列表
     *
     * @param questionManager 试题列表
     * @return 结果
     */
    public int insertQuestionManager(QuestionManager questionManager);

    /**
     * 修改试题列表
     *
     * @param questionManager 试题列表
     * @return 结果
     */
    public int updateQuestionManager(QuestionManager questionManager);

    /**
     * 删除试题列表
     *
     * @param questionManagerId 试题列表ID
     * @return 结果
     */
    public int deleteQuestionManagerById(Long questionManagerId);

    /**
     * 批量删除试题列表
     *
     * @param questionManagerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteQuestionManagerByIds(String[] questionManagerIds);
    /**
     * 查询未添加到考卷的题库试题
     * @param questionManager
     * @return
     */
    List<QuestionManager> selectNoQuestionManagerList(QuestionManager questionManager);
    /**
     * 根据课程章节id查询对应的试题数量
     * @param chapterId 章节id
     * @return
     */
    int selectQuestionManagerCountByChapterId(Long chapterId);
}
