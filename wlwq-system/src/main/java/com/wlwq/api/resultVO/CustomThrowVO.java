package com.wlwq.api.resultVO;

import lombok.Data;

import java.util.Date;

@Data
public class CustomThrowVO {
    /**
     * 客户id
     */
    private String customId;
    /**
     * 认领id
     */
    private String customClaimId;
    /**
     * 回访期限
     */
    private Date customFollowTime;
    /**
     * 最后跟进时间
     */
    private Date customFollowLastTime;
    /**
     * 等级
     */
    private String customGrade;
    /**
     * 公司id
     */
    private String companyId;
    /**
     * 回访等级日
     */
    private String customDays;
}
