package com.wlwq.api.resultVO;

import com.wlwq.common.annotation.Excel;
import lombok.Data;

@Data
public class CustomVO {
    private String customId;
    /**
     * 客户姓名
     */
    @Excel(name = "客户姓名")
    private String customName;
    /**
     * 客户需求
     */
    @Excel(name = "客户需求")
    private String customDemand;
    /**
     * 0平台录入1App录入
     */
    @Excel(name = "0平台录入1App录入")
    private Long addSource;
    /**
     * 是否释放
     */
    private Long status;
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 客户头像
     */
    private String customAnnex;
}
