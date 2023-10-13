package com.wlwq.params.courseExam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 训练跟进记录
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WinningTrainingFollowUpRecordParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 训练记录主键id
     */
    @NotNull(message = "请选择训练记录")
    private String winningTrainingRecordId;

    /**
     * 跟进内容
     */
    private String content;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 岗位名称
     */
    private String postNames;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 图片
     */
    private String pictureUrl;
}
