package com.wlwq.params.examine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author:gaoce
 * @Date:2021/11/15 17:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineSubVO implements Serializable {

    /**
     * 用户审批流程ID
     */
    @NotNull(message = "请传入审批ID！")
    private String flowAccountId;

    /**
     * 审批意见
     */
//    @NotNull(message = "请传入审批意见！")
    private String content;

    /**
     * 客户成交获得积分
     */
    private Integer integral;

    /**
     * 图片,多个以逗号隔开
     */
    private String pics;

    /**
     * 3：已通过 4：已驳回
     */
    @Builder.Default
    private Integer examineStatus = 3;

}
