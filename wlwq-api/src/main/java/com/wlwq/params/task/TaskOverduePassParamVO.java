package com.wlwq.params.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskOverduePassParamVO implements Serializable {

    /**
     * 任务审批ID
     */
    @NotBlank(message = "请传入任务审批ID")
    private String taskFlowAccountId;

    /**
     * 驳回理由
     */
    private String content;

    /**
     * 任务延期时间 yyyy-MM-dd HH:mm:ss
     */
    @NotNull(message = "请传入任务延期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskOverdueTime;

    /**
     * 审批状态 3：延期已通过 4：延期已驳回
     */
    @Builder.Default
    private Integer examineStatus = 3;

    /**
     * 图片,多个以逗号隔开
     */
    private String pics;

}
