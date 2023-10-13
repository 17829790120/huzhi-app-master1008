package com.wlwq.information.mapper;

import java.util.List;
import com.wlwq.information.domain.InformationPost;

/**
 * 资讯文章Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-04-19
 */
public interface InformationPostMapper 
{
    /**
     * 查询资讯文章
     * 
     * @param informationPostId 资讯文章ID
     * @return 资讯文章
     */
    public InformationPost selectInformationPostById(Long informationPostId);

    /**
     * 查询资讯文章列表
     * 
     * @param informationPost 资讯文章
     * @return 资讯文章集合
     */
    public List<InformationPost> selectInformationPostList(InformationPost informationPost);

    /**
     * 新增资讯文章
     * 
     * @param informationPost 资讯文章
     * @return 结果
     */
    public int insertInformationPost(InformationPost informationPost);

    /**
     * 修改资讯文章
     * 
     * @param informationPost 资讯文章
     * @return 结果
     */
    public int updateInformationPost(InformationPost informationPost);

    /**
     * 删除资讯文章
     * 
     * @param informationPostId 资讯文章ID
     * @return 结果
     */
    public int deleteInformationPostById(Long informationPostId);

    /**
     * 批量删除资讯文章
     * 
     * @param informationPostIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteInformationPostByIds(String[] informationPostIds);


    /**
     * api查询资讯文章列表
     *
     * @param informationPost 资讯文章
     * @return 资讯文章集合
     */
    public List<InformationPost> selectApiInformationPostList(InformationPost informationPost);
}
