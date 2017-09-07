package com.handicraft.api.exception;

import org.springframework.security.core.AuthenticationException;

public class NotAcceptableException extends AuthenticationException {

    public NotAcceptableException(String msg) {
        super(msg);

    }
}
