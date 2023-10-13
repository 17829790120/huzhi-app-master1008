package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName CourseOrderDetailResultVO
 * @Description 订单详情返回值封装
 * @Date 2021/1/29 16:42
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseOrderDetailResultVO implements Serializable {

    /** 订单ID. */
    private String orderId;

    /** 课程ID. */
    private Long courseId;

    /** 课程名字. */
    private String courseName;

    /** 课程封面图. */
    private String courseImage;

    /** 课程简介. */
    private String courseBrief;

    /** 课程价格. */
    private BigDecimal coursePrice;

    /** 实际支付金额. */
    private BigDecimal realPrice;

    /** 0：后台支付 1支付宝 2 微信. */
    private Integer payType;

    /** 支付状态（0未支付1已支付）. */
    private Integer payStatus;

    /** 订单状态 0已取消1待发货2待收货3已完成. */
    private Integer orderStatus;

    /** 支付时间. */
    private Date payDate;

    /** 订单创建时间. */
    private Date createDate;

    /** 支付截止时间. */
    private Date payOverDate;
}
