package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 消息提醒对象 message_remind
 *
 * @author gaoce
 * @date 2023-06-10
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("MessageRemind")
public class MessageRemind extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 消息提醒表
     */
    private String messageRemindId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 消息内容
     */
    @Excel(name = "消息内容")
    private String brief;

    /**
     * 消息类型（1系统消息 2积分消息）
     */
    @Excel(name = "消息类型", readConverterExp = "1=系统消息,2=积分消息")
    private Integer modelStatus;

    /**
     * 各个类型的ID
     */
    @Excel(name = "各个类型的ID")
    private String modelId;

    /**
     * 接收账号ID
     */
    @Excel(name = "接收账号ID")
    private String accountId;

    /**
     * 读取状态（0未读 1已读）
     */
    @Excel(name = "读取状态", readConverterExp = "0=未读,1=已读")
    private Integer readStatus;

    /**
     * 缩略图
     */
    @Excel(name = "缩略图")
    private String coverImage;

    /**
     * 跳转类型（-1：勋章 -2:积分中心 -3:任务 -4：红花 0:不跳转 其他的查看积分设置模板字典表(sys_set_score)
     */
    @Excel(name = "跳转类型")
    private Integer jumpType;

    /**
     * 关联对象id（需要查询某条数据详情时用）
     */
    private String targetId;

}
