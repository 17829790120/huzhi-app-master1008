package com.wlwq.note.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短信签名对象 note_sign
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoteSign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 短信签名ID */
    private String noteSignId;

    /** 短信配置ID */
    @Excel(name = "短信配置ID")
    private String noteConfigId;

    /** 短信配置名字 */
    @Excel(name = "短信配置名字")
    private String noteConfigName;

    /** 短信签名内容 */
    @Excel(name = "短信签名内容")
    private String noteSignValue;
}
