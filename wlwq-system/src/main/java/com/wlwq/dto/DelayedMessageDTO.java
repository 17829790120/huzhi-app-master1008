package com.wlwq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DelayedMessageDTO {
    /**
     * 消息模块（可以按照模块划分不同类型的业务）.
     */
    private String moduleName;
    /**
     * 消息内容.
     */
    private String messageContent;
}
