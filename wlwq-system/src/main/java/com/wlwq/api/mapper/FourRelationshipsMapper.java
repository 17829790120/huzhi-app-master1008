package com.wlwq.api.mapper;

import com.wlwq.api.domain.FourRelationships;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 四类关系Mapper接口
 * 
 * @author dxy
 */
public interface FourRelationshipsMapper {
    /**
     * 查询四类关系
     * 
     * @param fourRelationshipsId 四类关系ID
     * @return 四类关系
     */
    public FourRelationships selectFourRelationshipsById(String fourRelationshipsId);

    /**
     * 查询四类关系列表
     * 
     * @param fourRelationships 四类关系
     * @return 四类关系集合
     */
    public List<FourRelationships> selectFourRelationshipsList(FourRelationships fourRelationships);

    /**
     * 新增四类关系
     * 
     * @param fourRelationships 四类关系
     * @return 结果
     */
    public int insertFourRelationships(FourRelationships fourRelationships);

    /**
     * 修改四类关系
     * 
     * @param fourRelationships 四类关系
     * @return 结果
     */
    public int updateFourRelationships(FourRelationships fourRelationships);

    /**
     * 删除四类关系
     * 
     * @param fourRelationshipsId 四类关系D
     * @return 结果
     */
    public int deleteFourRelationshipsById(String fourRelationshipsId);

    /**
     * 批量删除四类关系
     * 
     * @param fourRelationshipsIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteFourRelationshipsByIds(String[] fourRelationshipsIds);

    /**
     * 查询某个分类下的所有发布的信息数量
     * @param fourRelationships
     * @return
     */
    int selectFourRelationshipsCount(FourRelationships fourRelationships);

    /**
     *  api四类关系列表
     * @param fourRelationships
     * @return
     */
    List<FourRelationships> selectApiFourRelationshipsList(FourRelationships fourRelationships);

    /**
     * 查询提交的人数
     * @param fourRelationships
     * @return
     */
    int selectApiFourRelationshipsCount(FourRelationships fourRelationships);

    /**
     * Web查询四类关系列表
     * @param fourRelationships
     * @return
     */
    List<FourRelationships> selectWebFourRelationshipsList(FourRelationships fourRelationships);

    /**
     * 查询对应分类下的发布数量
     * @param companyId
     * @param classIds
     * @return
     */
    Map<String, Integer> selectFourRelationshipsCountByClassIds(@Param("companyId") Long companyId, @Param("classIds")List<String> classIds);
}
