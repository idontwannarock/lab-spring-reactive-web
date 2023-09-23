package com.example.lab.spring.reactive.web.spring.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandlers extends ResponseEntityExceptionHandler {
}
