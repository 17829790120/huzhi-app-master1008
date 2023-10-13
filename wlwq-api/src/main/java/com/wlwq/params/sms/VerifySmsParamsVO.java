package com.wlwq.params.sms;

import com.wlwq.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author gaoce
 * @ClassName VerifySmsParamsVO
 * @Description 验证码校验
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifySmsParamsVO implements Serializable {

    @NotBlank(message = "手机号为空！")
    @Phone
    private String phone;

    @NotBlank(message = "验证码为空！")
    private String code;

    /** 验证码唯一标识. */
    @NotBlank(message = "验证码唯一标识为空！")
    private String uuid;

}
