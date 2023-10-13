package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName MyCourseResultVO
 * @Description 我的课程返回值封装
 * @Date 2021/1/29 10:54
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MyCourseResultVO implements Serializable {

    /** 课程ID. */
    private Long courseId;

    /** 课程名字. */
    private String courseName;

    /** 课程简介. */
    private String courseBriefTitle;

    /** 课程封面图. */
    private String courseImage;

    /** 课程有效期. */
    private Date validDate;

    /** 课程是否有效 1有效0失效. */
    private Integer validStatus;

    /** 课程价格. */
    private BigDecimal coursePrice;

    /** 苹果价格. */
    private BigDecimal applePrice;

}
