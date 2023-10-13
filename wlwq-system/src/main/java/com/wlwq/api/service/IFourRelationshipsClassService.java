package com.wlwq.api.service;

import com.wlwq.api.domain.FourRelationshipsClass;
import com.wlwq.common.core.domain.Ztree;

import java.util.List;

/**
 * 四类关系分类Service接口
 * 
 * @author dxy
 */
public interface IFourRelationshipsClassService {
    /**
     * 查询四类关系分类
     * 
     * @param fourRelationshipsClassId 四类关系分类ID
     * @return 四类关系分类
     */
    public FourRelationshipsClass selectFourRelationshipsClassById(Long fourRelationshipsClassId);

    /**
     * 查询四类关系分类列表
     *
     * @param fourRelationshipsClass 四类关系分类
     * @return 四类关系分类集合
     */
    public List<FourRelationshipsClass> selectFourRelationshipsClassList(FourRelationshipsClass fourRelationshipsClass);

    /**
     * 新增四类关系分类
     *
     * @param fourRelationshipsClass 四类关系分类
     * @return 结果
     */
    public int insertFourRelationshipsClass(FourRelationshipsClass fourRelationshipsClass);

    /**
     * 修改四类关系分类
     *
     * @param fourRelationshipsClass 四类关系分类
     * @return 结果
     */
    public int updateFourRelationshipsClass(FourRelationshipsClass fourRelationshipsClass);

    /**
     * 批量删除四类关系分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFourRelationshipsClassByIds(String ids);

    /**
     * 删除四类关系分类信息
     *
     * @param fourRelationshipsClassId 四类关系分类ID
     * @return 结果
     */
    public int deleteFourRelationshipsClassById(Long fourRelationshipsClassId);

    /**
     * 加载服务分类树列表
     * @return
     */
    List<Ztree> selectStoreClassTree();

    /**
     * App四类关系列表
     * @param fourRelationshipsClass
     * @return
     */
    List<FourRelationshipsClass> selectFourRelationshipsClassVoList(FourRelationshipsClass fourRelationshipsClass);

    /**
     * App四类关系列表查询parentId
     * @param fourRelationshipsClass
     * @return
     */
    FourRelationshipsClass selectApiFourRelationshipsClassList(FourRelationshipsClass fourRelationshipsClass);
}
