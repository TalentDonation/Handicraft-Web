package com.handicraft.api.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super();
    }

    public UnAuthorizedException(String msg, Throwable t) {
        super(msg, t);
    }
}