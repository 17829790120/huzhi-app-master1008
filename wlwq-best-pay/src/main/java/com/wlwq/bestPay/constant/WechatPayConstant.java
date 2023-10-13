package com.wlwq.bestPay.constant;

/**
 * @ClassName WechatPayConstant
 * @Description 微信支付使用的常量
 * @Date 2020/12/3 15:31
 * @Author Rick wlwq
 */
public class WechatPayConstant {

    /**
     * 表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等
     */
    public final static String EVENT_TYPE_SUCCESS = "TRANSACTION.SUCCESS";

    /**
     * 微信查询支付订单->状态为正常
     */
    public final static String QUERY_ORDER_STATUS_IS_SUCCESS = "SUCCESS";

    /**
     * 微信退款状态->状态为成功
     */
    public final static String REFUND_ORDER_STATUS_IS_SUCCESS = "SUCCESS";

    /**
     * 微信退款状态->状态为关闭
     */
    public final static String REFUND_ORDER_STATUS_IS_CLOSED = "CLOSED";

    /**
     * 微信退款状态->退款处理中
     */
    public static final String REFUND_ORDER_STATUS_IS_PROCESSING = "PROCESSING";
}
