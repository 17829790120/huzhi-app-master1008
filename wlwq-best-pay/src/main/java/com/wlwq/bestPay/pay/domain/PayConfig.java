package com.wlwq.bestPay.pay.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 支付配置2.0版对象 pay_config
 * 
 * @author Rick wlwq
 * @date 2021-07-02
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付配置ID */
    private String payConfigId;

    /** 应用名字 */
    @Excel(name = "应用名字")
    private String applicationName;

    /** 支付平台（alipay支付宝，wx微信） */
    @Excel(name = "支付平台", readConverterExp = "alipay支付宝，wx微信")
    private String payPlatform;

    /** 支付方式（alipay_app=支付宝APP支付，alipay_pc=支付宝pc支付，alipay_wap=支付宝wap支付，JSAPI=微信公众号小程序支付，MWEB=微信H5支付，NATIVE=微信Native支付，APP=微信APP支付） */
    @Excel(name = "支付方式", readConverterExp = "alipay_app=支付宝APP支付，alipay_pc=支付宝pc支付，alipay_wap=支付宝wap支付，JSAPI=微信公众号小程序支付，MWEB=微信H5支付，NATIVE=微信Native支付，APP=微信APP支付")
    private String payType;

    /** APP_ID */
    @Excel(name = "APP_ID")
    private String appId;

    /** APP_SECRET */
    @Excel(name = "APP_SECRET")
    private String appSecret;

    /** 支付宝应用私钥 */
    @Excel(name = "支付宝应用私钥")
    private String privateKey;

    /** 支付回调地址 */
    @Excel(name = "支付回调地址")
    private String notifyUrl;

    /** 支付宝应用公钥证书路径（appCertPublicKey_2021.crt） */
    @Excel(name = "支付宝应用公钥证书路径")
    private String certPath;

    /** 支付宝公钥证书路径（alipayCertPublicKey_RSA2.crt） */
    @Excel(name = "支付宝公钥证书路径")
    private String publicCertPath;

    /** 支付宝根证书路径（alipayRootCert.crt） */
    @Excel(name = "支付宝根证书路径")
    private String rootCertPath;

    /** 微信支付商户号 */
    @Excel(name = "微信支付商户号")
    private String mchId;

    /** 微信支付商户秘钥 */
    @Excel(name = "微信支付商户秘钥")
    private String mchKey;

    /** 服务商模式下的子商户公众账号ID */
    @Excel(name = "服务商模式下的子商户公众账号ID")
    private String subAppId;

    /** 服务商模式下的子商户号 */
    @Excel(name = "服务商模式下的子商户号")
    private String subMchId;

    /** 微信支付证书的位置 */
    @Excel(name = "微信支付证书的位置")
    private String keyPath;

    /** 微信支付apiclient_key.pem证书文件（暂时不用） */
    @Excel(name = "微信支付apiclient_key.pem证书文件")
    private String apiclientKey;

    /** 微信支付apiclient_cert.pem证书文件（暂时不用） */
    @Excel(name = "微信支付apiclient_cert.pem证书文件")
    private String apiclientCert;

    /** apiV3秘钥值（暂时不用） */
    @Excel(name = "apiV3秘钥值", readConverterExp = "暂时不用")
    private String apiV3Key;

    /** apiV3证书序列号值（暂时不用） */
    @Excel(name = "apiV3证书序列号值")
    private String certSerialNo;

    /** 是否启用（1启用 0关闭） */
    @Excel(name = "是否启用")
    private Integer startStatus;
}
