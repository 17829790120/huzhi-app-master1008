package com.wlwq.params.courseExam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 定制课程预约
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseCustomizationPostRecordParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资讯ID
     */
    @NotNull(message = "请选择需要报名的课程。")
    private List<String> courseCustomizationPostIds;

    /**
     * 岗位IDS
     */
    @Excel(name = "岗位IDS")
    private String postIds;

    /**
     * 岗位名称
     */
    private String postNames;

    /**
     * 公司id
     */
    @Excel(name = "公司id")
    private Long companyId;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 公司地址
     */
    @Excel(name = "公司地址")
    private String companyAddress;

    /**
     * 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date reservationTime;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String telephone;

    /**
     * 行业id
     */
    @Excel(name = "行业id")
    private Long industryId;

    /**
     * 行业名称
     */
    @Excel(name = "行业名称")
    private String industryName;

}
