package com.portfolio.config;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.portfolio.filter.RateLimitFilter;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SecurityConfig Unit Tests")
class SecurityConfigTest {

  @Test
  @DisplayName("Should configure CORS with allowed origins and methods")
  void testCorsConfigurationSource() {
    // Arrange: Create security config
    SecurityConfig config = new SecurityConfig(new RateLimitFilter());
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");

    // Act: Build CORS configuration
    CorsConfigurationSource source = config.corsConfigurationSource();
    CorsConfiguration cors = source.getCorsConfiguration(request);

    // Assert: Verify configuration
    assertNotNull(cors);
    assertTrue(cors.getAllowedOrigins().contains("http://localhost:3000"));
    assertTrue(cors.getAllowedOrigins().contains("http://localhost:5173"));
    List<String> methods = cors.getAllowedMethods();
    assertTrue(methods.containsAll(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")));
    assertTrue(cors.getAllowCredentials());
  }
}
