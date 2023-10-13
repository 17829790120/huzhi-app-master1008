package com.wlwq.bestPay.enums;

import lombok.Getter;

/**
 * @author Rick wlwq
 * @Description 支付宝退款状态
 * @Date 2021/7/1 16:45
 */
@Getter
public enum AlipayQueryStatusEnum {

    /**
     *
     */
    WAIT_BUYER_PAY("交易创建，等待买家付款"),
    TRADE_CLOSED("未付款交易超时关闭，或支付完成后全额退款"),
    TRADE_SUCCESS("交易支付成功"),
    TRADE_FINISHED("交易结束，不可退款"),
    ;

    /**
     * 描述
     */
    private String desc;

    AlipayQueryStatusEnum(String desc) {
        this.desc = desc;
    }

    public static AlipayQueryStatusEnum findByName(String name) {
        for (AlipayQueryStatusEnum alipayQueryStatusEnum : AlipayQueryStatusEnum.values()) {
            if (name.toLowerCase().equals(alipayQueryStatusEnum.name().toLowerCase())) {
                return alipayQueryStatusEnum;
            }
        }
        throw new RuntimeException("错误的支付宝退款状态");
    }
}
