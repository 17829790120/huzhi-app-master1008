package com.wlwq.api.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 任务接收对象 task_receive
 *
 * @author gaoce
 * @date 2023-05-29
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskReceive extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 接收者所属公司ID
     */
    private String taskReceiveId;

    /**
     * 任务发起ID
     */
    @Excel(name = "任务发起ID")
    private String taskInitiateId;

    /**
     * 接收者ID
     */
    @Excel(name = "接收者ID")
    private String accountId;

    /**
     * 接收者姓名
     */
    @Excel(name = "接收者姓名")
    private String accountName;

    /**
     * 接收者手机号
     */
    @Excel(name = "接收者手机号")
    private String accountPhone;

    /**
     * 接收者头像
     */
    @Excel(name = "接收者头像")
    private String accountHead;

    /**
     * 接收者所属部门ID
     */
    @Excel(name = "接收者所属部门ID")
    private Long deptId;

    /**
     * 接收者所属公司ID
     */
    @Excel(name = "接收者所属公司ID")
    private Long companyId;

    /**
     * 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
     */
    @Excel(name = "审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过")
    private Integer examineStatus;

    /**
     * 已读标识 0：否 1：已读
     */
    @Excel(name = "已读标识 0：否 1：已读")
    private Integer readStatus;

    /**
     * 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date taskEndTime;

    /**
     * 多图片
     */
    @Excel(name = "多图片")
    private String pics;


    /**
     * 延期理由/完成理由
     */
    private String remark;

    /**
     * 任务延期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务延期时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date taskOverdueTime;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 拒绝原因/批准原因
     */
    @Excel(name = "拒绝原因/批准原因")
    private String rejectContent;

    /**
     * 同一批审批标识
     */
    private String examineTag;
}
