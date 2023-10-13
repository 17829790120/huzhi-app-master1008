package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * 红花记录对象 account_flower_record
 *
 * @author gaoce
 * @date 2023-05-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountFlowerRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 红花记录ID
     */
    private String accountFlowerRecordId;

    /**
     * 记录名称
     */
    @Excel(name = "记录名称")
    private String recordName;

    /**
     * 记录数量
     */
    @Excel(name = "记录数量")
    private Integer recordNum;

    /**
     * 发起者用户ID
     */
    private String giveAccountId;

    /**
     * 发起用户姓名
     */
    @Excel(name = "发起用户姓名")
    private String giveAccountName;

    /**
     * 发起用户手机号
     */
    @Excel(name = "发起用户手机号")
    private String giveAccountPhone;

    /**
     * 发起用户头像
     */
    private String giveAccountHead;

    /**
     * 接收用户ID
     */
    private String accountId;

    /**
     * 接收用户姓名
     */
    @Excel(name = "接收用户姓名")
    private String accountName;

    /**
     * 接收用户手机号
     */
    @Excel(name = "接收用户手机号")
    private String accountPhone;

    /**
     * 接收用户头像
     */
    private String accountHead;

    /**
     * 是否删除 0：否 1：已删除
     */
    private Integer delStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 接收用户职位
     */
    @Excel(name = "接收用户职位")
    private String postNames;

    /**
     * 发起用户职位
     */
    @Excel(name = "发起用户职位")
    private String givePostNames;


    ////////////////////////////

    /**
     * 查询消耗花花
     */
    private Integer giveStatus;

    /**
     * 判断是否计算平台值  1不计算
     */
    private Integer incomeStatus;

    /**
     * 1：收入 2：消耗
     */
    private Integer status;

}
