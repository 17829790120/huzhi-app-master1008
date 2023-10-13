package com.wlwq.api.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 会议训练对象 meeting_training
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
@Alias("MeetingTraining")
public class MeetingTraining extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 会议训练主键id
     */
    private String meetingTrainingId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 显示状态 0：显示 1：隐藏
     */
    @Excel(name = "显示状态 0：显示 1：隐藏")
    private Integer showStatus;

    /**
     * 排序，数字越小越靠前
     */
    @Excel(name = "排序，数字越小越靠前")
    private Integer sortNum;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

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
     * 会议审核状态 0：未审核，1：审核未通过；2：审核已通过
     */
    @Excel(name = "会议审核状态 0：待审核，1：已通过；2：已驳回；3：已取消")
    private Integer meetingReviewStatus;

    /**
     * 会议类型：0：会议训练；1：课程章节转训
     */
    @Excel(name = "会议类型：0：会议训练；1：课程章节转训")
    private Integer meetingType;

    /**
     * 章节ID
     */
    @Excel(name = "章节ID")
    private Long chapterId;

    /**
     * 课程ID
     */
    @Excel(name = "课程ID")
    private Long courseId;

    /**
     * 课程标题
     */
    @Excel(name = "课程标题")
    private String courseTitle;

    /**
     * 章节名字
     */
    @Excel(name = "章节名字")
    private String chapterName;

    /**
     * 会议流程事项
     */
    private List<MeetingTrainingItem> meetingTrainingItemList;

}
