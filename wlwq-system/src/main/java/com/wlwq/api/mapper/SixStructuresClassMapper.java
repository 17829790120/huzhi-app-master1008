package com.wlwq.api.mapper;

import java.util.List;
import com.wlwq.api.domain.SixStructuresClass;

/**
 * 六大架构分类Mapper接口
 * 
 * @author wlwq
 * @date 2023-08-28
 */
public interface SixStructuresClassMapper {
    /**
     * 查询六大架构分类
     * 
     * @param sixStructuresClassId 六大架构分类ID
     * @return 六大架构分类
     */
    public SixStructuresClass selectSixStructuresClassById(Long sixStructuresClassId);

    /**
     * 查询六大架构分类列表
     *
     * @param sixStructuresClass 六大架构分类
     * @return 六大架构分类集合
     */
    public List<SixStructuresClass> selectSixStructuresClassList(SixStructuresClass sixStructuresClass);

    /**
     * 新增六大架构分类
     *
     * @param sixStructuresClass 六大架构分类
     * @return 结果
     */
    public int insertSixStructuresClass(SixStructuresClass sixStructuresClass);

    /**
     * 修改六大架构分类
     *
     * @param sixStructuresClass 六大架构分类
     * @return 结果
     */
    public int updateSixStructuresClass(SixStructuresClass sixStructuresClass);

    /**
     * 删除六大架构分类
     *
     * @param sixStructuresClassId 六大架构分类ID
     * @return 结果
     */
    public int deleteSixStructuresClassById(Long sixStructuresClassId);

    /**
     * 批量删除六大架构分类
     * 
     * @param sixStructuresClassIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSixStructuresClassByIds(String[] sixStructuresClassIds);

    /**
     * App六大架构列表
     * @param sixStructuresClass
     * @return
     */
    List<SixStructuresClass> selectSixStructuresClassVoList(SixStructuresClass sixStructuresClass);

    /**
     * App六大架构列表查询parentId
     * @param sixStructuresClass
     * @return
     */
    SixStructuresClass selectApiSixStructuresClassList(SixStructuresClass sixStructuresClass);
}
