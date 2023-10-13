package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 资源文件类别对象 resource_file_category
 *
 * @author wwb
 * @date 2023-05-23
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("ResourceFileCategory")
public class ResourceFileCategory extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资源文件类别ID
     */
    private Long resourceFileCategoryId;

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

    /** 父菜单ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 1:前端api查询；2：后台查询；
     */
    private Integer tag;

}
