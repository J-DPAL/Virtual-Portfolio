package com.portfolio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EurekaServerApplication Tests")
class EurekaServerApplicationTest {

  @Test
  @DisplayName("Should start and close application context")
  void testApplicationContextLoads() {
    // Arrange: Configure application
    SpringApplication app = new SpringApplication(EurekaServerApplication.class);
    app.setWebApplicationType(WebApplicationType.SERVLET);
    app.setAdditionalProfiles("test");

    // Act: Start context
    try (ConfigurableApplicationContext context = app.run()) {
      // Assert: Verify context
      assertTrue(context.isActive());
    }
  }
}
