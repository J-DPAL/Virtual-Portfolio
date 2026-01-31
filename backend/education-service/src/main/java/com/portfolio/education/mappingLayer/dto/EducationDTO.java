package com.portfolio.education.mappingLayer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationDTO {
  private Long id;

  @NotBlank(message = "Institution name in English is required")
  private String institutionNameEn;

  @NotBlank(message = "Institution name in French is required")
  private String institutionNameFr;

  @NotBlank(message = "Institution name in Spanish is required")
  private String institutionNameEs;

  @NotBlank(message = "Degree in English is required")
  private String degreeEn;

  @NotBlank(message = "Degree in French is required")
  private String degreeFr;

  @NotBlank(message = "Degree in Spanish is required")
  private String degreeEs;

  @NotBlank(message = "Field of study in English is required")
  private String fieldOfStudyEn;

  @NotBlank(message = "Field of study in French is required")
  private String fieldOfStudyFr;

  @NotBlank(message = "Field of study in Spanish is required")
  private String fieldOfStudyEs;

  private String descriptionEn;
  private String descriptionFr;
  private String descriptionEs;

  @NotNull(message = "Start date is required")
  private LocalDate startDate;

  private LocalDate endDate;

  @NotNull(message = "Current status is required")
  private Boolean isCurrent;

  @DecimalMin(value = "0.00", message = "GPA must be at least 0.00")
  @DecimalMax(value = "4.00", message = "GPA must not exceed 4.00")
  @Digits(
      integer = 1,
      fraction = 2,
      message = "GPA must have at most 1 integer digit and 2 decimal places")
  private BigDecimal gpa;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
