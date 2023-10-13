package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 用户审批的抄送流程对象 examine_flow_copy_account
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
public class ExamineFlowCopyAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户审批的抄送流程ID
     */
    private String examineFlowCopyAccountId;

    /**
     * 抄送者ID
     */
    @Excel(name = "抄送者ID")
    private String copyAccountId;

    /**
     * 审批发起ID
     */
    @Excel(name = "审批发起ID")
    private String examineInitiateId;

    /**
     * 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
     */
    @Excel(name = "审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回")
    private Integer examineStatus;

    /**
     * 1:抄送
     */
    @Excel(name = "1:抄送")
    private Long examineType;

    /**
     * 已读标识 0：否 1：已读
     */
    @Excel(name = "已读标识 0：否 1：已读")
    private Integer readStatus;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Long delStatus;

    /**
     * 抄送者姓名
     */
    @Excel(name = "抄送者姓名")
    private String copyAccountName;

    /**
     * 抄送者手机号
     */
    @Excel(name = "抄送者手机号")
    private String copyAccountPhone;

    /**
     * 抄送者头像
     */
    @Excel(name = "抄送者头像")
    private String copyAccountHead;


    /**
     * 同一批审批标识
     */
    private String examineTag;
}
