package com.reserve.reserve.exception;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ErrorDTO {

    private String errorCode;
    private String message;

    public static ResponseEntity<ErrorDTO> toResponseEntity(ApiException ex){
        ErrorDTO errorDTO = ErrorDTO.builder()
        .errorCode(ex.getErrorCode())
        .message(ex.getMessage())
        .build();
        
        return ResponseEntity.ok(errorDTO);
    }

}
