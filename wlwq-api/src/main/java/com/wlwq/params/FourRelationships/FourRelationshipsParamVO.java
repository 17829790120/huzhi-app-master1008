package com.wlwq.params.FourRelationships;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 四类关系查询参数
 * @author dxy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FourRelationshipsParamVO implements Serializable {

    /**
     * 三级分类ID
     */
    private String threeStoreClassId;


    /**
     * 格式为yyyy-MM-dd
     */
    private String date;

    /**
     * 格式为yyyy-MM
     * 月份筛选
     */
    private String month;

    /**
     *  0：只查自己的 1：查所有的
     */
    private Integer tag;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 关键字搜索
     */
    private String keyword;
}
