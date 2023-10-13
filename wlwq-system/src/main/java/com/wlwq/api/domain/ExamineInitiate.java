package com.wlwq.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 审批发起对象 examine_initiate
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
public class ExamineInitiate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 审批类型ID  1：请假 2：报销 3：合同 4：其他 5：补卡
     */
    @Excel(name = "审批类型ID")
    private Long examineModuleId;


    /**
     * 请假类型
     */
    @Excel(name = "请假类型")
    private String leaveType;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 请假时长(天)
     */
    @Excel(name = "请假时长(天)")
    private BigDecimal askForLeaveHour;

    /**
     * 请假事由/报销事由/合同说明/申请内容/补卡理由
     */
    @Excel(name = "请假事由/报销事由/合同说明/申请内容/补卡理由")
    private String reason;

    /**
     * 图片,多个以逗号隔开
     */
    @Excel(name = "图片,多个以逗号隔开")
    private String pics;

    /**
     * 报销类型
     */
    @Excel(name = "报销类型")
    private String expenseType;

    /**
     * 报销金额(元)
     */
    @Excel(name = "报销金额(元)")
    private BigDecimal reimburseMoney;

    /**
     * 费用发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "费用发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date moneyDate;

    /**
     * 总报销金额/合同总额(元)
     */
    @Excel(name = "总报销金额/合同总额(元)")
    private BigDecimal totalMoney;

    /**
     * 父类ID
     */
    @Excel(name = "父类ID")
    private String parentId;

    /**
     * 合同正文
     */
    @Excel(name = "合同正文")
    private String contractText;

    /**
     * 合同名称
     */
    @Excel(name = "合同名称")
    private String contractName;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private String contractNumber;

    /**
     * 合同金额类型
     */
    @Excel(name = "合同金额类型")
    private String contractMoneyType;

    /**
     * 合同期限类型
     */
    @Excel(name = "合同期限类型")
    private String contractDeadlineType;

    /**
     * 合同签署日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同签署日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signatureDate;

    /**
     * 合同印章类型
     */
    @Excel(name = "合同印章类型")
    private String sealType;

    /**
     * 审批详情
     */
    @Excel(name = "审批详情")
    private String content;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
     */
    @Excel(name = "审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回")
    private Integer examineStatus;

    /**
     * 已读标识 0：否 1：已读
     */
    @Excel(name = "已读标识 0：否 1：已读")
    private Integer readStatus;

    /**
     * 发起者所属部门ID
     */
    @Excel(name = "发起者所属部门ID")
    private Long deptId;

    /**
     * 发起者所属公司ID
     */
    @Excel(name = "发起者所属公司ID")
    private Long companyId;

    /**
     * 发起者ID
     */
    @Excel(name = "发起者ID")
    private String accountId;

    /**
     * 发起者姓名
     */
    @Excel(name = "发起者姓名")
    private String accountName;

    /**
     * 发起者手机号
     */
    @Excel(name = "发起者手机号")
    private String accountPhone;

    /**
     * 发起者头像
     */
    @Excel(name = "发起者头像")
    private String accountHead;

    /**
     * 补卡日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "补卡日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reissueClockingDate;

    /**
     * 原打卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "原打卡时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date rawClockingTime;

    /**
     * 补卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "补卡时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reissueClockingTime;


    /**
     * 1：上班打卡 2：下班打卡
     */
    @Excel(name = "1：上班打卡 2：下班打卡")
    private Integer clockingStatus;

    /**
     * 同一批审批标识
     */
    private String examineTag;

    /////////////////////////////

    /**
     * 审批详情信息列表
     */
    private List<ExamineInitiate> initiateDetailList;


    /**
     * 审批文件列表
     */
    private List<ExamineFile> fileList;

    /**
     * 合同相对方信息
     */
    List<ExamineContractCounterparty> counterpartyList;

    /**
     *  模块（类型）(可以是多个，以逗号隔开) 1：请假 2：报销 3：合同 4：其他 5：补卡
     */
    private String examineModuleStr;

    /**
     * 1:最近时间 2：最远时间
     */
    private Integer searchTimeTag;

    /**
     * 审批状态 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
     */
    private String examineStatusStr;

    /**
     * 月份查询
     */
    private String month;

    /**
     * 请假月份
     */
    private String leaveMonth;

    /**
     * 月份查询
     */
    private String leaveYear;

}
