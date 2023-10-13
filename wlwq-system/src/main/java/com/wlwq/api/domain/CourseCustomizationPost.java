package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 课程定制资讯对象 course_customization_post
 *
 * @author wwb
 * @date 2023-05-19
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("CourseCustomizationPost")
public class CourseCustomizationPost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资讯ID
     */
    private String courseCustomizationPostId;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 资讯分类ID
     */
    @Excel(name = "资讯分类ID")
    private Long informationCategoryId;

    /**
     * 资讯标题
     */
    @Excel(name = "资讯标题")
    private String informationPostTitle;

    /**
     * 封面图片（最多三张）
     */
    @Excel(name = "封面图片", readConverterExp = "最=多三张")
    private String informationPostImages;

    /**
     * 封面图片类型（1图片 2视频）
     */
    @Excel(name = "封面图片类型", readConverterExp = "1=图片,2=视频")
    private Integer informationPostImagesType;

    /**
     * 单张封面图片展示位置
     */
    @Excel(name = "单张封面图片展示位置")
    private String imagesAddress;

    /**
     * 资讯内容
     */
    @Excel(name = "资讯内容")
    private String informationPostContent;

    /**
     * 排序（大靠前）
     */
    @Excel(name = "排序", readConverterExp = "大=靠前")
    private Integer sortNum;

    /**
     * 基础预约数量
     */
    @Excel(name = "基础预约数量")
    private Integer basicsReservationNumber;

    /**
     * 热门状态（0普通 1热门）
     */
    @Excel(name = "热门状态", readConverterExp = "0=普通,1=热门")
    private Integer hostStatus;

    /**
     * 置顶状态（0普通 1置顶）
     */
    @Excel(name = "置顶状态", readConverterExp = "0=普通,1=置顶")
    private Integer topStatus;

    /**
     * 删除状态（0正常 1删除）
     */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=删除")
    private Integer delStatus;

    /**
     * 祖级列表
     */
    @Excel(name = "祖级列表")
    private String ancestors;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String synopsis;
    /**
     * 咨询电话
     */
    @Excel(name = "咨询电话")
    private String telephone;
////////////////////////////////////////////////////////////
    /**
     * 分类标题
     */
    private String categoryTitle;

    /**
     * 预约个数
     */
    private Integer count;
}
