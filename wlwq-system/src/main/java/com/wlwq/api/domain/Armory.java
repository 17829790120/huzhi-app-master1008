package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 英雄榜列表对象 armory
 *
 * @author wwb
 * @date 2023-04-18
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Armory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 英雄榜id
     */
    private String armoryId;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 上榜理由
     */
    private String context;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 用户归属部门ID
     */
    private Long deptId;

    /**
     * 用户归属部门ID
     */
    private String postIds;

    /**
     * 部门
     */
    private String deptName;
    /**
     * 岗位
     */
    private String postNames;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 排序(排序越小，越靠前)
     */
    @Excel(name = "排序(排序越小，越靠前)")
    private Integer sortNum;

/////////////////////////////////////////////////////////////
    /**
     * tag -1:查询总公司与当前dept对应的部门（分公司）;0:查询当前分公司与下属部门的数据
     */
    private Integer tag;
}
