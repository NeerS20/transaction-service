package com.transaction.service.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handle(NotFoundException ex)
    {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex)
    {
        return ResponseEntity.status(500).body("Internal error");
    }
}
