package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;


/**
 * 举报记录对象 report_record
 *
 * @author wlwq
 * @date 2023-07-17
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("ReportRecord")
public class ReportRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 举报记录id
     */
    private String reportRecordId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 被举报id
     */
    @Excel(name = "被举报id")
    private Long targetId;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像")
    private String accountHead;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String accountName;

    /**
     * 用户手机号
     */
    @Excel(name = "用户手机号")
    private String accountPhone;

    /**
     * 举报内容
     */
    @Excel(name = "举报内容")
    private String reportContent;

    /**
     * 举报图片
     */
    @Excel(name = "举报图片")
    private String reportImages;

    /**
     * 是否已读(1已读2未读)
     */
    @Excel(name = "是否已读(1已读2未读)")
    private Long readStatus;

    /**
     * 处理状态(1已处理2未处理)
     */
    @Excel(name = "处理状态(1已处理2未处理)")
    private Long processingStatus;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String contactWay;

    /**
     * 举报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "举报时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportTime;


//    /**
//     * 举报ID
//     */
//    private String reportRecordId;
//
//    /**
//     * 用户ID
//     */
//    @Excel(name = "用户ID")
//    private String accountId;
//
//    /**
//     * 用户头像
//     */
//    @Excel(name = "用户头像")
//    private String accountHead;
//
//    /**
//     * 用户名字
//     */
//    @Excel(name = "用户名字")
//    private String accountName;
//
//    /**
//     * 用户手机号
//     */
//    @Excel(name = "用户手机号")
//    private String accountPhone;
//
//    /**
//     * 举报类型
//     */
//    @Excel(name = "举报类型")
//    private String reportTypeId;
//
//    /**
//     * 举报内容
//     */
//    @Excel(name = "举报内容")
//    private String reportContent;
//
//    /**
//     * 举报图片
//     */
//    @Excel(name = "举报图片")
//    private String reportImages;
//
//    /**
//     * 是否已读（0未读 1已读）
//     */
//    @Excel(name = "是否已读", readConverterExp = "0=未读,1=已读")
//    private Long readStatus;
//
//    /**
//     * 处理状态（0未处理， 1已处理）
//     */
//    @Excel(name = "处理状态", readConverterExp = "0=未处理，,1=已处理")
//    private Long processingStatus;
//
//    /**
//     * 联系方式
//     */
//    @Excel(name = "联系方式")
//    private String contactWay;
//
//    /**
//     * 被举报的id
//     */
//    private String targetId;

}
