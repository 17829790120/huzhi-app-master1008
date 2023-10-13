package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 任务附件对象 task_file
 *
 * @author gaoce
 * @date 2023-05-29
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务附件ID
     */
    private String taskFileId;

    /**
     * 任务发起ID
     */
    @Excel(name = "任务发起ID")
    private String taskInitiateId;

    /**
     * 任务文件类型 1:word 2:excel  3:pdf  4:ppt
     */
    @Excel(name = "任务文件类型 1:word 2:excel  3:pdf  4:ppt")
    private Long taskFileType;

    /**
     * 任务文件名称
     */
    @Excel(name = "任务文件名称")
    private String taskFileName;

    /**
     * 任务文件地址
     */
    @Excel(name = "任务文件地址")
    private String taskFileUrl;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Long delStatus;

    /**
     * 文件大小,单位是b,1kb=1000b
     */
    @Excel(name = "文件大小,单位是b,1kb=1000b")
    private Integer examineFileSize;

    /**
     * 同批次生成的标识
     */
    @Excel(name = "同批次生成的标识")
    private String fileTag;

}
