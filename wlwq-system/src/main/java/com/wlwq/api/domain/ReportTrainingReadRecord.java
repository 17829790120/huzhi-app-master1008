package com.wlwq.api.domain;

import com.github.pagehelper.PageInfo;
import com.wlwq.api.paramsVO.TemplateFieldParamVO;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import java.util.List;

/**
 * 汇报训练已读记录对象 report_training_read_record
 *
 * @author gaoce
 * @date 2023-07-08
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportTrainingReadRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 汇报训练已读记录ID
     */
    private String reportTrainingReadRecordId;

    /**
     * 汇报训练ID
     */
    @Excel(name = "汇报训练ID")
    private String reportTrainingId;

    /**
     * 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     */
    @Excel(name = "1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报")
    private Integer templateType;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private String accountId;

    /**
     * 岗位IDS
     */
    @Excel(name = "岗位IDS")
    private String postIds;

    /**
     * 所属部门ID
     */
    @Excel(name = "所属部门ID")
    private Long deptId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String accountName;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String accountPhone;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String accountHead;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 0:否 1:删除
     */
    @Excel(name = "0:否 1:删除")
    private Long delStatus;

    /////////////////////////////////


    /**
     * 日筛选 格式为2023-05-01
     */
    private String date;

    /**
     * 格式为yyyy-MM
     * 月份筛选
     */
    private String month;


}
