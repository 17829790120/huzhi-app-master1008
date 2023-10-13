package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 用户勋章记录对象 account_medal_record
 *
 * @author gaoce
 * @date 2023-06-09
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountMedalRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户勋章记录ID
     */
    private String accountMedalRecordId;

    /**
     * 勋章名称
     */
    @Excel(name = "勋章名称")
    private String medalName;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
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
    @Excel(name = "用户头像")
    private String accountHead;

    /**
     * 已获得图标
     */
    @Excel(name = "获得图标")
    private String alreadyIcon;

    /**
     * 本次获得积分
     */
    @Excel(name = "本次获得积分")
    private Integer score;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间",dateFormat  = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 岗位IDS
     */
    private String postIds;
    /**
     * 勋章ID
     */
    private Long accountMedalId;

    /** 备注 */
    private String remark;

    /**
     * 勋章父类ID
     */
    private Long medalParentId;


    /**
     * 0:否 1:删除
     */
    private Integer delStatus;

    ///////////////////////////////////

    /**
     * 0：查询父类勋章 1:查询子类勋章
     */
    private Integer tag;

    /**
     * 二级勋章分类列表
     */
    private List<AccountMedal> childList;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
