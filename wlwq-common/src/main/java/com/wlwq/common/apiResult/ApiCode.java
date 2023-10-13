package com.wlwq.common.apiResult;


public enum ApiCode {

    /*
    返回值枚举类
     */

    FAIL(0, "操作失败"),

    SUCCESS(1, "操作成功"),

    DONT_BIND_WECHAT(12,"未绑定微信！"),

    APPLE_ORDER(13,"订单已生成，请拉起苹果支付！"),

    PAY_SUCCESS(14,"订单支付成功！"),

    UNAUTHORIZED(401, "非法访问"),
    DONT_LOGIN(201, "未登录！"),

    NOT_FOUND(404, "你请求的路径不存在"),


    ERROR(500,"网络异常，请稍后重试！"),

    SYSTEM_EXCEPTION(5000, "系统异常!"),

    LOGIN_EXCEPTION(5005, "系统登录异常");

    private final int code;
    private final String msg;

    ApiCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiCode getApiCode(int code) {
        ApiCode[] ecs = ApiCode.values();
        for (ApiCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
