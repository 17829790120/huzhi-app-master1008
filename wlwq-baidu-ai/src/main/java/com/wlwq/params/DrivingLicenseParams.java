package com.wlwq.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName DrivingLicenseParams
 * @Description 驾驶证识别参数封装
 * @Date 2021/7/17 17:05
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DrivingLicenseParams implements Serializable {

    /** 驾驶证图片base64编码. */
    private String imageBase64;

    /**
     * 驾驶证图片地址请
     * 大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/jpeg/png/bmp格
     * 注意关闭URL防盗链.
     */
    private String imageUrl;

    /**
     * - false：默认值，不检测朝向，朝向是指输入图像是正常方向、逆时针旋转90/180/270度
     * - true：检测朝向
     */
    private String detectDirection;

    /**
     * - front：默认值，识别驾驶证正页
     * - back：识别驾驶证副页
     */
    private String drivingLicenseSide;
}
