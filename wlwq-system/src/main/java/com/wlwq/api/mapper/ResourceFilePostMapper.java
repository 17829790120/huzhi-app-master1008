package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ResourceFilePost;

/**
 * 资源文件Mapper接口
 *
 * @author wwb
 * @date 2023-05-24
 */
public interface ResourceFilePostMapper {
    /**
     * 查询资源文件
     *
     * @param resourceFilePostId 资源文件ID
     * @return 资源文件
     */
    public ResourceFilePost selectResourceFilePostById(String resourceFilePostId);

    /**
     * 查询资源文件列表
     *
     * @param resourceFilePost 资源文件
     * @return 资源文件集合
     */
    public List<ResourceFilePost> selectResourceFilePostList(ResourceFilePost resourceFilePost);

    /**
     * 新增资源文件
     *
     * @param resourceFilePost 资源文件
     * @return 结果
     */
    public int insertResourceFilePost(ResourceFilePost resourceFilePost);

    /**
     * 修改资源文件
     *
     * @param resourceFilePost 资源文件
     * @return 结果
     */
    public int updateResourceFilePost(ResourceFilePost resourceFilePost);

    /**
     * 删除资源文件
     *
     * @param resourceFilePostId 资源文件ID
     * @return 结果
     */
    public int deleteResourceFilePostById(String resourceFilePostId);

    /**
     * 批量删除资源文件
     *
     * @param resourceFilePostIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteResourceFilePostByIds(String[] resourceFilePostIds);
}
