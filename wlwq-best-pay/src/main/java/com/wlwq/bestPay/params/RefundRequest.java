package com.wlwq.bestPay.params;

import com.wlwq.bestPay.enums.BestPayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName RefundRequest
 * @Description 退款参数封装
 * @Date 2021/7/6 11:09
 * @Author Rick wlwq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequest implements Serializable {


    /** APP_ID. */
    @NotNull(message = "APP_ID为空！")
    private String appId;

    /** 支付方式. */
    @NotNull(message = "支付方式为空！")
    private BestPayTypeEnum payTypeEnum;

    /** 订单号. */
    @NotBlank(message = "订单号为空！")
    private String orderId;

    /** 支付交易订单号（和商户订单号填一个就行）. */
    private String tradeNo;

    /** 退款单号 （由商户生成）. */
    @NotBlank(message = "退款单号为空！")
    private String refundOrderId;

    /** 订单总金额. */
    @NotBlank(message = "订单总金额为空！")
    @DecimalMin(value = "0.01", message = "订单总金额最少为1分！")
    private BigDecimal totalAmount;

    /** 退款金额. */
    @NotNull(message = "退款金额为空！")
    @DecimalMin(value = "0.01", message = "退款金额最少为1分！")
    private BigDecimal refundAmount;

    /** 退款原因. */
    @NotBlank(message = "退款原因为空！")
    private String refundReason;

    /** 操作员ID. */
    @NotBlank(message = "操作员ID！")
    private String opUserId;

}
