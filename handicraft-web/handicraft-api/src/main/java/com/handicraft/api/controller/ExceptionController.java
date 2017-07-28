package com.handicraft.api.controller;

import com.handicraft.api.exception.BadRequestException;
import com.handicraft.api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

/**
 * Created by 고승빈 on 2017-07-26.
 */

@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    public ResponseEntity<?> badRequestHandler()
    {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> notFoundHandler()
    {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
