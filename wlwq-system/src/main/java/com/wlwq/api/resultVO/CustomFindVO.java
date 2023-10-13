package com.wlwq.api.resultVO;

import lombok.Data;

@Data
public class CustomFindVO {
    /**
     * 客户状态
     */
    private String status;
    /**
     * 客户来源
     */
    private String customSource;
    /**
     * 回访日
     */
    private String type;
    /**
     * 当前用户级别
     */
    private String positionType;
    /**
     * 客户名
     */
    private String customName;
    /**
     * 当期是否跟进
     */
    private String isFollow;
    /**
     * 查询日期
     */
    private String date;
    /**
     * 部门
     */
    private String deptId;
    /**
     * 公司
     */
    private String companyId;
    /**
     * 用户
     */
    private String userId;
}
