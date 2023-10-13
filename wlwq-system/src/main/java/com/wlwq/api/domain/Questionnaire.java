package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 内部调研问卷对象 questionnaire
 *
 * @author web
 * @date 2023-05-09
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("Questionnaire")
public class Questionnaire extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷表id
     */
    private String questionnaireId;

    /**
     * 内部调研类别ID
     */
    @Excel(name = "内部调研类别ID")
    private String insideSurveyCategoryId;

    /**
     * 问卷标题
     */
    @Excel(name = "问卷标题")
    private String title;

    /**
     * 问卷题数
     */
    @Excel(name = "问卷题数")
    private Long questionnaireCount;

    /**
     * 问卷分数
     */
    @Excel(name = "问卷分数")
    private Double score;

    /**
     * 创建者id
     */
    @Excel(name = "创建者id")
    private Long creatorId;

    /**
     * 创建者名称
     */
    @Excel(name = "创建者名称")
    private String creatorName;

    /**
     * 创建者部门
     */
    @Excel(name = "创建者部门")
    private Long creatorDeptId;

    /**
     * 删除状态
     */
    @Excel(name = "删除状态")
    private Integer delStatus;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String imageUrl;

    /**
     * 排序(排序越小，越靠前)
     */
    @Excel(name = "排序(排序越小，越靠前)")
    private Integer sortNum;

}
