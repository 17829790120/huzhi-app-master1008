package com.wlwq.setting.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 常见问题对象 setting_problem
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
public class SettingProblem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 常见问题ID */
    private Long problemId;

    /** 标题 */
    @Excel(name = "标题")
    private String problemTitle;

    /** 内容 */
    @Excel(name = "内容")
    private String problemAnswer;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortNum;

    /** 展示状态（0隐藏 1展示） */
    @Excel(name = "展示状态", readConverterExp = "0=隐藏,1=展示")
    private Integer showStatus;

    /** 删除状态（0正常 1删除） */
    @Excel(name = "删除状态", readConverterExp = "0=正常,1=删除")
    private Integer delStatus;

}
