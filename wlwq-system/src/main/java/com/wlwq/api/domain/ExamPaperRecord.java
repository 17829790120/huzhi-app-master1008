package com.wlwq.api.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 考试试卷记录对象 exam_paper_record
 *
 * @author wwb
 * @date 2023-05-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("ExamPaperRecord")
public class ExamPaperRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

    /**
     * 发放状态（0：未发放，已发放）
     */
    @Excel(name = "发放状态", readConverterExp = "0=：未发放，已发放")
    private Integer grantStatus;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 测试训练类别ID
     */
    @Excel(name = "测试训练类别ID")
    private String testTrainingCategoryId;

    /**
     * 类别标题
     */
    @Excel(name = "类别标题")
    private String testTrainingCategoryTitle;

    /**
     * 试题类型（0：章节课程；1：测试训练）
     */
    @Excel(name = "试题类型", readConverterExp = "0=：章节课程；1：测试训练")
    private Integer questionType;

}
