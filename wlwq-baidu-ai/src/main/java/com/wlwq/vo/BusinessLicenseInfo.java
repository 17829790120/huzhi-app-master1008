package com.wlwq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * 百度云返回示例json
 * {
 * "log_id": 490058765,
 * "words_result": {
 * "社会信用代码": {
 * "words": "10440119MA06M8503",
 * "location": {
 * "top": 296,
 * "left": 237,
 * "width": 178,
 * "height": 18
 * }
 * },
 * "组成形式": {
 * "words": "无",
 * "location": {
 * "top": -1,
 * "left": -1,
 * "width": 0,
 * "height": 0
 * }
 * },
 * "经营范围": {
 * "words": "商务服务业",
 * "location": {
 * "top": 587,
 * "left": 378,
 * "width": 91,
 * "height": 18
 * }
 * },
 * "成立日期": {
 * "words": "2019年01月01日",
 * "location": {
 * "top": 482,
 * "left": 1045,
 * "width": 119,
 * "height": 19
 * }
 * },
 * "法人": {
 * "words": "方平",
 * "location": {
 * "top": 534,
 * "left": 377,
 * "width": 39,
 * "height": 19
 * }
 * },
 * "注册资本": {
 * "words": "200万元",
 * "location": {
 * "top": 429,
 * "left": 1043,
 * "width": 150,
 * "height": 19
 * }
 * },
 * "证件编号": {
 * "words": "921MA190538210301",
 * "location": {
 * "top": 216,
 * "left": 298,
 * "width": 146,
 * "height": 16
 * }
 * },
 * "地址": {
 * "words": "广州市",
 * "location": {
 * "top": 585,
 * "left": 1041,
 * "width": 55,
 * "height": 19
 * }
 * },
 * "单位名称": {
 * "words": "有限公司",
 * "location": {
 * "top": 429,
 * "left": 382,
 * "width": 72,
 * "height": 19
 * }
 * },
 * "有效期": {
 * "words": "长期",
 * "location": {
 * "top": 534,
 * "left": 1045,
 * "width": 0,
 * "height": 0
 * }
 * },
 * "类型": {
 * "words": "有限责任公司(自然人投资或控股)",
 * "location": {
 * "top": 482,
 * "left": 382,
 * "width": 260,
 * "height": 18
 * }
 * }
 * },
 * "log_id": 1310106134421438464,
 * "words_result_num": 11
 * }
 */

/**
 * @ClassName BusinessLicenseInfo
 * @Description 营业执照识别结果封装
 * @Date 2021/7/17 16:45
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BusinessLicenseInfo implements Serializable {

    /** 社会信用代码. */
    private String socialCreditCode;

    /** 组成形式. */
    private String organizationType;

    /** 经营范围. */
    private String businessScope;

    /** 成立日期. */
    private String establishmentDate;

    /** 法人. */
    private String legalPerson;

    /** 注册资本. */
    private String registeredCapital;

    /** 证件编号. */
    private String businessLicenseCode;

    /** 地址. */
    private String site;

    /** 单位名称. */
    private String organizationName;

    /** 有效期. */
    private String validityDate;

    /** 类型. */
    private String type;
}
