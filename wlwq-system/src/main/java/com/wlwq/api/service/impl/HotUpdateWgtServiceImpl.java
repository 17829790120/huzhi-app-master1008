package com.wlwq.api.service.impl;

import com.wlwq.api.domain.HotUpdateWgt;
import com.wlwq.api.mapper.HotUpdateWgtMapper;
import com.wlwq.api.service.IHotUpdateWgtService;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 热更新Service业务层处理
 *
 * @author lk
 * @date 2022-10-26
 */
@Service
public class HotUpdateWgtServiceImpl implements IHotUpdateWgtService {

    @Autowired
    private HotUpdateWgtMapper hotUpdateWgtMapper;

    /**
     * 查询热更新
     *
     * @param id 热更新ID
     * @return 热更新
     */
    @Override
    public HotUpdateWgt selectHotUpdateWgtById(Long id) {
        return hotUpdateWgtMapper.selectHotUpdateWgtById(id);
    }

    /**
     * 查询可以热更新的版本内容
     *
     * @param versionCode 热更新ID
     * @return 热更新
     */
    @Override
    public HotUpdateWgt getHotUpdateUrl(Long versionCode, String type) {
        return hotUpdateWgtMapper.getHotUpdateUrl(versionCode, type);
    }

    /**
     * 查询热更新列表
     *
     * @param hotUpdateWgt 热更新
     * @return 热更新
     */
    @Override
    public List<HotUpdateWgt> selectHotUpdateWgtList(HotUpdateWgt hotUpdateWgt) {
        return hotUpdateWgtMapper.selectHotUpdateWgtList(hotUpdateWgt);
    }

    /**
     * 新增热更新
     *
     * @param hotUpdateWgt 热更新
     * @return 结果
     */
    @Override
    public int insertHotUpdateWgt(HotUpdateWgt hotUpdateWgt) {
        hotUpdateWgt.setCreateTime(DateUtils.getNowDate());
        return hotUpdateWgtMapper.insertHotUpdateWgt(hotUpdateWgt);
    }

    /**
     * 修改热更新
     *
     * @param hotUpdateWgt 热更新
     * @return 结果
     */
    @Override
    public int updateHotUpdateWgt(HotUpdateWgt hotUpdateWgt) {
        return hotUpdateWgtMapper.updateHotUpdateWgt(hotUpdateWgt);
    }

    /**
     * 删除热更新对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHotUpdateWgtByIds(String ids) {
        return hotUpdateWgtMapper.deleteHotUpdateWgtByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除热更新信息
     *
     * @param id 热更新ID
     * @return 结果
     */
    @Override
    public int deleteHotUpdateWgtById(Long id) {
        return hotUpdateWgtMapper.deleteHotUpdateWgtById(id);
    }
}