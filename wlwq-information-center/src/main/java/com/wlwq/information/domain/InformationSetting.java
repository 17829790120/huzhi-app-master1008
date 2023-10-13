package com.wlwq.information.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 资讯设置对象 information_setting
 * 
 * @author Rick wlwq
 * @date 2021-04-19
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InformationSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 资讯设置ID */
    @Excel(name = "资讯设置ID")
    private Long informationSettingId;

    /** 资讯设置标题 */
    @Excel(name = "资讯设置标题")
    private String informationSettingTitle;

    /** 资讯设置状态（0不开启 1开启） */
    @Excel(name = "资讯设置状态", readConverterExp = "0=不开启,1=开启")
    private Integer informationSettingStatus;
}
