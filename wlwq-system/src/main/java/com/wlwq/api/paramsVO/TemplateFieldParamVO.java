package com.wlwq.api.paramsVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 自定义字段相关值
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateFieldParamVO implements Serializable {

    /**
     * 字段英文名称
     */
    private String fieldEnglishName;

    /**
     * 字段中文名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段选填
     */
    private String fieldOptional;

    /**
     * 字段长度
     */
    private String fieldLength;

    /**
     * 字段值
     */
    private String value;

    /**
     * 字段排序，越小越靠前
     */
    private Integer sortNum;
}
