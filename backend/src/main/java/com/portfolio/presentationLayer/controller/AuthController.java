package com.portfolio.presentationLayer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.businessLogicLayer.service.AuthService;
import com.portfolio.mappingLayer.dto.LoginRequest;
import com.portfolio.mappingLayer.dto.LoginResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    LoginResponse response = authService.login(loginRequest);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return new ResponseEntity<>("Auth service is running", HttpStatus.OK);
  }
}
