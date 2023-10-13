package com.wlwq.setting.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * APP版本控制对象 setting_app_version
 *
 * @author Rick wlwq
 * @date 2022-04-13
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingAppVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 版本ID */
    private Long versionId;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long versionNo;

    /** 资源路径 */
    @Excel(name = "资源路径")
    private String resourceUrl;

    /** 版本名称 */
    @Excel(name = "版本名称")
    private String versionTitle;

    /** 版本描述 */
    @Excel(name = "版本描述")
    private String versionDescription;

    /** forcibly = 强制更新, solicit = 弹窗确认更新, silent = 静默更新 */
    @Excel(name = "forcibly = 强制更新, solicit = 弹窗确认更新, silent = 静默更新")
    private String updateType;

    /** 1101:安卓 1102:ios */
    @Excel(name = "1101:安卓 1102:ios")
    private String resourceType;

    /** 该版本开放状态(0关闭 1开放) */
    @Excel(name = "该版本开放状态(0关闭 1开放)")
    private Integer openStatus;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
