package com.wlwq.common.exception;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 23:55
 *  @Description: Image format exception.
 */
public class ImageFormatException extends BadRequestException {

    public ImageFormatException(String message) {
        super(message);
    }

    public ImageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
