package com.wlwq.common.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gaoce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeekVO implements Serializable {

    /**
     * 数字
     */
    private Integer num;

    /**
     * 周几
     */
    private String name;

    private Boolean flag;
}
