package com.reserve.reserve.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorDTO> handleCustomException(ApiException ex){
        return ErrorDTO.toResponseEntity(ex);
    }

}
