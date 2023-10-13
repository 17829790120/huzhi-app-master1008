package com.wlwq.setting.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 意见反馈类型对象 setting_feedback_type
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingFeedbackType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 反馈类型ID */
    private Long feedbackTypeId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortNum;

    /** 删除状态（0正常 1已删除） */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=已删除")
    private Integer delStatus;

}
