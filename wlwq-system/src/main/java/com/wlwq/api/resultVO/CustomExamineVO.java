package com.wlwq.api.resultVO;

import lombok.Data;

import java.util.List;

@Data
public class CustomExamineVO {
    /**
     * 客户id
     */
    private String customId;
    /**
     * 客户名称
     */
    private String customName;
    /**
     * 客户手机号
     */
    private String customPhone;
    /**
     * 创建时间
     */
    private String creatTime;
    /**
     * 客户意向
     */
    private String customLabel;
    private List<String> customLabels;
    /**
     * 最近跟进时间
     */
    private String customFollowLastTime;
    /**
     * 认领id
     */
    private String customClaimId;
}
