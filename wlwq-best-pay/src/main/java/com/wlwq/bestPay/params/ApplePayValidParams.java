package com.wlwq.bestPay.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName ApplePayValidParams
 * @Description 苹果支付验证参数封装
 * @Date 2021/1/28 20:09
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApplePayValidParams implements Serializable {

    /** 苹果支付流水单号. */
    @NotBlank(message = "苹果支付流水单号为空！")
    private String transactionId;

    /** 苹果支付完后负载信息. */
    @NotBlank(message = "苹果支付完后负载的信息为空！")
    private String payload;

}
