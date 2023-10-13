package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.SysDeptScore;

/**
 * 公司积分设置Mapper接口
 *
 * @author gaoce
 * @date 2023-06-06
 */
public interface SysDeptScoreMapper {
    /**
     * 查询公司积分设置
     *
     * @param sysDeptScoreId 公司积分设置ID
     * @return 公司积分设置
     */
    public SysDeptScore selectSysDeptScoreById(String sysDeptScoreId);

    /**
     * 查询公司积分设置列表
     *
     * @param sysDeptScore 公司积分设置
     * @return 公司积分设置集合
     */
    public List<SysDeptScore> selectSysDeptScoreList(SysDeptScore sysDeptScore);

    /**
     * 新增公司积分设置
     *
     * @param sysDeptScore 公司积分设置
     * @return 结果
     */
    public int insertSysDeptScore(SysDeptScore sysDeptScore);

    /**
     * 修改公司积分设置
     *
     * @param sysDeptScore 公司积分设置
     * @return 结果
     */
    public int updateSysDeptScore(SysDeptScore sysDeptScore);

    /**
     * 删除公司积分设置
     *
     * @param sysDeptScoreId 公司积分设置ID
     * @return 结果
     */
    public int deleteSysDeptScoreById(String sysDeptScoreId);

    /**
     * 批量删除公司积分设置
     *
     * @param sysDeptScoreIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDeptScoreByIds(String[] sysDeptScoreIds);

    /**
     * 只查询最新的一条
     * @param sysDeptScore
     * @return
     */
    public SysDeptScore selectSysDeptScore(SysDeptScore sysDeptScore);
}
