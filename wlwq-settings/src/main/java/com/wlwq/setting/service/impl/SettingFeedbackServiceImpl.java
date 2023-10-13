package com.wlwq.setting.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.setting.domain.SettingFeedback;
import com.wlwq.setting.mapper.SettingFeedbackMapper;
import com.wlwq.setting.service.ISettingFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 意见反馈Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Service
public class SettingFeedbackServiceImpl implements ISettingFeedbackService 
{
    @Autowired
    private SettingFeedbackMapper settingFeedbackMapper;

    /**
     * 查询意见反馈
     * 
     * @param feedbackId 意见反馈ID
     * @return 意见反馈
     */
    @Override
    public SettingFeedback selectSettingFeedbackById(Long feedbackId)
    {
        return settingFeedbackMapper.selectSettingFeedbackById(feedbackId);
    }

    /**
     * 查询意见反馈列表
     * 
     * @param settingFeedback 意见反馈
     * @return 意见反馈
     */
    @Override
    public List<SettingFeedback> selectSettingFeedbackList(SettingFeedback settingFeedback)
    {
        return settingFeedbackMapper.selectSettingFeedbackList(settingFeedback);
    }

    /**
     * 新增意见反馈
     * 
     * @param settingFeedback 意见反馈
     * @return 结果
     */
    @Override
    public int insertSettingFeedback(SettingFeedback settingFeedback)
    {
        settingFeedback.setCreateTime(DateUtils.getNowDate());
        return settingFeedbackMapper.insertSettingFeedback(settingFeedback);
    }

    /**
     * 修改意见反馈
     * 
     * @param settingFeedback 意见反馈
     * @return 结果
     */
    @Override
    public int updateSettingFeedback(SettingFeedback settingFeedback)
    {
        settingFeedback.setUpdateTime(DateUtils.getNowDate());
        return settingFeedbackMapper.updateSettingFeedback(settingFeedback);
    }

    /**
     * 删除意见反馈对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSettingFeedbackByIds(String ids)
    {
        return settingFeedbackMapper.deleteSettingFeedbackByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除意见反馈信息
     * 
     * @param feedbackId 意见反馈ID
     * @return 结果
     */
    @Override
    public int deleteSettingFeedbackById(Long feedbackId)
    {
        return settingFeedbackMapper.deleteSettingFeedbackById(feedbackId);
    }
}
