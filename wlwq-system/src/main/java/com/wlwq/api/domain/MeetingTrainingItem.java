package com.wlwq.api.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 会议训练流程事项对象 meeting_training_item
 * 
 * @author wwb
 * @date 2023-05-29
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("MeetingTrainingItem")
public class MeetingTrainingItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 会议训练流程事项主键id
     */
    private String meetingTrainingItemId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String synopsis;

    /**
     * 排序，数字越小越靠前
     */
    @Excel(name = "排序，数字越小越靠前")
    private Integer sortNum;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long companyId;

    /**
     * 会议训练主键id
     */
    @Excel(name = "会议训练主键id")
    private String meetingTrainingId;

}
