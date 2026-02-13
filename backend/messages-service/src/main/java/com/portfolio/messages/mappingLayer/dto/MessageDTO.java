package com.portfolio.messages.mappingLayer.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
  private String senderName;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Sender email is required")
  private String senderEmail;

  private String subject;

  @NotBlank(message = "Message is required")
  private String message;

  private Boolean isRead;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  /**
   * Mail notification status - only populated in responses, not in requests. Provides frontend with
   * visibility into email delivery status.
   */
  private MailNotificationStatus mailNotificationStatus;
}
