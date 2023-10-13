package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.Armory;

/**
 * 英雄榜列表Mapper接口
 *
 * @author wwb
 * @date 2023-04-18
 */
public interface ArmoryMapper {
    /**
     * 查询英雄榜列表
     *
     * @param armoryId 英雄榜列表ID
     * @return 英雄榜列表
     */
    public Armory selectArmoryById(String armoryId);

    /**
     * 查询英雄榜列表列表
     *
     * @param armory 英雄榜列表
     * @return 英雄榜列表集合
     */
    public List<Armory> selectArmoryList(Armory armory);

    /**
     * 新增英雄榜列表
     *
     * @param armory 英雄榜列表
     * @return 结果
     */
    public int insertArmory(Armory armory);

    /**
     * 修改英雄榜列表
     *
     * @param armory 英雄榜列表
     * @return 结果
     */
    public int updateArmory(Armory armory);

    /**
     * 删除英雄榜列表
     *
     * @param armoryId 英雄榜列表ID
     * @return 结果
     */
    public int deleteArmoryById(String armoryId);

    /**
     * 批量删除英雄榜列表
     *
     * @param armoryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteArmoryByIds(String[] armoryIds);
    /**
     * APP查询英雄榜列表列表
     *
     * @param armory 英雄榜列表
     * @return 英雄榜列表集合
     */
    List<Armory> selectApiArmoryList(Armory armory);
}
