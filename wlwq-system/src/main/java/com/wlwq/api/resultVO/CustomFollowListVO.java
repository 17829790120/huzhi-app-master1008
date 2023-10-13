package com.wlwq.api.resultVO;

import com.wlwq.common.annotation.Excel;
import lombok.Data;

@Data
public class CustomFollowListVO {
    private transient String customName;
    private transient String nickName;
    /**
     *
     */
    private String customFollowId;
    /**
     * 内容
     */
    private String customContent;
}
