package com.wlwq.api.domain;

import java.util.List;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 内部调研问卷记录对象 questionnaire_record
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
public class QuestionnaireRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷记录id
     */
    private String questionnaireRecordId;

    /**
     * 问卷表id
     */
    @Excel(name = "问卷表id")
    private String questionnaireId;

    /**
     * 问卷内容
     */
    @Excel(name = "问卷内容")
    private String content;

    /**
     * 问卷得分
     */
    @Excel(name = "问卷得分")
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
//////////////////////////////////////////////////////
    /**
     * 问卷标题
     */
    private String title;
    /**
     * 问卷记录答案
     */
    private List<QuestionnaireRecordAnswer> questionnaireRecordAnswerList;
}
