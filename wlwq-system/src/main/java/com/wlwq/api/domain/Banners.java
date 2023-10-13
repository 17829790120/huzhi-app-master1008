package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * banner列表对象 banners
 *
 * @author gaoce
 * @date 2022-11-24
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banners extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String bannerId;

    /**
     * 后台用户Id
     */
    private Long userId;

    /**
     * 部门Id
     */
    @Excel(name = "部门Id")
    private Long deptId;

    /**
     * 部门Id
     */
    private Long cityId;

    /**
     * 文件类型(图片:image 视频:video)
     */
    @Excel(name = "文件类型(图片:image 视频:video)")
    private String fileType;

    /**
     * 图片(视频）
     */
    @Excel(name = "图片(视频)")
    private String imageUrl;

    /**
     * banner位置(1:首页 2:管理 3：学习 4：直播)
     */
    @Excel(name = "banner位置(1:首页 2:管理 3：学习 4：直播")
    private String bannerLocation;

    /**
     * 来源(‘APP’)
     */
    private String sourceName;

    /**
     * 跳转类型(0:不跳转 1:详情 2:商城 3:服务 4:申请陪诊实习)
     */
    @Excel(name = "跳转类型(0:不跳转 1:详情 2:商城 3:服务 4:申请陪诊实习)")
    private String jumpType;

    /**
     * 跳转地址
     */
    @Excel(name = "跳转地址")
    private String jumpUrl;

    /**
     * 排序(排序越大，越靠前)
     */
    @Excel(name = "排序(排序越大，越靠前)")
    private Integer sortNum;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

    /**
     * 是否删除(0:未删除 1:已删除)
     */
    private Integer delStatus;

    /**
     * 详情
     */
    @Excel(name = "详情")
    private String content;
    /**
     * tag -1:查询总公司与当前dept对应的部门（分公司）
     */
    private Integer tag;
    ///////////////////////////////

    /**
     * 部门
     */
    @Excel(name = "部门")
    private String deptName;

}
