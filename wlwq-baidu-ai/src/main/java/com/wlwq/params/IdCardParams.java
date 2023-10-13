package com.wlwq.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName IdCardParams
 * @Description 身份证识别参数封装
 * @Date 2021/2/7 11:41
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IdCardParams implements Serializable {

    /** 身份证图片base64编码. */
    private String imageBase64;

    /**
     * 身份证图片地址请
     * 大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/jpeg/png/bmp格
     * 注意关闭URL防盗链.
     */
    private String imageUrl;

    /**
     * -front：身份证含照片的一面
     * -back：身份证带国徽的一面.
     * 常量类 IdCardConstant
     */
    private String idCardSide;

    /** 是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能 默认false 常量类 IdCardConstant . */
    private String detectRisk;

    /** 是否检测头像内容，默认不检测. */
    private String detectPhoto;
}
