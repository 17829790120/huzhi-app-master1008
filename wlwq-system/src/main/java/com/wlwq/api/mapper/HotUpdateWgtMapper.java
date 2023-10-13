package com.wlwq.api.mapper;

import com.wlwq.api.domain.HotUpdateWgt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 热更新Mapper接口
 *
 * @author lk
 * @date 2022-10-26
 */
public interface HotUpdateWgtMapper {
    /**
     * 查询热更新
     *
     * @param id 热更新ID
     * @return 热更新
     */
    public HotUpdateWgt selectHotUpdateWgtById(Long id);

    /**
     * 查询可以热更新的版本内容
     *
     * @param versionCode
     * @param type
     * @return
     */
    public HotUpdateWgt getHotUpdateUrl(@Param("versionCode") Long versionCode, @Param("type") String type);

    /**
     * 查询热更新列表
     *
     * @param hotUpdateWgt 热更新
     * @return 热更新集合
     */
    public List<HotUpdateWgt> selectHotUpdateWgtList(HotUpdateWgt hotUpdateWgt);

    /**
     * 新增热更新
     *
     * @param hotUpdateWgt 热更新
     * @return 结果
     */
    public int insertHotUpdateWgt(HotUpdateWgt hotUpdateWgt);

    /**
     * 修改热更新
     *
     * @param hotUpdateWgt 热更新
     * @return 结果
     */
    public int updateHotUpdateWgt(HotUpdateWgt hotUpdateWgt);

    /**
     * 删除热更新
     *
     * @param id 热更新ID
     * @return 结果
     */
    public int deleteHotUpdateWgtById(Long id);

    /**
     * 批量删除热更新
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHotUpdateWgtByIds(String[] ids);
}