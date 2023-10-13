package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 合同相对方信息对象 examine_contract_counterparty
 *
 * @author gaoce
 * @date 2023-05-11
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineContractCounterparty extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 合同相对方信息ID
     */
    private String examineContractCounterpartyId;

    /**
     * 审批发起者ID
     */
    @Excel(name = "审批发起者ID")
    private String examineInitiateId;

    /**
     * 同批次生成的标识
     */
    @Excel(name = "同批次生成的标识")
    private String counterpartyTag;

    /**
     * 1:我方主体 2:对方主体
     */
    @Excel(name = "1:我方主体 2:对方主体")
    private Long counterpartyType;

    /**
     * 主体名称
     */
    @Excel(name = "主体名称")
    private String counterpartyName;

    /**
     * 负责人名称
     */
    @Excel(name = "负责人名称")
    private String principalName;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

}
