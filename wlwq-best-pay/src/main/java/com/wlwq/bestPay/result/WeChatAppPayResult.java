package com.wlwq.bestPay.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName WeChatAppPayResult
 * @Description 微信App支付返回值
 * @Date 2021/7/5 17:18
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WeChatAppPayResult implements Serializable {

    /** 应用id. */
    private String appId;

    /** 商户号. */
    private String partnerId;

    /** 预支付交易会话ID. */
    private String prepayId;

    /** 订单详情扩展字符串. */
    private String packageValue;

    /** 随机字符串. */
    private String nonceStr;

    /** 时间戳. */
    private String timestamp;

    /** 签名(签名，使用字段appId、timeStamp、nonceStr、partnerId计算得出的签名值 注意：取值RSA格式). */
    private String sign;


    // -----------------小程序支付需要------------------

    private String signType;

    private String timeStamp;

    private String paySign;
}
