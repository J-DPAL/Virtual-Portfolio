package com.portfolio.monolith.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageDto {
  public Long id;

  @NotBlank(message = "Sender name is required")
  @Size(min = 2, max = 100, message = "Sender name must be between 2 and 100 characters")
  public String senderName;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Sender email is required")
  public String senderEmail;

  @NotBlank(message = "Subject is required")
  @Size(min = 3, max = 200, message = "Subject must be between 3 and 200 characters")
  public String subject;

  @NotBlank(message = "Message is required")
  @Size(min = 10, max = 2000, message = "Message must be between 10 and 2000 characters")
  public String message;

  public String captchaToken;
  public String website;
  public Boolean isRead;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;

  // Frontend aliases used in admin screens.
  public String name;
  public String email;
  public String content;
  public String date;
}
