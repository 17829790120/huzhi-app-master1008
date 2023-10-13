package com.wlwq.setting.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.setting.domain.SettingAppVersion;
import com.wlwq.setting.mapper.SettingAppVersionMapper;
import com.wlwq.setting.service.ISettingAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * APP版本控制Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2022-04-13
 */
@Service
public class SettingAppVersionServiceImpl implements ISettingAppVersionService
{
    @Autowired
    private SettingAppVersionMapper settingAppVersionMapper;

    /**
     * 查询APP版本控制
     * 
     * @param versionId APP版本控制ID
     * @return APP版本控制
     */
    @Override
    public SettingAppVersion selectSettingAppVersionById(Long versionId)
    {
        return settingAppVersionMapper.selectSettingAppVersionById(versionId);
    }

    /**
     * 查询APP版本控制列表
     * 
     * @param settingAppVersion APP版本控制
     * @return APP版本控制
     */
    @Override
    public List<SettingAppVersion> selectSettingAppVersionList(SettingAppVersion settingAppVersion)
    {
        return settingAppVersionMapper.selectSettingAppVersionList(settingAppVersion);
    }

    /**
     * 新增APP版本控制
     * 
     * @param settingAppVersion APP版本控制
     * @return 结果
     */
    @Override
    public int insertSettingAppVersion(SettingAppVersion settingAppVersion)
    {
        settingAppVersion.setCreateTime(DateUtils.getNowDate());
        return settingAppVersionMapper.insertSettingAppVersion(settingAppVersion);
    }

    /**
     * 修改APP版本控制
     * 
     * @param settingAppVersion APP版本控制
     * @return 结果
     */
    @Override
    public int updateSettingAppVersion(SettingAppVersion settingAppVersion)
    {
        settingAppVersion.setUpdateTime(DateUtils.getNowDate());
        return settingAppVersionMapper.updateSettingAppVersion(settingAppVersion);
    }

    /**
     * 删除APP版本控制对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSettingAppVersionByIds(String ids)
    {
        return settingAppVersionMapper.deleteSettingAppVersionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除APP版本控制信息
     * 
     * @param versionId APP版本控制ID
     * @return 结果
     */
    @Override
    public int deleteSettingAppVersionById(Long versionId)
    {
        return settingAppVersionMapper.deleteSettingAppVersionById(versionId);
    }

    /**
     * 获取最新的版本
     * @param resourceType
     * @return
     */
    @Override
    public SettingAppVersion selectNewVersion(String resourceType) {
        return settingAppVersionMapper.selectNewVersion(resourceType);
    }
}
