package com.example.letsplay.common;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {


@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<ApiError> handleIllegalArg(IllegalArgumentException ex, HttpServletRequest req) {
return ResponseEntity.status(HttpStatus.BAD_REQUEST)
.body(new ApiError(400, "Bad Request", ex.getMessage(), req.getRequestURI()));
}


@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
String msg = ex.getBindingResult().getAllErrors().stream().findFirst().map(e -> e.getDefaultMessage()).orElse("Validation error");
return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
.body(new ApiError(422, "Validation Error", msg, req.getRequestURI()));
}


@ExceptionHandler(ConstraintViolationException.class)
public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
.body(new ApiError(422, "Validation Error", ex.getMessage(), req.getRequestURI()));
}


@ExceptionHandler(Exception.class)
public ResponseEntity<ApiError> handleAny(Exception ex, HttpServletRequest req) {
// Convert unexpected errors to 400/401/403 when possible in controllers/services; fallback 500 guarded here
return ResponseEntity.status(HttpStatus.BAD_REQUEST)
.body(new ApiError(400, "Bad Request", ex.getMessage(), req.getRequestURI()));
}
}
