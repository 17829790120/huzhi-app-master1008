package com.wlwq.api.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ResourceFileCategoryMapper;
import com.wlwq.api.domain.ResourceFileCategory;
import com.wlwq.api.service.IResourceFileCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 资源文件类别Service业务层处理
 *
 * @author wwb
 * @date 2023-05-23
 */
@Service
public class ResourceFileCategoryServiceImpl implements IResourceFileCategoryService {

    @Autowired
    private ResourceFileCategoryMapper resourceFileCategoryMapper;

    /**
     * 查询资源文件类别
     *
     * @param resourceFileCategoryId 资源文件类别ID
     * @return 资源文件类别
     */
    @Override
    public ResourceFileCategory selectResourceFileCategoryById(Long resourceFileCategoryId) {
        return resourceFileCategoryMapper.selectResourceFileCategoryById(resourceFileCategoryId);
    }

    /**
     * 查询资源文件类别列表
     *
     * @param resourceFileCategory 资源文件类别
     * @return 资源文件类别
     */
    @Override
    public List<ResourceFileCategory> selectResourceFileCategoryList(ResourceFileCategory resourceFileCategory) {
        return resourceFileCategoryMapper.selectResourceFileCategoryList(resourceFileCategory);
    }

    /**
     * 新增资源文件类别
     *
     * @param resourceFileCategory 资源文件类别
     * @return 结果
     */
    @Override
    public int insertResourceFileCategory(ResourceFileCategory resourceFileCategory) {
        resourceFileCategory.setCreateTime(DateUtils.getNowDate());
        return resourceFileCategoryMapper.insertResourceFileCategory(resourceFileCategory);
    }

    /**
     * 修改资源文件类别
     *
     * @param resourceFileCategory 资源文件类别
     * @return 结果
     */
    @Override
    public int updateResourceFileCategory(ResourceFileCategory resourceFileCategory) {
        resourceFileCategory.setUpdateTime(DateUtils.getNowDate());
        return resourceFileCategoryMapper.updateResourceFileCategory(resourceFileCategory);
    }

    /**
     * 删除资源文件类别对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteResourceFileCategoryByIds(String ids) {
        return resourceFileCategoryMapper.deleteResourceFileCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资源文件类别信息
     *
     * @param resourceFileCategoryId 资源文件类别ID
     * @return 结果
     */
    @Override
    public int deleteResourceFileCategoryById(Long resourceFileCategoryId) {
        return resourceFileCategoryMapper.deleteResourceFileCategoryById(resourceFileCategoryId);
    }

    /**
     * 查询资源文件类别树列表
     *
     * @return 所有资源文件类别信息
     */
    @Override
    public List<Ztree> selectResourceFileCategoryTree() {
        List<ResourceFileCategory> resourceFileCategoryList = resourceFileCategoryMapper.selectResourceFileCategoryList(new ResourceFileCategory());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (ResourceFileCategory resourceFileCategory : resourceFileCategoryList) {
            Ztree ztree = new Ztree();
            ztree.setId(resourceFileCategory.getResourceFileCategoryId());
            ztree.setpId(resourceFileCategory.getParentId());
            ztree.setName(resourceFileCategory.getCategoryTitle());
            ztree.setTitle(resourceFileCategory.getCategoryTitle());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
