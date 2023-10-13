package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 目标训练记录对象 target_training_record
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
@Alias("TargetTrainingRecord")
public class TargetTrainingRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 目标训练记录主键id
     */
    private String targetTrainingRecordId;

    /**
     * 目标训练主键id
     */
    @Excel(name = "目标训练主键id")
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
    private Long sortNum;

    /**
     * 申请者公司ID
     */
    @Excel(name = "申请者公司ID")
    private Long applicantCompanyId;

    /**
     * 申请者部门ID
     */
    @Excel(name = "申请者部门ID")
    private Long applicantDeptId;

    /**
     * 申请者岗位IDS
     */
    @Excel(name = "申请者岗位IDS")
    private Long applicantPostIds;

    /**
     * 申请者公司名称
     */
    @Excel(name = "申请者公司名称")
    private String applicantCompanyName;

    /**
     * 申请者部门名称
     */
    @Excel(name = "申请者部门名称")
    private String applicantDeptName;

    /**
     * 申请者岗位名称
     */
    @Excel(name = "申请者岗位名称")
    private String applicantPostName;

    /**
     * 申请者id
     */
    @Excel(name = "申请者id")
    private String applicantId;

    /**
     * 申请者头像
     */
    @Excel(name = "申请者头像")
    private String applicantrHeadPortrait;

    /**
     * 申请者昵称
     */
    @Excel(name = "申请者昵称")
    private String applicantNickName;

    /**
     * 现在的自己图片
     */
    @Excel(name = "现在的自己图片")
    private String nowMyselfImg;

    /**
     * 自我介绍图片
     */
    @Excel(name = "自我介绍图片")
    private String selfIntroductionImg;

    /**
     * 理想的自己图片
     */
    @Excel(name = "理想的自己图片")
    private String idealSelfImg;

    /**
     * 自我介绍
     */
    @Excel(name = "自我介绍")
    private String selfIntroduction;

    /**
     * 图片链接
     */
    @Excel(name = "图片链接")
    private String imgUrl;

    /**
     * 文件链接
     */
    @Excel(name = "文件链接")
    private String fileUrl;

    /**
     * 个性签名
     */
    @Excel(name = "个性签名")
    private String briefIntroduction;

    /**
     * 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回；6：已取消
     */
    @Excel(name = "审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回")
    private Integer auditStatus;

    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 审批详情发起ID
     */
    private String  examineInitiateIdContent;

    /**
     * 通过审核后编辑次数
     */
    private String editNum;
    /**
     * 编辑时间
     */
    private Date editDate;
}
