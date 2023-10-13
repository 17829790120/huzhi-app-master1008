package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;

/**
 * 审批类型对象 examine_module
 *
 * @author gaoce
 * @date 2023-05-10
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineModule extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 审批类型ID
     */
    private Long examineModuleId;

    /**
     * 模块名称
     */
    @Excel(name = "模块名称")
    private String moduleName;

    /**
     * 图标
     */
    @Excel(name = "图标")
    private String icon;

    /**
     * 排序(排序越小，越靠前)
     */
    @Excel(name = "排序(排序越小，越靠前)")
    private Integer sortNum;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

    /**
     * 0:否 1:删除
     */
    private Integer delStatus;

    /** 父菜单ID */
    private Long parentId;

    /**
     * 模块等级(一级、二级、三级)
     */
    private Long moduleLevel;

    /**
     * 标识 0：查询审批流程的标识
     */
    private Integer tag;

}
