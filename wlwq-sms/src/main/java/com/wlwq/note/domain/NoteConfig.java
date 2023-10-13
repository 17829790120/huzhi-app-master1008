package com.wlwq.note.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 短信配置对象 note_config
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
public class NoteConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 短信配置ID
     */
    @Excel(name = "短信配置ID")
    private String noteConfigId;

    /** 短信配置名字 */
    @Excel(name = "短信配置名字")
    private String noteConfigName;

    /**
     * access_key
     */
    @Excel(name = "access_key")
    private String accessKeyId;

    /**
     * access_key_secret
     */
    @Excel(name = "access_key_secret")
    private String accessKeySecret;

    /**
     * 短信类型
     */
    @Excel(name = "短信类型")
    private String noteConfigType;

    /**
     * SDK_APP_ID(腾讯云短信需要此参数)
     */
    @Excel(name = "SDK_APP_ID(腾讯云短信需要此参数)")
    private String sdkAppId;
}