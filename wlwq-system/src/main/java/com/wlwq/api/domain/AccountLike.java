package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 用户点赞对象 account_like
 * 
 * @author gaoce
 * @date 2023-06-02
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountLike extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户点赞ID
     */
    private String accountLikeId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 目标id
     */
    @Excel(name = "目标id")
    private String targetId;

    /**
     * 点赞类型（1：日精进）
     */
    @Excel(name = "点赞类型", readConverterExp = "1=：日精进")
    private Integer likeType;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String accountName;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String accountPhone;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String accountHead;

}
