package com.wlwq.bestPay.params;

import com.wlwq.bestPay.enums.BestPayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付时请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayRequest implements Serializable {

    /**
     * 支付配置的APP_ID.
     */
    private String appId;

    /**
     * 支付方式.
     */
    private BestPayTypeEnum payTypeEnum;

    /**
     * 订单超时时间（分钟）.
     */
    private Integer timeOutValue;

    /**
     * 订单号.
     */
    private String orderId;

    /**
     * 订单金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单名字.
     */
    private String orderName;

    /**
     * 微信openid, 仅微信公众号/小程序支付时需要
     */
    private String openid;

    /**
     * 客户端访问Ip  外部H5支付时必传，需要真实Ip
     * 微信h5支付已不需要真实的ip
     */
    private String spbillCreateIp;

    /**
     * 附加内容，发起支付时传入（之前使用的交易模块）
     */
    private String moduleName;


    /**
     * 苹果支付价格编码ID.
     */
    private String appleBundleId;
}
