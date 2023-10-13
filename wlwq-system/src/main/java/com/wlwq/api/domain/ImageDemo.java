package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * 图片视频上传示例对象 image_demo
 *
 * @author Renbowen
 * @date 2020-10-09
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDemo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /** 排序越大越靠前 */
    @Excel(name = "排序越大越靠前")
    private Integer sortNum;

    /**
     * 单图片
     */
    @Excel(name = "单图片")
    private String image;

    /**
     * 多图片
     */
    @Excel(name = "多图片")
    private String images;

    /**
     * 单视频
     */
    @Excel(name = "单视频")
    private String video;

    /**
     * 多视频
     */
    @Excel(name = "多视频")
    private String videos;

    /**
     * 编辑器内容
     */
    @Excel(name = "编辑器内容")
    private String content;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 最后一次修改时间
     */
    @Excel(name = "最后一次修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastDate;


    private String[] imagesStr;
    private String[] videosStr;


}
