package com.wlwq.params.courseExam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 心得分享
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExperienceSharingParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 心得分享id
     */
    private String experienceSharingId;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 分享内容
     */
    private String sharingContent;

    /**
     * 章节ID
     */
    private Long chapterId;

    /**
     * 章节名字
     */
    private String chapterName;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 用户归属部门ID
     */
    private Long deptId;

    /**
     * 用户归属部门名称
     */
    private String deptName;

    /**
     * 岗位IDS
     */
    private String postIds;

    /**
     * 岗位名称
     */
    private String postNames;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程标题
     */
    private String courseTitle;
    /**
     * 积分
     */
    private Integer score;

}
