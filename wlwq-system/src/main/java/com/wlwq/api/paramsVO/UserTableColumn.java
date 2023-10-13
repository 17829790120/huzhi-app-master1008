package com.wlwq.api.paramsVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTableColumn {

    /** title表头 */
    private String fieldName;
    /** 字段 */
    private String fieldEnglishName;
}
