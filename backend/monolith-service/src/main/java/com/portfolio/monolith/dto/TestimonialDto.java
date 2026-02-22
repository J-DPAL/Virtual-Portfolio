package com.portfolio.monolith.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TestimonialDto {
  public Long id;

  @NotBlank(message = "Client name is required")
  public String clientName;

  @NotBlank(message = "Client position is required")
  public String clientPosition;

  @NotBlank(message = "Client company is required")
  public String clientCompany;

  @NotBlank(message = "Testimonial text in English is required")
  public String testimonialTextEn;

  @NotBlank(message = "Testimonial text in French is required")
  public String testimonialTextFr;

  @NotBlank(message = "Testimonial text in Spanish is required")
  public String testimonialTextEs;

  @NotNull(message = "Rating is required")
  @Min(value = 1, message = "Rating must be at least 1")
  @Max(value = 5, message = "Rating must be at most 5")
  public Integer rating;

  public String clientImageUrl;
  public TestimonialStatus status;
  public Boolean approved;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;
}
