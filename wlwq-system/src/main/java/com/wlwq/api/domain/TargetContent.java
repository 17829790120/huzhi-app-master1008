package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.List;

/**
 * 目标训练内容对象 target_content
 *
 * @author wwb
 * @date 2023-06-02
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("TargetContent")
public class TargetContent extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

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
     * 图片
     */
    @Excel(name = "图片")
    private String coverImage;

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
     * 创建者id
     */
    @Excel(name = "创建者id")
    private Long creatorId;

    /**
     * 创建者昵称
     */
    @Excel(name = "创建者昵称")
    private String creatorNickName;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /** 父菜单ID */
    private Long parentId;

    /**
     * 0:申请实现梦想；1：不申请实现梦想
     */
    @Excel(name = "0:申请实现梦想；1：不申请实现梦想")
    private Integer dreamState;


    /**
     * 0：否 1：必填
     */
    @Excel(name = "0：否 1：必填")
    private Integer requiredStatus;

    /**
     * 子集列表
     */
    List<TargetContent> subsetList;

}
