package com.portfolio.monolith.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

  @Email(message = "Email should be valid")
  @NotBlank(message = "Email is required")
  public String email;

  @NotBlank(message = "Password is required")
  @Size(min = 6, message = "Password should have at least 6 characters")
  public String password;
}
