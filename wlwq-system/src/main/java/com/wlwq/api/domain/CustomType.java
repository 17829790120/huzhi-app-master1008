package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 客户意向对象 custom_type
 * 
 * @author wlwq
 * @date 2023-06-10
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户意向id
     */
    private String customTypeId;

    /**
     * 客户意向名称
     */
    @Excel(name = "客户意向名称")
    private String customTypeName;

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

    /**
     * 所属公司id
     */
    @Excel(name = "所属公司id")
    private String deptId;

    /**
     * 用户所属公司ID
     */
    @Excel(name = "用户所属公司ID")
    private Long companyId;

}
