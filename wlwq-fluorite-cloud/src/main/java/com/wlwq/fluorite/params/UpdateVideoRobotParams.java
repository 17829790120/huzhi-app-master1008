package com.wlwq.fluorite.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName UpdateVideoRobotParams
 * @Description 修改设备名称参数封装
 * @Date 2021/4/13 16:07
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateVideoRobotParams implements Serializable {

    /** 设备序列号,存在英文字母的设备序列号，字母需为大写. */
    private String deviceSerial;

    /** 设备名称，长度不大于50字节，不能包含特殊字符. */
    private String deviceName;

}
