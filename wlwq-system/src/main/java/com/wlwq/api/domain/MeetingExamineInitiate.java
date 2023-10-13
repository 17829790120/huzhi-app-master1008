package com.wlwq.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 会议审批发起对象 meeting_examine_initiate
 * 
 * @author wwb
 * @date 2023-05-29
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("MeetingExamineInitiate")
public class MeetingExamineInitiate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 审批类型ID  24：会议训练审核 25：会议训练评价 3：其他
     */
    @Excel(name = "审批类型ID  1：会议训练审核 2：会议训练评价 3：其他")
    private Integer examineModuleId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 申请内容
     */
    @Excel(name = "申请内容")
    private String reason;

    /**
     * 图片,多个以逗号隔开
     */
    @Excel(name = "图片,多个以逗号隔开")
    private String pics;

    /**
     * 父类ID
     */
    @Excel(name = "父类ID")
    private String parentId;

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
     * 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回；6：已取消
     */
    @Excel(name = "审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回")
    private Integer examineStatus;

    /**
     * 已读标识 0：否 1：已读
     */
    @Excel(name = "已读标识 0：否 1：已读")
    private Integer readStatus;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date meetintBeginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
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
    private Integer meetingStatus;

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

    /**
     * 父级标题
     */
    private String parentTitle;

    /**
     * 子级标题
     */
    private String subTitle;

    /**
     * 子级内容
     */
    private String subContent;

    /**
     * 证明文件
     */
    private String documentationUrl;

    /**
     * 自定义字段id
     */
    private String targetTrainingRecordContentId;

    ////////领导评价
    /**
     * 星级评价
     */
    private Double evaluate;

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
     * 评价状态 （0：未评价；1：已评价）
     */
    private Integer evaluateStatus;

    /**
     * 发起者所在部门名称
     */
    private String deptName;

    /**
     * 自我介绍
     */
    private String selfIntroduction;

    /**
     * 目标主键id
     */
    private String targetId;

    /**
     * 成交客户id
     */
    private String customId;

    /**
     * 客户姓名
     */
    private String customName;

    /**
     * 客户头像
     */
    private String customHeadPortrait;

    /**
     * 客户手机号
     */
    private String customPhone;

    /**
     * 客户成交金额
     */
    private BigDecimal customeStrikeMoney;

    /**
     * 客户成交附件
     */
    private String customEnclosure;

    /**
     * 会议类型：0：会议训练；1：课程章节转训
     */
    private Integer meetingType;

    /**
     * 同一批审批标识
     */
    private String examineTag;

  ////////////////////////////////////////////////////////////////////////////

    /**
     *  模块（类型）(可以是多个，以逗号隔开) 24：会议训练审核 25：会议训练评价 
     */
    private String examineModuleStr;

    /**
     * 审批详情信息列表
     */
    private List<MeetingExamineInitiate> initiateDetailList;


    /**
     * 审批文件列表
     */
    private List<ExamineFile> fileList;

    /**
     * 1:最近时间 2：最远时间
     */
    private Integer searchTimeTag;

    /**
     * 审批状态 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回；6：已取消
     */
    private String examineStatusStr;

    /**
     * 月份查询
     */
    private String month;

    private List<MeetingTrainingItem> meetingTrainingItemList;

    /**
     * 1:未开始；2：进行中；3：已结束；4：已取消
     */
    private Integer tag;

}
