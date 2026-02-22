package com.portfolio.monolith.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkExperienceDto {
  public Long id;

  @NotBlank(message = "Company name in English is required")
  public String companyNameEn;

  @NotBlank(message = "Company name in French is required")
  public String companyNameFr;

  @NotBlank(message = "Company name in Spanish is required")
  public String companyNameEs;

  @NotBlank(message = "Position in English is required")
  public String positionEn;

  @NotBlank(message = "Position in French is required")
  public String positionFr;

  @NotBlank(message = "Position in Spanish is required")
  public String positionEs;

  public String descriptionEn;
  public String descriptionFr;
  public String descriptionEs;

  public String locationEn;
  public String locationFr;
  public String locationEs;

  @NotNull(message = "Start date is required")
  public LocalDate startDate;

  public LocalDate endDate;

  @NotNull(message = "Current status is required")
  public Boolean isCurrent;

  public String skillsUsed;
  public String icon;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;
}
