package com.portfolio.skills.security;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

  @Mock private JwtTokenProvider jwtTokenProvider;

  @Mock private HttpServletRequest request;

  @Mock private HttpServletResponse response;

  @Mock private FilterChain filterChain;

  @Mock private SecurityContext securityContext;

  @InjectMocks private JwtAuthenticationFilter jwtAuthenticationFilter;

  @BeforeEach
  void setUp() {
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void testDoFilterInternalWithValidToken() throws ServletException, IOException {
    String token = "valid.jwt.token";
    String authHeader = "Bearer " + token;
    String email = "test@example.com";
    String role = "ROLE_USER";

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(securityContext).setAuthentication(any(Authentication.class));
  }

  @Test
  void testDoFilterInternalWithInvalidToken() throws ServletException, IOException {
    String token = "invalid.jwt.token";
    String authHeader = "Bearer " + token;

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(false);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(securityContext, never()).setAuthentication(any());
  }

  @Test
  void testDoFilterInternalWithMissingAuthorizationHeader() throws ServletException, IOException {
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(securityContext, never()).setAuthentication(any());
    verify(jwtTokenProvider, never()).validateToken(anyString());
  }

  @Test
  void testDoFilterInternalWithNonBearerHeader() throws ServletException, IOException {
    String authHeader = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(securityContext, never()).setAuthentication(any());
    verify(jwtTokenProvider, never()).validateToken(anyString());
  }

  @Test
  void testDoFilterInternalWithEmptyBearerToken() throws ServletException, IOException {
    String authHeader = "Bearer ";

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void testDoFilterInternalWithAdminRole() throws ServletException, IOException {
    String token = "valid.jwt.token";
    String authHeader = "Bearer " + token;
    String email = "admin@example.com";
    String role = "ROLE_ADMIN";

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
    when(jwtTokenProvider.validateToken(token)).thenReturn(true);
    when(jwtTokenProvider.getEmailFromToken(token)).thenReturn(email);
    when(jwtTokenProvider.getRoleFromToken(token)).thenReturn(role);

    jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(securityContext).setAuthentication(any(Authentication.class));
  }
}
