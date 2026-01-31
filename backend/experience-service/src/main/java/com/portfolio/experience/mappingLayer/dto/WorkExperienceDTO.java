package com.portfolio.experience.mappingLayer.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class WorkExperienceDTO {
  private Long id;

  @NotBlank(message = "Company name in English is required")
  private String companyNameEn;

  @NotBlank(message = "Company name in French is required")
  private String companyNameFr;

  @NotBlank(message = "Position in English is required")
  private String positionEn;

  @NotBlank(message = "Position in French is required")
  private String positionFr;

  private String descriptionEn;
  private String descriptionFr;

  private String locationEn;
  private String locationFr;

  @NotNull(message = "Start date is required")
  private LocalDate startDate;

  private LocalDate endDate;

  @NotNull(message = "Current status is required")
  private Boolean isCurrent;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
