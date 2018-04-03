package com.handicraft.api.controller;

import com.handicraft.api.exception.*;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ResponseEntity badRequestHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(value = {NotFoundException.class, IllegalArgumentException.class, FileNotFoundException.class})
    public ResponseEntity notFoundHandler() {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(value = InternalServerErrorException.class)
    public ResponseEntity internalServerErrorHandler() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = {NotAcceptableException.class})
    public ResponseEntity notAcceptableHandler() {
        return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @ExceptionHandler(value = {EncryptionException.class, UnsupportedEncodingException.class, NoSuchPaddingException.class, NoSuchAlgorithmException.class, InvalidAlgorithmParameterException.class, InvalidKeyException.class, BadPaddingException.class, IllegalBlockSizeException.class})
    public ResponseEntity encryptionException() {
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
