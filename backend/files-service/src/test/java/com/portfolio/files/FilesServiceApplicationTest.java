package com.portfolio.files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("FilesServiceApplication Tests")
class FilesServiceApplicationTest {

  @Test
  @DisplayName("Should load application context")
  void testContextLoads() {
    assertTrue(true);
  }
}
