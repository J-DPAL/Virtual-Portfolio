package com.portfolio.monolith.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EducationDto {
  public Long id;

  @NotBlank(message = "Institution name in English is required")
  public String institutionNameEn;

  @NotBlank(message = "Institution name in French is required")
  public String institutionNameFr;

  @NotBlank(message = "Institution name in Spanish is required")
  public String institutionNameEs;

  @NotBlank(message = "Degree in English is required")
  public String degreeEn;

  @NotBlank(message = "Degree in French is required")
  public String degreeFr;

  @NotBlank(message = "Degree in Spanish is required")
  public String degreeEs;

  @NotBlank(message = "Field of study in English is required")
  public String fieldOfStudyEn;

  @NotBlank(message = "Field of study in French is required")
  public String fieldOfStudyFr;

  @NotBlank(message = "Field of study in Spanish is required")
  public String fieldOfStudyEs;

  public String descriptionEn;
  public String descriptionFr;
  public String descriptionEs;

  @NotNull(message = "Start date is required")
  public LocalDate startDate;

  public LocalDate endDate;

  @NotNull(message = "Current status is required")
  public Boolean isCurrent;

  @DecimalMin(value = "0.00", message = "GPA must be at least 0.00")
  @DecimalMax(value = "4.00", message = "GPA must not exceed 4.00")
  @Digits(
      integer = 1,
      fraction = 2,
      message = "GPA must have at most 1 integer digit and 2 decimal places")
  public BigDecimal gpa;

  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;
}
