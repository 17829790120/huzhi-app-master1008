package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 考试类目对象 category
 *
 * @author Renbowen
 * @date 2021-01-07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("Categorys")
public class Categorys extends TreeEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 考试分类ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long categoryId;

    /**
     * 考试分类名字
     */
    @Excel(name = "考试分类名字")
    private String categoryName;

    /** 父菜单ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sortNum;


    /**
     * 考试时间
     */
    @Excel(name = "考试时间")
    private String examTime;

    /**
     * 0:否：1删除
     */
    private Integer delStatus;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String categoryCode;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /** 等级 1 2 3 */
    @Excel(name = "等级")
    private Integer level;

    /** 是否显示(0:不显示 1:显示) */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

    /**
     * 模块ID
     */
    private String trainingModuleId;

    /**
     * 模块名称
     */
    @Excel(name = "模块名称")
    private String trainingModuleName;

    ////////////////////////////////

    // 二级
    private List<Categorys> childCategoryList;

    // 三级
    private List<Categorys> threeCategoryList;


    private String parentIds;

    /**
     * 所有课程数量
     */
    private Integer allCount;

    /**
     * 已学完的课程数量
     */
    private Integer finishLern;
}
