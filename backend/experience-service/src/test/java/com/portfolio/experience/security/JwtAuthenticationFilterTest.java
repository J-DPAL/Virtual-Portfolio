package com.portfolio.experience.security;

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
    String token = "valid.jwt.token";
    String authHeader = "Bearer " + token;
    String email = "test@example.com";
    String role = "USER";

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

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
    String token = "invalid.jwt.token";
    String authHeader = "Bearer " + token;

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(false);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNull(authentication);
    verify(filterChain).doFilter(request, response);
  }

  @Test
  @DisplayName("Should ignore when Authorization header is missing")
  void testDoFilterInternal_MissingHeader() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn(null);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNull(authentication);
    verify(filterChain).doFilter(request, response);
  }

  @Test
  @DisplayName("Should ignore when Authorization header is not Bearer")
  void testDoFilterInternal_NonBearerHeader() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn("Basic abc123");

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNull(authentication);
    verify(filterChain).doFilter(request, response);
  }

  @Test
  @DisplayName("Should handle empty Bearer token")
  void testDoFilterInternal_EmptyBearerToken() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn("Bearer ");
    when(jwtTokenProvider.validateToken("")).thenReturn(false);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNull(authentication);
    verify(filterChain).doFilter(request, response);
  }

  @Test
  @DisplayName("Should set ROLE_ prefix for ADMIN role")
  void testDoFilterInternal_AdminRole() throws ServletException, IOException {
    String token = "admin.jwt.token";
    String authHeader = "Bearer " + token;
    String email = "admin@example.com";
    String role = "ADMIN";

    when(request.getHeader("Authorization")).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNotNull(authentication);
    assertTrue(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    verify(filterChain).doFilter(request, response);
  }
}
