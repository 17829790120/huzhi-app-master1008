package com.wlwq.bestPay.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName RefundResult
 * @Description 退款返回值封装
 * @Date 2021/7/6 17:44
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RefundResult implements Serializable {

    /** 退款返回状态码. */
    private String code;

    /** 退款返回信息. */
    private String message;

}
