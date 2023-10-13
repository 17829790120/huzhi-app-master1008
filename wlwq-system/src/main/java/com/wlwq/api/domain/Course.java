package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

/**
 * 课程对象 course
 *
 * @author Rick Jen
 * @date 2021-01-13
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("Course")
public class Course extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long courseId;

    /**
     * 课程标题
     */
    @Excel(name = "课程标题")
    private String courseTitle;

    /**
     * 课程价格
     */
    @Excel(name = "课程价格")
    private BigDecimal coursePrice;

    /**
     * 苹果价格
     */
    @Excel(name = "苹果价格")
    private Integer applePrice;

    /**
     * 封面图片
     */
    @Excel(name = "封面图片")
    private String coverImage;

    /**
     * 视频图片
     */
    @Excel(name = "视频图片")
    private String videoImage;

    /**
     * 简介标题
     */
    @Excel(name = "简介标题")
    private String briefTitle;

    /**
     * 是否推荐 0：不推荐  1：推荐
     */
    @Excel(name = "是否推荐")
    private Integer recommendStatus;

    /**
     * 一级分类
     */
    private Long oneCategoryId;

    @Excel(name = "一级分类")
    private String oneCategoryName;

    /**
     * 二级分类
     */

    private Long twoCategoryId;

    @Excel(name = "二级分类")
    private String twoCategoryName;

    /** 三级分类 */
    private Long threeCategoryId;

    @Excel(name = "三级分类")
    private String threeCategoryName;

    /**
     * 课程详情
     */
    @Excel(name = "课程详情")
    private String courseDetail;

    /**
     * 老师微信号
     */
    @Excel(name = "老师微信号")
    private String teacherWechatNumber;

    /**
     * 老师微信二维码
     */
    @Excel(name = "老师微信二维码")
    private String teacherWechatImage;


    /** 有效天数 */
    @Excel(name = "有效天数")
    private Integer useDay;

    /**
     * 删除状态
     */
    @Excel(name = "删除状态")
    private Integer delStatus;

    @Excel(name = "排序")
    private Integer sortNum;

    private Long categoryId;

    private BigDecimal applePriceMoney;

    /**
     * 苹果价格编码
     */
    private String appleBundleId;

    /**
     * 课程老师名称
     */
    private String teacherName;

    /**
     * 课程老师简介
     */
    private String teacherProfile;

    /**
     * 课程老师头像
     */
    private String teacherImage;

    /**
     * 课程老师职位
     */
    private String teacherJob;
////////////////////////////////////////////
    /**
     * 课程总章数
     */
    private Long zhangCount;
    /**
     * 课程总节数
     */
    private Long jieCount;
    /**
     * 课程总学习人数
     */
    private Long learnersCount;

    /**
     * 积分
     */
    private Integer integral;
}
