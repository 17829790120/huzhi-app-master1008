package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 及时通讯群组对象 im_group
 *
 * @author gaoce
 * @date 2022-06-01
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImGroup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 及时通讯群组ID
     */
    private String imGroupId;

    /**
     * 群组缩略图
     */
    @NotBlank(message = "请传入群组缩略图")
    @Excel(name = "群组缩略图")
    private String groupThumb;

    /**
     * 群组名称
     */
    @NotBlank(message = "请传入群组名称")
    @Excel(name = "群组名称")
    private String groupName;

    /**
     * 群组人数
     */
    @Excel(name = "群组人数")
    private Long groupPeopleNum;

    /**
     * 群主ID
     */
    @Excel(name = "群主ID")
    private String accountId;

    /**
     * 区域省ID
     */
    @Excel(name = "区域省ID")
    private Long groupAreaProvinceId;

    /**
     * 区域市ID
     */
    @Excel(name = "区域市ID")
    private Long groupAreaCityId;

    /**
     * 排序(排序越大，越靠前)
     */
    @Excel(name = "排序(排序越大，越靠前)")
    private Long sortNum;

    /**
     * 已加入的人数
     */
    @Excel(name = "已加入的人数")
    private Long addPeopleNum;

    /**
     * 1：后台创建的群(平台群) 2：App创建的群(个人群)
     */
    @Excel(name = "群组类型", readConverterExp = "1=平台群, 2=个人群")
    private Integer groupType;

    /**
     * 安卓金额(元)
     */
    @Excel(name = "安卓金额(元)")
    private BigDecimal groupMoney;

    /**
     * 苹果的金额(元)
     */
    @Excel(name = "苹果的金额(元)")
    private BigDecimal appleMoney;

    /**
     * 苹果的编码
     */
    @Excel(name = "苹果的编码")
    private String appleEncoding;

    /**
     * 会员苹果的金额(元)
     */
    @Excel(name = "会员苹果的金额(元)")
    private BigDecimal memberAppleMoney;

    /**
     * 会员苹果的编码
     */
    @Excel(name = "会员苹果的编码")
    private String memberEncoding;

    /**
     * 0:否 1:已删除
     */
    private Integer delStatus;

    /**
     * 简介
     */
    @NotBlank(message = "请传入群组简介")
    @Excel(name = "简介")
    private String groupContent;

    /**
     * 一级行业类别ID
     */
    @Excel(name = "一级行业类别ID")
    private Long groupProfessionStairId;

    /**
     * 二级行业类别ID
     */
    @Excel(name = "二级行业类别ID")
    private Long groupProfessionSecondId;

    /**
     * unpaid 未支付 paid 已支付
     */
    @Excel(name = "支付状态", readConverterExp = "unpaid=未支付, paid=已支付")
    private String orderStatus;


    /////////////////////////////////////

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 省区域名称
     */
    @Excel(name = "省区域名称")
    private String provinceAreaName;

    /**
     * 市区域名称
     */
    @Excel(name = "市区域名称")
    private String cityAreaName;

    /**
     * 一级行业类别名称
     */
    @Excel(name = "一级行业类别名称")
    private String groupProfessionStairName;

    /**
     * 二级行业类别名称
     */
    @Excel(name = "二级行业类别名称")
    private String groupProfessionSecondName;
}
