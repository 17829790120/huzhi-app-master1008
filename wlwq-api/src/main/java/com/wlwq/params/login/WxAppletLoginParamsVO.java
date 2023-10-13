package com.wlwq.params.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author gaoce
 * @ClassName LoginParamsVO
 * @Description 小程序登录参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxAppletLoginParamsVO implements Serializable {


    @NotBlank(message = "昵称为空！")
    private String nickName;

    @NotBlank(message = "头像为空！")
    private String headPortrait;

    @NotBlank(message = "微信openId为空！")
    private String wxOpenId;

    /**
     * 0:未知 1:男 2:女
     */
    @NotNull(message = "性别为空！")
    private Integer sex;


    /**
     * code 小程序code
     */
    @NotBlank(message = "小程序code为空！")
    private String code;



    /** 邀请码 */
    private String invitationCode;
}
