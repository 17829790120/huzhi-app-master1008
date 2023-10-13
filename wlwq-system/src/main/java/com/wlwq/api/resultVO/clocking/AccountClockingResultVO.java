package com.wlwq.api.resultVO.clocking;

import com.wlwq.common.annotation.Excel;
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
public class AccountClockingResultVO implements Serializable {

    /**
     * 用户ID
     */
    private String accountId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String accountName;

    /**
     * 用户手机号
     */
    @Excel(name = "手机号")
    private String accountPhone;

    /**
     * 性别 1：男 2：女 0：未知
     */
    @Excel(name = "性别", readConverterExp = "0=未知,1=男,2=女")
    private Integer sex;

    /**
     * 用户头像
     */
    private String accountHead;

    /**
     * 公司
     */
    @Excel(name = "公司")
    private String companyName;

    /**
     * 部门名称
     */
    @Excel(name = "部门")
    private String deptName;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位")
    private String postNames;

    /**
     * 考勤月份
     */
    @Excel(name = "考勤月份")
    private String month;


    /**
     * 正常天数
     */
    @Excel(name = "正常天数")
    private Integer normalDay;

    /**
     * 出勤天数
     */
    @Excel(name = "出勤天数")
    private Integer clockingCount;


    /**
     * 请假次数
     */
    @Excel(name = "请假次数")
    private Integer leaveCount;

    /**
     * 旷工次数
     */
    @Excel(name = "旷工次数")
    private Integer absenteeismDay;

    /**
     * 迟到次数
     */
    @Excel(name = "迟到次数")
    private Integer beLateCount;

    /**
     * 早退次数
     */
    @Excel(name = "早退次数")
    private Integer leaveEarlyCount;

    /**
     * 缺卡次数
     */
    @Excel(name = "缺卡次数")
    private Integer lackCount;


    /**
     * 异常天数
     */
    @Excel(name = "异常天数")
    private Integer abnormalDay;

    /**
     * 休息天数
     */
    @Excel(name = "休息天数")
    private Integer restDay;


    /**
     * 外勤次数
     */
    @Excel(name = "外勤次数")
    private Integer signCount;

    /**
     * 补卡申请次数
     */
    @Excel(name = "补卡申请次数")
    private Integer reissueCount;

}
