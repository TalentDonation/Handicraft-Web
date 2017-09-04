package com.handicraft.api.controller;

import com.handicraft.api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 고승빈 on 2017-07-26.
 */

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {BadRequestException.class })
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

    @ExceptionHandler(value = {NotAcceptableException.class})
    @ResponseBody
    public ResponseEntity<?> notAcceptableHandler()
    {
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {UnAuthorizedException.class , HttpClientErrorException.class})
    @ResponseBody
    public ResponseEntity<?> unAuthorizedHandler()
    {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {EncryptionException.class , UnsupportedEncodingException.class , NoSuchPaddingException.class , NoSuchAlgorithmException.class , InvalidAlgorithmParameterException.class, InvalidKeyException.class ,BadPaddingException.class , IllegalBlockSizeException.class})
    @ResponseBody
    public ResponseEntity<?> encryptionException()
    {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
