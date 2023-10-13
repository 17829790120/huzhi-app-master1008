package com.wlwq.api.domain;

import com.wlwq.common.enums.AttachmentType;
import lombok.*;

import java.util.Date;

/**
 * 附件对象 attachments
 *
 * @author Renbowen
 * @date 2020-09-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachments{
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    private String fileKey;

    /**
     * $column.columnComment
     */
    private Integer height;

    /**
     * $column.columnComment
     */
    private String mediaType;

    /**
     * $column.columnComment
     */
    private String name;

    /**
     * $column.columnComment
     */
    private String path;

    /**
     * $column.columnComment
     */
    private Long size;

    /**
     * $column.columnComment
     */
    private String suffix;

    /**
     * $column.columnComment
     */
    private String thumbPath;

    /**
     * $column.columnComment
     */
    private Integer type;

    /**
     * $column.columnComment
     */
    private Integer width;

    /**
     * $column.columnComment
     */
    private Date createDate;

    /**
     * $column.columnComment
     */
    private Date updateDate;

    private AttachmentType attachmentType;


}
