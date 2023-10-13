package com.wlwq.api.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 节假日对象 holidays
 *
 * @author gaoce
 * @date 2023-05-25
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Holidays extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 节假日ID
     */
    private String holidaysId;

    /**
     * 节假日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "节假日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date holidaysDate;

}
