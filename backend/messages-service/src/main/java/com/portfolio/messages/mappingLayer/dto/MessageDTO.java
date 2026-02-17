package com.portfolio.messages.mappingLayer.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
  private Long id;

  @NotBlank(message = "Sender name is required")
  @Size(min = 2, max = 100, message = "Sender name must be between 2 and 100 characters")
  private String senderName;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Sender email is required")
  private String senderEmail;

  @NotBlank(message = "Subject is required")
  @Size(min = 3, max = 200, message = "Subject must be between 3 and 200 characters")
  private String subject;

  @NotBlank(message = "Message is required")
  @Size(min = 10, max = 2000, message = "Message must be between 10 and 2000 characters")
  private String message;

  /** Cloudflare Turnstile token provided by the frontend widget. */
  private String captchaToken;

  /** Honeypot field for bot detection. Legitimate users should leave this empty. */
  private String website;

  private Boolean isRead;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  /**
   * Mail notification status - only populated in responses, not in requests. Provides frontend with
   * visibility into email delivery status.
   */
  private MailNotificationStatus mailNotificationStatus;
}
