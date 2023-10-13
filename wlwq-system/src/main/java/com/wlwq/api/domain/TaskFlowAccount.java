package com.wlwq.api.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 用户任务审批流程对象 task_flow_account
 *
 * @author gaoce
 * @date 2023-05-30
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskFlowAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务审批ID
     */
    private String taskFlowAccountId;

    /**
     * 任务接收ID
     */
    @Excel(name = "任务接收ID")
    private String taskReceiveId;

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
     * 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：延期完成待审批 12：延期完成审批中 13：延期完成已驳回 14延期完成已撤回
     */
    @Excel(name = "审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：延期完成待审批 12：延期完成审批中 13：延期完成已驳回 14延期完成已撤回")
    private Integer examineStatus;

    /**
     * 1:延期审批 2:完成审批
     */
    @Excel(name = "1:延期审批 2:完成审批")
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
     * 审批者所属部门ID
     */
    @Excel(name = "审批者所属部门ID")
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
     * 任务标题
     */
    private String taskTitle;

    /**
     * 0:待审核 1：已通过 2：已驳回
     */
    private Integer tag;

    /**
     * 岗位名称
     */
    private String postName;

}
