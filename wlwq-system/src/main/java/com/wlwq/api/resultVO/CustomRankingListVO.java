package com.wlwq.api.resultVO;

import lombok.Data;

@Data
public class CustomRankingListVO {
    /**
     * 用户id
     */
    private String customUserId;
    /**
     * 员工姓名
     */
    private String userName;
    /**
     * 所在部门
     */
    private String deptName;
    /**
     * 成交量
     */
    private String dealAmount;
    /**
     * 序号
     */
    private String rankNo;
    /**
     * 头像
     */
    private String headPortrait;
}
