package com.example.demo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Data;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Entity Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntityException(DuplicateEntityException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), "Duplicate Entity");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), ex.getMessage(), "Authorization Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "Invalid Request");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", "Internal Server Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;

        public ErrorResponse(LocalDateTime timestamp, int status, String message, String error) {
            this.timestamp = timestamp;
            this.status = status;
            this.message = message;
            this.error = error;
        }

        // Getters and setters
    }

}