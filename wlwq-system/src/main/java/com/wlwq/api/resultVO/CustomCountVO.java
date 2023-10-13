package com.wlwq.api.resultVO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomCountVO {
    //本月新增客户
    private BigDecimal newCustom;
    //本月成交
    private BigDecimal newDeal;
    //累计客户
    private BigDecimal allCustom;
    //累计成交
    private BigDecimal allDeal;
}
