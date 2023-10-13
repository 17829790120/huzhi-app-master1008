package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 理论体系类目对象 counselling_theoretical_category
 *
 * @author wwb
 * @date 2023-05-18
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("CounsellingTheoreticalCategory")
public class CounsellingTheoreticalCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private String counsellingTheoreticalCategoryId;

    /**
     * 分类名字
     */
    @Excel(name = "分类名字")
    private String categoryName;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sortNum;

    /**
     * 0：否 1：删除
     */
    @Excel(name = "0：否 1：删除")
    private Integer delStatus;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

}
