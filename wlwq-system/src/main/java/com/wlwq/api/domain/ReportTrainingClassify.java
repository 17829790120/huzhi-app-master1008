package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 汇报训练分类对象 report_training_classify
 *
 * @author gaoce
 * @date 2023-06-01
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportTrainingClassify extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 汇报训练分类ID
     */
    private Integer reportTrainingClassifyId;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String classifyName;

    /**
     * 分类图标
     */
    @Excel(name = "分类图标")
    private String classifyIcon;

    /**
     * 排序(排序越大，越靠前)
     */
    @Excel(name = "排序(排序越大，越靠前)")
    private Long sortNum;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

    /**
     * 是否删除(0:未删除 1:已删除)
     */
    @Excel(name = "是否删除(0:未删除 1:已删除)")
    private Integer delStatus;

    ////////////////////////

    /**
     * 未读数量
     */
    private Integer noReadCount;
}
