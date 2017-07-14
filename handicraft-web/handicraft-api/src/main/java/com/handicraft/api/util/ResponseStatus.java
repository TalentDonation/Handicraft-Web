package com.handicraft.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
/**
 * Created by 고승빈 on 2017-07-10.
 */
public class ResponseStatus {


    public ResponseEntity responseByResult(Object object , HttpStatus status) {

        if(object == null)
            return new ResponseEntity<String>(status);
        else
            return new ResponseEntity<Object>(object, status);

    }

}
