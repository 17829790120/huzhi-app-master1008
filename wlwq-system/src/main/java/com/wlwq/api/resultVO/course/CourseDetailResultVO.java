package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName CourseDetailResultVO
 * @Description 课程详情返回值封装
 * @Date 2021/1/20 15:54
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseDetailResultVO implements Serializable {

    /** 课程ID. */
    private Long courseId;

    /** 视频封面图. */
    private String videoImage;

    /** 视频链接. */
    private String videoUrl;

    /** 课程价格. */
    private BigDecimal coursePrice;

    /** 苹果价格. */
    private BigDecimal applePrice;

    /** 课程标题. */
    private String courseTitle;

    /** 课程简介标题. */
    private String briefTitle;

    /** 课程详情介绍. */
    private String courseContent;

    /** 收藏标识 true已收藏false未收藏. */
    private Boolean collectFlag;

    /** 分享链接. */
    private String shareUrl;

    /** 购买人数. */
    private Integer buyNumber;

    /** 老师微信二维码. */
    private String teacherWechatQRCode;

    /** 老师微信号. */
    private String teacherWechatNumber;

    /** 购买状态 0未购买过1已购买2已购买但过期. */
    private Integer buyStatus;

    /** 是否已评论 true已评论false未评论. */
    private Boolean commentStatus;

    /** 是否试看 true试看false需购买. */
    private Boolean tryWatchFlag;
}
