package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 用户积分审批抄送流程对象 score_flow_copy_account
 *
 * @author gaoce
 * @date 2023-06-07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreFlowCopyAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户积分审批的抄送流程ID
     */
    private String scoreFlowCopyAccountId;

    /**
     * 抄送者ID
     */
    @Excel(name = "抄送者ID")
    private String copyAccountId;

    /**
     * 用户积分ID
     */
    @Excel(name = "用户积分ID")
    private String accountScoreId;

    /**
     * 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回
     */
    @Excel(name = "审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回")
    private Integer examineStatus;

    /**
     * 1:抄送
     */
    @Excel(name = "1:抄送")
    private Long examineType;

    /**
     * 已读标识 0：否 1：已读
     */
    @Excel(name = "已读标识 0：否 1：已读")
    private Long readStatus;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Long delStatus;

    /**
     * 抄送者姓名
     */
    @Excel(name = "抄送者姓名")
    private String copyAccountName;

    /**
     * 抄送者手机号
     */
    @Excel(name = "抄送者手机号")
    private String copyAccountPhone;

    /**
     * 抄送者头像
     */
    @Excel(name = "抄送者头像")
    private String copyAccountHead;


    /**
     * 抄送者处理状态 0:否 1:已处理
     */
    @Excel(name = "抄送者处理状态 0:否 1:已处理")
    private Integer disposeStatus;

    /**
     * 抄送者处理原因
     */
    @Excel(name = "抄送者处理原因")
    private String disposeRemark;

    /**
     * 抄送者处理图片
     */
    @Excel(name = "抄送者处理图片")
    private String disposePics;

    /**
     * 同一批审批标识
     */
    private String examineTag;

    ///////////////////////////

    /**
     * 发起审批者名字
     */
    private String accountName;

}
