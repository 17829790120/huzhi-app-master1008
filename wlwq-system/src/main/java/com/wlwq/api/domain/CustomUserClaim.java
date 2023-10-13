package com.wlwq.api.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 客户认领对象 custom_user_claim
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
public class CustomUserClaim extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String customClaimId;

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
     * 回访期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回访期限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date customFollowTime;

    /**
     * 最后跟进时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后跟进时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date customFollowLastTime;

    /**
     * 0跟进中1战败2已成交
     */
    @Excel(name = "0跟进中1战败2已成交")
    private String status;

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
     * 认领时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "认领时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date claimTime;

    /**
     * 放弃客户时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "放弃客户时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseTime;

    /**
     * 查询
     */
    private transient String customName;

    /**
     * 当期是否跟进
     */
    private String  isFollow;

}
