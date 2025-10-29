package com.example.letsplay.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class SecurityExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(new ApiError(403, "Forbidden", "Access denied", req.getRequestURI()));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleAuth(AuthenticationException ex, HttpServletRequest req) {
    String msg = (ex instanceof BadCredentialsException) ? "Invalid credentials" : "Unauthorized";
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ApiError(401, "Unauthorized", msg, req.getRequestURI()));
  }
}
