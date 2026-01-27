package com.portfolio.hobbies.mappingLayer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HobbyDTO {
    private Long id;

    @NotBlank(message = "Name in English is required")
    private String nameEn;

    @NotBlank(message = "Name in Arabic is required")
    private String nameAr;

    private String descriptionEn;
    private String descriptionAr;
    private String icon;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
