package com.wlwq.params.login;

import com.wlwq.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Administrator
 * @ClassName LoginParamsVO
 * @Description 小程序登录参数
 * @Date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginParamsVO implements Serializable {

    @NotNull(message = "手机号为空！")
    @Phone
    private String phone;

    @NotNull(message = "验证码为空！")
    private String code;

    /** 邀请码 */
    private String invitationCode;

    /** 微信唯一标识. */
    private String wxOpenId;

    /** 验证码唯一标识. */
    private String uuid;

    /**
     * 绑定标识（已废弃）
     */
    private String bindingTag;

    /**
     * 已经绑定的用户ID
     */
    private String accountId;

}
