package com.wlwq.api.mapper;

import java.util.List;
import com.wlwq.api.domain.CustomType;
import com.wlwq.api.resultVO.CustomTypeVO;
import org.apache.ibatis.annotations.Param;

/**
 * 客户意向Mapper接口
 * 
 * @author wlwq
 * @date 2023-06-10
 */
public interface CustomTypeMapper {
    /**
     * 查询客户意向
     * 
     * @param customTypeId 客户意向ID
     * @return 客户意向
     */
    public CustomType selectCustomTypeById(String customTypeId);

    /**
     * 查询客户意向列表
     * 
     * @param customType 客户意向
     * @return 客户意向集合
     */
    public List<CustomType> selectCustomTypeList(CustomType customType);

    /**
     * 新增客户意向
     * 
     * @param customType 客户意向
     * @return 结果
     */
    public int insertCustomType(CustomType customType);

    /**
     * 修改客户意向
     * 
     * @param customType 客户意向
     * @return 结果
     */
    public int updateCustomType(CustomType customType);

    /**
     * 删除客户意向
     * 
     * @param customTypeId 客户意向ID
     * @return 结果
     */
    public int deleteCustomTypeById(String customTypeId);

    /**
     * 批量删除客户意向
     * 
     * @param customTypeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomTypeByIds(String[] customTypeIds);

    /**
     * 查询客户意向
     */
    List<CustomTypeVO> findCustomTypeVO(@Param("companyId") String companyId,@Param("name")  String name);

    /**
     * 批量查询客户标签
     */
    List<String> findLabel(@Param("customLabel") String customLabel);
}
