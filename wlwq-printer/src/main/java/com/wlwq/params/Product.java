package com.wlwq.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Product
 * @Description 商品参数封装
 * @Date 2021/1/16 16:13
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product implements Serializable {

    /** 商品名字. */
    private String productName;

    /** 商品数量. */
    private Integer productNumber;

    /** 商品价格. */
    private Double productPrice;

}
