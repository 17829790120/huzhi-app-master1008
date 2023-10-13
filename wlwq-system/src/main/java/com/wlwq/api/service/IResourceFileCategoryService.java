package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.ResourceFileCategory;
import com.wlwq.common.core.domain.Ztree;

/**
 * 资源文件类别Service接口
 *
 * @author wwb
 * @date 2023-05-23
 */
public interface IResourceFileCategoryService {
    /**
     * 查询资源文件类别
     *
     * @param resourceFileCategoryId 资源文件类别ID
     * @return 资源文件类别
     */
    public ResourceFileCategory selectResourceFileCategoryById(Long resourceFileCategoryId);

    /**
     * 查询资源文件类别列表
     *
     * @param resourceFileCategory 资源文件类别
     * @return 资源文件类别集合
     */
    public List<ResourceFileCategory> selectResourceFileCategoryList(ResourceFileCategory resourceFileCategory);

    /**
     * 新增资源文件类别
     *
     * @param resourceFileCategory 资源文件类别
     * @return 结果
     */
    public int insertResourceFileCategory(ResourceFileCategory resourceFileCategory);

    /**
     * 修改资源文件类别
     *
     * @param resourceFileCategory 资源文件类别
     * @return 结果
     */
    public int updateResourceFileCategory(ResourceFileCategory resourceFileCategory);

    /**
     * 批量删除资源文件类别
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteResourceFileCategoryByIds(String ids);

    /**
     * 删除资源文件类别信息
     *
     * @param resourceFileCategoryId 资源文件类别ID
     * @return 结果
     */
    public int deleteResourceFileCategoryById(Long resourceFileCategoryId);

    /**
     * 查询资源文件类别树列表
     *
     * @return 所有资源文件类别信息
     */
    public List<Ztree> selectResourceFileCategoryTree();
}
