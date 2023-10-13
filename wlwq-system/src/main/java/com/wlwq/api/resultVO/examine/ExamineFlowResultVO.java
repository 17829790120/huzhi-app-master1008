package com.wlwq.api.resultVO.examine;

import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author:gaoce
 * @Date:2021/11/9 18:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineFlowResultVO implements Serializable {


    /**
     * 审批人/抄送人
     */
    @Builder.Default
    private String examineFlowName = "审批人";

    /**
     * 1:审批人/2:抄送人
      */
    @Builder.Default
    private Integer examineFlowNameTag = 1;

    /**
     * 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
      */
    @Builder.Default
    private Integer examineFlowStatus = 0;

    /**
     * 审批的人数
     */
    @Builder.Default
    private Integer examinePeopleCount = 1;

    /**
     * 审批顺序，正序
     */
    private Integer examineSequence;

    /**
     * 审批人员列表
     */
    private List<ExamineFlowAccountResultVO> accountResultVOList;



}
