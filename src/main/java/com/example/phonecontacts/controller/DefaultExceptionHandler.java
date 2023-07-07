package com.example.phonecontacts.controller;

import com.example.phonecontacts.dto.ExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ExceptionDto> notSupported(Exception exception) {
    return ResponseEntity.status(405).body(build(exception.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<ExceptionDto> illegalArgument(Exception exception) {
    return ResponseEntity.status(400).body(build(exception.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ExceptionDto> defaultHandle(Exception exception) {
    return ResponseEntity.status(500).body(build(exception.getMessage()));
  }

  private ExceptionDto build(String message) {
    return new ExceptionDto(message);
  }
}
