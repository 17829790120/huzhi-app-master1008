package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.StudyInformationPost;

/**
 * 资讯文章Service接口
 *
 * @author web
 * @date 2023-05-16
 */
public interface IStudyInformationPostService {
    /**
     * 查询资讯文章
     *
     * @param studyInformationPostId 资讯文章ID
     * @return 资讯文章
     */
    public StudyInformationPost selectStudyInformationPostById(Long studyInformationPostId);

    /**
     * 查询资讯文章列表
     *
     * @param studyInformationPost 资讯文章
     * @return 资讯文章集合
     */
    public List<StudyInformationPost> selectStudyInformationPostList(StudyInformationPost studyInformationPost);

    /**
     * 新增资讯文章
     *
     * @param studyInformationPost 资讯文章
     * @return 结果
     */
    public int insertStudyInformationPost(StudyInformationPost studyInformationPost);

    /**
     * 修改资讯文章
     *
     * @param studyInformationPost 资讯文章
     * @return 结果
     */
    public int updateStudyInformationPost(StudyInformationPost studyInformationPost);

    /**
     * 批量删除资讯文章
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStudyInformationPostByIds(String ids);

    /**
     * 删除资讯文章信息
     *
     * @param studyInformationPostId 资讯文章ID
     * @return 结果
     */
    public int deleteStudyInformationPostById(Long studyInformationPostId);
}
