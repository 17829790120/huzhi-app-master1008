package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户积分对象 account_score
 *
 * @author gaoce
 * @date 2023-06-06
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountScore")
public class AccountScore extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户积分ID
     */
    private String accountScoreId;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 目标id
     */
    private String targetId;

    /**
     * 积分类型（-1:积分兑换  0：任务 剩下的在字典表sys_set_score -2:章节课程考试获得积分  -3：测试训练考试获得积分
     * -4：心得分享打赏得积分 -5：分享成果获得积分 -6:阅读新闻的积分 -7：会议训练获得积分 -8：会议训练评价获得积分 -9：观看课程视频获得积分）
     */
    private Integer scoreType;

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
     * 积分来源
     */
    @Excel(name = "积分来源")
    private String scoreSource;

    /**
     * 1:获得 2:支出
     */
    @Excel(name = "1:获得 2:支出")
    private Integer scoreStatus;

    /**
     * 积分
     */
    @Excel(name = "积分")
    private Integer score;

    /**
     * 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回
     */
    @Excel(name = "审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回")
    private Integer examineStatus;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间",dateFormat  = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 多图片
     */
    private String pics;

    /**
     * 拒绝原因/批准原因
     */
    private String rejectContent;

    /**
     * 用户所属部门ID
     */
    private Long deptId;

    /**
     * 用户所属公司ID
     */
    private Long companyId;

    /**
     * 同一批审批标识
     */
    private String examineTag;

    ///////////////////////

    /**
     * 月份筛选 格式为2023-05
     */
    private String month;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 课程ID（统计课程积分用）
     */
    private Long courseId;


}
