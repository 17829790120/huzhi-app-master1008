package com.wlwq.setting.mapper;

import com.wlwq.setting.domain.SettingFeedbackType;

import java.util.List;

/**
 * 意见反馈类型Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
public interface SettingFeedbackTypeMapper 
{
    /**
     * 查询意见反馈类型
     * 
     * @param feedbackTypeId 意见反馈类型ID
     * @return 意见反馈类型
     */
    public SettingFeedbackType selectSettingFeedbackTypeById(Long feedbackTypeId);

    /**
     * 查询意见反馈类型列表
     * 
     * @param settingFeedbackType 意见反馈类型
     * @return 意见反馈类型集合
     */
    public List<SettingFeedbackType> selectSettingFeedbackTypeList(SettingFeedbackType settingFeedbackType);

    /**
     * 新增意见反馈类型
     * 
     * @param settingFeedbackType 意见反馈类型
     * @return 结果
     */
    public int insertSettingFeedbackType(SettingFeedbackType settingFeedbackType);

    /**
     * 修改意见反馈类型
     * 
     * @param settingFeedbackType 意见反馈类型
     * @return 结果
     */
    public int updateSettingFeedbackType(SettingFeedbackType settingFeedbackType);

    /**
     * 删除意见反馈类型
     * 
     * @param feedbackTypeId 意见反馈类型ID
     * @return 结果
     */
    public int deleteSettingFeedbackTypeById(Long feedbackTypeId);

    /**
     * 批量删除意见反馈类型
     * 
     * @param feedbackTypeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSettingFeedbackTypeByIds(String[] feedbackTypeIds);

    /**
     * 意见反馈类型
     * @return
     */
    List<String> selectTypeList();
}
