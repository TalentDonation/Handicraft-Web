package com.handicraft.api.exception;

/**
 * Created by 고승빈 on 2017-07-27.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super();
    }

    public NotFoundException(String msg, Throwable t){
        super(msg, t);
    }
}
