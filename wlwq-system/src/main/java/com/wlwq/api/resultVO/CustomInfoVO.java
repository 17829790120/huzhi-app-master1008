package com.wlwq.api.resultVO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomInfoVO {
    private String customId;
    /**
     * 客户来源
     */
    @Excel(name = "客户来源")
    private String customSource;

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
     * 客户头像
     */
    @Excel(name = "客户头像")
    private String customAnnex;
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
    private List<String> customLabelList;

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
     * 备注
     */
    @Excel(name = "备注")
    private String customRemark;
    /**
     * 客户决策人0是1否
     */
    @Excel(name = "客户决策人0是1否")
    private Long customDecisions;
    private String customDecisionsName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 客户负责人
     */
    private String customResponsible;
    private String customResponsibleName;
    /**
     * 跟进记录
     */
    private List<CustomFollowVO> customFollowVOs;
}
