package com.portfolio.messages.mappingLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO to track mail notification status along with message data. Provides frontend with clear
 * visibility into email delivery status.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailNotificationStatus {

  /** Whether email notification was sent successfully */
  private Boolean sent;

  /** Status message: "SUCCESS", "CIRCUIT_BREAKER_OPEN", "RETRY_EXHAUSTED", "MAIL_DISABLED" */
  private String status;

  /** Error message if notification failed (visible to frontend for debugging) */
  private String errorMessage;

  /** Number of retry attempts made */
  private Integer retryCount;

  /** Last error cause if applicable */
  private String errorCause;

  public static MailNotificationStatus success() {
    return MailNotificationStatus.builder().sent(true).status("SUCCESS").retryCount(0).build();
  }

  public static MailNotificationStatus disabled() {
    return MailNotificationStatus.builder()
        .sent(false)
        .status("MAIL_DISABLED")
        .errorMessage("Mail service is not configured")
        .retryCount(0)
        .build();
  }

  public static MailNotificationStatus circuitBreakerOpen(String errorMessage) {
    return MailNotificationStatus.builder()
        .sent(false)
        .status("CIRCUIT_BREAKER_OPEN")
        .errorMessage(
            "Mail service temporarily unavailable - " + errorMessage + ". Will retry later.")
        .build();
  }

  public static MailNotificationStatus retryExhausted(String errorMessage, int retryCount) {
    return MailNotificationStatus.builder()
        .sent(false)
        .status("RETRY_EXHAUSTED")
        .errorMessage(
            "Failed to send notification after " + retryCount + " attempts: " + errorMessage)
        .retryCount(retryCount)
        .errorCause(errorMessage)
        .build();
  }

  public static MailNotificationStatus transientFailure(String errorMessage, int retryCount) {
    return MailNotificationStatus.builder()
        .sent(false)
        .status("RETRY_IN_PROGRESS")
        .errorMessage(
            "Notification delivery in progress (attempt " + retryCount + "): " + errorMessage)
        .retryCount(retryCount)
        .build();
  }

  /** Checks if the notification was sent successfully */
  public boolean isSuccess() {
    return sent != null && sent;
  }
}
