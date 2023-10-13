package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 问卷记录答题对象 questionnaire_account_answer
 *
 * @author wwb
 * @date 2023-05-11
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionnaireAccountAnswer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷发放记录答题表id
     */
    private String questionnaireAccountAnswerId;

    /**
     * 问卷发放记录表id
     */
    @Excel(name = "问卷发放记录表id")
    private String questionnaireAccountId;

    /**
     * 问卷标题
     */
    @Excel(name = "问卷标题")
    private String title;

    /**
     * 问卷分数
     */
    @Excel(name = "问卷分数")
    private Double score;

    /**
     * 问卷得分
     */
    @Excel(name = "问卷得分")
    private Double myScore;

    /**
     * 删除状态
     */
    private Integer delStatus;

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
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;
/////////////////////////////////////////////////////////////
    /**
     * 问卷记录答案
     */
    private List<QuestionnaireAccountAnswerResult> questionnaireAccountAnswerResultList;

    private Map<String, Object> map;
}
