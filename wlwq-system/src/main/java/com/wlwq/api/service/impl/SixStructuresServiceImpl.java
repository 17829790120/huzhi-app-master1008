package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.SixStructuresMapper;
import com.wlwq.api.domain.SixStructures;
import com.wlwq.api.service.ISixStructuresService;
import com.wlwq.common.core.text.Convert;

/**
 * 六大架构Service业务层处理
 * 
 * @author wlwq
 * @date 2023-08-28
 */
@Service
public class SixStructuresServiceImpl implements ISixStructuresService {

    @Autowired
    private SixStructuresMapper sixStructuresMapper;

    /**
     * 查询六大架构
     * 
     * @param sixStructuresId 六大架构ID
     * @return 六大架构
     */
    @Override
    public SixStructures selectSixStructuresById(String sixStructuresId) {
        return sixStructuresMapper.selectSixStructuresById(sixStructuresId);
    }

    /**
     * 查询六大架构列表
     * 
     * @param sixStructures 六大架构
     * @return 六大架构
     */
    @Override
    public List<SixStructures> selectSixStructuresList(SixStructures sixStructures) {
        return sixStructuresMapper.selectSixStructuresList(sixStructures);
    }

    /**
     * 新增六大架构
     * 
     * @param sixStructures 六大架构
     * @return 结果
     */
    @Override
    public int insertSixStructures(SixStructures sixStructures) {
        sixStructures.setSixStructuresId(IdUtil.getSnowflake(1,1).nextIdStr());
        sixStructures.setCreateTime(DateUtils.getNowDate());
        return sixStructuresMapper.insertSixStructures(sixStructures);
    }

    /**
     * 修改六大架构
     * 
     * @param sixStructures 六大架构
     * @return 结果
     */
    @Override
    public int updateSixStructures(SixStructures sixStructures) {
        sixStructures.setUpdateTime(DateUtils.getNowDate());
        return sixStructuresMapper.updateSixStructures(sixStructures);
    }

    /**
     * 删除六大架构对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSixStructuresByIds(String ids) {
        return sixStructuresMapper.deleteSixStructuresByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除六大架构信息
     * 
     * @param sixStructuresId 六大架构ID
     * @return 结果
     */
    @Override
    public int deleteSixStructuresById(String sixStructuresId) {
        return sixStructuresMapper.deleteSixStructuresById(sixStructuresId);
    }

    /**
     * 查询某个分类下的所有发布的信息数量
     * @param sixStructures
     * @return
     */
    @Override
    public int selectSixStructuresCount(SixStructures sixStructures) {
        return sixStructuresMapper.selectSixStructuresCount(sixStructures);
    }

    /**
     *  api六大架构列表
     * @param sixStructures
     * @return
     */
    @Override
    public List<SixStructures> selectApiSixStructuresList(SixStructures sixStructures) {
        return sixStructuresMapper.selectApiSixStructuresList(sixStructures);
    }

    /**
     * 查询提交的人数
     * @param sixStructures
     * @return
     */
    @Override
    public int selectApiSixStructuresCount(SixStructures sixStructures) {
        return sixStructuresMapper.selectApiSixStructuresCount(sixStructures);
    }

    /**
     * Web查询六大架构列表
     * @param sixStructures
     * @return
     */
    @Override
    public List<SixStructures> selectWebSixStructuresList(SixStructures sixStructures) {
        return sixStructuresMapper.selectWebSixStructuresList(sixStructures);
    }

    /**
     * 查询对应分类下的发布数量
     * @param companyId
     * @param classIds
     * @return
     */
    @Override
    public Map<String, Integer> selectSixStructuresCountByClassIds(Long companyId, List<String> classIds) {
        return sixStructuresMapper.selectSixStructuresCountByClassIds(companyId,classIds);
    }

}
