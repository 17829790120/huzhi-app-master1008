package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 客户跟进对象 custom_follow
 * 
 * @author wlwq
 * @date 2023-06-02
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomFollow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private String customFollowId;

    /**
     * 客户ID
     */
    @Excel(name = "客户ID")
    private String customInfoId;

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
     * 负责人ID
     */
    @Excel(name = "负责人ID")
    private String customUserId;
    /**
     * 内容
     */
    @Excel(name = "内容")
    private String customContent;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String customAnnex;

    /**
     * 排序(排序越大，越靠前)
     */
    @Excel(name = "排序(排序越大，越靠前)")
    private Integer sortNum;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private String showStatustiny;

    /**
     * 是否删除(0:未删除 1:已删除)
     */
    @Excel(name = "是否删除(0:未删除 1:已删除)")
    private Integer delStatus;

    /**
     * 认领id
     */
    private transient String customClaimId;

    /**
     * 查询
     */
    private transient String customName;

}
