package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 课程章节对象 course_chapter
 *
 * @author Rick Jen
 * @date 2021-01-18
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("CourseChapter")
public class CourseChapter extends TreeEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 章节ID
     */
    private Long chapterId;

    /**
     * 课程ID
     */
    private Long courseId;

    /** 父ID */
    private Long parentId;

    @Excel(name = "课程")
    private String courseName;

    /**
     * 章节名字
     */
    @Excel(name = "章节名字")
    private String chapterName;

    /**
     * 章节时长/秒
     */
    @Excel(name = "章节时长/秒")
    private Double totalTime;

    /**
     * 是否试看（0:不是 1:是）
     */
    @Excel(name = "是否试看", readConverterExp = "0=不是,1=是")
    private Integer tryStatus;

    /**
     * 视频地址
     */
    @Excel(name = "视频地址")
    private String videoUrl;

    @Excel(name = "排序")
    private Integer sortNum;
    /**
     * 考试时长
     */
    @Excel(name = "考试时长")
    private Integer examDuration;

    /**
     * 考试题型
     */
    @Excel(name = "考试题型")
    private String examTypes;

    /**
     * 考试说明
     */
    @Excel(name = "考试说明")
    private String examDescription;

    /**
     * 0:否 1:删除
     */
    private Integer delStatus;

    /**
     * 考试内容
     */
    @Excel(name = "考试内容")
    private String examContent;

    /**
     * 文件大小（kb）
     */
    private Double fileSize;

    /**
     * 文件时长（s秒）
     */
    private Double fileDuration;
    /**
     * 资源类型(1:视频;2:pdf;3:PPT)
     */
    private Integer resourceType;
///////////////////////////////////////////////

    /**
     * 考试说明
     */
    @Excel(name = "考试说明")
    private String examExplain;

    /**
     * 满分
     */
    @Excel(name = "满分")
    private Double fullScore;

    /**
     * 优秀分数
     */
    @Excel(name = "优秀分数")
    private Double excellentScore;

    /**
     * 良好分数
     */
    @Excel(name = "良好分数")
    private Double goodScore;

    /**
     * 及格分数
     */
    @Excel(name = "及格分数")
    private Double passingScore;

    /**
     * 满分获得积分
     */
    @Excel(name = "满分获得积分")
    private Integer fullIntegralScore;

    /**
     * 优秀获得积分
     */
    @Excel(name = "优秀获得积分")
    private Integer excellentIntegralScore;

    /**
     * 良好获得积分
     */
    @Excel(name = "良好获得积分")
    private Integer goodIntegralScore;

    /**
     * 及格获得积分
     */
    @Excel(name = "及格获得积分")
    private Integer passingIntegralScore;

///////////////////////////////////////////////
    /**
     * 观看状态：0：未完成；1：已完成
     */
    private Integer watchStatus;

    /**
     * 考试状态：0：未考试；1：考试（得分计算中）；2：不及格，再次考试；3：考试及格
     */
    private Integer examStatus;

    /**
     * 分数
     */
    private Double score;
    /**
     * 心得分享状态：0：未分享；1：分享失效；2：已分享
     */
    private Integer experienceSharingStatus;
    /**
     * 考试记录表id
     */
    private String examRecordId;

    /**
     * 转训状态：0：待审核，1：已通过；2：已驳回；3：已取消；-1：未转训
     */
    private Integer transferTrainingStatus;

    /**
     * 获得的积分
     */
    private Integer integral;


    /**
     * 是否通过观看课程视频获取过积分
     */
    private Integer scoreStatus;

}
