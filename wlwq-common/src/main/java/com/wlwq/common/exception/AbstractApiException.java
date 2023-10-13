package com.wlwq.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 23:55
 *  @Description: Base exception of the project.
 */
public abstract class AbstractApiException extends RuntimeException {

    /**
     * Error errorData.
     */
    private Object errorData;

    public AbstractApiException(String message) {
        super(message);
    }

    public AbstractApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Http status code
     *
     * @return {@link HttpStatus}
     */
    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public AbstractApiException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
