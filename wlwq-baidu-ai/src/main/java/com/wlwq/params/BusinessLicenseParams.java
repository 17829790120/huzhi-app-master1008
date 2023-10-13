package com.wlwq.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName BusinessLicenseParams
 * @Description 营业执照识别参数封装
 * @Date 2021/7/17 16:38
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BusinessLicenseParams implements Serializable {

    /** 营业执照图片base64编码. */
    private String imageBase64;

    /**
     * 营业执照图片地址请
     * 大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/jpeg/png/bmp格
     * 注意关闭URL防盗链.
     */
    private String imageUrl;
}
