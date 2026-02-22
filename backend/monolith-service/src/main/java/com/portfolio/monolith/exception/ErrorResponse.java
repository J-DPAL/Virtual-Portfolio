package com.portfolio.monolith.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
  public int status;
  public String message;
  public LocalDateTime timestamp;
  public Map<String, String> errors;

  public ErrorResponse(int status, String message, LocalDateTime timestamp, Map<String, String> errors) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
    this.errors = errors;
  }
}
