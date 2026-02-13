package com.portfolio.files.config;

import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;

@Component
public class SecretsValidator {

  private final Environment environment;

  public SecretsValidator(Environment environment) {
    this.environment = environment;
  }

  @PostConstruct
  public void validateSecrets() {
    if (environment.acceptsProfiles(Profiles.of("dev", "test"))) {
      return;
    }

    require("DB_PASSWORD", environment.getProperty("spring.datasource.password"));
    require("JWT_SECRET", environment.getProperty("jwt.secret"));
  }

  private void require(String name, String value) {
    if (!StringUtils.hasText(value)) {
      throw new IllegalStateException("Missing required secret: " + name);
    }
  }
}
