package com.portfolio.messages.utils.exceptions;

/** Exception thrown when mail notification fails after retries and circuit breaker checks. */
public class MailNotificationException extends RuntimeException {

  private final String
      failureType; // "RETRY_EXHAUSTED", "CIRCUIT_BREAKER_OPEN", "TRANSIENT_FAILURE"
  private final String errorCode; // HTTP-friendly error code
  private final int attemptCount;
  private final long delayMs;

  public MailNotificationException(
      String message, String errorCode, int attemptCount, long delayMs) {
    super(message);
    this.errorCode = errorCode;
    this.failureType = errorCode;
    this.attemptCount = attemptCount;
    this.delayMs = delayMs;
  }

  public MailNotificationException(String message, String errorCode, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
    this.failureType = errorCode;
    this.attemptCount = 0;
    this.delayMs = 0;
  }

  public String getFailureType() {
    return failureType;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public int getAttemptCount() {
    return attemptCount;
  }

  public long getDelayMs() {
    return delayMs;
  }
}
