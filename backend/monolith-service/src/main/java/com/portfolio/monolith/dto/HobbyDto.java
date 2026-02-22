package com.portfolio.monolith.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class HobbyDto {
  public Long id;

  @NotBlank(message = "Name in English is required")
  public String nameEn;

  @NotBlank(message = "Name in French is required")
  public String nameFr;

  @NotBlank(message = "Name in Spanish is required")
  public String nameEs;

  public String descriptionEn;
  public String descriptionFr;
  public String descriptionEs;
  public String icon;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;
}
