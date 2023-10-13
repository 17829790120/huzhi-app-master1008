package com.wlwq.params.clocking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReissueClockingParamVO implements Serializable {


    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 1：上班打卡 2：下班打卡
     */
    @NotNull(message = "请传入上下班打卡类型")
    private Integer clockingStatus;

    /**
     * 补卡日期 yyyy-MM-dd
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "请传入补卡日期")
    private Date reissueClockingDate;

    /**
     * 原打卡时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请传入原打卡时间")
    private Date rawClockingTime;

    /**
     * 补卡时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请传入补卡时间")
    private Date reissueClockingTime;

    /**
     * 补卡理由
     */
    @NotBlank(message = "请传入补卡理由")
    private String reason;

    /**
     * 图片,多个以逗号隔开
     */
    private String pics;
}
