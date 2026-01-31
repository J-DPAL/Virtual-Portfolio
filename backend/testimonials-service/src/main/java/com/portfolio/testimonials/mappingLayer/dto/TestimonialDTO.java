package com.portfolio.testimonials.mappingLayer.dto;

import java.time.LocalDateTime;

import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial.TestimonialStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestimonialDTO {
  private Long id;

  @NotBlank(message = "Client name is required")
  private String clientName;

  @NotBlank(message = "Client position is required")
  private String clientPosition;

  @NotBlank(message = "Client company is required")
  private String clientCompany;

  @NotBlank(message = "Testimonial text in English is required")
  private String testimonialTextEn;

  @NotBlank(message = "Testimonial text in French is required")
  private String testimonialTextFr;

  @NotBlank(message = "Testimonial text in Spanish is required")
  private String testimonialTextEs;

  @NotNull(message = "Rating is required")
  @Min(value = 1, message = "Rating must be at least 1")
  @Max(value = 5, message = "Rating must be at most 5")
  private Integer rating;

  private String clientImageUrl;
  private TestimonialStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
