package com.wlwq.setting.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.setting.domain.SettingAgreement;
import com.wlwq.setting.mapper.SettingAgreementMapper;
import com.wlwq.setting.service.ISettingAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 协议Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Service
public class SettingAgreementServiceImpl implements ISettingAgreementService 
{
    @Autowired
    private SettingAgreementMapper settingAgreementMapper;

    /**
     * 查询协议
     * 
     * @param agreementId 协议ID
     * @return 协议
     */
    @Override
    public SettingAgreement selectSettingAgreementById(String agreementId)
    {
        return settingAgreementMapper.selectSettingAgreementById(agreementId);
    }

    /**
     * 查询协议列表
     * 
     * @param settingAgreement 协议
     * @return 协议
     */
    @Override
    public List<SettingAgreement> selectSettingAgreementList(SettingAgreement settingAgreement)
    {
        return settingAgreementMapper.selectSettingAgreementList(settingAgreement);
    }

    /**
     * 新增协议
     * 
     * @param settingAgreement 协议
     * @return 结果
     */
    @Override
    public int insertSettingAgreement(SettingAgreement settingAgreement)
    {
        settingAgreement.setCreateTime(DateUtils.getNowDate());
        return settingAgreementMapper.insertSettingAgreement(settingAgreement);
    }

    /**
     * 修改协议
     * 
     * @param settingAgreement 协议
     * @return 结果
     */
    @Override
    public int updateSettingAgreement(SettingAgreement settingAgreement)
    {
        settingAgreement.setUpdateTime(DateUtils.getNowDate());
        return settingAgreementMapper.updateSettingAgreement(settingAgreement);
    }

    /**
     * 删除协议对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSettingAgreementByIds(String ids)
    {
        return settingAgreementMapper.deleteSettingAgreementByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除协议信息
     * 
     * @param agreementId 协议ID
     * @return 结果
     */
    @Override
    public int deleteSettingAgreementById(String agreementId)
    {
        return settingAgreementMapper.deleteSettingAgreementById(agreementId);
    }

    /**
     * 根据ID获取协议内容
     * @param agreementId
     * @return
     */
    @Override
    public String selectAgreementContentById(String agreementId) {
        return settingAgreementMapper.selectAgreementContentById(agreementId);
    }
}
