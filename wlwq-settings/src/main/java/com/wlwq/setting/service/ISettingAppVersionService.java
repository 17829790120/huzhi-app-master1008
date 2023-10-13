package com.wlwq.setting.service;


import com.wlwq.setting.domain.SettingAppVersion;

import java.util.List;

/**
 * APP版本控制Service接口
 * 
 * @author Rick wlwq
 * @date 2022-04-13
 */
public interface ISettingAppVersionService 
{
    /**
     * 查询APP版本控制
     * 
     * @param versionId APP版本控制ID
     * @return APP版本控制
     */
    public SettingAppVersion selectSettingAppVersionById(Long versionId);

    /**
     * 查询APP版本控制列表
     * 
     * @param settingAppVersion APP版本控制
     * @return APP版本控制集合
     */
    public List<SettingAppVersion> selectSettingAppVersionList(SettingAppVersion settingAppVersion);

    /**
     * 新增APP版本控制
     * 
     * @param settingAppVersion APP版本控制
     * @return 结果
     */
    public int insertSettingAppVersion(SettingAppVersion settingAppVersion);

    /**
     * 修改APP版本控制
     * 
     * @param settingAppVersion APP版本控制
     * @return 结果
     */
    public int updateSettingAppVersion(SettingAppVersion settingAppVersion);

    /**
     * 批量删除APP版本控制
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSettingAppVersionByIds(String ids);

    /**
     * 删除APP版本控制信息
     * 
     * @param versionId APP版本控制ID
     * @return 结果
     */
    public int deleteSettingAppVersionById(Long versionId);

    /**
     * 获取最新的版本
     * @param resourceType
     * @return
     */
    SettingAppVersion selectNewVersion(String resourceType);
}
