package com.wlwq.fluorite.config;

/**
 * @ClassName FluoriteCloudConfig
 * @Description 萤石云配置类
 * @Date 2021/4/13 14:37
 * @Author Rick wlwq
 */
public class FluoriteCloudConfig {

    /**
     * AppKey
     */
    public static final String APP_KEY = "0253c0a52fd44e8a89b4a4f9ad9a148d";

    /**
     * Secret
     */
    public static final String APP_SECRET = "9ebd9734f22260811792a00c6d9195bf";

    /**
     * 授权URL
     */
    public static final String AUTH_URL = "https://open.ys7.com/api/lapp/token/get";

    /**
     * 添加设备URL
     */
    public static final String ADD_VIDEO_ROBOT_URL = "https://open.ys7.com/api/lapp/device/add";

    /**
     * 删除设备URL
     */
    public static final String DELETE_VIDEO_ROBOT_URL = "https://open.ys7.com/api/lapp/device/delete";

    /**
     * 修改设备URL
     */
    public static final String UPDATE_VIDEO_ROBOT_NAME_URL = "https://open.ys7.com/api/lapp/device/name/update";

    /**
     * 设备抓拍URL
     */
    public static final String TAKE_PICTURE_URL = "https://open.ys7.com/api/lapp/device/capture";

    /**
     * 获取播放地址URL
     */
    public static final String PLAY_URL = "https://open.ys7.com/api/lapp/v2/live/address/get";

}
