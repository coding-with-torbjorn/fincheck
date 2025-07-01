package com.financials.fincheck.controller;

import com.financials.fincheck.dto.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationResponse> handleDeserializationError(HttpMessageNotReadableException ex) {
        Throwable rootCause = ex.getCause();

        String message = "Invalid request format";
        if (rootCause instanceof IOException) {
            message = rootCause.getMessage(); // e.g. "Invalid currency code: FAKE"
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationResponse(false, List.of(message)));
    }
}
