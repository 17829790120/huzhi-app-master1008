package com.wlwq.fluorite.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PlayParams
 * @Description 播放地址接口参数封装
 * @Date 2021/4/13 16:36
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlayParams implements Serializable {

    /** 直播源(设备编号)，例如427734222，均采用英文符号，限制50个. */
    private String deviceSerial;

    /** 通道号,(非必填)，默认为1. */
    private Integer channelNo;

    /** ezopen协议地址的设备的视频加密密码(非必填). */
    private String code;

    /** 过期时长 (非必填)，单位秒；针对hls/rtmp设置有效期，相对时间；30秒-7天. */
    private Integer expireTime;

    /** 流播放协议(非必填)，1-ezopen、2-hls、3-rtmp、4-flv，默认为1. */
    private Integer protocol;

    /** 视频清晰度(非必填)，1-高清（主码流）、2-流畅（子码流）. */
    private Integer quality;

    /**(非必填) ezopen协议地址的本地录像/云存储录像回放开始时间,示例：2019-12-01 00:00:00. */
    private String startTime;

    /** (非必填) ezopen协议地址的本地录像/云存储录像回放开始时间,示例：2019-12-01 00:00:00. */
    private String stopTime;

    /** (非必填) ezopen协议地址的类型，1-预览，2-本地录像回放，3-云存储录像回放，非必选，默认为1. */
    private String type;

}
