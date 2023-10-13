package com.wlwq.params.examine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class MeetingEvaluateParamVO implements Serializable {


    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 开始时间 yyyy-MM-dd HH:mm:ss
     */
    private Date startTime;

    /**
     * 结束时间 yyyy-MM-dd HH:mm:ss
     */
    private Date endTime;

    /**
     * 审批事由
     */
    private String reason;

    /**
     * 会议训练主键id
     */
    private String meetingTrainingId;

    /**
     * 标题
     */
    private String title;

    /**
     * 开始时间
     */
    private Date meetintBeginTime;

    /**
     * 结束时间
     */
    private Date meetintEndTime;

    /**
     * 地址
     */
    private String address;

    /**
     * 会议状态 0：新建会议1：会议纪要；2：领导评价
     */
    private Long meetingStatus;

    /**
     * 参会人员id
     */
    private String joinAccountId;

    /**
     * 参会人员名称
     */
    private String joinAccountNickName;

    /**
     * 参会人员部门名称
     */
    private String joinAccountDeptName;

    /**
     * 参会人员部门id
     */
    private String joinAccountDeptId;

    /**
     * 简介
     */
    private String synopsis;

    /**
     * 组织者id
     */
    private String organizerAccountId;

    /**
     * 组织者头像
     */
    private String organizerHeadPortrait;

    /**
     * 组织者昵称
     */
    private String organizerNickName;
///////////////////////////////////////////////////
    ////////领导评价
    /**
     * 星级评价
     */
    private String evaluate;

    /**
     * 会议优点
     */
    private String meetingBenefits;

    /**
     * 会议缺点
     */
    private String meetingDisadvantages;

    /**
     * 改进之处
     */
    private String improvement;

    /**
     * 评价状态
     */
    private Integer evaluateStatus;
}
