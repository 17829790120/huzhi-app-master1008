package com.wlwq.api.resultVO.flower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gaoce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountFlowerResultVO implements Serializable {

    /**
     * 用户ID
     */
    private String accountId;

    /**
     * 用户名称
     */
    private String accountName;

    /**
     * 用户手机号
     */
    private String accountPhone;

    /**
     * 用户头像
     */
    private String accountHead;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 岗位名称
     */
    private String postNames;

    /**
     * 总红花数
     */
    private Integer totalFlower;
    /**
     * 日红花数
     */
    private Integer dayFlower;

}
