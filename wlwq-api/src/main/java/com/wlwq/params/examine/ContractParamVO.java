package com.wlwq.params.examine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.api.domain.ExamineContractCounterparty;
import com.wlwq.api.domain.ExamineFile;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gaoce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractParamVO implements Serializable {

    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 合同正文
     */
    @NotBlank(message = "请传入合同正文")
    private String contractText;

    /**
     * 合同名称
     */
    @NotBlank(message = "请传入合同名称")
    private String contractName;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 合同金额类型
     */
    @NotBlank(message = "请传入合同金额类型")
    private String contractMoneyType;

    /**
     * 合同总额(元)
     */
    private BigDecimal totalMoney;

    /**
     * 合同期限类型
     */
    @NotBlank(message = "请传入合同期限类型")
    private String contractDeadlineType;

    /**
     * 合同说明
     */
    private String reason;

    /**
     * 文件列表
     */
    private List<ExamineFile> fileList;

    /**
     * 相对方信息
     */
    @NotNull(message = "请传入相对方信息")
    private List<ExamineContractCounterparty> counterpartyList;

    /**
     * 合同签署日期 年月日
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date signatureDate;

    /**
     * 合同印章类型
     */
    private String sealType;

}
