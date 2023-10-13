package com.wlwq.params.courseExam;

import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * 问卷发放记录对象
 * 
 * @author wwb
 * @date 2023-05-11
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuestionnaireAccountRecordParam  implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<QuestionnaireAccountParam> list;
}
