package com.portfolio.hobbies.mappingLayer.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HobbyDTO {
  private Long id;

  @NotBlank(message = "Name in English is required")
  private String nameEn;

  @NotBlank(message = "Name in French is required")
  private String nameFr;

  @NotBlank(message = "Name in Spanish is required")
  private String nameEs;

  private String descriptionEn;
  private String descriptionFr;
  private String descriptionEs;
  private String icon;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
