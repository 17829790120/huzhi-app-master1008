package com.wlwq.fluorite.constant;

/**
 * @ClassName FluoriteCloudConstant
 * @Description 萤石云常量类
 * @Date 2021/4/13 14:54
 * @Author Rick wlwq
 */
public class FluoriteCloudConstant {

    /**
     * 萤石云存储在redis中的accessToken的key
     */
    public static final String FLUORITE_CLOUD_ACCESS_TOKEN_REDIS_KEY = "FLUORITE_CLOUD_ACCESS_TOKEN";

    /**
     * 萤石云接口返回值字段
     */
    public static final String RESULT_KEY = "code";

    /**
     * 萤石云接口返回成功值
     */
    public static final String SUCCESS_CODE_VALUE = "200";

    /**
     * 未授权或授权过期值
     */
    public static final String NOT_AUTH_VALUE = "10002";

}
