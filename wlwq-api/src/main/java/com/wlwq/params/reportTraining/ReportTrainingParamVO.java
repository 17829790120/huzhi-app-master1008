package com.wlwq.params.reportTraining;

import lombok.*;
import java.io.Serializable;

/**
 * 汇报训练查询参数
 * @author gaoce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportTrainingParamVO implements Serializable {

    /**
     * 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     */
    @Builder.Default
    private Integer templateType = 1;

    /**
     * 格式为yyyy-MM-dd
     */
    private String date;

    /**
     * 格式为yyyy-MM
     * 月份筛选
     */
    private String month;

    /**
     *  0：只查自己的 1：查所有的
     */
    private Integer tag;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 关键字搜索
     */
    private String keyword;
}
