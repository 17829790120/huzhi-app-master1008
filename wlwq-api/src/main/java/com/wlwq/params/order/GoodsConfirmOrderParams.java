package com.wlwq.params.order;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author gaoce
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsConfirmOrderParams implements Serializable {

    /**
     * 商品ID
     */
    @NotBlank(message = "商品ID为空")
    private String goodsId;

    /**
     * 商品类型（0：资源文件）
     */
    @NotNull(message = "商品类型为空")
    private Integer orderType;
}
