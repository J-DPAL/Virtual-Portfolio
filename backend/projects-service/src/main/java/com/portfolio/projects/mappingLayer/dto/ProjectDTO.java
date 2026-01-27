package com.portfolio.projects.mappingLayer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long id;

    @NotBlank(message = "English title is required")
    private String titleEn;

    @NotBlank(message = "Arabic title is required")
    private String titleAr;

    private String descriptionEn;
    private String descriptionAr;
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
