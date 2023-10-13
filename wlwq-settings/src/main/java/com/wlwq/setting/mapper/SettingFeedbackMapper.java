package com.wlwq.setting.mapper;

import com.wlwq.setting.domain.SettingFeedback;

import java.util.List;

/**
 * 意见反馈Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
public interface SettingFeedbackMapper 
{
    /**
     * 查询意见反馈
     * 
     * @param feedbackId 意见反馈ID
     * @return 意见反馈
     */
    public SettingFeedback selectSettingFeedbackById(Long feedbackId);

    /**
     * 查询意见反馈列表
     * 
     * @param settingFeedback 意见反馈
     * @return 意见反馈集合
     */
    public List<SettingFeedback> selectSettingFeedbackList(SettingFeedback settingFeedback);

    /**
     * 新增意见反馈
     * 
     * @param settingFeedback 意见反馈
     * @return 结果
     */
    public int insertSettingFeedback(SettingFeedback settingFeedback);

    /**
     * 修改意见反馈
     * 
     * @param settingFeedback 意见反馈
     * @return 结果
     */
    public int updateSettingFeedback(SettingFeedback settingFeedback);

    /**
     * 删除意见反馈
     * 
     * @param feedbackId 意见反馈ID
     * @return 结果
     */
    public int deleteSettingFeedbackById(Long feedbackId);

    /**
     * 批量删除意见反馈
     * 
     * @param feedbackIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSettingFeedbackByIds(String[] feedbackIds);
}
