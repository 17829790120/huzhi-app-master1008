package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 直播报名对象 live_registration
 * 
 * @author wwb
 * @date 2023-05-15
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("LiveRegistration")
public class LiveRegistration extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String liveRegistrationId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String headPortrait;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 用户归属部门ID
     */
    @Excel(name = "用户归属部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String deptName;

    /**
     * 直播id
     */
    @Excel(name = "直播id")
    private String liveVideoId;

    /**
     * 删除状态（0未1已）
     */
    @Excel(name = "删除状态", readConverterExp = "0=未1已")
    private Integer delStatus;
///////////////////////////////////////////////////////////
    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String synopsis;
    /**
     * 直播开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 直播结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 报名人数
     */
    private Integer registrationsNumber;

    private LiveVideo liveVideo;
}
