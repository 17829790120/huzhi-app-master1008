package com.wlwq.params.examine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.api.domain.ExamineFile;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gaoce
 * 报销提交详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDetailParamVO implements Serializable{


    /**
     * 报销类型
     */
    @NotBlank(message = "请传入报销类型")
    private String expenseType;

    /**
     * 报销金额(元)
     */
    @NotNull(message = "请传入报销金额")
    private BigDecimal reimburseMoney;

    /**
     * 费用发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "请传入费用发生时间")
    private Date moneyDate;

    /**
     * 报销事由
     */
    @NotBlank(message = "请传入报销事由")
    private String reason;

    /**
     * 图片,多个以逗号隔开
     */
    private String pics;

    /**
     * 文件列表
     */
    private List<ExamineFile> fileList;
}
