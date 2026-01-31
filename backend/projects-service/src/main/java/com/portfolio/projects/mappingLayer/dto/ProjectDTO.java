package com.portfolio.projects.mappingLayer.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
  private Long id;

  @NotBlank(message = "English title is required")
  private String titleEn;

  @NotBlank(message = "French title is required")
  private String titleFr;

  @NotBlank(message = "Spanish title is required")
  private String titleEs;

  private String descriptionEn;
  private String descriptionFr;
  private String descriptionEs;
  private String technologies;

  @Pattern(regexp = "^(https?://.*)?$", message = "Invalid project URL format")
  private String projectUrl;

  @Pattern(regexp = "^(https?://.*)?$", message = "Invalid GitHub URL format")
  private String githubUrl;

  @Pattern(regexp = "^(https?://.*)?$", message = "Invalid image URL format")
  private String imageUrl;

  private LocalDate startDate;
  private LocalDate endDate;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
