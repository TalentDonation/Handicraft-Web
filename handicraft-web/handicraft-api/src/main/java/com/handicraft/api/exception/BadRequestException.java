package com.handicraft.api.exception;

/**
 * Created by 고승빈 on 2017-07-26.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super();
    }

    public BadRequestException(String msg, Throwable t) {
        super(msg, t);
    }
}
