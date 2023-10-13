package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.api.resultVO.CustomTypeVO;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CustomTypeMapper;
import com.wlwq.api.domain.CustomType;
import com.wlwq.api.service.ICustomTypeService;
import com.wlwq.common.core.text.Convert;

/**
 * 客户意向Service业务层处理
 * 
 * @author wlwq
 * @date 2023-06-10
 */
@Service
public class CustomTypeServiceImpl implements ICustomTypeService {

    @Autowired
    private CustomTypeMapper customTypeMapper;

    /**
     * 查询客户意向
     * 
     * @param customTypeId 客户意向ID
     * @return 客户意向
     */
    @Override
    public CustomType selectCustomTypeById(String customTypeId) {
        return customTypeMapper.selectCustomTypeById(customTypeId);
    }

    /**
     * 查询客户意向列表
     * 
     * @param customType 客户意向
     * @return 客户意向
     */
    @Override
    public List<CustomType> selectCustomTypeList(CustomType customType) {
        return customTypeMapper.selectCustomTypeList(customType);
    }

    /**
     * 新增客户意向
     * 
     * @param customType 客户意向
     * @return 结果
     */
    @Override
    public int insertCustomType(CustomType customType) {
        customType.setCustomTypeId(IdUtil.getSnowflake(1,1).nextIdStr());
        customType.setCreateTime(DateUtils.getNowDate());
        return customTypeMapper.insertCustomType(customType);
    }

    /**
     * 修改客户意向
     * 
     * @param customType 客户意向
     * @return 结果
     */
    @Override
    public int updateCustomType(CustomType customType) {
        customType.setUpdateTime(DateUtils.getNowDate());
        return customTypeMapper.updateCustomType(customType);
    }

    /**
     * 删除客户意向对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomTypeByIds(String ids) {
        return customTypeMapper.deleteCustomTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除客户意向信息
     * 
     * @param customTypeId 客户意向ID
     * @return 结果
     */
    @Override
    public int deleteCustomTypeById(String customTypeId) {
        return customTypeMapper.deleteCustomTypeById(customTypeId);
    }

    @Override
    public List<CustomTypeVO> findCustomTypeVO(String companyId, String name) {
        return customTypeMapper.findCustomTypeVO(companyId,name);
    }
}
