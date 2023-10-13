package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.NewsCenterPost;

/**
 * 新闻中心资讯Service接口
 *
 * @author wwb
 * @date 2023-05-06
 */
public interface INewsCenterPostService {
    /**
     * 查询新闻中心资讯
     *
     * @param newsCenterPostId 新闻中心资讯ID
     * @return 新闻中心资讯
     */
    public NewsCenterPost selectNewsCenterPostById(Long newsCenterPostId);

    /**
     * 查询新闻中心资讯列表
     *
     * @param newsCenterPost 新闻中心资讯
     * @return 新闻中心资讯集合
     */
    public List<NewsCenterPost> selectNewsCenterPostList(NewsCenterPost newsCenterPost);

    /**
     * 新增新闻中心资讯
     *
     * @param newsCenterPost 新闻中心资讯
     * @return 结果
     */
    public int insertNewsCenterPost(NewsCenterPost newsCenterPost);

    /**
     * 修改新闻中心资讯
     *
     * @param newsCenterPost 新闻中心资讯
     * @return 结果
     */
    public int updateNewsCenterPost(NewsCenterPost newsCenterPost);

    /**
     * 批量删除新闻中心资讯
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNewsCenterPostByIds(String ids);

    /**
     * 删除新闻中心资讯信息
     *
     * @param newsCenterPostId 新闻中心资讯ID
     * @return 结果
     */
    public int deleteNewsCenterPostById(Long newsCenterPostId);
}
