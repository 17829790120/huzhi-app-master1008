package com.wlwq.params.login;

import com.wlwq.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author:gaoce
 * @Date:2021/6/7 17:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditPasswordParamsVO implements Serializable {

    @NotBlank(message = "手机号不能为空！")
    @Phone(message = "手机号码不正确！")
    @Size(min = 1, max = 11, message = "手机号码长度不能超过11个字符")
    private String phone;

    @NotNull(message = "验证码为空！")
    private String code;

    /** 密码 */
    @NotNull(message = "密码不能为空")
    private String password;

    /** 验证码唯一标识. */
    private String uuid;
}
