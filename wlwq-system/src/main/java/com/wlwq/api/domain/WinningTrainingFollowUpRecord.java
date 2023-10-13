package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 训练跟进记录对象 winning_training_follow_up_record
 * 
 * @author wwb
 * @date 2023-06-08
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("WinningTrainingFollowUpRecord")
public class WinningTrainingFollowUpRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 训练跟进记录主键id
     */
    private String winningTrainingFollowUpRecordId;

    /**
     * 训练记录主键id
     */
    @Excel(name = "训练记录主键id")
    private String winningTrainingRecordId;

    /**
     * 跟进内容
     */
    @Excel(name = "跟进内容")
    private String content;

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
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 创建者id
     */
    @Excel(name = "创建者id")
    private String creatorId;

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
     * pk/对赌类型 1：员工与员工pk，2：部门与部门pk,3:小组与小组pk，4：个人与部门对赌，5：个人与公司对赌，6：部门与公司对赌
     */
    @Excel(name = "pk/对赌类型 1：员工与员工pk，2：部门与部门pk,3:小组与小组pk，4：个人与部门对赌，5：个人与公司对赌，6：部门与公司对赌")
    private Integer pkBettingType;

    /**
     * 赢的训练类型 1：pk，2：对赌
     */
    @Excel(name = "赢的训练类型 1：pk，2：对赌")
    private Integer winningTrainingType;

    /**
     * 用户归属部门ID
     */
    @Excel(name = "用户归属部门ID")
    private Long deptId;

    /**
     * 岗位IDS
     */
    @Excel(name = "岗位IDS")
    private String postIds;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    private String postNames;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String deptName;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String pictureUrl;

}
