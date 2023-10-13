package com.wlwq.common.exception;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 23:55
 *  @Description: RepeatTypeException
 */
public class RepeatTypeException extends ServiceException {
    public RepeatTypeException(String message) {
        super(message);
    }

    public RepeatTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
