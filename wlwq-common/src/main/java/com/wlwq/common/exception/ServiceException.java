package com.wlwq.common.exception;

import org.springframework.http.HttpStatus;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 23:56
 *  @Description: Exception caused by service.
 */
public class ServiceException extends AbstractApiException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
