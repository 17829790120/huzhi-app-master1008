package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 审批抄送流程对象 examine_copy_flow
 *
 * @author gaoce
 * @date 2023-05-10
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineCopyFlow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 审批抄送流程ID
     */
    private String examineCopyFlowId;

    /**
     * 抄送岗位ID
     */
    @Excel(name = "抄送岗位ID")
    private Long postId;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 审批类型ID
     */
    @Excel(name = "审批类型ID")
    private Long examineModuleId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

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
