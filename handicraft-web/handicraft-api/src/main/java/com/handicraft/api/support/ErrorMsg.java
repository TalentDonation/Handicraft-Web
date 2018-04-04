package com.handicraft.api.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Setter
@Getter
public class ErrorMsg {
    private String msg;
    private HttpStatus status;

    public ErrorMsg() {
    }

    public ErrorMsg(String msg, HttpStatus status){
        this.msg = msg;
        this.status = status;
    }
}
