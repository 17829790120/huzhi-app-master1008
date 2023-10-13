package com.wlwq.api.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 会议训练纪要对象 meeting_training_minutes
 *
 * @author wwb
 * @date 2023-05-31
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("MeetingTrainingMinutes")
public class MeetingTrainingMinutes extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 会议训练纪要主键id
     */
    private String meetingTrainingMinutesId;

    /**
     * 会议训练主键id
     */
    @Excel(name = "会议训练主键id")
    private String meetingTrainingId;

    /**
     * 会议纪要
     */
    @Excel(name = "会议纪要")
    private String minutes;

    /**
     * 会议总结
     */
    @Excel(name = "会议总结")
    private String summary;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String picUrl;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String fileUrl;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private String accountId;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String headPortrait;

    /**
     * 用户手机号
     */
    @Excel(name = "用户手机号")
    private String phone;

    /**
     * 用户归属部门ID
     */
    @Excel(name = "用户归属部门ID")
    private Long deptId;

    /**
     * 岗位IDS
     */
    @Excel(name = "岗位IDS")
    private String postIds;

}
