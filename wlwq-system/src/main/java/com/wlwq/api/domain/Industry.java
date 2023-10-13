package com.wlwq.api.domain;

import java.math.BigDecimal;
import java.util.List;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 行业信息对象 b_industry
 *
 * @author wwb
 * @date 2023-05-23
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("Industry")
public class Industry extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 行业名称
     */
    @Excel(name = "行业名称")
    private String name;

    /**
     * 保证金
     */
    @Excel(name = "保证金")
    private BigDecimal ensurePrice;

    /**
     * 服务提成低（%）
     */
    @Excel(name = "服务提成低", readConverterExp = "%=")
    private BigDecimal serviceCommissionStart;

    /**
     * 服务提成高（%）
     */
    @Excel(name = "服务提成高", readConverterExp = "%=")
    private BigDecimal serviceCommissionEnd;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String coverImage;

    /**
     * 轮播图
     */
    @Excel(name = "轮播图")
    private String bannerImage;

    /** 父菜单ID */
    private Long parentId;
    private List<Industry> industryList;
}
