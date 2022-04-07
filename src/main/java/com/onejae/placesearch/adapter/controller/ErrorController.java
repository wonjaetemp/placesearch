package com.onejae.placesearch.adapter.controller;

import com.onejae.placesearch.adapter.controller.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> exceptionHandlerIllegalArgumentException(final IllegalArgumentException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.MISSING_PARAMS);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerNotFound() {
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_URI);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler() {
        ErrorResponse response = new ErrorResponse(ErrorCode.UNKNOWN_ERROR);

        return ResponseEntity.badRequest().body(response);
    }
}
