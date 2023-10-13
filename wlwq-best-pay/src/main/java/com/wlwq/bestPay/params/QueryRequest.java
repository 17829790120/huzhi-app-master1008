package com.wlwq.bestPay.params;

import com.wlwq.bestPay.enums.BestPayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName QueryRequest
 * @Description 查询订单参数封装
 * @Date 2021/7/6 14:52
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QueryRequest implements Serializable {

    /** APP_ID. */
    @NotNull(message = "APP_ID为空！")
    private String appId;

    /** 支付方式. */
    @NotNull(message = "支付方式为空！")
    private BestPayTypeEnum payTypeEnum;

    /** 商户订单号 （和交易订单号选填一个）. */
    private String orderId;

    /** 交易订单号. */
    private String tradeNo;

}
