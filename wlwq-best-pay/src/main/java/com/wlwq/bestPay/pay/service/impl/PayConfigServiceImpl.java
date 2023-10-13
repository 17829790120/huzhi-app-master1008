package com.wlwq.bestPay.pay.service.impl;

import com.wlwq.bestPay.pay.domain.PayConfig;
import com.wlwq.bestPay.pay.mapper.PayConfigMapper;
import com.wlwq.bestPay.pay.service.IPayConfigService;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付配置2.0版Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-07-02
 */
@Service
public class PayConfigServiceImpl implements IPayConfigService
{
    @Autowired
    private PayConfigMapper payConfigMapper;

    /**
     * 查询支付配置2.0版
     * 
     * @param payConfigId 支付配置2.0版ID
     * @return 支付配置2.0版
     */
    @Override
    public PayConfig selectPayConfigById(String payConfigId)
    {
        return payConfigMapper.selectPayConfigById(payConfigId);
    }

    /**
     * 查询支付配置2.0版列表
     * 
     * @param payConfig 支付配置2.0版
     * @return 支付配置2.0版
     */
    @Override
    public List<PayConfig> selectPayConfigList(PayConfig payConfig)
    {
        return payConfigMapper.selectPayConfigList(payConfig);
    }

    /**
     * 新增支付配置2.0版
     * 
     * @param payConfig 支付配置2.0版
     * @return 结果
     */
    @Override
    public int insertPayConfig(PayConfig payConfig)
    {
        payConfig.setCreateTime(DateUtils.getNowDate());
        return payConfigMapper.insertPayConfig(payConfig);
    }

    /**
     * 修改支付配置2.0版
     * 
     * @param payConfig 支付配置2.0版
     * @return 结果
     */
    @Override
    public int updatePayConfig(PayConfig payConfig)
    {
        payConfig.setUpdateTime(DateUtils.getNowDate());
        return payConfigMapper.updatePayConfig(payConfig);
    }

    /**
     * 删除支付配置2.0版对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePayConfigByIds(String ids)
    {
        return payConfigMapper.deletePayConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除支付配置2.0版信息
     * 
     * @param payConfigId 支付配置2.0版ID
     * @return 结果
     */
    @Override
    public int deletePayConfigById(String payConfigId)
    {
        return payConfigMapper.deletePayConfigById(payConfigId);
    }

    /**
     * 根据APP_ID获取支付配置信息
     * @param appId
     * @return
     */
    @Override
    public PayConfig selectPayConfigByAppId(String appId) {
        return payConfigMapper.selectPayConfigByAppId(appId);
    }
}
