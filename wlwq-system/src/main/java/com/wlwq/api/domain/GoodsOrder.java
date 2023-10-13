package com.wlwq.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 订单信息对象 goods_order
 * 
 * @author wwb
 * @date 2023-05-25
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("GoodsOrder")
public class GoodsOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderSn;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 收货人
     */
    @Excel(name = "收货人")
    private String consigneeName;

    /**
     * 收货人手机号
     */
    @Excel(name = "收货人手机号")
    private String consigneePhone;

    /**
     * 商品金额
     */
    @Excel(name = "商品金额")
    private BigDecimal totalPrice;

    /**
     * 实付金额
     */
    @Excel(name = "实付金额")
    private BigDecimal payPrice;

    /**
     * 订单状态（0：待付款 1：待发货 2：待收货 3：已完成  4：退款/售后  5：已取消 6：已关闭）
     */
    @Excel(name = "订单状态", readConverterExp = "0=：待付款,1=：待发货,2=：待收货,3=：已完成,4=：退款/售后,5=：已取消,6=：已关闭")
    private String orderStatus;

    /**
     * 支付方式（APP=微信,alipay_app=支付宝,JSAPI=微信小程序支付,free=免费支付 platform=平台支付 apple_pay=苹果支付）
     */
    @Excel(name = "支付方式", readConverterExp = "A=PP=微信,alipay_app=支付宝,JSAPI=微信小程序支付,free=免费支付,platform=平台支付,apple_pay=苹果支付")
    private String payType;

    /**
     * 是否支付（0：否 1：是）
     */
    @Excel(name = "是否支付", readConverterExp = "0=：否,1=：是")
    private Integer payStatus;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /**
     * 支付流水号
     */
    @Excel(name = "支付流水号")
    private String tradeNo;

    /**
     * AppId
     */
    @Excel(name = "AppId")
    private String appId;

    /**
     * 模块名称
     */
    @Excel(name = "模块名称")
    private String moduleName;

    /**
     * 取消原因
     */
    @Excel(name = "取消原因")
    private String cancelReason;

    /**
     * 取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "取消时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cancelTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /**
     * 是否删除(0：否 1：是）
     */
    @Excel(name = "是否删除(0：否 1：是）")
    private Integer delStatus;

    /**
     * 所得积分
     */
    @Excel(name = "所得积分")
    private BigDecimal integral;

    /**
     * 0：资源文件
     */
    @Excel(name = "0：资源文件")
    private Integer orderType;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String headPortrait;

    /**
     * 商品id
     */
    @Excel(name = "商品id")
    private String goodsId;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String goodsName;

    /**
     * 商品价格
     */
    @Excel(name = "商品价格")
    private BigDecimal goodsPrice;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;


    /**
     * 资讯文件
     */
    @Excel(name = "资讯文件")
    private String informationPostFile;

    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    private Double fileSize;

    /**
     * 封面图片
     */
    @Excel(name = "封面图片")
    private String informationPostImages;

}
