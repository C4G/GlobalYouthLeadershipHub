package com.legacyinternational.globalyouthleadership.adapter.web;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.Map;

@RestControllerAdvice
@Order(1)
public class TargetedExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of("error", ex.getReason()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleDateTimeParseException(DateTimeParseException e) {
        return ResponseEntity.badRequest().body(
                Map.of("error", "Invalid date format. Expected ISO 8601 format: yyyy-MM-dd'T'HH:mm:ss'Z'. Example: 1990-05-20T15:30:00Z")
        );
    }
}
