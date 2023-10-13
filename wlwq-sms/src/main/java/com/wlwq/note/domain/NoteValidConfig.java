package com.wlwq.note.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 人机验证配置对象 note_valid_config
 * 
 * @author Rick wlwq
 * @date 2021-07-08
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteValidConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 人机验证配置ID */
    private String noteValidConfigId;

    /** 短信配置ID */
    @Excel(name = "短信配置ID")
    private String noteConfigId;

    /** 短信配置ID */
    @Excel(name = "短信配置名字")
    private String noteConfigName;

    /** app_key */
    @Excel(name = "app_key")
    private String appKey;

    /** 使用场景 */
    @Excel(name = "使用场景")
    private String scene;

    /** 验证方式 */
    @Excel(name = "验证方式")
    private String validType;
}
