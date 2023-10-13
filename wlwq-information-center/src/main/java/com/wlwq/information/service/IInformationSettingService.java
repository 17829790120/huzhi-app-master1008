package com.wlwq.information.service;

import java.util.List;
import com.wlwq.information.domain.InformationSetting;

/**
 * 资讯设置Service接口
 * 
 * @author Rick wlwq
 * @date 2021-04-19
 */
public interface IInformationSettingService 
{
    /**
     * 查询资讯设置
     * 
     * @param informationSettingId 资讯设置ID
     * @return 资讯设置
     */
    public InformationSetting selectInformationSettingById(Long informationSettingId);

    /**
     * 查询资讯设置列表
     * 
     * @param informationSetting 资讯设置
     * @return 资讯设置集合
     */
    public List<InformationSetting> selectInformationSettingList(InformationSetting informationSetting);

    /**
     * 新增资讯设置
     * 
     * @param informationSetting 资讯设置
     * @return 结果
     */
    public int insertInformationSetting(InformationSetting informationSetting);

    /**
     * 修改资讯设置
     * 
     * @param informationSetting 资讯设置
     * @return 结果
     */
    public int updateInformationSetting(InformationSetting informationSetting);

    /**
     * 批量删除资讯设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteInformationSettingByIds(String ids);

    /**
     * 删除资讯设置信息
     * 
     * @param informationSettingId 资讯设置ID
     * @return 结果
     */
    public int deleteInformationSettingById(Long informationSettingId);
}
