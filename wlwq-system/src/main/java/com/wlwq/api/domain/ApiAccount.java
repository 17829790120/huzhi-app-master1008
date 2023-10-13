package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户账户对象 api_account
 *
 * @author Renbowen
 * @date 2020-09-25
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("ApiAccount")
public class ApiAccount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String accountId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 用户手机号
     */
    @Excel(name = "用户手机号")
    private String phone;

    /**
     * 微信openid
     */
    private String wxOpenid;

    /**
     * 小程序授权登录返回值
     */
    private String sessionKey;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 性别（1男2女0未知）
     */
//    @Excel(name = "性别", readConverterExp = "1=男,2=女,0=未知")
    @Excel(name = "性别")
    private Integer sex;

    /**
     * 生日
     */
    @Excel(name = "生日")
    private String birthday;

    /**
     * 用户类型（1中介0普通用户）
     */
    private Integer type;

    /**
     * 删除状态（0未1已）
     */
    private Integer delStatus;


    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    /** 父类ID */
    private String parentId;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 个性签名 */
    @Excel(name = "个性签名")
    private String briefIntroduction;


    /**
     * UUID
     */
    private String uuid;

    /**
     * 父类邀请码
     */
    private String invitationCode;

    /**
     * 我的邀请码
     */
    private String myInvitationCode;

    /**
     * 融云token
     */
    private String rongToken;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信小程序openid
     */
    private String wxAppletOpenid;

    /**
     * 公司ID
     */
    private Long companyId;
    /**
     * 用户归属部门ID
     */
    private Long deptId;

    /**
     * 岗位IDS
     */
    private String postIds;


    /**
     * 0：否 1：禁用
     */
    private Long forbiddenStatus;

    /**
     * 工号
     */
    @Excel(name = "工号")
    private String jobNumber;

    /**
     * 共获得的红花数量
     */
    @Excel(name = "共获得红花数量")
    private Integer totalFlowerNum;

    /**
     * 剩余的红花数量
     */
    @Excel(name = "剩余红花数量")
    private Integer surplusFlowerNum;

    /**
     * 共获得的积分
     */
    @Excel(name = "共获得积分")
    private Integer totalScore;

    /**
     * 剩余的积分
     */
    @Excel(name = "剩余积分")
    private Integer surplusScore;

    /**
     * 绑定标识
     */
    @Excel(name = "绑定标识")
    private String bindingTag;

    //////////数据库没有的字段///////////////////

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 部门
     */
    @Excel(name = "部门")
    private String deptName;
    /**
     * 岗位
     */
    @Excel(name = "岗位")
    private String postNames;
    /**
     * 公司
     */
    @Excel(name = "公司")
    private String companyName;

    /**
     * 用户详细信息
     */
    private String accountName;

    /**
     * 1:积分称谓 2:勋章称谓
     */
    private Integer appellationType;

    /**
     * 一级徽章数量
     */
    @Excel(name = "一级徽章数量")
    private Integer medalCount;

    /**
     * 二级勋章数量
     */
    @Excel(name = "二级勋章数量")
    private Integer medalTwoCount;

    /**
     * 手机端权限
     */
    private String appPower;

    /**
     * 小组ids
     */
    private String groupInforIds;

    /**
     * 小组名称
     */
    private String groupInforName;

    /**
     * 岗位Id
     */
    private Long postId;

    /**
     * 0：只查自己的   不等于0的情况下：排除自己，查其他人
     */
    private String tag;

    /**
     * 年龄
     */
    private Integer age;
    /**
     * 职位等级 0普通员工 1部门级领导 2公司级领导
     */
    private String positionType;


    /**
     * 是否体验用户
     */
    private String isExperience;

    /**
     * 体验时长
     */
    private Date experienceTime;

    /**
     * 月份筛选 格式为2023-08-01
     */
    private String month;
}
