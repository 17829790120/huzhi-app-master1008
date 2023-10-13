package com.wlwq.api.domain;

import com.github.pagehelper.PageInfo;
import com.wlwq.api.paramsVO.TemplateFieldParamVO;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 汇报训练对象 report_training
 *
 * @author gaoce
 * @date 2023-06-05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportTraining extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 汇报训练ID
     */
    private String reportTrainingId;

    /**
     * 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     */
    @NotNull(message = "请传入类型！")
    @Excel(name = "1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报")
    private Integer templateType;

    /**
     * 内容
     */
    @NotBlank(message = "请传入内容！")
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
    private Integer delStatus;

    ///////////////////////////////////////////


    /**
     * 日筛选 格式为2023-05-01
     */
    private String date;

    /**
     * 0：只查自己的 1：查所有的
     */
    private Integer tag;

    /**
     * 点赞标识 0：否 大于0的情况已点赞
     */
    private Integer likeTag;

    /**
     * 点赞人员列表
     */
    private String likePeople;

    /**
     * 评价列表
     */
    private PageInfo<AccountEvaluate> evaluateList;

    /**
     * 字段集合
     */
    private List<TemplateFieldParamVO> templateFieldList;

    /**
     * 格式为yyyy-MM
     * 月份筛选
     */
    private String month;

    /**
     * 岗位
     */
    private String postNames;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 0：否 1:可以删除
     */
    private Integer delTag;

    /**
     * 是否上传文件 0：否 1:是
     */
    @Builder.Default
    private Integer fileTag = 0;

    /**
     * 点赞数量
     */
    private int likeCount;

    /**
     * 已读标识 0：否 1：已读
     */
    private int readTag;
}
