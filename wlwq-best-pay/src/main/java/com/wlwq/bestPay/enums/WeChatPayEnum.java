package com.wlwq.bestPay.enums;

import lombok.Getter;

/**
 * @author Rick wlwq
 * @Description 微信支付使用的枚举
 * @Date 2021/7/1 16:41
 */
@Getter
public enum WeChatPayEnum {
    /**
     *
     */
    CURRENCY("CNY"),
    ;

    /**
     * 描述 微信退款后有内容
     */
    private String desc;

    WeChatPayEnum(String desc) {
        this.desc = desc;
    }

    public static WeChatPayEnum findByName(String name) {
        for (WeChatPayEnum weChatPayEnum : WeChatPayEnum.values()) {
            if (name.toLowerCase().equals(weChatPayEnum.name().toLowerCase())) {
                return weChatPayEnum;
            }
        }
        throw new RuntimeException("错误的微信支付枚举");
    }
}
