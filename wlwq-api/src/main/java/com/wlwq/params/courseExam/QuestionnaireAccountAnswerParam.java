package com.wlwq.params.courseExam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuestionnaireAccountAnswerParam  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷发放记录答题表id
     */
    private String questionnaireAccountAnswerId;

    /**
     * 问卷发放记录表id
     */
    private String questionnaireAccountId;

    /**
     * 问卷标题
     */
    private String title;

    /**
     * 问卷分数
     */
    private Double score;

    /**
     * 问卷得分
     */
    private Double myScore;

    /**
     * 删除状态
     */
    private Integer delStatus;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 问卷是否答题(0:未答卷，已答卷)
     */
    private Integer answerStatus;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 公司ID
     */
    private Long companyId;

    private List<QuestionnaireAccountAnswerResultParam> questionnaireAccountAnswerResultList;

    ///////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 问卷记录id(新添加，前端不好传值时，后端逻辑使用字段)
     */
    private String questionnaireRecordId;

}
