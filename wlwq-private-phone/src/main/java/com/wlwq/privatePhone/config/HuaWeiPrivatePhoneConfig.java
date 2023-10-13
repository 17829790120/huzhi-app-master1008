package com.wlwq.privatePhone.config;

/**
 * @ClassName HuaWeiPrivatePhoneConfig
 * @Description 华为云隐私通话配置
 * @Date 2021/4/2 11:47
 * @Author Rick wlwq
 */
public class HuaWeiPrivatePhoneConfig {

    /**
     * APP_Key
     */
    public final static String OMP_APP_KEY = "KLY3NypSP53Y9536Tbsi8v8p9hr9";

    /**
     * APP_Secret
     */
    public final static String OMP_APP_SECRET = "M9WV1cz73SaemZl0EuJyHMR2cJgN";

    /**
     *  APP接入地址
     */
    public final static String OMP_DOMAIN_NAME = "https://rtcpns.cn-north-1.myhuaweicloud.com:443";

    /**
     * TODO 隐私通话回调配置的外网可访问的服务地址
     */
    public final static String SERVICE_URL = "http://2ql7515607.qicp.vip";

    /**
     * AX模式绑定接口地址
     */
    public final static String AX_BIND_URL = "/rest/provision/caas/privatenumber/v1.0";

    /**
     * AX模式解绑接口地址
     */
    public final static String AX_UNBIND_URL = "/rest/provision/caas/privatenumber/v1.0";

    /**
     * AX临时被叫接口地址
     */
    public final static String AX_SET_CALLEE_NUMBER_URL = "/rest/caas/privatenumber/calleenumber/v1.0";

    /**
     * AX模式获取录音文件下载地址接口访问URI
     */
    public final static String AX_GET_RECORD_DOWNLOAD_LINK_URL = "/rest/provision/voice/record/v1.0";

    /**
     * AX停止呼叫接口访问地址
     */
    public final static String AX_STOP_CALL_URL = "/rest/httpsessions/callStop/v2.0";

    /**
     * 国内手机号码前缀
     */
    public final static String CHINESE_PHONE_PREFIX = "+86";
}
