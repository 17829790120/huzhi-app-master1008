package com.wlwq.params.courseExam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.api.domain.MeetingTrainingItem;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会议测试
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MeetingTrainingParam implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 地址
     */
    private String address;

    /**
     * 会议状态 0：新建会议1：会议纪要；2：领导评价
     */
    private Integer meetingStatus;

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
     * 会议审核状态 0：未审核，1：审核未通过；2：审核已通过
     */
    private Integer meetingReviewStatus;

    /**
     * 会议类型：0：会议训练；1：课程章节转训
     */
    private Integer meetingType;

    /**
     * 章节ID
     */
    private Long chapterId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程标题
     */
    private String courseTitle;

    /**
     * 章节名字
     */
    private String chapterName;

    /**
     * 会议流程事项
     */
    private List<MeetingTrainingItem> meetingTrainingItemList;

}
