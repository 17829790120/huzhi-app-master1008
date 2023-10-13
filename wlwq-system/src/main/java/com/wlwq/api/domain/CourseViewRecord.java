package com.wlwq.api.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 章节观看记录对象 course_view_record
 *
 * @author wwb
 * @date 2023-04-24
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseViewRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 观看记录表id
     */
    private String courseViewRecordId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String headPortrait;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 用户归属部门ID
     */
    @Excel(name = "用户归属部门ID")
    private Long deptId;

    /**
     * 观看开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "观看开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /**
     * 观看结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "观看结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 观看时长
     */
    @Excel(name = "观看时长")
    private Long watchTime;

    /**
     * 章节ID
     */
    @Excel(name = "章节ID")
    private Long chapterId;

    /** 父ID */
    private Long parentId;

    /**
     * 课程ID
     */
    @Excel(name = "课程ID")
    private Long courseId;

    /**
     * 观看状态：0：未完成；1：已完成
     */
    @Excel(name = "观看状态：0：未完成；1：已完成")
    private Integer watchStatus;

}
