package com.wlwq.note.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 短信发送记录对象 note_send_record
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
public class NoteSendRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 短信发送记录ID */
    private Long noteSendRecordId;

    /** 短信配置ID */
    @Excel(name = "短信配置ID")
    private String noteConfigId;

    /** 短信类型 */
    @Excel(name = "短信类型")
    private String noteConfigType;

    /** 短信模板ID */
    @Excel(name = "短信模板ID")
    private String noteTemplateId;

    /** 短信模板名字 */
    @Excel(name = "短信模板名字")
    private String noteTemplateName;

    /** 短信模板code */
    @Excel(name = "短信模板code")
    private String noteTemplateCode;

    /** 短信模板类型 */
    @Excel(name = "短信模板类型")
    private String noteTemplateType;

    /** 接收方手机号 */
    @Excel(name = "接收方手机号")
    private String receivePhone;

    /** 短信内容 */
    @Excel(name = "短信内容")
    private String noteContent;

    /** 发送回执ID */
    @Excel(name = "发送回执ID")
    private String resultBizId;

    /** 请求状态码 */
    @Excel(name = "请求状态码")
    private String resultCode;

    /** 状态码描述 */
    @Excel(name = "状态码描述")
    private String resultMessage;

    /** 请求ID */
    @Excel(name = "请求ID")
    private String resultRequestId;
}
