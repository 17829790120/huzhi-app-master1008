package com.wlwq.params.examine;

import com.wlwq.api.domain.ExamineFile;
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
 * 客户成交记录提交参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomCustomInfoParamVO implements Serializable {

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
     * 文件链接
     */
    private String fileUrl;
    /**
     * 文件列表
     */
    private List<ExamineFile> fileList;

    /**
     * 0:未审核；1：审核未通过；2：审核已通过
     */
    private Long auditStatus;

    /**
     * 发起者所在部门名称
     */
    private String deptName;

    /**
     * 被审批主键id
     */
    private String meetingTrainingId;

    /**
     * 审批类型ID
     */
    private Integer examineModuleId;

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
