package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 测试训练类别对象 test_training_category
 * 
 * @author wwb
 * @date 2023-05-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("TestTrainingCategory")
public class TestTrainingCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 测试训练类别ID
     */
    private String testTrainingCategoryId;

    /**
     * 类别标题
     */
    @Excel(name = "类别标题")
    private String title;

    /**
     * 封面图片
     */
    @Excel(name = "封面图片")
    private String coverImage;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String synopsis;

    /**
     * 删除状态
     */
    @Excel(name = "删除状态")
    private Integer delStatus;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sortNum;

    /**
     * 是否推荐 0：不推荐  1：推荐
     */
    @Excel(name = "是否推荐 0：不推荐  1：推荐")
    private Integer recommendStatus;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

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

}
