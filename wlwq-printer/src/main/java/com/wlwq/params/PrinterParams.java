package com.wlwq.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PrinterParams
 * @Description 打印内容参数封装
 * @Date 2021/1/16 16:16
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PrinterParams implements Serializable {

    /** 打印机编号. */
    private String printerSn;

    /** 打印数量. */
    private Integer copies;

    /** 打印标题. */
    private String title;

    /** 总金额. */
    private String totalPrice;

    /** 优惠金额. */
    private String discountPrice;

    /** 实付金额. */
    private String realPrice;

    /** 客户地址. */
    private String accountAddress;

    /** 客户电话. */
    private String accountPhone;

    /** 客户名字. */
    private String accountName;

    /** 下单时间. */
    private String orderTime;

    /** 客户备注. */
    private String accountRemark;

    /** 商品明细. */
    private List<Product> productList;
}
