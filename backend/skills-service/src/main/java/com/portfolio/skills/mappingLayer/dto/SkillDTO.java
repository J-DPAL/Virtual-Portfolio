package com.portfolio.skills.mappingLayer.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDTO {
  private Long id;

  @NotBlank(message = "English name is required")
  private String nameEn;

  @NotBlank(message = "French name is required")
  private String nameFr;

  private String descriptionEn;
  private String descriptionFr;

  @NotBlank(message = "Proficiency level is required")
  private String proficiencyLevel;

  @NotBlank(message = "Category is required")
  private String category;

  @Min(value = 0, message = "Years of experience must be positive")
  private Integer yearsOfExperience;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
