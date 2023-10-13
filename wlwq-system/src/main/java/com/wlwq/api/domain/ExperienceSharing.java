package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 心得分享对象 experience_sharing
 *
 * @author wwb
 * @date 2023-05-05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("ExperienceSharing")
public class ExperienceSharing extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 心得分享id
     */
    private String experienceSharingId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String headPortrait;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 分享内容
     */
    @Excel(name = "分享内容")
    private String sharingContent;

    /**
     * 章节ID
     */
    @Excel(name = "章节ID")
    private Long chapterId;

    /**
     * 章节名字
     */
    @Excel(name = "章节名字")
    private String chapterName;

    /**
     * 课程ID
     */
    @Excel(name = "课程ID")
    private Long courseId;

    /**
     * 课程标题
     */
    @Excel(name = "课程标题")
    private String courseTitle;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 用户归属部门ID
     */
    @Excel(name = "用户归属部门ID")
    private Long deptId;

    /**
     * 用户归属部门名称
     */
    @Excel(name = "用户归属部门名称")
    private String deptName;

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
     * 删除状态（0未1已）
     */
    private Integer delStatus;

    /**
     * 心得分享获得积分列表
     */
    private List<AccountScore> accountScoreList;

}
