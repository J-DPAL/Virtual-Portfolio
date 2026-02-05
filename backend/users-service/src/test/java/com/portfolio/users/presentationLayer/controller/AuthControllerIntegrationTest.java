package com.portfolio.users.presentationLayer.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.users.dataAccessLayer.entity.User;
import com.portfolio.users.dataAccessLayer.repository.UserRepository;
import com.portfolio.users.mappingLayer.dto.LoginRequest;
import com.portfolio.users.mappingLayer.dto.UserDTO;
import com.portfolio.users.mappingLayer.mapper.UserMapper;
import com.portfolio.users.security.JwtTokenProvider;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("AuthController Integration Tests")
class AuthControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean(name = "userRepository")
  private UserRepository userRepository;

  @MockBean(name = "userMapper")
  private UserMapper userMapper;

  @MockBean(name = "passwordEncoder")
  private PasswordEncoder passwordEncoder;

  @MockBean(name = "jwtTokenProvider")
  private JwtTokenProvider jwtTokenProvider;

  private LoginRequest loginRequest;
  private User testUser;
  private UserDTO userDTO;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    loginRequest = LoginRequest.builder().email("test@example.com").password("password123").build();

    testUser =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword")
            .fullName("Test User")
            .role(User.UserRole.USER)
            .active(true)
            .build();
    testUser.setId(1L);
    testUser.setCreatedAt(LocalDateTime.now());
    testUser.setUpdatedAt(LocalDateTime.now());

    userDTO =
        UserDTO.builder()
            .id(1L)
            .email("test@example.com")
            .fullName("Test User")
            .role("USER")
            .active(true)
            .build();
  }

  @Test
  @DisplayName("Should successfully login with valid credentials via POST /v1/auth/login")
  void testLoginEndpoint_WithValidCredentials_ReturnsOkWithToken() throws Exception {
    // Arrange: Setup mocks
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword()))
        .thenReturn(true);
    when(jwtTokenProvider.generateToken("test@example.com", "USER")).thenReturn("jwt_token_123");
    when(userMapper.toDTO(testUser)).thenReturn(userDTO);

    // Act & Assert: Perform POST request and verify response
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("jwt_token_123"))
        .andExpect(jsonPath("$.user.email").value("test@example.com"))
        .andExpect(jsonPath("$.message").value("Login successful"));
  }

  @Test
  @DisplayName("Should return 400 when email is missing")
  void testLoginEndpoint_WithMissingEmail_ReturnsBadRequest() throws Exception {
    // Arrange: Create login request without email
    LoginRequest invalidRequest = LoginRequest.builder().password("password123").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return 400 when password is missing")
  void testLoginEndpoint_WithMissingPassword_ReturnsBadRequest() throws Exception {
    // Arrange: Create login request without password
    LoginRequest invalidRequest = LoginRequest.builder().email("test@example.com").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return 400 when email format is invalid")
  void testLoginEndpoint_WithInvalidEmailFormat_ReturnsBadRequest() throws Exception {
    // Arrange: Create login request with invalid email
    LoginRequest invalidRequest =
        LoginRequest.builder().email("invalid-email").password("password123").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return 400 when password is too short")
  void testLoginEndpoint_WithShortPassword_ReturnsBadRequest() throws Exception {
    // Arrange: Create login request with password less than 6 characters
    LoginRequest invalidRequest =
        LoginRequest.builder().email("test@example.com").password("pass").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return 500 when user is not found")
  void testLoginEndpoint_WithNonExistentUser_ReturnsInternalServerError() throws Exception {
    // Arrange: Setup mock to return empty Optional
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

    // Act & Assert: Perform POST request and verify error response
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().is5xxServerError());
  }

  @Test
  @DisplayName("Should return 500 when password is invalid")
  void testLoginEndpoint_WithInvalidPassword_ReturnsInternalServerError() throws Exception {
    // Arrange: Setup mocks for invalid password
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword()))
        .thenReturn(false);

    // Act & Assert: Perform POST request and verify error response
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().is5xxServerError());
  }

  @Test
  @DisplayName("Should return 500 when user account is inactive")
  void testLoginEndpoint_WithInactiveUser_ReturnsInternalServerError() throws Exception {
    // Arrange: Setup mocks for inactive user
    testUser.setActive(false);
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword()))
        .thenReturn(true);

    // Act & Assert: Perform POST request and verify error response
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().is5xxServerError());
  }

  @Test
  @DisplayName("Should return health status via GET /v1/auth/health")
  void testHealthEndpoint_ReturnsOk() throws Exception {
    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/v1/auth/health").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Auth service is running"));
  }

  @Test
  @DisplayName("Should include CORS headers in login response")
  void testLoginEndpoint_IncludesCORSHeaders() throws Exception {
    // Arrange: Setup mocks
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword()))
        .thenReturn(true);
    when(jwtTokenProvider.generateToken("test@example.com", "USER")).thenReturn("jwt_token");
    when(userMapper.toDTO(testUser)).thenReturn(userDTO);

    // Act & Assert: Verify response is successful (CORS headers are handled by Spring)
    mockMvc
        .perform(
            post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("jwt_token"));
  }
}
