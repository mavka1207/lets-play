package com.example.letsplay.common;

import java.time.Instant;

/** Uniform API error payload used across handlers and security entry points. */
public class ApiError {
  private final int status;
  private final String error;
  private final String message;
  private final String path;
  private final Instant timestamp = Instant.now();

  public ApiError(int status, String error, String message, String path) {
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }

  public int getStatus() { return status; }
  public String getError() { return error; }
  public String getMessage() { return message; }
  public String getPath() { return path; }
  public Instant getTimestamp() { return timestamp; }
}
