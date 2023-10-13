package com.wlwq.common.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author:gaoce
 * @Date:2021/7/17 11:07
 */
public class QrCodeUtils {
    /**
     * 获取二维码
     * @param content  内容
     * @param logo 图片Base64值
     * @return
     */
    public static String getBase64QrCode(int width,int height,int margin,String content,String logo) {
        QrConfig config = new QrConfig(width, height);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(margin);
        if (StringUtils.isBlank(logo)){
            return QrCodeUtil.generateAsBase64(content, config, ImgUtil.IMAGE_TYPE_JPG);
        }
        return QrCodeUtil.generateAsBase64(content, config, ImgUtil.IMAGE_TYPE_JPG,logo);
    }
}
