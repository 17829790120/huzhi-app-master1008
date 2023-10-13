package com.wlwq.setting.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 意见反馈对象 setting_feedback
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 反馈ID */
    private Long feedbackId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String accountId;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String accountHead;

    /** 用户名字 */
    @Excel(name = "用户名字")
    private String accountName;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    private String accountPhone;

    /** 反馈类型 */
    @Excel(name = "反馈类型")
    private String feedbackType;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String feedbackContent;

    /** 反馈图片 */
    @Excel(name = "反馈图片")
    private String feedbackImages;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactWay;



    private String[] feedbackImagesStr;

    /** 是否已读（0未读 1已读） */
    @Excel(name = "是否已读", readConverterExp = "0=未读,1=已读")
    private Integer readStatus;
}
