package com.wlwq.bestPay.enums;

import static com.wlwq.bestPay.enums.BestPayPlatformEnum.*;
import static com.wlwq.bestPay.enums.BestPayPlatformEnum.FREE;

/**
 * @author Rick wlwq
 * @Description 支付方式
 * @Date 2021/7/1 16:41
 */
public enum BestPayTypeEnum {

    /**
     * 支付宝app
     */
    ALIPAY_APP("alipay_app", ALIPAY, "支付宝app"),

    ALIPAY_PC("alipay_pc", ALIPAY, "支付宝pc"),

    ALIPAY_WAP("alipay_wap", ALIPAY, "支付宝wap"),

//    WXPAY_MP("JSAPI", WX, "微信公众账号支付"),

    WXPAY_MWEB("MWEB", WX, "微信H5支付"),

    WXPAY_NATIVE("NATIVE", WX, "微信Native支付"),

    WXPAY_MINI("JSAPI", WX, "微信小程序支付"),

    SYSTEM_PAY("system_pay",SYSTEM, "后台支付"),

    FREE_PAY("free",FREE, "免费支付"),

    WXPAY_APP("APP", WX, "微信APP支付"),
    ;

    private String code;

    private BestPayPlatformEnum platform;

    private String desc;

    BestPayTypeEnum(String code, BestPayPlatformEnum platform, String desc) {
        this.code = code;
        this.platform = platform;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public BestPayPlatformEnum getPlatform() {
        return platform;
    }

    public String getDesc() {
        return desc;
    }

    public static BestPayTypeEnum getByName(String code) {
        for (BestPayTypeEnum bestPayTypeEnum : BestPayTypeEnum.values()) {
            if (bestPayTypeEnum.name().equalsIgnoreCase(code)) {
                return bestPayTypeEnum;
            }
        }
        throw new RuntimeException("BestPayTypeEnum执行getByName异常！错误的支付方式");
    }

    /**
     * 根据code查询支付类型枚举
     *
     * @param payType 支付类型
     * @return 类型枚举
     */
    public static BestPayTypeEnum getByCode(String payType) {
        for (BestPayTypeEnum payTypeEnum : values()) {
            if (payTypeEnum.code.equals(payType)) {
                return payTypeEnum;
            }
        }
        throw new RuntimeException("错误的支付方式");
    }
}
