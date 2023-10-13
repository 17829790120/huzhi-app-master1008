package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * 用户审批流程对象 examine_flow_account
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
public class ExamineFlowAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户审批流程ID
     */
    private String examineFlowAccountId;

    /**
     * 审批发起ID
     */
    @Excel(name = "审批发起ID")
    private String examineInitiateId;

    /**
     * 审批用户ID
     */
    @Excel(name = "审批用户ID")
    private String accountId;

    /**
     * 审批岗位ID
     */
    @Excel(name = "审批岗位ID")
    private Long postId;

    /**
     * 审批顺序，正序
     */
    @Excel(name = "审批顺序，正序")
    private Integer examineSequence;

    /**
     * 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
     */
    @Excel(name = "审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回")
    private Integer examineStatus;

    /**
     * 1:审批
     */
    @Excel(name = "1:审批")
    private Integer examineType;

    /**
     * 已读标识 0：否 1：已读
     */
    @Excel(name = "已读标识 0：否 1：已读")
    private Integer readStatus;

    /**
     * 拒绝原因/批准原因
     */
    @Excel(name = "拒绝原因/批准原因")
    private String rejectContent;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 审批者姓名
     */
    @Excel(name = "审批者姓名")
    private String accountName;

    /**
     * 审批者手机号
     */
    @Excel(name = "审批者手机号")
    private String accountPhone;

    /**
     * 审批者头像
     */
    @Excel(name = "审批者头像")
    private String accountHead;

    /**
     * 通过/拒绝图片，多图片逗号隔开
     */
    @Excel(name = "通过/拒绝图片，多图片逗号隔开")
    private String pics;

    /**
     * 审批截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审批截止时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date examineEndTime;

    /**
     * 同一批审批标识
     */
    private String examineTag;

    /////////////////////////////

    private Integer readTag;

    /**
     * 岗位名称
     */
    private String postName;

}
