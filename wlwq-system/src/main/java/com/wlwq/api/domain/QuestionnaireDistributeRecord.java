package com.wlwq.api.domain;

import java.math.BigDecimal;
import java.util.Map;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 问卷发放记录对象 questionnaire_distribute_record
 *
 * @author wwb
 * @date 2023-05-12
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionnaireDistributeRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷下发用户记录表id
     */
    private String questionnaireDistributeRecordId;

    /**
     * 问卷表id
     */
    @Excel(name = "问卷表id")
    private String questionnaireId;

    /**
     * 问卷标题
     */
    @Excel(name = "问卷标题")
    private String title;

    /**
     * 问卷分数
     */
    @Excel(name = "问卷分数")
    private BigDecimal score;

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
////////////////////////////////////////
    private Map<String, Object> map;
}
