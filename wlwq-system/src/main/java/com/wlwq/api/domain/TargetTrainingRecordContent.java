package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

/**
 * 目标训练记录自定义内容对象 target_training_record_content
 *
 * @author wwb
 * @date 2023-06-05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("TargetTrainingRecordContent")
public class TargetTrainingRecordContent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String targetTrainingRecordContentId;

    /**
     * 目标训练自定义id
     */
    @Excel(name = "目标训练自定义id")
    private Long contentId;

    /**
     * 父级分类id
     */
    @Excel(name = "父级分类id")
    private Long parentId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 下级分类状态：0：无下级，1：有下级
     */
    @Excel(name = "下级分类状态：0：无下级，1：有下级")
    private Integer subsetState;

    /**
     * 目标训练主键id
     */
    @Excel(name = "目标训练主键id")
    private String targetTrainingId;

    /**
     * 目标训练记录主键id
     */
    @Excel(name = "目标训练记录主键id")
    private String targetTrainingRecordId;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 0:申请实现梦想；1：不申请实现梦想
     */
    @Excel(name = "0:申请实现梦想；1：不申请实现梦想")
    private Integer dreamState;
    /**
     * 子集列表
     */
    List<TargetTrainingRecordContent> subsetList;

}
