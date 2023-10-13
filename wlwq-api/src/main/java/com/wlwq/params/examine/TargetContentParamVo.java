package com.wlwq.params.examine;

import com.wlwq.api.domain.ExamineFile;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * @author gaoce
 * 目标训练目标完成提交参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TargetContentParamVo implements Serializable {

    /**
     * 父级标题
     */
    private String parentTitle;

    /**
     * 子级标题
     */
    private String subTitle;

    /**
     * 子级内容
     */
    private String subContent;

    /**
     * 证明文件
     */
    private String documentationUrl;
    /**
     * 文件列表
     */
    private List<ExamineFile> fileList;

    /**
     * 自定义字段id
     */
    private String targetTrainingRecordContentId;

    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 目标训练记录主键id
     */
    private String targetTrainingRecordId;

    /**
     * 目标训练主键id
     */
    private String targetTrainingId;

    /**
     * 发起者所在部门名称
     */
    private String deptName;

    /**
     * 会议训练主键id
     */
    private String meetingTrainingId;
}
