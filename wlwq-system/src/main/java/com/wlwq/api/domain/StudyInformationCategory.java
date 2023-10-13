package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 资讯分类对象 study_information_category
 *
 * @author web
 * @date 2023-05-16
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("StudyInformationCategory")
public class StudyInformationCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资讯分类ID
     */
    private Long studyInformationCategoryId;

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
     * 资讯分类标题
     */
    @Excel(name = "资讯分类标题")
    private String informationCategoryTitle;

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
////////////////////////////////////////////////////////////////
    private String parentName;
}
