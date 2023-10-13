package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户评价对象 account_evaluate
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
public class AccountEvaluate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户评价ID
     */
    private String accountEvaluateId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 目标id
     */
    @NotBlank(message = "请传入目标id！")
    @Excel(name = "目标id")
    private String targetId;

    /**
     * 评价类型（1：日精进）
     */

    @Excel(name = "评价类型", readConverterExp = "1=：日精进")
    private Integer evaluateType;

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

    /**
     * 评价内容
     */
    @NotBlank(message = "请传入评价内容！")
    @Excel(name = "评价内容")
    private String evaluateName;

    /**
     * 父类ID
     */
    @Excel(name = "父类ID")
    private String parentId;

    /**
     * 评价图片
     */
    @Excel(name = "评价图片")
    private String evaluatePics;

    /**
     * 三级分类ID
     */
    @Excel(name = "三级分类ID")
    private String threeStoreClassId;

    ////////////////////////

    /**
     * 评价标识 1：评价可以删除 0“否
     */
    private Integer evaluateTag;

    /**
     * 回复列表
     */
    private List<AccountEvaluate> replyList;

}
