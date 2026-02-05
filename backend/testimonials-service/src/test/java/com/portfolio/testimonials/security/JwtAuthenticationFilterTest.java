package com.portfolio.testimonials.security;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
  void testDoFilterInternal_ValidToken() throws ServletException, IOException {
    String token = "valid.jwt.token";
    String email = "user@example.com";
    String role = "ADMIN";

    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    assertEquals(email, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    assertTrue(
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
    String token = "invalid.jwt.token";

    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtTokenProvider.validateToken(token)).thenReturn(false);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_MissingHeader() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn(null);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain).doFilter(request, response);
    verify(jwtTokenProvider, never()).validateToken(anyString());
  }

  @Test
  void testDoFilterInternal_NonBearerHeader() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn("Basic credentials");

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain).doFilter(request, response);
    verify(jwtTokenProvider, never()).validateToken(anyString());
  }

  @Test
  void testDoFilterInternal_EmptyBearerToken() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn("Bearer ");

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(jwtTokenProvider).validateToken("");
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternal_UserRole() throws ServletException, IOException {
    String token = "valid.jwt.token";
    String email = "user@example.com";
    String role = "USER";

    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    assertTrue(
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    verify(filterChain).doFilter(request, response);
  }
}
