package com.wlwq.api.resultVO;

import lombok.Data;

@Data
public class CustomUserVO {
    /**
     * 客户名称
     */
    private String customName;
    /**
     * 负责人
     */
    private String nickName;
    /**
     *回访期限
     */
    private String customFollowTime;
    /**
     *最后跟进时间
     */
    private String customFollowLastTime;
    /**
     *认领时间
     */
    private String claimTime;
    /**
     *放弃客户时间
     */
    private String releaseTime;
    /**
     *0跟进中1战败2已成交
     */
    private String status;
}
