package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 目标训练管理对象 target_training
 *
 * @author wwb
 * @date 2023-06-01
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("TargetTraining")
public class TargetTraining extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 目标训练主键id
     */
    private String targetTrainingId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 年份
     */
    @Excel(name = "年份")
    private Integer years;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 显示状态 0：显示 1：隐藏
     */
    @Excel(name = "显示状态 0：显示 1：隐藏")
    private Integer showStatus;

    /**
     * 排序，数字越小越靠前
     */
    @Excel(name = "排序，数字越小越靠前")
    private Integer sortNum;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 创建者id
     */
    @Excel(name = "创建者id")
    private Long creatorId;

    /**
     * 创建者头像
     */
    @Excel(name = "创建者头像")
    private String creatorHeadPortrait;

    /**
     * 创建者昵称
     */
    @Excel(name = "创建者昵称")
    private String creatorNickName;

    /**
     * 发布状态 0：未发布，1：已发布
     */
    @Excel(name = "发布状态 0：未发布，1：已发布")
    private Integer publishStatus;

}
