package com.wlwq.api.resultVO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MyCustomVO {
    private String customId;
    /**
     * 客户姓名
     */
    @Excel(name = "客户姓名")
    private String customName;
    /**
     * 客户标签
     */
    @Excel(name = "客户标签")
    private String customLabel;
    private List<String> customLabelList;
    /**
     * 客户等级
     */
    @Excel(name = "客户等级")
    private String customGrade;
    /**
     * 客户需求
     */
    @Excel(name = "客户需求")
    private String customDemand;
    /**
     * 客户头像
     */
    @Excel(name = "客户头像")
    private String customAnnex;
    /**
     * 回访期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回访期限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date customFollowTime;
    /**
     * 最后跟进时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后跟进时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date customFollowLastTime;
    /**
     * 客户手机号
     */
    @Excel(name = "客户手机号")
    private String customPhone;
    /**
     * 0跟进中1战败2已成交
     */
    @Excel(name = "0跟进中1战败2已成交")
    private String status;
    /**
     * 认领id
     */
    private String customClaimId;
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 0后台 1app
     */
    private String addSource;
    /**
     * 当期是否跟进
     */
    private String isFollow;
}
