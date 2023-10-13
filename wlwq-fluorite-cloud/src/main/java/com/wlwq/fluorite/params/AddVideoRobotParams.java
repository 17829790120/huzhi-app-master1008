package com.wlwq.fluorite.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName AddVideoRobotParams
 * @Description 添加设备参数封装
 * @Date 2021/4/13 15:38
 * @Author Rick wlwq
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddVideoRobotParams implements Serializable {

    /** 设备序列号,存在英文字母的设备序列号，字母需为大写. */
    private String deviceSerial;

    /** 设备验证码，设备机身上的六位大写字母. */
    private String validateCode;

}
