package com.portfolio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ApiGatewayApplication Tests")
class ApiGatewayApplicationTest {

  @Test
  @DisplayName("Should start and close application context")
  void testApplicationContextLoads() {
    // Arrange: Configure application
    SpringApplication app = new SpringApplication(ApiGatewayApplication.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.setDefaultProperties(
        java.util.Map.of(
            "eureka.client.enabled", "false",
            "eureka.client.registerWithEureka", "false",
            "eureka.client.fetchRegistry", "false"));

    // Act: Start context
    try (ConfigurableApplicationContext context = app.run()) {
      // Assert: Verify context
      assertTrue(context.isActive());
    }
  }
}
