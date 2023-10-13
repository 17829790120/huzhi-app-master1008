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
public class QuestionnaireAccountAnswerResultParam  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 下发问卷记录答案id
     */
    private String questionnaireAccountAnswerResultId;

    /**
     * 问卷发放记录答题表id
     */
    private String questionnaireAccountAnswerId;

    /**
     * 答题内容
     */
    private String content;

    /**
     * 答题得分
     */
    private Double score;

    /**
     * 得分
     */
    private Double myScore;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 问卷是否答题(0:未答卷，1:已答卷)
     */
    private Integer answerStatus;

    /**
     * 删除状态
     */
    private Integer delStatus;

    /**
     * 是否选中(0:未选中，1:已选中)
     */
    private Integer selectStatus;

}
