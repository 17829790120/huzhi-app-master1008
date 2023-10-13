package com.wlwq.setting.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.setting.domain.SettingFeedbackType;
import com.wlwq.setting.mapper.SettingFeedbackTypeMapper;
import com.wlwq.setting.service.ISettingFeedbackTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 意见反馈类型Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Service
public class SettingFeedbackTypeServiceImpl implements ISettingFeedbackTypeService 
{
    @Autowired
    private SettingFeedbackTypeMapper settingFeedbackTypeMapper;

    /**
     * 查询意见反馈类型
     * 
     * @param feedbackTypeId 意见反馈类型ID
     * @return 意见反馈类型
     */
    @Override
    public SettingFeedbackType selectSettingFeedbackTypeById(Long feedbackTypeId)
    {
        return settingFeedbackTypeMapper.selectSettingFeedbackTypeById(feedbackTypeId);
    }

    /**
     * 查询意见反馈类型列表
     * 
     * @param settingFeedbackType 意见反馈类型
     * @return 意见反馈类型
     */
    @Override
    public List<SettingFeedbackType> selectSettingFeedbackTypeList(SettingFeedbackType settingFeedbackType)
    {
        return settingFeedbackTypeMapper.selectSettingFeedbackTypeList(settingFeedbackType);
    }

    /**
     * 新增意见反馈类型
     * 
     * @param settingFeedbackType 意见反馈类型
     * @return 结果
     */
    @Override
    public int insertSettingFeedbackType(SettingFeedbackType settingFeedbackType)
    {
        settingFeedbackType.setCreateTime(DateUtils.getNowDate());
        return settingFeedbackTypeMapper.insertSettingFeedbackType(settingFeedbackType);
    }

    /**
     * 修改意见反馈类型
     * 
     * @param settingFeedbackType 意见反馈类型
     * @return 结果
     */
    @Override
    public int updateSettingFeedbackType(SettingFeedbackType settingFeedbackType)
    {
        settingFeedbackType.setUpdateTime(DateUtils.getNowDate());
        return settingFeedbackTypeMapper.updateSettingFeedbackType(settingFeedbackType);
    }

    /**
     * 删除意见反馈类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSettingFeedbackTypeByIds(String ids)
    {
        return settingFeedbackTypeMapper.deleteSettingFeedbackTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除意见反馈类型信息
     * 
     * @param feedbackTypeId 意见反馈类型ID
     * @return 结果
     */
    @Override
    public int deleteSettingFeedbackTypeById(Long feedbackTypeId)
    {
        return settingFeedbackTypeMapper.deleteSettingFeedbackTypeById(feedbackTypeId);
    }

    /**
     * 意见反馈类型
     * @return
     */
    @Override
    public List<String> selectTypeList() {
        return settingFeedbackTypeMapper.selectTypeList();
    }
}
