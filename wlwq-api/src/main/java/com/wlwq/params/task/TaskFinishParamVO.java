package com.wlwq.params.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class TaskFinishParamVO implements Serializable {

    /**
     * 任务接收ID
     */
    @NotBlank(message = "请传入任务接收ID")
    private String taskReceiveId;

    /**
     * 完成理由
     */
    @NotBlank(message = "请传入完成理由")
    private String remark;


    /**
     * 多图片
     */
    private String pics;
}
