package com.wlwq.api.domain;

import com.wlwq.api.paramsVO.TemplateFieldParamVO;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import java.util.List;

/**
 * 自定义模版对象 template
 *
 * @author gaoce
 * @date 2023-06-05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Template extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模版ID
     */
    private String templateId;

    /**
     * 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     */
    @Excel(name = "1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报")
    private Integer templateType;

    /**
     * 模版内容
     */
    @Excel(name = "模版内容")
    private String templateContent;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

    /**
     * 字段集合
     */
    private List<TemplateFieldParamVO>  templateFieldList;
}
