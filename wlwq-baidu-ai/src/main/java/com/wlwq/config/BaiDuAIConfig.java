package com.wlwq.config;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TextRecognitionConfig
 * @Description 百度AI文字识别配置
 * @Date 2021/2/7 10:54
 * @Author Rick wlwq
 */
public class BaiDuAIConfig {

    /**
     * AppID
     */
    public final static String APP_ID = "24497095";

    /**
     * API Key
     */
    public final static String API_KEY = "0ENRnikwg0KG5TAcU7MUR3Rp";

    /**
     * Secret Key
     */
    public final static String SECRET_KEY = "cXmHMqrtsXZVgomUR2ss6m6u0j5LTDQt";

    /**
     * 百度AI授权地址
     */
    public final static String AUTH_URL = "https://aip.baidubce.com/oauth/2.0/token";

    /**
     * 百度AI授权类型 固定为client_credentials
     */
    public final static String GRANT_TYPE = "client_credentials";

    /**
     * 身份证识别url
     */
    public final static String ID_CARD_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";

    /**
     * 银行卡识别url
     */
    public final static String BANK_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/bankcard";

    /**
     * 营业执照识别url
     */
    public final static String BUSINESS_LICENSE_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/business_license";

    /**
     * 驾驶证识别url
     */
    public final static String DRIVING_LICENSE_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/driving_license";

    /**
     * 行驶证识别url
     */
    public final static String VEHICLE_LICENSE_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/vehicle_license";

    /**
     * 文本审核URL
     */
    public final static String AUDIT_TEXT_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined";

    /**
     * 图像审核URL
     */
    public final static String AUDIT_IMAGE_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined";

    /**
     * 视频审核URL
     */
    public final static String AUDIT_VIDEO_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/video_censor/v2/user_defined";

    /**
     * 音频审核URL
     */
    public final static String AUDIT_VOICE_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/voice_censor/v2/user_defined";


    /**
     * 获取百度AI AipOcr
     * @return
     */
    public static AipOcr getAipOcr(){
        return new AipOcr(APP_ID,API_KEY,SECRET_KEY);
    }
}
