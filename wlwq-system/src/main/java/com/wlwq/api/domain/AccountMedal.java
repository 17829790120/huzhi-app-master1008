package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;

import java.util.List;

/**
 * 用户勋章对象 account_medal
 *
 * @author gaoce
 * @date 2023-06-08
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountMedal extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户勋章ID
     */
    private Long accountMedalId;

    /**
     * 勋章名称
     */
    @Excel(name = "勋章名称")
    private String medalName;


    /**
     * 已获得图标
     */
    @Excel(name = "已获得图标")
    private String alreadyIcon;

    /**
     * 排序(排序越小，越靠前)
     */
    @Excel(name = "排序(排序越小，越靠前)")
    private Integer sortNum;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 积分
     */
    @Excel(name = "积分")
    private Integer score;


    /**
     * 勋章介绍
     */
    @Excel(name = "勋章介绍")
    private String content;


    /** 父菜单ID */
    private Long parentId;


    /////////////////////////////

    /**
     * 用户ID
     */
    private String accountId;

    /**
     * 二级勋章领取标识 0：否 1:已领取
     */
    private Integer alreadyTag;

    /**
     * 二级勋章数量
     */
    private Integer alreadyCount;


    /**
     * 二级勋章分类列表
     */
    private List<AccountMedal> childList;
}
