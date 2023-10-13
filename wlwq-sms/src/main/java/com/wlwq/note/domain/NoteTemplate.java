package com.wlwq.note.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 短信模板对象 note_template
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 短信模板ID */
    private String noteTemplateId;

    /** 短信配置ID */
    @Excel(name = "短信配置ID")
    private String noteConfigId;

    /** 短信配置ID */
    @Excel(name = "短信配置名字")
    private String noteConfigName;

    /** 短信模板名字 */
    @Excel(name = "短信模板名字")
    private String noteTemplateName;

    /** 短信模板code */
    @Excel(name = "短信模板code")
    private String noteTemplateCode;

    /** 短信模板类型 */
    @Excel(name = "短信模板类型")
    private String noteTemplateType;

    /** 短信模板内容 */
    @Excel(name = "短信模板内容")
    private String noteTemplateContent;
}
