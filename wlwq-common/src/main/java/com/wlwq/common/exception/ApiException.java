package com.wlwq.common.exception;


import com.wlwq.common.apiResult.ApiCode;

/**
 * @ClassName ApiException
 * @Description api通用异常处理
 * @Date 2020/9/30 10:19
 * Create By Renbowen
 */
public class ApiException extends RuntimeException {

    private Integer code;

    public ApiException(ApiCode apiCode) {
        super(apiCode.getMsg());
        this.code = apiCode.getCode();
    }

    public ApiException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
