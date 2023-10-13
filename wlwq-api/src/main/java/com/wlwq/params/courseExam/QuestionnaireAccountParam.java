package com.wlwq.params.courseExam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuestionnaireAccountParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷发放记录表id
     */
    private String questionnaireAccountId;

    /**
     * 问卷表id
     */
    private String questionnaireId;

    /**
     * 问卷标题
     */
    private String title;

    /**
     * 问卷分数
     */
    private Double score;

    /**
     * 创建者id
     */
    private Long creatorId;

    /**
     * 创建者名称
     */
    private String creatorName;

    /**
     * 创建者部门
     */
    private Long creatorDeptId;

    /**
     * 删除状态
     */
    private Integer delStatus;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 昵称
     */
    private String nickName;

}
