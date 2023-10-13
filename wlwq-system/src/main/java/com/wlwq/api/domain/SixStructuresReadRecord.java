package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;

/**
 * 六大架构已读记录对象 six_structures_read_record
 * 
 * @author wlwq
 * @date 2023-08-28
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SixStructuresReadRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 六大架构已读记录ID
     */
    private String sixStructuresReadRecordId;

    /**
     * 六大架构ID
     */
    @Excel(name = "六大架构ID")
    private String sixStructuresId;

    /**
     * 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     */
    @Excel(name = "1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报")
    private Long templateType;

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

    /**
     * 一级分类ID
     */
    @Excel(name = "一级分类ID")
    private String oneStoreClassId;

    /**
     * 二级分类ID
     */
    @Excel(name = "二级分类ID")
    private String twoStoreClassId;

    /**
     * 三级分类ID
     */
    @Excel(name = "三级分类ID")
    private String threeStoreClassId;

}
