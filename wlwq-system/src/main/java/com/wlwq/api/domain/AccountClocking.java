package com.wlwq.api.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 用户考勤对象 account_clocking
 *
 * @author gaoce
 * @date 2023-05-15
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountClocking extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户考勤ID
     */
    private String accountClockingId;

    /**
     * 用户ID
     */
    private String accountId;

    /**
     * 用户姓名
     */
    @Excel(name = "用户姓名")
    private String accountName;

    /**
     * 用户手机号
     */
    @Excel(name = "用户手机号")
    private String accountPhone;

    /**
     * 用户头像
     */
//    @Excel(name = "用户头像")
    private String accountHead;

    /**
     * 打卡日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "打卡日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date clockingDate;

    /**
     * 上班打卡类型 0：未打卡 1：正常打卡 2：外勤打卡
     */
    @Excel(name = "上班打卡类型", readConverterExp = "0=未打卡,1=正常打卡,2=外勤打卡")
    private Integer clockingType;



    /**
     * 上班打卡状态 0：未打卡 1：正常 2：迟到 3：早退  5：旷工 6：补卡
     */
    @Excel(name = "上班打卡状态", readConverterExp = "0=未打卡,1=正常,2=迟到,3=早退,5=旷工,6=补卡")
    private Integer clockingStatus;


    /**
     * 上班打卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上班打卡时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date onWorkTime;

    /**
     * 上班打卡地点
     */
    @Excel(name = "上班打卡地点")
    private String clockingAddress;


    /** 上班班外勤打卡备注 */
    @Excel(name = "上班班外勤打卡备注")
    private String remark;


    /**
     * 下班打卡类型  0：未打卡 1：正常打卡 2：外勤打卡
     */
    @Excel(name = "下班打卡类型",readConverterExp = "0=未打卡,1=正常打卡,2=外勤打卡")
    private Integer offClockingType;


    /**
     * 下班打卡状态 0：未打卡 1：正常 2：迟到 3：早退 5：旷工 6：补卡
     */
    @Excel(name = "下班打卡状态", readConverterExp = "0=未打卡,1=正常,2=迟到,3=早退,5=旷工,6=补卡")
    private Integer offClockingStatus;


    /**
     * 下班打卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下班打卡时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date offWorkTime;


    /**
     * 下班打卡地点
     */
    @Excel(name = "下班打卡地点")
    private String offClockingAddress;


    /**
     * 上班外勤打卡图片
     */
//    @Excel(name = "上班外勤打卡图片")
    private String pics;


    /**
     * 下班外勤打卡图片
     */
//    @Excel(name = "下班外勤打卡图片")
    private String offClockingPics;

    /**
     * 下班外勤打卡备注
     */
    @Excel(name = "下班外勤打卡备注")
    private String offClockingRemark;

    /**
     * 考勤打卡/外勤打卡赠送积分
     */
//    @Excel(name = "考勤打卡/外勤打卡赠送积分")
    private Long clockingScore;

    /**
     * 部门ID
     */
//    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 公司ID
     */
//    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 岗位IDS
     */
//    @Excel(name = "岗位IDS")
    private String postIds;

    /**
     * 0:否 1:删除
     */
    private Integer delStatus;

    //////////////////////////

    /**
     * 月份查询
     */
    private String month;

    /**
     * 1：正常打卡 2：外勤打卡
     */
    private Integer status;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 1:缺卡
     */
    private Integer tag;

    /**
     * 日期 格式为年月日
     */
    private String date;
}
