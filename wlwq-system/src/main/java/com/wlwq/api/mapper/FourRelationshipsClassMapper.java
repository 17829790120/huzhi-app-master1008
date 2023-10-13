package com.wlwq.api.mapper;

import com.wlwq.api.domain.FourRelationshipsClass;

import java.util.List;

/**
 * 四类关系分类Mapper接口
 * 
 * @author dxy
 */
public interface FourRelationshipsClassMapper {
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
     * 删除四类关系分类
     *
     * @param fourRelationshipsClassId 四类关系分类ID
     * @return 结果
     */
    public int deleteFourRelationshipsClassById(Long fourRelationshipsClassId);

    /**
     * 批量删除四类关系分类
     * 
     * @param fourRelationshipsClassIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteFourRelationshipsClassByIds(String[] fourRelationshipsClassIds);

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
