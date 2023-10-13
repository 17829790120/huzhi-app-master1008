package com.wlwq.setting.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 协议对象 setting_agreement
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingAgreement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 协议ID */
    private String agreementId;

    /** 协议标题 */
    @Excel(name = "协议标题")
    private String agreementTitle;

    /** 协议内容 */
    @Excel(name = "协议内容")
    private String agreementContent;

    /** 删除状态（0正常 1已删除） */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=已删除")
    private Integer delStatus;

}
