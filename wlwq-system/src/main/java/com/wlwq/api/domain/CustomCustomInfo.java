package com.wlwq.api.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 客户对象 custom_custom_info
 * 
 * @author wlwq
 * @date 2023-06-02
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomCustomInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;


    private String customId;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 客户来源
     */
    @Excel(name = "客户来源")
    private String customSource;
    /**
     * 客户头像
     */
    @Excel(name = "客户头像")
    private String customAnnex;

    /**
     * 客户姓名
     */
    @Excel(name = "客户姓名")
    private String customName;

    /**
     * 客户性别
     */
    @Excel(name = "客户性别")
    private String customSex;

    /**
     * 客户生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "客户生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date customBirthday;

    /**
     * 客户标签
     */
    @Excel(name = "客户标签")
    private String customLabel;
    private transient List<String> customLabelList;

    /**
     * 客户手机号
     */
    @Excel(name = "客户手机号")
    private String customPhone;

    /**
     * 客户等级
     */
    @Excel(name = "客户等级")
    private String customGrade;

    /**
     * 客户公司名称
     */
    @Excel(name = "客户公司名称")
    private String customCompanyName;

    /**
     * 客户住址
     */
    @Excel(name = "客户住址")
    private String customAddress;

    /**
     * 客户需求
     */
    @Excel(name = "客户需求")
    private String customDemand;

    /**
     * 客户负责人
     */
    @Excel(name = "客户负责人")
    private Long customResponsible;

    private transient String customResponsibleName;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String customRemark;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private String delStatus;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sortNum;

    /**
     * 是否显示
     */
    @Excel(name = "是否显示")
    private Long showStatustiny;

    /**
     * 客户决策人0否1是
     */
    @Excel(name = "客户决策人0否1是")
    private Long customDecisions;
    private transient String customDecisionsName;

    /**
     * 0平台录入1App录入
     */
    @Excel(name = "0平台录入1App录入")
    private Long addSource;

    /**
     * 0未认领1认领
     */
    @Excel(name = "0未认领1认领")
    private Long claimStatus;
    private transient String claimStatusName;

}
