package com.portfolio.monolith.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class SkillDto {
  public Long id;

  @NotBlank(message = "English name is required")
  public String nameEn;

  @NotBlank(message = "French name is required")
  public String nameFr;

  @NotBlank(message = "Spanish name is required")
  public String nameEs;

  public String descriptionEn;
  public String descriptionFr;
  public String descriptionEs;

  @NotBlank(message = "Proficiency level is required")
  public String proficiencyLevel;

  @NotBlank(message = "Category is required")
  public String category;

  @Min(value = 0, message = "Years of experience must be positive")
  public Integer yearsOfExperience;

  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;
}
