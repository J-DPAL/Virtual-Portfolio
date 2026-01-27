package com.portfolio.users.mappingLayer.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  private Long id;
  private String email;
  private String fullName;
  private String role;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
