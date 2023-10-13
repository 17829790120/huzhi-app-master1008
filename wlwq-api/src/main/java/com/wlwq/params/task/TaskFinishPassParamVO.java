package com.wlwq.params.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskFinishPassParamVO implements Serializable {

    /**
     * 任务审批ID
     */
    @NotBlank(message = "请传入任务审批ID")
    private String taskFlowAccountId;

    /**
     * 通过理由
     */
    private String content;


    /**
     *  审批通过之后  审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
     */
    @Builder.Default
    private Integer examineStatus = 8;

    /**
     * 图片,多个以逗号隔开
     */
    private String pics;

}
