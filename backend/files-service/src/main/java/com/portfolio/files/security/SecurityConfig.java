package com.portfolio.files.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final Environment environment;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, Environment environment) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.environment = environment;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/resume/download",
                        "/resume/current",
                        "/api/resume/download",
                        "/api/resume/current",
                        // Health endpoints for monitoring / wake-up pings
                        "/health",
                        "/api/health",
                        // If actuator is added later
                        "/actuator/health")
                    .permitAll()
                    .requestMatchers("/resume/upload", "/api/resume/upload")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated());

    // Only add JWT filter if not running under 'test' profile
    String[] activeProfiles = environment.getActiveProfiles();
    boolean isTestProfile = false;
    for (String profile : activeProfiles) {
      if (profile.equalsIgnoreCase("test")) {
        isTestProfile = true;
        break;
      }
    }
    if (!isTestProfile) {
      http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    return http.build();
  }
}
