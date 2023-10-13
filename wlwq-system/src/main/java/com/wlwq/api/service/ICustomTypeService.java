package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.CustomType;
import com.wlwq.api.resultVO.CustomTypeVO;

/**
 * 客户意向Service接口
 * 
 * @author wlwq
 * @date 2023-06-10
 */
public interface ICustomTypeService {
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
     * 批量删除客户意向
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomTypeByIds(String ids);

    /**
     * 删除客户意向信息
     * 
     * @param customTypeId 客户意向ID
     * @return 结果
     */
    public int deleteCustomTypeById(String customTypeId);

    /**
     * 查询客户意向
     */
    List<CustomTypeVO> findCustomTypeVO(String companyId,String name);
}
