package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ArmoryMapper;
import com.wlwq.api.domain.Armory;
import com.wlwq.api.service.IArmoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 英雄榜列表Service业务层处理
 *
 * @author wwb
 * @date 2023-04-18
 */
@Service
public class ArmoryServiceImpl implements IArmoryService {

    @Autowired
    private ArmoryMapper armoryMapper;

    /**
     * 查询英雄榜列表
     *
     * @param armoryId 英雄榜列表ID
     * @return 英雄榜列表
     */
    @Override
    public Armory selectArmoryById(String armoryId) {
        return armoryMapper.selectArmoryById(armoryId);
    }

    /**
     * 查询英雄榜列表列表
     *
     * @param armory 英雄榜列表
     * @return 英雄榜列表
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<Armory> selectArmoryList(Armory armory) {
        return armoryMapper.selectArmoryList(armory);
    }

    /**
     * 新增英雄榜列表
     *
     * @param armory 英雄榜列表
     * @return 结果
     */
    @Override
    public int insertArmory(Armory armory) {
        armory.setArmoryId(IdUtil.getSnowflake(1, 1).nextIdStr());
        armory.setCreateTime(DateUtils.getNowDate());
        return armoryMapper.insertArmory(armory);
    }

    /**
     * 修改英雄榜列表
     *
     * @param armory 英雄榜列表
     * @return 结果
     */
    @Override
    public int updateArmory(Armory armory) {
        return armoryMapper.updateArmory(armory);
    }

    /**
     * 删除英雄榜列表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteArmoryByIds(String ids) {
        return armoryMapper.deleteArmoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除英雄榜列表信息
     *
     * @param armoryId 英雄榜列表ID
     * @return 结果
     */
    @Override
    public int deleteArmoryById(String armoryId) {
        return armoryMapper.deleteArmoryById(armoryId);
    }
    /**
     * APP查询英雄榜列表列表
     *
     * @param armory 英雄榜列表
     * @return 英雄榜列表集合
     */
    @Override
    public List<Armory> selectApiArmoryList(Armory armory) {
        return armoryMapper.selectApiArmoryList(armory);
    }
}
