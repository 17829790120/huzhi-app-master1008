package com.wlwq.params.examine;

import com.wlwq.api.domain.ExamineFile;
import com.wlwq.api.domain.TargetContent;
import com.wlwq.api.domain.TargetTrainingRecordContent;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author gaoce
 * 目标训练记录提交参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TargetTrainingRecordParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 目标训练记录主键id
     */
    private String targetTrainingRecordId;

    /**
     * 目标训练主键id
     */
    @NotNull(message = "请传入目标训练")
    private String targetTrainingId;

    /**
     * 标题
     */
    private String title;

    /**
     * 年份
     */
    private Integer years;

    /**
     * 排序，数字越小越靠前
     */
    private Long sortNum;

    /**
     * 申请者公司ID
     */
    private Long applicantCompanyId;

    /**
     * 申请者部门ID
     */
    private Long applicantDeptId;

    /**
     * 申请者岗位IDS
     */
    private Long applicantPostIds;

    /**
     * 申请者公司名称
     */
    private String applicantCompanyName;

    /**
     * 申请者部门名称
     */
    private String applicantDeptName;

    /**
     * 申请者岗位名称
     */
    private String applicantPostName;

    /**
     * 申请者id
     */
    private String applicantId;

    /**
     * 申请者头像
     */
    private String applicantrHeadPortrait;

    /**
     * 申请者昵称
     */
    private String applicantNickName;

    /**
     * 现在的自己图片
     */
    private String nowMyselfImg;

    /**
     * 理想的自己图片
     */
    private String idealSelfImg;

    /**
     * 自我介绍图片
     */
    private String selfIntroductionImg;

    /**
     * 自我介绍
     */
    private String selfIntroduction;

    /**
     * 图片链接
     */
    private String imgUrl;

    /**
     * 文件链接
     */
    private String fileUrl;
    /**
     * 文件列表
     */
    private List<ExamineFile> fileList;

    /**
     * 个性签名
     */
    private String briefIntroduction;

    /**
     * 0:未审核；1：审核未通过；2：审核已通过
     */
    private Long auditStatus;

    /**
     * 发起者所在部门名称
     */
    private String deptName;

    /**
     * 目标主键id
     */
    private String targetId;

    /**
     * 会议训练主键id
     */
    @Excel(name = "会议训练主键id")
    private String meetingTrainingId;

    /**
     * 父级标题
     */
    private String parentTitle;

    /**
     * 子级标题
     */
    private String subTitle;

    /**
     * 子级内容
     */
    private String subContent;

    /**
     * 证明文件
     */
    private String documentationUrl;

    /**
     * 自定义字段id
     */
    private String targetTrainingRecordContentId;

    /**
     * 审批类型ID
     */
    private Integer examineModuleId;

    /**
     * 子集列表
     */
    List<TargetContent> targetContentList;

    /**
     * 子集列表
     */
    List<TargetTrainingRecordContent> targetTrainingRecordContentList;

    /**
     * 0:否 1:删除
     */
    private Integer delStatus;

    /**
     * 显示状态 0：显示 1：隐藏
     */
    private Integer showStatus;

    /**
     * 审批详情发起ID
     */
    private String  examineInitiateIdContent;

    /**
     * 成交客户id
     */
    private String customId;

    /**
     * 客户姓名
     */
    private String customName;

    /**
     * 客户头像
     */
    private String customHeadPortrait;

    /**
     * 客户手机号
     */
    private String customPhone;

    /**
     * 客户成交金额
     */
    private BigDecimal customeStrikeMoney;

    /**
     * 客户成交附件
     */
    private String customEnclosure;


}
