package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 公司积分设置对象 sys_dept_score
 *
 * @author gaoce
 * @date 2023-06-06
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysDeptScore extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公司积分设置ID
     */
    @Excel(name = "公司积分设置ID")
    private String sysDeptScoreId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long deptId;

    /**
     * 积分设置模板
     */
    @Excel(name = "积分设置模板")
    private Integer sysSetScore;

    /**
     * 积分值
     */
    @Excel(name = "积分值")
    private Integer score;

    /**
     * 是否删除(0:未删除 1:已删除)
     */
    @Excel(name = "是否删除(0:未删除 1:已删除)")
    private Long delStatus;

}
