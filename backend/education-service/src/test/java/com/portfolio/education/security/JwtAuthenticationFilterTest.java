package com.portfolio.education.security;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtAuthenticationFilter Tests")
class JwtAuthenticationFilterTest {

  @Mock private JwtTokenProvider jwtTokenProvider;

  @Mock private HttpServletRequest request;

  @Mock private HttpServletResponse response;

  @Mock private FilterChain filterChain;

  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @BeforeEach
  void setUp() {
    jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);
    SecurityContextHolder.clearContext();
  }

  @Test
  @DisplayName("Should authenticate with valid Bearer token")
  void testDoFilterInternal_ValidToken() throws ServletException, IOException {
    // Arrange
    String token = "valid.jwt.token";
    String authHeader = "Bearer " + token;
    String email = "test@example.com";
    String role = "USER";

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNotNull(authentication);
    assertEquals(email, authentication.getPrincipal());
    assertTrue(
        authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role)));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  @DisplayName("Should not authenticate with invalid token")
  void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
    // Arrange
    String token = "invalid.jwt.token";
    String authHeader = "Bearer " + token;

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(false);

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNull(authentication);
    verify(filterChain).doFilter(request, response);
    verify(jwtTokenProvider, never()).getEmailFromToken(anyString());
    verify(jwtTokenProvider, never()).getRoleFromToken(anyString());
  }

  @Test
  @DisplayName("Should not authenticate when Authorization header is missing")
  void testDoFilterInternal_NoAuthHeader() throws ServletException, IOException {
    // Arrange
    when(request.getHeader("Authorization")).thenReturn(null);

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNull(authentication);
    verify(filterChain).doFilter(request, response);
    verify(jwtTokenProvider, never()).validateToken(anyString());
  }

  @Test
  @DisplayName("Should not authenticate when Authorization header doesn't start with Bearer")
  void testDoFilterInternal_NotBearerToken() throws ServletException, IOException {
    // Arrange
    when(request.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNz");

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNull(authentication);
    verify(filterChain).doFilter(request, response);
    verify(jwtTokenProvider, never()).validateToken(anyString());
  }

  @Test
  @DisplayName("Should handle ADMIN role correctly")
  void testDoFilterInternal_AdminRole() throws ServletException, IOException {
    // Arrange
    String token = "valid.jwt.token";
    String authHeader = "Bearer " + token;
    String email = "admin@example.com";
    String role = "ADMIN";

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNotNull(authentication);
    assertEquals(email, authentication.getPrincipal());
    assertTrue(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  @DisplayName("Should continue filter chain even with invalid token")
  void testDoFilterInternal_ContinuesFilterChain() throws ServletException, IOException {
    // Arrange
    String token = "invalid.token";
    String authHeader = "Bearer " + token;

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(false);

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  @DisplayName("Should set authentication details from request")
  void testDoFilterInternal_SetsAuthenticationDetails() throws ServletException, IOException {
    // Arrange
    String token = "valid.jwt.token";
    String authHeader = "Bearer " + token;
    String email = "test@example.com";
    String role = "USER";

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNotNull(authentication);
    assertNotNull(authentication.getDetails());
  }

  @Test
  @DisplayName("Should handle empty Bearer token")
  void testDoFilterInternal_EmptyBearerToken() throws ServletException, IOException {
    // Arrange
    when(request.getHeader("Authorization")).thenReturn("Bearer ");

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNull(authentication);
    verify(filterChain).doFilter(request, response);
  }

  @Test
  @DisplayName("Should handle Bearer with spaces")
  void testDoFilterInternal_BearerWithSpaces() throws ServletException, IOException {
    // Arrange
    String token = "valid.jwt.token";
    String authHeader = "Bearer " + token;

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn("test@example.com");
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn("USER");

    // Act
    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    // Assert
    verify(jwtTokenProvider).validateToken(token);
    verify(filterChain).doFilter(request, response);
  }
}
