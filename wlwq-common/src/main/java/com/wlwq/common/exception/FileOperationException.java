package com.wlwq.common.exception;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 23:55
 *  @Description: File operation exception.
 */ 
public class FileOperationException extends ServiceException {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
