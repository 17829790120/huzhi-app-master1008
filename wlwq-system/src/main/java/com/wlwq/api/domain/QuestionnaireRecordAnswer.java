package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 问卷记录答案对象 questionnaire_record_answer
 * 
 * @author wwb
 * @date 2023-05-09
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionnaireRecordAnswer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷记录答题id
     */
    private String questionnaireRecordAnswerId;

    /**
     * 问卷记录id
     */
    @Excel(name = "问卷记录id")
    private String questionnaireRecordId;

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
     * 创建者id
     */
    @Excel(name = "创建者id")
    private Long creatorId;

    /**
     * 创建者名称
     */
    @Excel(name = "创建者名称")
    private String creatorName;

    /**
     * 创建者部门
     */
    @Excel(name = "创建者部门")
    private Long creatorDeptId;

    /**
     * 删除状态
     */
    @Excel(name = "删除状态")
    private Integer delStatus;

}
