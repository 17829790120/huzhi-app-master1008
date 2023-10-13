package com.wlwq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName IdCardInfo
 * @Description 身份证信息解析
 * @Date 2021/2/7 15:17
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IdCardInfo implements Serializable {

    /** 真实姓名. */
    private String realName;

    /** 民族. */
    private String nation;

    /** 地址. */
    private String address;

    /** 身份证号码. */
    private String idCardNumber;

    /** 生日. */
    private String birthday;

    /** 性别. */
    private String sex;

    /** 失效日期. */
    private String pastDueDate;

    /** 签发日期. */
    private String signDate;

    /** 签发机关. */
    private String signDept;

    /** 身份证头像. */
    private String headPhoto;

    /** 身份证类型 常量类 IdCardRiskTypeConstant. */
    private String riskType;

    /** 身份证校验结果 常量说 IdCardNumberTypeConstant. */
    private Integer idCardNumberType;
}
