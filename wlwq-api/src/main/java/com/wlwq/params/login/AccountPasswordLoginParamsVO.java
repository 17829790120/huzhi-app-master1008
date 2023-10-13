package com.wlwq.params.login;

import com.wlwq.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author:gaoce
 * @Date:2021/6/7 18:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountPasswordLoginParamsVO implements Serializable {

    @NotNull(message = "手机号为空！")
    @Phone
    private String phone;

    /** 密码 */
    @NotNull(message = "密码不能为空")
    private String password;

    /**
     * 已经绑定的用户ID
     */
    private String accountId;

}
