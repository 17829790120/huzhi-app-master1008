package com.wlwq.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 任务发起对象 task_initiate
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
public class TaskInitiate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务发起ID
     */
    private String taskInitiateId;

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
     * 任务标题
     */
    @Excel(name = "任务标题")
    @NotBlank(message = "请输入任务标题")
    private String taskTitle;

    /**
     * 任务描述
     */
    @Excel(name = "任务描述")
    @NotBlank(message = "请输入任务描述")
    private String taskDescribe;

    /**
     * 紧急/一般
     */
    @Excel(name = "紧急/一般")
    @NotBlank(message = "请传入紧急/一般")
    private String taskType;

    /**
     * 任务积分
     */
    @Excel(name = "任务积分")
    @NotNull(message = "请输入任务积分")
    private Integer taskScore;

    /**
     * 任务开始时间 yyyy-MM-dd HH:mm:ss
     */
    @NotNull(message = "请输入任务开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskStartTime;

    /**
     * 任务结束时间 yyyy-MM-dd HH:mm:ss
     */
    @NotNull(message = "请输入任务结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskEndTime;

    /**
     * 执行-全员/部门/指定
     */
    @NotBlank(message = "请传入执行-全员/部门/指定")
    @Excel(name = "执行-全员/部门/指定")
    private String executeName;

    /**
     * 执行者IDS(可以是多个)
     */
    @NotBlank(message = "请传入执行者IDS(可以是多个)")
    @Excel(name = "执行者IDS(可以是多个)")
    private String executeAccountIds;

    /**
     * 协助-全员/部门/指定
     */
    @Excel(name = "协助-全员/部门/指定")
    private String assistName;

    /**
     * 协助者IDS(可以是多个)
     */
    @Excel(name = "协助者IDS(可以是多个)")
    private String assistAccountIds;

    /**
     * 多图片
     */
    @Excel(name = "多图片")
    private String pics;

    /** 备注 */
    private String remark;


    /**
     * 0:否 1:删除
     */
    private Integer delStatus;

    /////////////////////////////////////

    /**
     * 附件列表
     */
    private List<TaskFile> fileList;

    /**
     * 0：否 1：执行
     */
    private Integer executeStatus;

    /**
     * 0：否 1：协助
     */
    private Integer assistStatus;

    /**
     * 接收者ID
     */
    private String taskReceiveId;

    /**
     * 0：查询已接收的 1：查询已逾期的 2：查询已完成的
     */
    private Integer tag;

    /**
     * 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
     */
    private Integer examineStatus;

    /**
     * 任务延期时间
     */
    private Date taskOverdueTime;

    /**
     * 执行者名称
     */
    private String executeAccountNames;

    /**
     * 协助者名称
     */
    private String assistAccountNames;

    /**
     * 审批唯一标识
     */
    private String examineTag;

}
