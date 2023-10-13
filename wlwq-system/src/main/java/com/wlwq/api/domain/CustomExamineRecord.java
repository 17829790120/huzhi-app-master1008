package com.wlwq.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 客户成交对象 custom_examine_record
 * 
 * @author wlwq
 * @date 2023-06-10
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomExamineRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String customExamineRecordId;
    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;
    /**
     * 审核人ID
     */
    @Excel(name = "审核人ID")
    private String customExamineUserId;

    /**
     * 审核人姓名
     */
    @Excel(name = "审核人姓名")
    private String customExamineUserName;

    /**
     * 客户ID
     */
    @Excel(name = "客户ID")
    private Long customInfoId;

    /**
     * 客户姓名
     */
    @Excel(name = "客户姓名")
    private String customInfoName;

    /**
     * 负责人ID
     */
    @Excel(name = "负责人ID")
    private Long customUserId;

    /**
     * 负责人姓名
     */
    @Excel(name = "负责人姓名")
    private String customUserName;

    /**
     * 审核状态0待审核1已通过2已驳回
     */
    @Excel(name = "审核状态0待审核1已通过2已驳回")
    private String customExamineStatus;

    /**
     * 成交金额
     */
    @Excel(name = "成交金额")
    private BigDecimal customeStrikeMoney;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date examineTime;

    /**
     * 附件
     */
    private String customAnnex;

}
