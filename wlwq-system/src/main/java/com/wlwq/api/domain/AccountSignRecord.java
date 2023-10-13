package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 用户签到记录对象 account_sign_record
 *
 * @author gaoce
 * @date 2023-07-03
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSignRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户签到记录ID
     */
    private String accountSignRecordId;

    /**
     * 用户ID
     */
//    @Excel(name = "用户ID")
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
     * 签到地址
     */
    @NotBlank(message = "请传入签到地址")
    @Excel(name = "签到地址")
    private String signAddress;

    /**
     * 签到图片
     */
    @Excel(name = "签到图片")
    private String signPics;

    /**
     * 签到备注
     */
    @Excel(name = "签到备注")
    private String signRemark;

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

    ///////////////////////////////////////////////////

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 月份筛选
     */
    private String month;
}
