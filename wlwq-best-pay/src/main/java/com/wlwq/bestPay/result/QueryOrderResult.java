package com.wlwq.bestPay.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName QueryOrderResult
 * @Description 查询订单结果返回值封装
 * @Date 2021/2/3 15:13
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QueryOrderResult implements Serializable {

    // 支付宝交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
    // 微信SUCCESS—支付成功,REFUND—转入退款,NOTPAY—未支付,CLOSED—已关闭,REVOKED—已撤销（刷卡支付）,USERPAYING--用户支付中,PAYERROR--支付失败(其他原因，如银行返回失败)

    /** 查询返回的交易状态code 例：WAIT_BUYER_PAY. */
    private String code;

    /** 查询返回的交易状态对应的说明 例：交易创建，等待买家付款. */
    private String msg;

    /** 返回对错 success表示订单正常 error表示订单不正常（若要实现自己的逻辑，可使用code进行判断）. */
    private Boolean data;
}
