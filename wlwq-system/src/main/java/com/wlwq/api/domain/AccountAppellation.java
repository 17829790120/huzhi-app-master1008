package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 用户称谓对象 account_appellation
 *
 * @author gaoce
 * @date 2023-06-08
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountAppellation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户称谓ID
     */
    private String accountAppellationId;

    /**
     * 称谓名称
     */
    @Excel(name = "称谓名称")
    private String appellationName;

    /**
     * 最小积分
     */
    @Excel(name = "最小积分")
    private Integer minScore;

    /**
     * 最大积分
     */
    @Excel(name = "最大积分")
    private Integer maxScore;

    /**
     * 1:积分称谓 2:勋章称谓
     */
    @Excel(name = "1:积分称谓 2:勋章称谓")
    private Integer appellationType;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Integer delStatus;

}
