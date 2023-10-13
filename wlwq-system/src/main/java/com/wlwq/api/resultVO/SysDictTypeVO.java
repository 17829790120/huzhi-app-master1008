package com.wlwq.api.resultVO;

import com.wlwq.common.annotation.Excel;
import lombok.Data;

@Data
public class SysDictTypeVO {
    /** 字典类型 */
    private String dictType;
    /**
     * 字典值
     */
    private String dictValue;
    /**
     * 字典标签
     */
    private String dictLabel;
}
