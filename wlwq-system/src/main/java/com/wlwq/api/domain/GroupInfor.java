package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 小组信息对象 group_infor
 *
 * @author wwb
 * @date 2023-06-10
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("GroupInfor")
public class GroupInfor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 小组信息ID
     */
    private Long groupInforId;

    /**
     * 小组名称
     */
    @Excel(name = "小组名称")
    private String groupInforName;

    /**
     * 小组图标
     */
    @Excel(name = "小组图标")
    private String groupInforIcon;

    /**
     * 排序(排序越小，越靠前)
     */
    @Excel(name = "排序(排序越小，越靠前)")
    private Long sortNum;

    /**
     * 是否显示(0:不显示 1:显示)
     */
    @Excel(name = "是否显示(0:不显示 1:显示)")
    private Integer showStatus;

    /**
     * 是否删除(0:未删除 1:已删除)
     */
    @Excel(name = "是否删除(0:未删除 1:已删除)")
    private Integer delStatus;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 创建者id
     */
    @Excel(name = "创建者id")
    private Long creatorId;

    /**
     * 创建者头像
     */
    @Excel(name = "创建者头像")
    private String creatorHeadPortrait;

    /**
     * 创建者昵称
     */
    @Excel(name = "创建者昵称")
    private String creatorNickName;

}
