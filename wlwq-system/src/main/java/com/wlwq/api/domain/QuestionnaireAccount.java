package com.wlwq.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 问卷发放记录对象 questionnaire_account
 * 
 * @author wwb
 * @date 2023-05-11
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("QuestionnaireAccount")
public class QuestionnaireAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 问卷发放记录表id
     */
    private String questionnaireAccountId;

    /**
     * 问卷下发用户记录表id
     */
    private String questionnaireDistributeRecordId;

    /**
     * 问卷表id
     */
    @Excel(name = "问卷表id")
    private String questionnaireId;

    /**
     * 问卷标题
     */
    @Excel(name = "问卷标题")
    private String title;

    /**
     * 问卷分数
     */
    @Excel(name = "问卷分数")
    private Double score;

    /**
     * 创建者id
     */
    @Excel(name = "创建者id")
    private Long creatorId;

    /**
     * 创建者名称
     */
    @Excel(name = "创建者名称")
    private String creatorName;

    /**
     * 创建者部门
     */
    @Excel(name = "创建者部门")
    private Long creatorDeptId;

    /**
     * 删除状态
     */
    @Excel(name = "删除状态")
    private Integer delStatus;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String headPortrait;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 问卷是否答题(0:未答卷，1:已答卷)
     */
    private Integer answerStatus;

    /**
     * 提交问卷时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    ////////////////////////////////////////////////
    /**
     * 图片
     */
    private String imageUrl;
}
