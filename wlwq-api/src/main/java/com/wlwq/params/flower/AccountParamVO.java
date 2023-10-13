package com.wlwq.params.flower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountParamVO implements Serializable {

    /**
     * 用户ID
     */
    @NotBlank(message = "请传入要奖励的对象ID")
    private String accountId;

    /**
     * 要奖励的对象姓名
     */
    @NotBlank(message = "请传入要奖励的对象姓名")
    private String accountName;

    /**
     * 要奖励的对象手机号
     */
    @NotBlank(message = "请传入要奖励的对象手机号")
    private String accountPhone;

    /**
     * 要奖励的对象头像
     */
    @NotBlank(message = "请传入要奖励的对象头像")
    private String accountHead;

    /**
     * 岗位名称
     */
    private String postNames;

}
