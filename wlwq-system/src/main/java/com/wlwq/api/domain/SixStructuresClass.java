package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 六大架构分类对象 six_structures_class
 *
 * @author wlwq
 * @date 2023-08-28
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SixStructuresClass extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 六大架构分类id
     */
    private Long sixStructuresClassId;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String className;

    /**
     * 分类图标
     */
    @Excel(name = "分类图标")
    private String icon;

    /**
     * 父类ID
     */
    @Excel(name = "父类ID")
    private Long parentId;

    /**
     * 顺序
     */
    @Excel(name = "顺序")
    private Long orderNum;

    /**
     * 级别
     */
    @Excel(name = "级别")
    private Long level;

    /**
     * 显示状态（0隐藏 1显示）
     */
    @Excel(name = "显示状态", readConverterExp = "0=隐藏,1=显示")
    private Integer showStatus;

    /**
     * 删除状态（0正常 1删除）
     */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=删除")
    private Integer delStatus;

    /**
     * 分类标题
     */
    @Excel(name = "分类标题")
    private String classTitle;

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

    /////////////////////////
    /**
     * 是否已选择
     */
    private Integer flag;

    /**
     * 二级分类ID
     */
    private String twoStoreClassIds;

    /**
     * 三级分类id
     */
    private String ThreeStoreClassIds;

    /**
     * 父类名称
     */
    private String parentName;


    ////////////////////////
    /**
     * 未读数量
     */
    private Integer noReadCount;

}
