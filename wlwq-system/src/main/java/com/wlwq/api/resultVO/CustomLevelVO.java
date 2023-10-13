package com.wlwq.api.resultVO;

import lombok.Data;

@Data
public class CustomLevelVO {
    private String id;
    /**
     * 等级
     */
    private String customLevel;
    /**
     * 几日内回访
     */
    private String customDays;
}
