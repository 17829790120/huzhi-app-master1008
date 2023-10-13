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
public class QuestionnaireParam  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷发放记录表id
     */
    private String questionnaireAccountId;

    private List<QuestionnaireAccountAnswerParam> list;
}
