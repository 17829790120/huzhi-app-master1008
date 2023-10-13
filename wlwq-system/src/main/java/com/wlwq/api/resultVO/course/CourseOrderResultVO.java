package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName CourseOrderResultVO
 * @Description 我的课程订单返回值封装
 * @Date 2021/1/29 11:50
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseOrderResultVO implements Serializable {

    /** 订单ID. */
    private String orderId;

    /** 课程ID. */
    private Long courseId;

    /** 课程名字. */
    private String courseName;

    /** 课程简介. */
    private String courseBrief;

    /** 课程图片. */
    private String courseImage;

    /** 课程价格. */
    private BigDecimal coursePrice;

    /** 实付. */
    private BigDecimal realPrice;

    /** 支付状态（0未支付1已支付）. */
    private Integer payStatus;

    /** 订单状态0已取消1待发货2待收货3已完成. */
    private Integer orderStatus;

    /** 支付剩余时间 默认30分钟后取消订单 . */
    private Date payOverDate;

    /** 订单创建时间. */
    private Date createDate;
}
