package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 审批文件对象 examine_file
 *
 * @author gaoce
 * @date 2023-05-11
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 审批文件ID
     */
    private String examineFileId;

    /**
     * 审批发起ID
     */
    @Excel(name = "审批发起ID")
    private String examineInitiateId;

    /**
     * 审批文件类型 1:word 2:excel  3:pdf  4:ppt
     */
    @Excel(name = "审批文件类型 1:word 2:excel  3:pdf  4:ppt")
    private Long examineFileType;

    /**
     * 审批文件名称
     */
    @Excel(name = "审批文件名称")
    private String examineFileName;

    /**
     * 审批文件地址
     */
    @Excel(name = "审批文件地址")
    private String examineFileUrl;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

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
