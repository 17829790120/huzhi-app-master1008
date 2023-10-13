package com.wlwq.information.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 点赞记录对象 information_like_record
 * 
 * @author Rick wlwq
 * @date 2021-04-20
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformationLikeRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 点赞记录ID */
    private Long informationLikeRecordId;

    /** 被点赞ID */
    private Long primaryId;

    @Excel(name = "资讯标题/资讯评论内容")
    private String primaryTitle;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String accountId;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String accountHead;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String accountName;

    /** 点赞类型（1资讯 2资讯评论） */
    @Excel(name = "点赞类型", readConverterExp = "1=资讯,2=资讯评论")
    private Integer likeType;

    /** 咨询类型
     *  1：文化展示；2：新闻中心；3：个人与组织的关系--学习
     * */
    private Integer informationType;
}
