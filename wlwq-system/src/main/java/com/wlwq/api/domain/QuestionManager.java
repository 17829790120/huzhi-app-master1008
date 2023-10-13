package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 试题列表对象 question_manager
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
@Alias("QuestionManager")
public class QuestionManager extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 题库表id
     */
    private String questionManagerId;

    /**
     * 1:单项选择题 2：多项选择题 3：解答题
     */
    @Excel(name = "1:单项选择题 2：多项选择题 3：解答题")
    private Integer questionStatus;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String pic;

    /**
     * 解析
     */
    @Excel(name = "解析")
    private String analysis;

    /**
     * 分数
     */
    @Excel(name = "分数")
    private Double score;

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
     * 难易程度 1：难 2：中等 3：易
     */
    @Excel(name = "难易程度 1：难 2：中等 3：易")
    private Integer complexityStatus;

    /**
     * 排序，数字越小越靠前
     */
    @Excel(name = "排序，数字越小越靠前")
    private Integer sortNum;

    /**
     * 选项A
     */
    @Excel(name = "选项A")
    private String optionA;

    /**
     * 选项B
     */
    @Excel(name = "选项B")
    private String optionB;

    /**
     * 选项C
     */
    @Excel(name = "选项C")
    private String optionC;

    /**
     * 选项D
     */
    @Excel(name = "选项D")
    private String optionD;

    /**
     * 选项E
     */
    @Excel(name = "选项E")
    private String optionE;

    /**
     * 选项F
     */
    @Excel(name = "选项F")
    private String optionF;

    /**
     * 正确答案
     */
    @Excel(name = "正确答案")
    private String answerStatus;

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
     * 试题类型（0：章节课程；1：测试训练）
     */
    @Excel(name = "课程ID")
    private Integer questionType;

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
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;
//////////////////////////////////////////////////////
    /**
     * 考试试卷记录表id
     */
    @Excel(name = "考试试卷记录表id")
    private String examPaperRecordId;
}
