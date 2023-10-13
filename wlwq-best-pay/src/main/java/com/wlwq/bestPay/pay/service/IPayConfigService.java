package com.wlwq.bestPay.pay.service;

import com.wlwq.bestPay.pay.domain.PayConfig;

import java.util.List;

/**
 * 支付配置2.0版Service接口
 * 
 * @author Rick wlwq
 * @date 2021-07-02
 */
public interface IPayConfigService 
{
    /**
     * 查询支付配置2.0版
     * 
     * @param payConfigId 支付配置2.0版ID
     * @return 支付配置2.0版
     */
    public PayConfig selectPayConfigById(String payConfigId);

    /**
     * 查询支付配置2.0版列表
     * 
     * @param payConfig 支付配置2.0版
     * @return 支付配置2.0版集合
     */
    public List<PayConfig> selectPayConfigList(PayConfig payConfig);

    /**
     * 新增支付配置2.0版
     * 
     * @param payConfig 支付配置2.0版
     * @return 结果
     */
    public int insertPayConfig(PayConfig payConfig);

    /**
     * 修改支付配置2.0版
     * 
     * @param payConfig 支付配置2.0版
     * @return 结果
     */
    public int updatePayConfig(PayConfig payConfig);

    /**
     * 批量删除支付配置2.0版
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePayConfigByIds(String ids);

    /**
     * 删除支付配置2.0版信息
     * 
     * @param payConfigId 支付配置2.0版ID
     * @return 结果
     */
    public int deletePayConfigById(String payConfigId);

    /**
     * 根据APP_ID获取支付配置信息
     * @param appId
     * @return
     */
    PayConfig selectPayConfigByAppId(String appId);
}
