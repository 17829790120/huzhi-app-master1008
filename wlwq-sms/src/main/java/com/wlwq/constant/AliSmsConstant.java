package com.wlwq.constant;

/**
 * @ClassName AliSmsConfig
 * @Description 阿里云短信配置
 * @Date 2021/4/13 17:58
 * @Author Rick wlwq
 */
public class AliSmsConstant {

    /**
     * 是否开启人机验证  true打开 false关闭
     */
    public final static Boolean ROBOT_FLAG = false;

    /**
     * 短信发送成功
     */
    public static final String SEND_SUCCESS = "OK";

    /**
     * 产品域名,开发者无需替换
     */
    public static final String SMS_DOMAIN = "dysmsapi.aliyuncs.com";

    /**
     * 人机验证区域
     */
    public static final String VALID_ROBOT_REGION_ID = "cn-hangzhou";

    /**
     * 人机验证endpointName
     */
    public static final String VALID_ROBOT_ENDPOINT_NAME = "cn-hangzhou";

    /**
     * 人机验证product
     */
    public static final String VALID_ROBOT_PRODUCT = "afs";


    /**
     * 人机验证产品域名
     */
    public static final String VALID_ROBOT_DOMAIN = "afs.aliyuncs.com";

    /**
     * 短信类型-验证码
     */
    public static final String SMS_TYPE_CODE = "code";


    /**
     * 短信类型-通知
     */
    public static final String SMS_TYPE_INFORM = "inform";


    /**
     * 腾讯短信发送成功
     */
    public static final String TENCENT_SEND_SUCCESS = "Ok";


    /**
     * 腾讯短信错误类型
     * 单个手机号日下发短信条数超过设定的上限，可自行到控制台调整短信频率限制策略。
     */
    public static final String TENCENT_PhoneNumberDailyLimit = "LimitExceeded.PhoneNumberDailyLimit";

    /**
     * 腾讯短信错误类型
     * 单个手机号1小时内下发短信条数超过设定的上限，可自行到控制台调整短信频率限制策略。
     */
    public static final String TENCENT_PhoneNumberOneHourLimit = "LimitExceeded.PhoneNumberOneHourLimit";

    /**
     * 腾讯短信错误类型
     * 单个手机号30秒内下发短信条数超过设定的上限，可自行到控制台调整短信频率限制策略。
     */
    public static final String TENCENT_PhoneNumberThirtySecondLimit = "LimitExceeded.PhoneNumberThirtySecondLimit";

    /**
     * 腾讯短信错误类型
     * 欠费被停止服务，可自行登录云平台充值来缴清欠款
     */
    public static final String TENCENT_SerivceSuspendDueToArrears = "UnauthorizedOperation.SerivceSuspendDueToArrears";

}
