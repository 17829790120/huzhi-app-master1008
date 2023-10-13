package com.wlwq.privatePhone.axService;

import com.wlwq.common.apiResult.ApiCode;

/**
 * @ClassName HuaweiResultCodeEnum
 * @Description 华为云返回值封装
 * @Date 2021/4/6 16:09
 * @Author Rick wlwq
 */
public enum HuaweiResultCodeEnum {

    /*
     返回值枚举类
      */
    SUCCESS("0", "操作成功"),
    GET_VOICE_SUCCESS("301", "操作成功");

    private final String code;
    private final String msg;

    HuaweiResultCodeEnum(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static HuaweiResultCodeEnum getHuaweiResultCodeEnum(String code) {
        HuaweiResultCodeEnum[] ecs = HuaweiResultCodeEnum.values();
        for (HuaweiResultCodeEnum ec : ecs) {
            if (ec.getCode().equals(code)) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
