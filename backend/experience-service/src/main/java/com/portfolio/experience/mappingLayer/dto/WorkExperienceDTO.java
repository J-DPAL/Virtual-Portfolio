package com.portfolio.experience.mappingLayer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class WorkExperienceDTO {
    private Long id;

    @NotBlank(message = "Company name in English is required")
    private String companyNameEn;

    @NotBlank(message = "Company name in Arabic is required")
    private String companyNameAr;

    @NotBlank(message = "Position in English is required")
    private String positionEn;

    @NotBlank(message = "Position in Arabic is required")
    private String positionAr;

    private String descriptionEn;
    private String descriptionAr;

    private String locationEn;
    private String locationAr;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Current status is required")
    private Boolean isCurrent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
