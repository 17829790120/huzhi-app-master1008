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
public class ExamQuestionRecordParam  implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ExamQuestionParam> list;

    /**
     * 试题类型（0：章节课程；1：测试训练）
     */
    private Integer questionType;
    /**
     * 考试记录表id
     */
    private String examRecordId;
}
