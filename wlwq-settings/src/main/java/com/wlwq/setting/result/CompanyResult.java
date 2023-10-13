package com.wlwq.setting.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName CompanyResult
 * @Description 公司信息返回值封装
 * @Date 2021/6/7 17:37
 * @Author Rick wlwq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResult implements Serializable {

    /** 公司名字 */
    private String companyName;

    /** 公司LOGO */
    private String companyLogo;

    /** 公司电话 */
    private String companyPhone;

    /** 公司简介 */
    private String companyContent;

    /** 当前版本号 */
    private String versionNumber;
}
