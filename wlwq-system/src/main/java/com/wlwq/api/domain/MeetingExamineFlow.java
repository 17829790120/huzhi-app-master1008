package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

/**
 * 会议审批流程对象 meeting_examine_flow
 *
 * @author wlwq
 * @date 2023-05-29
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("MeetingExamineFlow")
public class MeetingExamineFlow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 审批流程ID
     */
    private String meetingExamineFlowId;

    /**
     * 审批岗位ID
     */
    @Excel(name = "审批岗位ID")
    private Long postId;

    /**
     * 审批顺序，正序
     */
    @Excel(name = "审批顺序，正序")
    private Integer examineSequence;

    /**
     * 此字段暂时作废  是否需要本部门领导审批 0:否 1:是
     */
    @Excel(name = "此字段暂时作废  是否需要本部门领导审批 0:否 1:是")
    private Long examineStatus;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 一级审批类型ID
     */
    @Excel(name = "一级审批类型ID")
    private Long examineModuleId;

    /**
     * 二级审批类型ID
     */
    @Excel(name = "二级审批类型ID")
    private Long twoExamineModuleId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 最小值(元)
     */
    @Excel(name = "最小值(元)")
    private BigDecimal minValue;

    /**
     * 最大值(元)
     */
    @Excel(name = "最大值(元)")
    private BigDecimal maxValue;
    ////////////////////////////

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String deptName;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    private String postName;

    /**  用户信息 */
    @Excel(name = "用户信息")
    private String accountName;

    /** 模块名称 */
    private String examineModuleName;

    /**
     * 公司名称
     */
    private String companyName;

}
