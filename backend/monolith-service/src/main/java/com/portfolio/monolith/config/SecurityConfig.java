package com.portfolio.monolith.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.portfolio.monolith.security.SupabaseAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final SupabaseAuthenticationFilter authFilter;
  private final List<String> allowedOrigins;

  public SecurityConfig(
      SupabaseAuthenticationFilter authFilter,
      @Value(
              "${app.cors.allowed-origins:http://localhost:3000,http://localhost:5173,https://virtual-portfolio-frontend.onrender.com}")
          String allowedOriginsCsv) {
    this.authFilter = authFilter;
    this.allowedOrigins =
        Arrays.stream(allowedOriginsCsv.split(","))
            .map(String::trim)
            .filter(s -> !s.isBlank())
            .distinct()
            .collect(Collectors.toList());
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            exceptions ->
                exceptions
                    .authenticationEntryPoint(
                        (request, response, ex) -> response.sendError(401, "Unauthorized"))
                    .accessDeniedHandler(
                        (request, response, ex) -> response.sendError(403, "Forbidden")))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers("/error", "/actuator/health", "/v1/auth/health")
                    .permitAll()
                    .requestMatchers("/messages/health", "/v1/files/health")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/auth/login", "/v1/auth/logout")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/projects/**", "/skills/**", "/education/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/experience/**", "/hobbies/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/testimonials/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/testimonials", "/messages")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/v1/files/resume/download", "/v1/files/resume/current")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Spring rejects allowCredentials=true when allowedOrigins contains "*".
    // Use allowedOriginPatterns for wildcard scenarios instead.
    if (allowedOrigins.stream().anyMatch("*"::equals)) {
      configuration.setAllowedOriginPatterns(List.of("*"));
    } else {
      configuration.setAllowedOrigins(allowedOrigins);
    }
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
