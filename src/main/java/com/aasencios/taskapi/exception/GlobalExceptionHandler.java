package com.aasencios.taskapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailExists(EmailAlreadyExistsException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFound(TaskNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Error interno del servidor");
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("timestamp", LocalDateTime.now());
        return ResponseEntity.badRequest().body(errors);
    }
}
