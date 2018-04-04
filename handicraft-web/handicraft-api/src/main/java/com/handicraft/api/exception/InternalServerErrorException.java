package com.handicraft.api.exception;

/**
 * Created by 고승빈 on 2017-07-29.
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String msg, Throwable t) {
        super(msg, t);
    }
}
