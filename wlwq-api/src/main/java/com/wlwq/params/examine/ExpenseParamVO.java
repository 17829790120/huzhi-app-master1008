package com.wlwq.params.examine;

import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 报销提交相关信息
 * @author gaoce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseParamVO implements Serializable {

    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 总报销金额(元)
     */
    @NotNull(message = "请传入总报销金额")
    private BigDecimal totalMoney;

    /**
     * 报销明细列表
     */
    @NotNull(message = "请传入报销明细")
    private List<ExpenseDetailParamVO> detailParamVOList;
}
