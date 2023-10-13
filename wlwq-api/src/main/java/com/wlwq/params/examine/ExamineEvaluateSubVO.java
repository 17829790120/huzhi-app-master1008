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
public class ExamineEvaluateSubVO implements Serializable {

    /**
     * 用户审批流程ID
     */
    @NotNull(message = "请传入审批ID！")
    private String flowAccountId;

    /**
     * 星级评价
     */
    private Double evaluate;

    /**
     * 会议优点
     */
    private String meetingBenefits;

    /**
     * 会议缺点
     */
    private String meetingDisadvantages;

    /**
     * 改进之处
     */
    private String improvement;

    /**
     * 评价状态 （0：未评价；1：已评价）
     */
    private Integer evaluateStatus;

}
