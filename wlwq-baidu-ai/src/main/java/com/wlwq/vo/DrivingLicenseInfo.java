package com.wlwq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName DrivingLicenseInfo
 * @Description 驾驶证识别结果封装
 * @Date 2021/7/17 17:14
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DrivingLicenseInfo implements Serializable {

    /** 姓名. */
    private String name;

    /** 有效期限-终. */
    private String overDate;

    /** 生日. */
    private String birthday;

    /** 证号. */
    private String idCardNumber;

    /** 住址. */
    private String address;

    /** 初次领证日期. */
    private String firstGetDate;

    /** 国籍. */
    private String nationality;

    /** 准驾车型. */
    private String canDrivingCarType;

    /** 性别. */
    private String sex;

    /** 有效期限-始. */
    private String startDate;

    /** 发证单位. */
    private String issueUnit;

    /** 记录. */
    private String record;

    /** 档案编号. */
    private String fileNumber;
}
