package com.wlwq.api.resultVO.examine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:gaoce
 * @Date:2021/11/9 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineFlowAccountResultVO implements Serializable {

    /**
     * 用户ID
     */
    private String accountId;

    /**
     * 姓名
     */
    private String accountName;

    /**
     * 头像
     */
    private String accountHead;

    /**
     * 岗位ids
     */
    private String postIds;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 审批岗位ID
     */
    private Long postId;


    /**
     * 审批顺序，正序
     */
    private Integer examineSequence;

    /** 部门ID */
    private Long deptId;

    /** 审批状态 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回 */
    private Integer examineStatus;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date examineTime;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 拒绝原因/批准原因
     */
    private String rejectContent;

    /**
     * 多图片
     */
    private String pics;

    /**
     * 更新时间
     */
    private Date updateTime;

}
