package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 热更新对象 hot_update_wgt
 *
 * @author lk
 * @date 2022-10-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotUpdateWgt extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 更新id
     */
    private Long id;

    /**
     * 版本号
     */
    @Excel(name = "版本号")
    private Long versionCode;

    /**
     * 更新的路径
     */
    @Excel(name = "更新的路径")
    private String downloadUrl;

    /**
     * 版本描述
     */
    @Excel(name = "版本描述")
    private String versionInfo;

    /**
     * 版本名称
     */
    @Excel(name = "版本名称")
    private String versionName;

    /**
     * forcibly = 强制更新, solicit = 弹窗确认更新, silent = 静默更新
     */
    @Excel(name = "forcibly = 强制更新, solicit = 弹窗确认更新, silent = 静默更新")
    private String updateType;

    /**
     * 1101:安卓 1102:ios
     */
    @Excel(name = "1101:安卓 1102:ios")
    private String type;

    /**
     * 该版本开放状态
     */
    @Excel(name = "该版本开放状态")
    private String openStatus;
}