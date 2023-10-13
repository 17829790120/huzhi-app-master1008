package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 预约记录对象 course_customization_post_record
 * 
 * @author wwb
 * @date 2023-05-22
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("CourseCustomizationPostRecord")
public class CourseCustomizationPostRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 预约记录主键ID
     */
    private String courseCustomizationPostRecordId;

    /**
     * 资讯ID
     */
    @Excel(name = "资讯ID")
    private String courseCustomizationPostId;

    /**
     * 资讯分类ID
     */
    @Excel(name = "资讯分类ID")
    private Long informationCategoryId;

    /**
     * 资讯标题
     */
    @Excel(name = "资讯标题")
    private String informationPostTitle;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String synopsis;

    /**
     * 封面图片（最多三张）
     */
    @Excel(name = "封面图片", readConverterExp = "最=多三张")
    private String informationPostImages;

    /**
     * 封面图片类型（1图片 2视频）
     */
    @Excel(name = "封面图片类型", readConverterExp = "1=图片,2=视频")
    private Integer informationPostImagesType;

    /**
     * 删除状态（0正常 1删除）
     */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=删除")
    private Integer delStatus;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private String accountId;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String headPortrait;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 岗位IDS
     */
    @Excel(name = "岗位IDS")
    private String postIds;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    private String postNames;

    /**
     * 公司id
     */
    @Excel(name = "公司id")
    private Long companyId;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 公司地址
     */
    @Excel(name = "公司地址")
    private String companyAddress;

    /**
     * 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date reservationTime;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String telephone;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 行业id
     */
    @Excel(name = "行业id")
    private Long industryId;

    /**
     * 行业名称
     */
    @Excel(name = "行业名称")
    private String industryName;

}
