package com.wlwq.params.courseExam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 观看记录
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseViewRecordParam implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "观看开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 观看结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "观看结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
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
    @NotNull(message = "请传入章节ID")
    private Long chapterId;

    /**
     * 课程ID
     */
    @NotNull(message = "请传入课程ID")
    private Long courseId;

    /**
     * 观看状态：0：未完成；1：已完成
     */
    @Excel(name = "观看状态：0：未完成；1：已完成")
    private Integer watchStatus;
}
