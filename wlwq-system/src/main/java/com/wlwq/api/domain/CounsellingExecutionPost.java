package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 辅导实施资讯对象 counselling_execution_post
 *
 * @author wwb
 * @date 2023-05-18
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("CounsellingExecutionPost")
public class CounsellingExecutionPost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 辅导实施资讯ID
     */
    private String counsellingExecutionPostId;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    private String counsellingExecutionCategoryId;

    /**
     * 分类名字
     */
    @Excel(name = "分类名字")
    private String categoryName;

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
     * 封面图片
     */
    @Excel(name = "封面图片")
    private String informationPostImages;

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
     * 基础浏览数量
     */
    @Excel(name = "基础浏览数量")
    private Integer basicsBrowseNumber;

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
     * 资讯标题
     */
    @Excel(name = "资讯标题")
    private String informationPostTitle;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String synopsis;
    //////////////////////////////////////////

    /**
     * 实际浏览数量
     */
    private Integer realBrowseNumber;
}
