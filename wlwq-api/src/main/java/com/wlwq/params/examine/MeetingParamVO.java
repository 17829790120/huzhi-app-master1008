package com.wlwq.params.examine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoce
 * 会议审批提交参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingParamVO implements Serializable {


    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 开始时间 yyyy-MM-dd HH:mm:ss
     */
    //@NotNull(message = "请传入开始时间")
    private Date startTime;

    /**
     * 结束时间 yyyy-MM-dd HH:mm:ss
     */
    //@NotNull(message = "请传入结束时间")
    private Date endTime;

    /**
     * 审批事由
     */
    private String reason;

    /**
     * 图片,多个以逗号隔开
     */
    private String pics;

    /**
     * 会议训练主键id
     */
    @Excel(name = "会议训练主键id")
    private String meetingTrainingId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date meetintBeginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date meetintEndTime;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 会议状态 0：新建会议1：会议纪要；2：领导评价
     */
    @Excel(name = "会议状态 0：新建会议1：会议纪要；2：领导评价")
    private Long meetingStatus;

    /**
     * 参会人员id
     */
    @Excel(name = "参会人员id")
    private String joinAccountId;

    /**
     * 参会人员名称
     */
    @Excel(name = "参会人员名称")
    private String joinAccountNickName;

    /**
     * 参会人员部门名称
     */
    @Excel(name = "参会人员部门名称")
    private String joinAccountDeptName;

    /**
     * 参会人员部门id
     */
    @Excel(name = "参会人员部门id")
    private String joinAccountDeptId;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String synopsis;

    /**
     * 组织者id
     */
    @Excel(name = "组织者id")
    private String organizerAccountId;

    /**
     * 组织者头像
     */
    @Excel(name = "组织者头像")
    private String organizerHeadPortrait;

    /**
     * 组织者昵称
     */
    @Excel(name = "组织者昵称")
    private String organizerNickName;
}
