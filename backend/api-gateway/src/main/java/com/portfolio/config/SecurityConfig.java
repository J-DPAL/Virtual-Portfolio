package com.portfolio.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.portfolio.filter.RateLimitFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String CSP_POLICY =
      "default-src 'self'; "
          + "img-src 'self' data: blob:; "
          + "style-src 'self' 'unsafe-inline'; "
          + "script-src 'self' 'unsafe-inline' 'unsafe-eval'; "
          + "connect-src 'self' http://localhost:3000 http://localhost:5173 "
          + "https://virtual-portfolio-frontend.onrender.com https://api-gateway-sit5.onrender.com";

  private final RateLimitFilter rateLimitFilter;
  private final List<String> allowedOrigins;

  public SecurityConfig(
      RateLimitFilter rateLimitFilter,
      @Value(
              "${app.cors.allowed-origins:http://localhost:3000,http://localhost:5173,https://virtual-portfolio-frontend.onrender.com}")
          String allowedOriginsCsv) {
    this.rateLimitFilter = rateLimitFilter;
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
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .headers(headers -> headers.contentSecurityPolicy(csp -> csp.policyDirectives(CSP_POLICY)))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.anyRequest()
                    .permitAll() // Gateway routes to services which handle authentication
            )
        .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(allowedOrigins);
    configuration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
