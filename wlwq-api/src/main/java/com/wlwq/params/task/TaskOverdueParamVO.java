package com.wlwq.params.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
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
public class TaskOverdueParamVO implements Serializable {

    /**
     * 任务接收ID
     */
    @NotBlank(message = "请传入任务接收ID")
    private String taskReceiveId;

    /**
     * 延期理由
     */
    @NotBlank(message = "请传入延期理由")
    private String remark;

    /**
     * 任务延期时间 yyyy-MM-dd HH:mm:ss
     */
    @NotNull(message = "请传入任务延期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskOverdueTime;

}
