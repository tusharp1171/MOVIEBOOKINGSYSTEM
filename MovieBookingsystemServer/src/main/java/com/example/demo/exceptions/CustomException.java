package com.example.demo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private LocalDateTime timestamp;
    private int statusCode;
    private String errorMessage;
    
    public CustomException(HttpStatus status, String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
        this.statusCode = status.value();
        this.errorMessage = message;
    }
    
    
}