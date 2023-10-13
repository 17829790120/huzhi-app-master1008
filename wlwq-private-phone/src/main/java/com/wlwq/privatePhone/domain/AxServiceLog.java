package com.wlwq.privatePhone.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * AX号码绑定日志对象 ax_service_log
 * 
 * @author Rick wlwq
 * @date 2021-04-02
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AxServiceLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** AX绑定日志ID */
    private Long axServiceLogId;

    /** 虚拟号码 */
    @Excel(name = "虚拟号码")
    private String virtualNumber;

    /** 真实号码 */
    @Excel(name = "真实号码")
    private String realNumber;

    /** 华为云绑定ID */
    @Excel(name = "华为云绑定ID")
    private String subId;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 状态： 0失败  1成功 */
    @Excel(name = "状态",readConverterExp = "0=失败,1=成功")
    private Integer status;

}
