package com.portfolio.users.businessLayer.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.portfolio.users.dataAccessLayer.entity.User;
import com.portfolio.users.dataAccessLayer.repository.UserRepository;
import com.portfolio.users.mappingLayer.dto.LoginRequest;
import com.portfolio.users.mappingLayer.dto.LoginResponse;
import com.portfolio.users.mappingLayer.dto.UserDTO;
import com.portfolio.users.mappingLayer.mapper.UserMapper;
import com.portfolio.users.security.JwtTokenProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Unit Tests")
class AuthServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private UserMapper userMapper;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private JwtTokenProvider jwtTokenProvider;

  @InjectMocks private AuthService authService;

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
  @DisplayName("Should successfully login with valid credentials")
  void testLogin_WithValidCredentials_ReturnsLoginResponse() {
    // Arrange: Setup mocks for successful login
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword()))
        .thenReturn(true);
    when(jwtTokenProvider.generateToken("test@example.com", "USER")).thenReturn("jwt_token_123");
    when(userMapper.toDTO(testUser)).thenReturn(userDTO);

    // Act: Call the login method
    LoginResponse response = authService.login(loginRequest);

    // Assert: Verify the response
    assertNotNull(response);
    assertEquals("jwt_token_123", response.getToken());
    assertEquals(userDTO, response.getUser());
    assertEquals("Login successful", response.getMessage());
    verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
    verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), testUser.getPassword());
    verify(jwtTokenProvider, times(1)).generateToken("test@example.com", "USER");
  }

  @Test
  @DisplayName("Should throw exception when user is not found")
  void testLogin_WithNonExistentEmail_ThrowsRuntimeException() {
    // Arrange: Setup mock to return empty Optional
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

    // Act & Assert: Verify exception is thrown
    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    assertEquals("User not found", exception.getMessage());
    verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
    verify(passwordEncoder, never()).matches(anyString(), anyString());
    verify(jwtTokenProvider, never()).generateToken(anyString(), anyString());
  }

  @Test
  @DisplayName("Should throw exception when password is invalid")
  void testLogin_WithInvalidPassword_ThrowsRuntimeException() {
    // Arrange: Setup mocks for invalid password
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword()))
        .thenReturn(false);

    // Act & Assert: Verify exception is thrown
    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    assertEquals("Invalid password", exception.getMessage());
    verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
    verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), testUser.getPassword());
    verify(jwtTokenProvider, never()).generateToken(anyString(), anyString());
  }

  @Test
  @DisplayName("Should throw exception when user account is inactive")
  void testLogin_WithInactiveUser_ThrowsRuntimeException() {
    // Arrange: Setup mocks for inactive user
    testUser.setActive(false);
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword()))
        .thenReturn(true);

    // Act & Assert: Verify exception is thrown
    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    assertEquals("User account is inactive", exception.getMessage());
    verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
    verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), testUser.getPassword());
    verify(jwtTokenProvider, never()).generateToken(anyString(), anyString());
  }

  @Test
  @DisplayName("Should generate token with correct email and role")
  void testLogin_VerifiesTokenGenerationWithCorrectParameters() {
    // Arrange: Setup mocks
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword()))
        .thenReturn(true);
    when(jwtTokenProvider.generateToken("test@example.com", "USER")).thenReturn("jwt_token");
    when(userMapper.toDTO(testUser)).thenReturn(userDTO);

    // Act: Call login
    authService.login(loginRequest);

    // Assert: Verify token generation with correct parameters
    verify(jwtTokenProvider, times(1)).generateToken("test@example.com", "USER");
  }

  @Test
  @DisplayName("Should handle admin role correctly in token generation")
  void testLogin_WithAdminRole_GeneratesTokenWithAdminRole() {
    // Arrange: Setup admin user
    User adminUser =
        User.builder()
            .email("test@example.com")
            .password("hashedPassword")
            .fullName("Test User")
            .role(User.UserRole.ADMIN)
            .active(true)
            .build();
    adminUser.setId(1L);
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(adminUser));
    when(passwordEncoder.matches(loginRequest.getPassword(), adminUser.getPassword()))
        .thenReturn(true);
    when(jwtTokenProvider.generateToken("test@example.com", "ADMIN")).thenReturn("admin_token");
    when(userMapper.toDTO(adminUser)).thenReturn(userDTO);

    // Act: Call login
    LoginResponse response = authService.login(loginRequest);

    // Assert: Verify token includes ADMIN role
    verify(jwtTokenProvider, times(1)).generateToken("test@example.com", "ADMIN");
    assertNotNull(response.getToken());
  }
}
