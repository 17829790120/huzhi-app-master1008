package com.wlwq.bestPay.constant;

/**
 * @ClassName BestPayConstant
 * @Description 通用常量类
 * @Date 2021/7/4 13:35
 * @Author Rick wlwq
 */
public class BestPayConstant {

    /**
     * 微信支付回调API
     */
    public final static String NOTIFY_API_URL_WECHAT = "/pay/wechatPay/notifyWxPay/";

    /**
     * 支付宝支付回调API
     */
    public final static String NOTIFY_API_URL_ALIPAY = "/pay/alipay/notifyAliPay/";


    /**
     * 退款成功接口返回值
     */
    public final static String REFUND_SUCCESS = "success";

    /**
     * 退款失败接口返回值
     */
    public final static String REFUND_ERROR = "error";
}
