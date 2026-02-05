package com.portfolio.files.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("SecurityConfig Tests")
class SecurityConfigTest {

  @Autowired private SecurityFilterChain securityFilterChain;

  @Test
  @DisplayName("Should provide SecurityFilterChain bean")
  void testSecurityFilterChainBeanExists() {
    assertNotNull(securityFilterChain);
  }
}
