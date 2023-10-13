package com.wlwq.params.sms;

import com.wlwq.annotation.Phone;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发送验证码请求参数
 * @author Rick wlwq
 */
@Data
public class SendSmsParams {

    @NotBlank(message = "手机号码为空！")
    @Phone(message = "手机号码不正确！")
    private String phone;

    @NotNull(message = "模板类型为空！")
    private Integer templateType;
}
