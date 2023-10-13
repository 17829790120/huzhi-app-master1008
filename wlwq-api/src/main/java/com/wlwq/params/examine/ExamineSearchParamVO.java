package com.wlwq.params.examine;

import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author:gaoce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineSearchParamVO implements Serializable {


    /**
     * 标题
     */
    private String title;
    /**
     * 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
     */
    private Integer examineStatus;

    /**
     *  模块（类型）(可以是多个，以逗号隔开) 1：请假 2：报销 3：合同 4：其他 5：补卡 24:会议训练审核 25:会议训练评价
     */
    private String examineModuleStr;

    /**
     * 1:最近时间 2：最远时间
     */
    @Builder.Default
    private Integer searchTimeTag = 1;


    /**
     * 已读标识 0：否 1：已读
     */
    private Integer readStatus;

    /**
     * 评价状态 （0：未评价；1：已评价）
     */
    private Integer evaluateStatus;

    /**
     * 1:未开始；2：进行中；3：已结束；4：已取消;
     */
    private Integer tag;

    private String accountId;

    /**
     * 会议类型：0：会议训练；1：课程章节转训
     */
    private Integer meetingType;

    /**
     *  搜索用，搜索客户名称
     */
    private String keyword;

}
