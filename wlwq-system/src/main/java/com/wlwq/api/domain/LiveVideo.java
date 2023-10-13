package com.wlwq.api.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 直播列表对象 live_video
 *
 * @author wwb
 * @date 2023-05-13
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("LiveVideo")
public class LiveVideo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 直播id
     */
    private String liveVideoId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String synopsis;

    /**
     * 详情
     */
    @Excel(name = "详情")
    private String details;

    /**
     * 资源链接
     */
    @Excel(name = "资源链接")
    private String resourceUrl;

    /**
     * 资源类型：:1：图片；2：视频
     */
    @Excel(name = "资源类型：:1：图片；2：视频")
    private Integer resourceType;

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
     * 直播时长（分钟）
     */
    @Excel(name = "直播时长", readConverterExp = "分钟")
    private Long duration;

    /**
     * 直播者名称
     */
    @Excel(name = "直播者名称")
    private String liveStreamerName;

    /**
     * 直播者职位
     */
    @Excel(name = "直播者职位")
    private String liveStreamerPosition;

    /**
     * 直播者头像
     */
    @Excel(name = "直播者头像")
    private String liveStreamerHead;

    /**
     * 直播链接
     */
    @Excel(name = "直播链接")
    private String liveUrl;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;
    /**
     * 删除状态（0未1已）
     */
    @Excel(name = "删除状态", readConverterExp = "0=未1已")
    private Integer delStatus;
    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;
/////////////////////////////////////////////////////////////////
    /**
     * 报名人数
     */
    private Integer registrationsNumber;

    /**
     * 报名状态（0：未报名；1：已报名）
     */
    private Integer registrationsStatus;
}
