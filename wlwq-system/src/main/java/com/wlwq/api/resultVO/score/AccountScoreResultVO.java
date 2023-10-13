package com.wlwq.api.resultVO.score;

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
public class AccountScoreResultVO implements Serializable {

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
     * 总积分
     */
    private Integer totalScore;

    /**
     * 日积分
     */
    private Integer dayScore;

    /**
     * 称谓名称
     */
    private String appellationName;
}
