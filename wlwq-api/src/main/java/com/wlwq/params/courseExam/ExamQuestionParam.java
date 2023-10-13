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
public class ExamQuestionParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 考试记录表id
     */
    private String examRecordId;

    /**
     * 题库表id
     */
    private String questionManagerId;

    /**
     * 测试训练题库表id
     */
    private String examQuestionManagerId;

    /**
     * 我的答案
     */
    private String myAnswerStatus;
}
