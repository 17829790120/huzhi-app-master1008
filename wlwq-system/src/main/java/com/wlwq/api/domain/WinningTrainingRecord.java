package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.api.paramsVO.TemplateFieldParamVO;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

/**
 * 人与人pk对象 winning_training_record
 *
 * @author wwb
 * @date 2023-06-07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("WinningTrainingRecord")
public class WinningTrainingRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 赢的训练记录主键id
     */
    private String winningTrainingRecordId;

    /**
     * 内容
     */
    @Excel(name = "内容")
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
     * 排序，数字越小越靠前
     */
    @Excel(name = "排序，数字越小越靠前")
    private Long sortNum;

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
     * 甲方名称
     */
    @Excel(name = "甲方名称")
    private String partyAName;

    /**
     * 乙方名称
     */
    @Excel(name = "乙方名称")
    private String partyBName;

    /**
     * 甲方头像
     */
    @Excel(name = "甲方头像")
    private String partyAHeadImg;

    /**
     * 乙方头像
     */
    @Excel(name = "乙方头像")
    private String partyBHeadImg;

    /**
     * pk/对赌开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String enclosure;
    /**
     * 0:未开始；1：进行中；2：已结束
     */
    @Excel(name = "0:未开始；1：进行中；2：已结束")
    private Long pkStatus;

    /**
     * pk/对赌照片
     */
    @Excel(name = "pk/对赌照片")
    private String pictureUrl;

    /**
     * 甲方目标
     */
    @Excel(name = "甲方目标")
    private String partyATarget;

    /**
     * 乙方目标
     */
    @Excel(name = "乙方目标")
    private String partyBTarget;
    ///////////////////////////////////////////////////

    /**
     * 字段集合
     */
    private List<TemplateFieldParamVO> templateFieldList;

    /**
     * 模版内容
     */
    @Excel(name = "模版内容")
    private String templateContent;

    /**
     * 模板类型
     */
    private Integer templateType;

    /**
     * 甲方id
     */
    @Excel(name = "甲方id")
    private String partyAAccountId;

    /**
     * 乙方id
     */
    @Excel(name = "乙方id")
    private String partyBAccountId;

    /**
     * 对赌金额
     */
    @Excel(name = "对赌金额")
    private Long betAmount;

    /**
     * 公式时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date formulaTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 甲方部门id
     */
    @Excel(name = "甲方部门id")
    private String partyADeptId;

    /**
     * 乙方部门id
     */
    @Excel(name = "乙方部门id")
    private String partyBDeptId;

    /**
     * 甲方部门名称
     */
    @Excel(name = "甲方部门名称")
    private String partyADeptName;

    /**
     * 乙方部门名称
     */
    @Excel(name = "乙方部门名称")
    private String partyBDeptName;

    /**
     * 胜利者id
     */
    @Excel(name = "胜利者id")
    private String winnerId;

    /**
     * 胜利者名称
     */
    @Excel(name = "胜利者名称")
    private String winnerName;

    /**
     * 甲方部门/小组人数
     */
    @Excel(name = "甲方部门/小组人数")
    private Integer partyADeptAccountCount;

    /**
     * 乙方部门/小组人数
     */
    @Excel(name = "乙方部门/小组人数")
    private Integer partyBDeptAccountCount;

    /**
     * 甲方部门/小组人员id
     */
    @Excel(name = "甲方部门/小组人员id")
    private String partyADeptAccountIds;

    /**
     * 乙方部门/小组人员id
     */
    @Excel(name = "乙方部门/小组人员id")
    private String partyBDeptAccountIds;

    /**
     * 甲方部门/小组人员名称
     */
    @Excel(name = "甲方部门/小组人员名称")
    private String partyADeptAccountNames;

    /**
     * 乙方部门/小组人员名称
     */
    @Excel(name = "乙方部门/小组人员名称")
    private String partyBDeptAccountNames;

    /**
     * pk/对赌结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 实时跟进岗位id
     */
    @Excel(name = "实时跟进岗位id")
    private String realFollowPostid;

    /**
     * 实时跟进岗位名称
     */
    @Excel(name = "实时跟进岗位名称")
    private String realFollowPostName;
    /**
     * 奖励积分
     */
    private Integer rewardScore;
//////////////////////////////////////////////////////////
    /**
     * 训练跟进记录对象
     */
    private List<WinningTrainingFollowUpRecord> winningTrainingFollowUpRecordList;

}
