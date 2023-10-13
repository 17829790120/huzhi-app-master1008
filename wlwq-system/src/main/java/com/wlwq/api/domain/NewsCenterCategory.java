package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 新闻中心对象 news_center_category
 *
 * @author wwb
 * @date 2023-05-06
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsCenterCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 新闻中心分类ID
     */
    private Long newsCenterCategoryId;

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
     * 新闻中心分类标题
     */
    @Excel(name = "新闻中心分类标题")
    private String newsCenterCategoryTitle;

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
////////////////////////////////////////
    private String parentName;

    private int tag;
}
