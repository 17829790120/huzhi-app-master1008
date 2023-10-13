package com.wlwq.api.service.impl;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.FourRelationships;
import com.wlwq.api.mapper.FourRelationshipsMapper;
import com.wlwq.api.service.IFourRelationshipsService;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 四类关系Service业务层处理
 * 
 * @author dxy
 */
@Service
public class FourRelationshipsServiceImpl implements IFourRelationshipsService {


    @Autowired
    private FourRelationshipsMapper fourRelationshipsMapper;

    /**
     * 查询四类关系
     * 
     * @param fourRelationshipsId 四类关系ID
     * @return 四类关系
     */
    @Override
    public FourRelationships selectFourRelationshipsById(String fourRelationshipsId) {
        return fourRelationshipsMapper.selectFourRelationshipsById(fourRelationshipsId);
    }

    /**
     * 查询四类关系列表
     * 
     * @param fourRelationships 四类关系
     * @return 四类关系
     */
    @Override
    public List<FourRelationships> selectFourRelationshipsList(FourRelationships fourRelationships) {
        return fourRelationshipsMapper.selectFourRelationshipsList(fourRelationships);
    }

    /**
     * 新增四类关系
     * 
     * @param fourRelationships 四类关系
     * @return 结果
     */
    @Override
    public int insertFourRelationships(FourRelationships fourRelationships) {
        fourRelationships.setFourRelationshipsId(IdUtil.getSnowflake(1,1).nextIdStr());
        fourRelationships.setCreateTime(DateUtils.getNowDate());
        return fourRelationshipsMapper.insertFourRelationships(fourRelationships);
    }

    /**
     * 修改四类关系
     * 
     * @param fourRelationships 四类关系
     * @return 结果
     */
    @Override
    public int updateFourRelationships(FourRelationships fourRelationships) {
        fourRelationships.setUpdateTime(DateUtils.getNowDate());
        return fourRelationshipsMapper.updateFourRelationships(fourRelationships);
    }

    /**
     * 删除四类关系对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFourRelationshipsByIds(String ids) {
        return fourRelationshipsMapper.deleteFourRelationshipsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除四类关系信息
     * 
     * @param fourRelationshipsId 四类关系ID
     * @return 结果
     */
    @Override
    public int deleteFourRelationshipsById(String fourRelationshipsId) {
        return fourRelationshipsMapper.deleteFourRelationshipsById(fourRelationshipsId);
    }

    /**
     * 查询某个分类下的所有发布的信息数量
     * @param fourRelationships
     * @return
     */
    @Override
    public int selectFourRelationshipsCount(FourRelationships fourRelationships) {
        return fourRelationshipsMapper.selectFourRelationshipsCount(fourRelationships);
    }

    /**
     *  api四类关系列表
     * @param fourRelationships
     * @return
     */
    @Override
    public List<FourRelationships> selectApiFourRelationshipsList(FourRelationships fourRelationships) {
        return fourRelationshipsMapper.selectApiFourRelationshipsList(fourRelationships);
    }

    /**
     * 查询提交的人数
     * @param fourRelationships
     * @return
     */
    @Override
    public int selectApiFourRelationshipsCount(FourRelationships fourRelationships) {
        return fourRelationshipsMapper.selectApiFourRelationshipsCount(fourRelationships);
    }

    /**
     * Web查询四类关系列表
     * @param fourRelationships
     * @return
     */
    @Override
    public List<FourRelationships> selectWebFourRelationshipsList(FourRelationships fourRelationships) {
        return fourRelationshipsMapper.selectWebFourRelationshipsList(fourRelationships);
    }

    /**
     * 查询对应分类下的发布数量
     * @param companyId
     * @param classIds
     * @return
     */
    @Override
    public Map<String, Integer> selectFourRelationshipsCountByClassIds(Long companyId, List<String> classIds) {
        return fourRelationshipsMapper.selectFourRelationshipsCountByClassIds(companyId,classIds);
    }


}
