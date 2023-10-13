package com.wlwq.params.order;

import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 订单参数对象
 *
 * @author jxh
 * @date 2020-11-23
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsOrderParams extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @NotBlank(message = "商品ID为空")
    private String goodsId;
    /**
     * appId
     */
    @NotBlank(message = "AppID不能为空")
    private String appId;

    /**
     * 支付方式（APP=微信,alipay_app=支付宝,JSAPI=微信小程序支付,free=免费支付 platform=平台支付 millet_bean=谷豆支付 apple_pay=苹果支付）
     */
    @NotBlank(message = "支付方式不能为空")
    private String payType;

    /**
     * 商品类型（0：资源文件）
     */
    @NotNull(message = "商品类型为空")
    private Integer orderType;

    /**
     * 购买数量
     */
    @NotNull(message = "购买数量为空")
    @Min(1)
    private Integer payNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商家名称
     */
    private String businessStoreName;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 父订单号
     */
    private String parentOrderSn;

    /**
     * 是否支付（0：否 1：是）
     */
    private String payStatus;

    /**
     * 订单状态（0：待付款 1：待发货 2：待收货 3：已完成  4：退款/售后  5：已取消 6：已关闭）
     */
    private String orderStatus;

    /**
     * 关键字搜索
     */
    private String keyword;
}