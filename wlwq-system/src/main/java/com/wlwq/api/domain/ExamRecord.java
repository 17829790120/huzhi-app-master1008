package com.wlwq.api.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 考试记录列表对象 exam_record
 *
 * @author wwb
 * @date 2023-04-23
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("ExamRecord")
public class ExamRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 考试记录表id
     */
    private String examRecordId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 考试开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考试开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /**
     * 考试结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考试结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 分数
     */
    @Excel(name = "分数")
    private Double score;

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
     * 用户归属部门ID
     */
    @Excel(name = "用户归属部门ID")
    private Long deptId;

    /**
     * 评分状态：0:未评分 1:已评分
     */
    @Excel(name = "评分状态：0:未评分 1:已评分")
    private Integer scoreStatus;

    /**
     * 试题类型（0：章节课程；1：测试训练）
     */
    @Excel(name = "试题类型")
    private Integer questionType;

    /**
     * 考试试卷记录表id
     */
    private String examPaperRecordId;

    /**
     * 试卷名称
     */
    @Excel(name = "试卷名称")
    private String examPaperTitle;

    /**
     * 答卷状态（0：未答卷；1：已答卷）
     */
    @Excel(name = "答卷状态")
    private Integer answerStatus;

    /**
     * 考试说明
     */
    @Excel(name = "考试说明")
    private String examExplain;

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
    private Double fullIntegralScore;

    /**
     * 优秀获得积分
     */
    @Excel(name = "优秀获得积分")
    private Double excellentIntegralScore;

    /**
     * 良好获得积分
     */
    @Excel(name = "良好获得积分")
    private Double goodIntegralScore;

    /**
     * 及格获得积分
     */
    @Excel(name = "及格获得积分")
    private Double passingIntegralScore;

////////////////////////////////////////////////////////////////////////////////////
    /**
     * 考试状态：0：未考试；1：考试（得分计算中）；2：不及格，再次考试；3：考试及格
     */
    private Integer examStatus;
    /**
     * 用户id
     */
    private String ids;
    /**
     * 获得的积分
     */
    private Integer integral;
}
