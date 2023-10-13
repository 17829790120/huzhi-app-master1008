package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 人员训练模块对象 training_module
 *
 * @author gaoce
 * @date 2023-07-31
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingModule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 人员训练模块ID
     */
    private String trainingModuleId;

    /**
     * 标题名称
     */
    @Excel(name = "标题名称")
    private String titleName;

    /**
     * 副标题名称
     */
    @Excel(name = "副标题名称")
    private String subheadName;

    /**
     * 模块图标
     */
    @Excel(name = "模块图标")
    private String moduleIcon;

    /**
     * 排序(排序越小，越靠前)
     */
    @Excel(name = "排序(排序越小，越靠前)")
    private Long sortNum;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;


    /**
     * 模块背景图
     */
    @Excel(name = "模块背景图")
    private String moduleImage;

    /**
     * 0:否 1:删除
     */
    private Integer delStatus;

}
