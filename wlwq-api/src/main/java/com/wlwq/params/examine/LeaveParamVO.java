package com.wlwq.params.examine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gaoce
 * 请假审批提交参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveParamVO implements Serializable {


    /**
     * 审批发起ID
     */
    private String examineInitiateId;

    /**
     * 请假类型名称
     */
    @NotBlank(message = "请传入请假类型名称")
    private String leaveType;

    /**
     * 开始时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请传入开始时间")
    private Date startTime;

    /**
     * 结束时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请传入结束时间")
    private Date endTime;

    /**
     * 请假时长(天)
     */
    @NotNull(message = "请传入请假时长(天)")
    private Double askForLeaveHour;

    /**
     * 请假事由
     */
    @NotBlank(message = "请传入请假事由")
    private String reason;

    /**
     * 图片,多个以逗号隔开
     */
    private String pics;
}
