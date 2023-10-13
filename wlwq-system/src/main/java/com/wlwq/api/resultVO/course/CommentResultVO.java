package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName CommentResultVO
 * @Description 评论列表
 * @Date 2021/1/26 17:07
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentResultVO implements Serializable {

    /** 用户ID. */
    private String accountId;

    /** 用户昵称. */
    private String accountName;

    /** 用户头像. */
    private String accountHead;

    /** 评论内容. */
    private String commentContent;

    /** 用户综合评分数. */
    private Integer avgScore;

    /** 评论日期. */
    private String commentDate;
}
