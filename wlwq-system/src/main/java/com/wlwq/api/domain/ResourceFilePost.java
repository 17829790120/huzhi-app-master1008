package com.wlwq.api.domain;

import java.math.BigDecimal;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 资源文件对象 resource_file_post
 *
 * @author wwb
 * @date 2023-05-24
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("ResourceFilePost")
public class ResourceFilePost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资讯文件ID
     */
    private String resourceFilePostId;

    /**
     * 资源文件类别ID
     */
    @Excel(name = "资源文件类别ID")
    private Long resourceFileCategoryId;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 原价格
     */
    @Excel(name = "原价格")
    private BigDecimal primaryPrice;

    /**
     * 资讯标题
     */
    @Excel(name = "资讯标题")
    private String informationPostTitle;

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
     * 单张封面图片展示位置
     */
    @Excel(name = "单张封面图片展示位置")
    private String imagesAddress;

    /**
     * 资讯内容
     */
    @Excel(name = "资讯内容")
    private String informationPostContent;

    /**
     * 资讯文件
     */
    @Excel(name = "资讯文件")
    private String informationPostFile;

    /**
     * 排序（大靠前）
     */
    @Excel(name = "排序", readConverterExp = "大=靠前")
    private Integer sortNum;

    /**
     * 基础下载数量
     */
    @Excel(name = "基础下载数量")
    private Integer basicsDownloadNumber;

    /**
     * 热门状态（0普通 1热门）
     */
    @Excel(name = "热门状态", readConverterExp = "0=普通,1=热门")
    private Integer hostStatus;

    /**
     * 置顶状态（0普通 1置顶）
     */
    @Excel(name = "置顶状态", readConverterExp = "0=普通,1=置顶")
    private Integer topStatus;

    /**
     * 删除状态（0正常 1删除）
     */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=删除")
    private Integer delStatus;

    /**
     * 祖级列表
     */
    @Excel(name = "祖级列表")
    private String ancestors;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    private Double fileSize;

    /**
     * 苹果价格
     */
    @Excel(name = "苹果价格")
    private BigDecimal applePrice;

    /**
     * 苹果编码
     */
    @Excel(name = "苹果编码")
    private String appleCoding;
//////////////////////////////////////////
    /**
     * 分类标题
     */
    private String categoryTitle;

    private String userName;

    private String deptName;

    /**
     * 下载次数
     */
    private Integer realDownloadNumber;
    /**
     * 1:前端api查询；2：后台查询；
     */
    private Integer tag;
}
