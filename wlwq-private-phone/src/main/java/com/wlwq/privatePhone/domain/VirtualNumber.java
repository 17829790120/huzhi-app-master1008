package com.wlwq.privatePhone.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 虚拟号码对象 virtual_number
 *
 * @author Rick wlwq
 * @date 2021-04-01
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VirtualNumber extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 虚拟号码ID
     */
    private Long virtualNumberId;

    /**
     * 虚拟号码
     */
    @Excel(name = "虚拟号码")
    private String virtualNumber;

    /**
     * 真实号码
     */
    @Excel(name = "真实号码")
    private String realNumber;

    /**
     * 华为云绑定ID
     */
    @Excel(name = "华为云绑定ID")
    private String subId;

    /**
     * 状态（0空闲 1绑定）
     */
    @Excel(name = "状态", readConverterExp = "0=空闲,1=绑定")
    private Integer status;
}
