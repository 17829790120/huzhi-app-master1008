package com.wlwq.information.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 资讯浏览记录对象 information_browse_record
 *
 * @author Rick wlwq
 * @date 2021-04-20
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformationBrowseRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 资讯浏览记录ID
     */
    private Long informationBrowseRecordId;

    /**
     * 资讯ID
     */
    private Long informationPostId;

    @Excel(name = "资讯标题")
    private String informationPostTitle;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private String accountId;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像")
    private String accountHead;

    /**
     * 用户名字
     */
    @Excel(name = "用户名字")
    private String accountName;

    /**
     * 咨询类型
     * 1：文化展示；2：新闻中心；3：个人与组织的关系--学习
     */
    private Integer informationType;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;
}
