package com.wlwq.information.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.TreeEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 资讯评论记录对象 information_comment_record
 * 
 * @author Rick wlwq
 * @date 2021-04-21
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("InformationCommentRecord")
public class InformationCommentRecord extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 资讯评论ID */
    private Long informationCommentId;

    /** 资讯ID */
    private Long informationPostId;

    @Excel(name = "资讯标题")
    private String informationPostTitle;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String accountId;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String accountHead;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String accountName;

    /** 审核状态. */
    @Excel(name = "审核状态",readConverterExp = "0=待审核,1=已通过,2=已拒绝")
    private Integer auditStatus;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String commentContent;

    @Excel(name = "评论或者回复",readConverterExp = "0=评论,1=回复")
    private Integer commentOrAnswerStatus;

    @Excel(name = "真实点赞人数")
    private Integer realLikeNumber;

    /** 咨询类型
     *  1：文化展示；2：新闻中心;3:家庭训练
     * */
    private Integer informationType;
    /**
     * 真实评论人数
     */
    private Integer realCommentNumber;
    /**
     * 答复对应的父级评论id
     */
    private Long parentId;

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
     * 资源链接
     */
    @Excel(name = "资源链接")
    private String resourceUrl;

    /**
     * 资源类型：:1：图片；2：视频；3：个人与组织的关系--学习
     */
    private Integer resourceType;
}
