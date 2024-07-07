package org.example.mangalistspringboot.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handle(final MethodArgumentNotValidException exception) {
    var errors = new HashMap<String, String>();

    exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    var responseBody = new HashMap<String, Object>();
    responseBody.put("errors", errors);
    responseBody.put("message", "Validation failed");
    responseBody.put("statusCode", 422);

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseBody);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
    var responseBody = new HashMap<String, Object>();
    responseBody.put("message", "Invalid request body. Please check the input values.");
    responseBody.put("statusCode", 400);

    responseBody.put("details", exception.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(final IllegalArgumentException exception) {
    var responseBody = new HashMap<String, Object>();
    responseBody.put("message", exception.getMessage());
    responseBody.put("statusCode", 400);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Map<String, Object>> handleNoSuchElementException(final NoSuchElementException exception) {
    var responseBody = new HashMap<String, Object>();
    responseBody.put("message", exception.getMessage());
    responseBody.put("statusCode", 404);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }
}
