package com.wlwq.api.service;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.SixStructures;

/**
 * 六大架构Service接口
 * 
 * @author wlwq
 * @date 2023-08-28
 */
public interface ISixStructuresService {
    /**
     * 查询六大架构
     * 
     * @param sixStructuresId 六大架构ID
     * @return 六大架构
     */
    public SixStructures selectSixStructuresById(String sixStructuresId);

    /**
     * 查询六大架构列表
     * 
     * @param sixStructures 六大架构
     * @return 六大架构集合
     */
    public List<SixStructures> selectSixStructuresList(SixStructures sixStructures);

    /**
     * 新增六大架构
     * 
     * @param sixStructures 六大架构
     * @return 结果
     */
    public int insertSixStructures(SixStructures sixStructures);

    /**
     * 修改六大架构
     * 
     * @param sixStructures 六大架构
     * @return 结果
     */
    public int updateSixStructures(SixStructures sixStructures);

    /**
     * 批量删除六大架构
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSixStructuresByIds(String ids);

    /**
     * 删除六大架构信息
     * 
     * @param sixStructuresId 六大架构ID
     * @return 结果
     */
    public int deleteSixStructuresById(String sixStructuresId);

    /**
     * 查询某个分类下的所有发布的信息数量
     * @param sixStructures
     * @return
     */
    int selectSixStructuresCount(SixStructures sixStructures);

    /**
     * api六大架构列表
     * @param sixStructures
     * @return
     */
    List<SixStructures> selectApiSixStructuresList(SixStructures sixStructures);

    /**
     * 查询提交的人数
     * @param sixStructures
     * @return
     */
    int selectApiSixStructuresCount(SixStructures sixStructures);

    /**
     * Web查询六大架构列表
     * @param sixStructures
     * @return
     */
    List<SixStructures> selectWebSixStructuresList(SixStructures sixStructures);

    /**
     * 查询对应分类下的发布数量
     * @param companyId
     * @param classIds
     * @return
     */
    Map<String, Integer> selectSixStructuresCountByClassIds(Long companyId, List<String> classIds);
}
