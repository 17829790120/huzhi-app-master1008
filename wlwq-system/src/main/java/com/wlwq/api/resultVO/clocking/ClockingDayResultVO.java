package com.wlwq.api.resultVO.clocking;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoce
 */
@Data
public class ClockingDayResultVO implements Serializable {

    /**
     * 日期，格式为年月日
     */
    public Date date;

    /**
     * 日期，格式为年月日
     */
    public String dateStr;

    /**
     * 考勤数量
     */
    public Integer clockingCount;

    /**
     * 节假日标识 0：否 1：有节假日
     */
    public Integer holidayTag;
}
