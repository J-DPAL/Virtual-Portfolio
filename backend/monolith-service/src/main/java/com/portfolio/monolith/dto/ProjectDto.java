package com.portfolio.monolith.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ProjectDto {
  public Long id;

  @NotBlank(message = "English title is required")
  public String titleEn;

  @NotBlank(message = "French title is required")
  public String titleFr;

  @NotBlank(message = "Spanish title is required")
  public String titleEs;

  public String descriptionEn;
  public String descriptionFr;
  public String descriptionEs;
  public String technologies;

  @Pattern(regexp = "^(https?://.*)?$", message = "Invalid project URL format")
  public String projectUrl;

  @Pattern(regexp = "^(https?://.*)?$", message = "Invalid GitHub URL format")
  public String githubUrl;

  @Pattern(regexp = "^(https?://.*)?$", message = "Invalid image URL format")
  public String imageUrl;

  public LocalDate startDate;
  public LocalDate endDate;
  public String status;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;
}
