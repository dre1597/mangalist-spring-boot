package org.example.mangalistspringboot.infra.api.exceptions;

import org.example.mangalistspringboot.domain.exceptions.MangaAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {
  @InjectMocks
  private CustomExceptionHandler customExceptionHandler;

  private MethodArgumentNotValidException methodArgumentNotValidException;
  private BindingResult bindingResult;

  @BeforeEach
  void setUp() {
    bindingResult = mock(BindingResult.class);
    methodArgumentNotValidException = new MethodArgumentNotValidException(null, bindingResult);
  }

  @Test
  void shouldHandleMethodArgumentNotValidException() {
    var fieldError = new FieldError("objectName", "fieldName", "defaultMessage");
    when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

    var response = customExceptionHandler.handle(methodArgumentNotValidException);

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    assertEquals("Validation failed", response.getBody().get("message"));

    var errors = (Map<String, String>) response.getBody().get("errors");
    assertEquals("defaultMessage", errors.get("fieldName"));
  }

  @Test
  void shouldHandleHttpMessageNotReadableException() {
    var exception = new HttpMessageNotReadableException("Invalid JSON");

    var response = customExceptionHandler.handleHttpMessageNotReadableException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(400, response.getBody().get("statusCode"));
    assertEquals("Invalid request body. Please check the input values.", response.getBody().get("message"));
    assertEquals("Invalid JSON", response.getBody().get("details"));
  }

  @Test
  void shouldHandleIllegalArgumentException() {
    var exception = new IllegalArgumentException("Invalid argument");

    var response = customExceptionHandler.handleIllegalArgumentException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(400, response.getBody().get("statusCode"));
    assertEquals("Invalid argument", response.getBody().get("message"));
  }

  @Test
  void shouldHandleNoSuchElementException() {
    var exception = new NoSuchElementException("Resource not found");

    var response = customExceptionHandler.handleNoSuchElementException(exception);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(404, response.getBody().get("statusCode"));
    assertEquals("Resource not found", response.getBody().get("message"));
  }

  @Test
  void shouldHandleMangaAlreadyExistsException() {
    var exception = new MangaAlreadyExistsException("Manga already exists");

    var response = customExceptionHandler.handleMangaAlreadyExistsException(exception);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals(409, response.getBody().get("statusCode"));
    assertEquals("Manga already exists", response.getBody().get("message"));
  }

  @Test
  void shouldHandleUnexpectedException() {
    var exception = new RuntimeException("Unexpected error");

    var response = customExceptionHandler.handleRuntimeException(exception);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(500, response.getBody().get("statusCode"));
    assertEquals("An unexpected error occurred: Unexpected error", response.getBody().get("message"));
  }
}
