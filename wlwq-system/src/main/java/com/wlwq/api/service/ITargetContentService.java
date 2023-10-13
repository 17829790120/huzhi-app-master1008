package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.TargetContent;
import com.wlwq.common.core.domain.Ztree;

/**
 * 目标训练内容Service接口
 *
 * @author wwb
 * @date 2023-06-02
 */
public interface ITargetContentService {
    /**
     * 查询目标训练内容
     *
     * @param id 目标训练内容ID
     * @return 目标训练内容
     */
    public TargetContent selectTargetContentById(Long id);

    /**
     * 查询目标训练内容列表
     *
     * @param targetContent 目标训练内容
     * @return 目标训练内容集合
     */
    public List<TargetContent> selectTargetContentList(TargetContent targetContent);

    /**
     * 新增目标训练内容
     *
     * @param targetContent 目标训练内容
     * @return 结果
     */
    public int insertTargetContent(TargetContent targetContent);

    /**
     * 修改目标训练内容
     *
     * @param targetContent 目标训练内容
     * @return 结果
     */
    public int updateTargetContent(TargetContent targetContent);

    /**
     * 批量删除目标训练内容
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTargetContentByIds(String ids);

    /**
     * 删除目标训练内容信息
     *
     * @param id 目标训练内容ID
     * @return 结果
     */
    public int deleteTargetContentById(Long id);

    /**
     * 查询目标训练内容树列表
     *
     * @return 所有目标训练内容信息
     */
    public List<Ztree> selectTargetContentTree();
}
