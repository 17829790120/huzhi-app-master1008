package com.wlwq.params.clocking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClockingParamVO implements Serializable {


    /**
     *  1:正常打卡 2:外勤打卡
     */
    @NotNull(message = "请传入打卡类型")
    private Integer status;

    /**
     * 签到地点
     */
    @NotBlank(message = "请传入打卡地点")
    private String address;

    /**
     * 经度
     */
    @NotBlank(message = "请传入打卡经度")
    private String lon;

    /**
     * 纬度
     */
    @NotBlank(message = "请传入打卡纬度")
    private String lat;


    /**
     * 外出签到图片
     */
    private String pics;

    /** 备注 */
    private String remark;

}
