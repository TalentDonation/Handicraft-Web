package com.handicraft.api.controller;

import com.handicraft.api.exception.*;
import com.handicraft.api.support.ErrorMsg;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.AccessDeniedException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 고승빈 on 2017-07-26.
 */

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {BadRequestException.class, AccessDeniedException.class})
    public ErrorMsg badRequestHandler(RuntimeException e) {
        return new ErrorMsg(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class, IllegalArgumentException.class, FileNotFoundException.class})
    public ErrorMsg notFoundHandler(RuntimeException e) {
        return new ErrorMsg(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InternalServerErrorException.class)
    public ErrorMsg internalServerErrorHandler(RuntimeException e) {
        return new ErrorMsg(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NotAcceptableException.class})
    public ErrorMsg notAcceptableHandler(RuntimeException e) {
        return new ErrorMsg(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {EncryptionException.class, UnsupportedEncodingException.class,
            NoSuchPaddingException.class, NoSuchAlgorithmException.class,
            InvalidAlgorithmParameterException.class, InvalidKeyException.class,
            BadPaddingException.class, IllegalBlockSizeException.class})
    public ErrorMsg encryptHandler(RuntimeException e) {
        return new ErrorMsg(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
