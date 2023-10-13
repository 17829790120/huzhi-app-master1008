package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 用户绑定对象 account_binding
 *
 * @author gaoce
 * @date 2023-07-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountBinding extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户绑定ID
     */
    private String accountBindingId;

    /**
     * 被绑定的用户ID
     */
    @Excel(name = "被绑定的用户ID")
    private String accountIds;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private String accountId;

}
