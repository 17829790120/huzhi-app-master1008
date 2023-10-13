package com.wlwq.params.reportTraining;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * pk与对赌查询参数
 * @author gaoce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WinningTrainingRecordParamVO implements Serializable {

    /**
     * 赢的训练记录主键id
     */
    private String winningTrainingRecordId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 创建者id
     */
    private Long creatorId;

    /**
     * 创建者昵称
     */
    private String creatorNickName;

    /**
     * pk/对赌类型 1：员工与员工pk，2：部门与部门pk,3:小组与小组pk，4：个人与部门对赌，5：个人与公司对赌，6：部门与公司对赌
     */
    private Integer pkBettingType;

    /**
     * 赢的训练类型 1：pk，2：对赌
     */
    private Integer winningTrainingType;


    /**
     * 甲方名称
     */
    private String partyAName;

    /**
     * 乙方名称
     */
    private String partyBName;

    /**
     * 模板类型
     */
    private Integer templateType;

    /**
     * 0:未开始；1：进行中；2：已结束
     */
    private Long pkStatus;
}
