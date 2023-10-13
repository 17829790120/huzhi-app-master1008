package com.wlwq.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReportRecordParams {

    /**
     * 被举报对象id
     */
    @NotNull(message = "被举报的对象为空")
    private Long targetId;

    /**
     * 举报内容
     */
    @NotBlank(message = "请选择举报内容")
    private String reportContent;

    /**
     * 举报图片
     */
    private String reportImages;

//    private String content;

    private String modelType;
}
