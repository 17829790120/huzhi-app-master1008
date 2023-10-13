package com.wlwq.common.exception;

import org.springframework.http.HttpStatus;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 23:55
 *  @Description: Exception caused by bad request.
 */
public class BadRequestException extends AbstractApiException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
