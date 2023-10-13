package com.wlwq.bestPay.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName NotifyResult
 * @Description 支付回调成功返回值封装
 * @Date 2021/1/27 14:38
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotifyResult implements Serializable {

    /** 商户订单号. */
    private String orderId;

    /** 支付宝交易号. */
    private String tradeNo;

    /** 付款金额. */
    private BigDecimal totalAmount;

    /** 购买者购买IP. */
    private String buyerLogonId;

    /** 模块名字（例如课程叫course商品就叫goods）. */
    private String moduleName;

}
