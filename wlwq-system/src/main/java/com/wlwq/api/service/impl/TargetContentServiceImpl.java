package com.wlwq.api.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TargetContentMapper;
import com.wlwq.api.domain.TargetContent;
import com.wlwq.api.service.ITargetContentService;
import com.wlwq.common.core.text.Convert;

/**
 * 目标训练内容Service业务层处理
 * 
 * @author wwb
 * @date 2023-06-02
 */
@Service
public class TargetContentServiceImpl implements ITargetContentService {

    @Autowired
    private TargetContentMapper targetContentMapper;

    /**
     * 查询目标训练内容
     * 
     * @param id 目标训练内容ID
     * @return 目标训练内容
     */
    @Override
    public TargetContent selectTargetContentById(Long id) {
        return targetContentMapper.selectTargetContentById(id);
    }

    /**
     * 查询目标训练内容列表
     * 
     * @param targetContent 目标训练内容
     * @return 目标训练内容
     */
    @Override
    public List<TargetContent> selectTargetContentList(TargetContent targetContent) {
        return targetContentMapper.selectTargetContentList(targetContent);
    }

    /**
     * 新增目标训练内容
     * 
     * @param targetContent 目标训练内容
     * @return 结果
     */
    @Override
    public int insertTargetContent(TargetContent targetContent) {
        targetContent.setCreateTime(DateUtils.getNowDate());
        return targetContentMapper.insertTargetContent(targetContent);
    }

    /**
     * 修改目标训练内容
     * 
     * @param targetContent 目标训练内容
     * @return 结果
     */
    @Override
    public int updateTargetContent(TargetContent targetContent) {
        return targetContentMapper.updateTargetContent(targetContent);
    }

    /**
     * 删除目标训练内容对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTargetContentByIds(String ids) {
        return targetContentMapper.deleteTargetContentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除目标训练内容信息
     * 
     * @param id 目标训练内容ID
     * @return 结果
     */
    @Override
    public int deleteTargetContentById(Long id) {
        return targetContentMapper.deleteTargetContentById(id);
    }

    /**
     * 查询目标训练内容树列表
     * 
     * @return 所有目标训练内容信息
     */
    @Override
    public List<Ztree> selectTargetContentTree() {
        List<TargetContent> targetContentList = targetContentMapper.selectTargetContentList(new TargetContent());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (TargetContent targetContent : targetContentList) {
            Ztree ztree = new Ztree();
            ztree.setId(targetContent.getId());
            ztree.setpId(targetContent.getParentId());
            ztree.setName(targetContent.getTitle());
            ztree.setTitle(targetContent.getTitle());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
