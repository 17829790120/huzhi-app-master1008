package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 课程定制类别对象 course_customization_category
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
@Alias("CourseCustomizationCategory")
public class CourseCustomizationCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 课程定制类别ID
     */
    private Long courseCustomizationCategoryId;

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
     * 上级分类ID
     */
    @Excel(name = "上级分类ID")
    private Long parentId;

    /**
     * 分类标题
     */
    @Excel(name = "分类标题")
    private String categoryTitle;

    /**
     * 排序（大靠前）
     */
    @Excel(name = "排序", readConverterExp = "大=靠前")
    private Integer sortNum;

    /**
     * 删除状态（0正常 1删除）
     */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=删除")
    private Integer delStatus;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

    /**
     * 级别
     */
    @Excel(name = "级别")
    private Integer level;

    /**
     * 祖级列表
     */
    @Excel(name = "祖级列表")
    private String ancestors;

}
