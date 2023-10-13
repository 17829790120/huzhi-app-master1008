package com.wlwq.information.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 资讯分类对象 information_category
 * 
 * @author Rick wlwq
 * @date 2021-04-19
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformationCategory extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 资讯分类ID */
    @Excel(name = "资讯分类ID")
    private Long informationCategoryId;

    private Long deptId;
    private Long userId;
    private Long parentId;

    /** 资讯分类标题 */
    @Excel(name = "资讯分类标题")
    private String informationCategoryTitle;

    /** 排序（大靠前） */
    @Excel(name = "排序", readConverterExp = "大=靠前")
    private Integer sortNum;

    /** 删除状态（0正常 1删除） */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=删除")
    private Integer delStatus;
}
