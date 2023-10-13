package com.wlwq.information.service.impl;

import java.util.List;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.information.mapper.InformationSettingMapper;
import com.wlwq.information.domain.InformationSetting;
import com.wlwq.information.service.IInformationSettingService;
import com.wlwq.common.core.text.Convert;

/**
 * 资讯设置Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-04-19
 */
@Service
public class InformationSettingServiceImpl implements IInformationSettingService 
{
    @Autowired
    private InformationSettingMapper informationSettingMapper;

    /**
     * 查询资讯设置
     * 
     * @param informationSettingId 资讯设置ID
     * @return 资讯设置
     */
    @Override
    public InformationSetting selectInformationSettingById(Long informationSettingId)
    {
        return informationSettingMapper.selectInformationSettingById(informationSettingId);
    }

    /**
     * 查询资讯设置列表
     * 
     * @param informationSetting 资讯设置
     * @return 资讯设置
     */
    @Override
    public List<InformationSetting> selectInformationSettingList(InformationSetting informationSetting)
    {
        return informationSettingMapper.selectInformationSettingList(informationSetting);
    }

    /**
     * 新增资讯设置
     * 
     * @param informationSetting 资讯设置
     * @return 结果
     */
    @Override
    public int insertInformationSetting(InformationSetting informationSetting)
    {
        informationSetting.setCreateTime(DateUtils.getNowDate());
        return informationSettingMapper.insertInformationSetting(informationSetting);
    }

    /**
     * 修改资讯设置
     * 
     * @param informationSetting 资讯设置
     * @return 结果
     */
    @Override
    public int updateInformationSetting(InformationSetting informationSetting)
    {
        informationSetting.setUpdateTime(DateUtils.getNowDate());
        return informationSettingMapper.updateInformationSetting(informationSetting);
    }

    /**
     * 删除资讯设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInformationSettingByIds(String ids)
    {
        return informationSettingMapper.deleteInformationSettingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资讯设置信息
     * 
     * @param informationSettingId 资讯设置ID
     * @return 结果
     */
    @Override
    public int deleteInformationSettingById(Long informationSettingId)
    {
        return informationSettingMapper.deleteInformationSettingById(informationSettingId);
    }
}
