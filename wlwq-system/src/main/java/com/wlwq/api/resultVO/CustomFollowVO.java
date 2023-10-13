package com.wlwq.api.resultVO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class CustomFollowVO {
    /**
     * id
     */
    private String customFollowId;
    /**
     * 负责人ID
     */
    @Excel(name = "负责人ID")
    private String customUserId;
    /**
     * 负责人名称
     */
    private String nickName;
    /**
     * 内容
     */
    @Excel(name = "内容")
    private String customContent;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String customAnnex;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
