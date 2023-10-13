package com.wlwq.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PrinterPlayVoiceParams
 * @Description 打印机播报消息参数
 * @Date 2021/1/16 15:56
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PrinterPlayVoiceParams implements Serializable {

    /** 打印机编号. */
    private String printerSn;

    /** 支付方式  支付宝 41、微信 42、云支付 43、银联刷卡 44、银联支付 45、会员卡消费 46、会员卡充值 47、翼支付 48、成功收款 49、嘉联支付 50、壹钱包 51、京东支付 52、快钱支付 53、威支付 54、享钱支付 55. */
    private Integer payType;

    /** 播报类型 退款 59 到账 60 消费 61. */
    private Integer payMode;

    /** 支付金额：最多允许保留2位小数. */
    private Double money;
}
