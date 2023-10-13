package com.wlwq.params.clocking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClockingSearchParamVO implements Serializable {

    /**
     * 月份 格式为年月 2023-05
     */
    private String month;

    /**
     *  1：正常打卡 2：外出签到
     */
    private Integer status;

    /**
     * 请假年份
     */
    private String  leaveYear;
}
