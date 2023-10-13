package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 客户等级回访日维护对象 custom_level_days
 * 
 * @author wlwq
 * @date 2023-06-07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomLevelDays extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;
    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;
    /**
     * 等级
     */
    @Excel(name = "等级")
    private String customLevel;

    /**
     * 几日内回访
     */
    @Excel(name = "几日内回访")
    private Long customDays;

    /**
     * 排序(排序越大，越靠前)
     */
    @Excel(name = "排序(排序越大，越靠前)")
    private Long sortNum;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Long showStatustiny;

    /**
     * 是否删除(0:未删除 1:已删除)
     */
    @Excel(name = "是否删除(0:未删除 1:已删除)")
    private Long delStatus;

}
