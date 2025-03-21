package com.reserve.reserve.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private String errorCode;

    public ApiException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}
