package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 问卷记录答案对象 questionnaire_account_answer_result
 *
 * @author wlwq
 * @date 2023-05-11
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionnaireAccountAnswerResult extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 下发问卷记录答案id
     */
    private String questionnaireAccountAnswerResultId;

    /**
     * 问卷发放记录答题表id
     */
    @Excel(name = "问卷发放记录答题表id")
    private String questionnaireAccountAnswerId;

    /**
     * 答题内容
     */
    @Excel(name = "答题内容")
    private String content;

    /**
     * 答题得分
     */
    @Excel(name = "答题得分")
    private Double score;

    /**
     * 得分
     */
    @Excel(name = "得分")
    private Double myScore;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 问卷是否答题(0:未答卷，1:已答卷)
     */
    @Excel(name = "问卷是否答题(0:未答卷，1:已答卷)")
    private Integer answerStatus;

    /**
     * 删除状态
     */
    private Integer delStatus;

    /**
     * 是否选中(0:未选中，1:已选中)
     */
    private Integer selectStatus;

    /**
     * 岗位ID
     */
    @Excel(name = "岗位ID")
    private Long postId;

    /**
     * 所属部门ID
     */
    @Excel(name = "所属部门ID")
    private Long deptId;

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
