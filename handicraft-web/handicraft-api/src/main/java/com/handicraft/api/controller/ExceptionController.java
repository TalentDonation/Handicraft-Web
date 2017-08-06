package com.handicraft.api.controller;

import com.handicraft.api.exception.BadRequestException;
import com.handicraft.api.exception.InternalServerErrorException;
import com.handicraft.api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

/**
 * Created by 고승빈 on 2017-07-26.
 */

@RestControllerAdvice
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

    @ExceptionHandler(value = InternalServerErrorException.class)
    @ResponseBody
    public ResponseEntity<?> internalServerErrorHandler()
    {
        return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
