package com.portfolio.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GatewayConfig Unit Tests")
class GatewayConfigTest {

  @Test
  @DisplayName("Should create RestTemplate bean")
  void testRestTemplateBean() {
    // Arrange: Create config
    GatewayConfig config = new GatewayConfig();

    // Act: Create RestTemplate
    RestTemplate restTemplate = config.restTemplate();

    // Assert: Verify not null
    assertNotNull(restTemplate);
  }

  @Test
  @DisplayName("Should create gateway routes")
  void testGatewayRoutesBean() {
    // Arrange: Create config and set URLs
    GatewayConfig config = new GatewayConfig();
    ReflectionTestUtils.setField(config, "usersServiceUrl", "http://localhost:8081");
    ReflectionTestUtils.setField(config, "skillsServiceUrl", "http://localhost:8082");
    ReflectionTestUtils.setField(config, "projectsServiceUrl", "http://localhost:8083");
    ReflectionTestUtils.setField(config, "experienceServiceUrl", "http://localhost:8084");
    ReflectionTestUtils.setField(config, "educationServiceUrl", "http://localhost:8085");
    ReflectionTestUtils.setField(config, "hobbiesServiceUrl", "http://localhost:8086");
    ReflectionTestUtils.setField(config, "testimonialsServiceUrl", "http://localhost:8087");
    ReflectionTestUtils.setField(config, "messagesServiceUrl", "http://localhost:8088");
    ReflectionTestUtils.setField(config, "filesServiceUrl", "http://localhost:8090");

    // Act: Create routes
    RouterFunction<ServerResponse> routes = config.gatewayRoutes(config.restTemplate());

    // Assert: Verify routes
    assertNotNull(routes);
  }
}
