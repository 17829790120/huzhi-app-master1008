package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.NewsCenterCategory;
import com.wlwq.api.domain.ResourceFileCategory;
import com.wlwq.api.mapper.ResourceFileCategoryMapper;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ResourceFilePostMapper;
import com.wlwq.api.domain.ResourceFilePost;
import com.wlwq.api.service.IResourceFilePostService;
import com.wlwq.common.core.text.Convert;

/**
 * 资源文件Service业务层处理
 *
 * @author wwb
 * @date 2023-05-24
 */
@Service
public class ResourceFilePostServiceImpl implements IResourceFilePostService {

    @Autowired
    private ResourceFilePostMapper resourceFilePostMapper;

    @Autowired
    private ResourceFileCategoryMapper resourceFileCategoryMapper;

    /**
     * 查询资源文件
     *
     * @param resourceFilePostId 资源文件ID
     * @return 资源文件
     */
    @Override
    public ResourceFilePost selectResourceFilePostById(String resourceFilePostId) {
        return resourceFilePostMapper.selectResourceFilePostById(resourceFilePostId);
    }

    /**
     * 查询资源文件列表
     *
     * @param resourceFilePost 资源文件
     * @return 资源文件
     */
    @Override
    public List<ResourceFilePost> selectResourceFilePostList(ResourceFilePost resourceFilePost) {
        return resourceFilePostMapper.selectResourceFilePostList(resourceFilePost);
    }

    /**
     * 新增资源文件
     *
     * @param resourceFilePost 资源文件
     * @return 结果
     */
    @Override
    public int insertResourceFilePost(ResourceFilePost resourceFilePost) {
        resourceFilePost.setResourceFilePostId(IdUtil.getSnowflake(1, 1).nextIdStr());
        resourceFilePost.setCreateTime(DateUtils.getNowDate());
        return resourceFilePostMapper.insertResourceFilePost(resourceFilePost);
    }

    /**
     * 修改资源文件
     *
     * @param resourceFilePost 资源文件
     * @return 结果
     */
    @Override
    public int updateResourceFilePost(ResourceFilePost resourceFilePost) {
        resourceFilePost.setUpdateTime(DateUtils.getNowDate());
        return resourceFilePostMapper.updateResourceFilePost(resourceFilePost);
    }

    /**
     * 删除资源文件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteResourceFilePostByIds(String ids) {
        return resourceFilePostMapper.deleteResourceFilePostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资源文件信息
     *
     * @param resourceFilePostId 资源文件ID
     * @return 结果
     */
    @Override
    public int deleteResourceFilePostById(String resourceFilePostId) {
        return resourceFilePostMapper.deleteResourceFilePostById(resourceFilePostId);
    }

    @Override
    public StringBuffer selectAncestorsById(Long informationCategoryId, StringBuffer buf) {
        ResourceFileCategory resourceFileCategory = resourceFileCategoryMapper.selectResourceFileCategoryById(informationCategoryId);
        if(resourceFileCategory !=null && resourceFileCategory.getParentId() == 0){
            return buf;
        }else if(resourceFileCategory !=null){
            buf.append(resourceFileCategory.getParentId()+",");
            selectAncestorsById(resourceFileCategory.getParentId(),buf);
        }
        return buf;
    }
}
