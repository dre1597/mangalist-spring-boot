package org.example.mangalistspringboot.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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
}
