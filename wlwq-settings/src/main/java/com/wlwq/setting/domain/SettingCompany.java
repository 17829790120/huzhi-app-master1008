package com.wlwq.setting.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 公司信息对象 setting_company
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
public class SettingCompany extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公司ID */
    private Long companyId;

    /** 公司名字 */
    @Excel(name = "公司名字")
    private String companyName;

    /** 公司LOGO */
    @Excel(name = "公司LOGO")
    private String companyLogo;

    /** 公司电话 */
    @Excel(name = "公司电话")
    private String companyPhone;

    /** 公司简介 */
    @Excel(name = "公司简介")
    private String companyContent;

    /** 当前版本号 */
    @Excel(name = "当前版本号")
    private String versionNumber;
}
