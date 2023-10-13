package com.wlwq.params.examine;

import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtherParamVO implements Serializable {

    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 申请内容
     */
    @NotBlank(message = "请输入申请内容")
    private String reason;

    /**
     * 审批详情
     */
    @NotBlank(message = "请输入审批详情")
    private String content;


    /**
     * 图片,多个以逗号隔开
     */
    private String pics;
}
