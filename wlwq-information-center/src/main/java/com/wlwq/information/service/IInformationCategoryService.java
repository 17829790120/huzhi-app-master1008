package com.wlwq.information.service;

import java.util.List;
import com.wlwq.information.domain.InformationCategory;
import com.wlwq.common.core.domain.Ztree;

/**
 * 资讯分类Service接口
 * 
 * @author Rick wlwq
 * @date 2021-04-19
 */
public interface IInformationCategoryService 
{
    /**
     * 查询资讯分类
     * 
     * @param informationCategoryId 资讯分类ID
     * @return 资讯分类
     */
    public InformationCategory selectInformationCategoryById(Long informationCategoryId);

    /**
     * 查询资讯分类列表
     * 
     * @param informationCategory 资讯分类
     * @return 资讯分类集合
     */
    public List<InformationCategory> selectInformationCategoryList(InformationCategory informationCategory);

    /**
     * 新增资讯分类
     * 
     * @param informationCategory 资讯分类
     * @return 结果
     */
    public int insertInformationCategory(InformationCategory informationCategory);

    /**
     * 修改资讯分类
     * 
     * @param informationCategory 资讯分类
     * @return 结果
     */
    public int updateInformationCategory(InformationCategory informationCategory);

    /**
     * 批量删除资讯分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteInformationCategoryByIds(String ids);

    /**
     * 删除资讯分类信息
     * 
     * @param informationCategoryId 资讯分类ID
     * @return 结果
     */
    public int deleteInformationCategoryById(Long informationCategoryId);

    /**
     * 查询资讯分类树列表
     * 
     * @return 所有资讯分类信息
     */
    public List<Ztree> selectInformationCategoryTree();
    /**
     * 查询资讯分类列表
     *
     * @return 所有资讯分类信息
     */
    List<InformationCategory> selectInformationCategoryVoList(InformationCategory informationCategory);

    StringBuffer selectAncestorsById(Long informationCategoryId, StringBuffer buf);
}
