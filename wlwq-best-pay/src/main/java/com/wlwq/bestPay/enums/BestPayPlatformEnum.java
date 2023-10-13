package com.wlwq.bestPay.enums;

/**
 * @author Rick wlwq
 * @Description 支付平台
 * @Date 2021/7/1 16:43
 */
public enum BestPayPlatformEnum {

    /**
     *
     */
    ALIPAY("alipay", "支付宝"),

    WX("wx", "微信"),

    SYSTEM("systemPay", "后台支付"),

    FREE("freePay", "免费支付"),
    ;

    private String code;

    private String name;

    BestPayPlatformEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
