package com.handicraft.api.exception;

public class EncryptionException extends RuntimeException {
    public EncryptionException() {
        super();
    }

    public EncryptionException(String msg, Throwable t) {
        super(msg, t);
    }
}