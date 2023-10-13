package com.wlwq.fluorite.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName TakePictureParams
 * @Description 设备抓拍参数封装
 * @Date 2021/4/13 16:14
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TakePictureParams implements Serializable {

    /** 设备序列号,存在英文字母的设备序列号，字母需为大写. */
    private String deviceSerial;

    /** 通道号，IPC设备填写1. */
    private Integer channelNo;
}
