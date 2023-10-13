package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * 附件设置对象 options
 *
 * @author Renbowen
 * @date 2020-09-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Options extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 选项键
     */
    @Excel(name = "选项键")
    private String optionKey;

    /**
     * 选项值
     */
    @Excel(name = "选项值")
    private String optionValue;

    /**
     * 预留字段
     */
    @Excel(name = "预留字段")
    private Long type;

    /**
     * 修改时间
     */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;


}
