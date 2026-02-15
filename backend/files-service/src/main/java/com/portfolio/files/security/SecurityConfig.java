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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;

import jakarta.servlet.Filter;

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
    http.csrf(
            csrf ->
                csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers("/actuator/health"))
        .cors(cors -> cors.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/resume/download",
                        "/resume/current",
                        "/api/resume/download",
                        "/api/resume/current")
                    .permitAll()
                    .requestMatchers("/resume/upload", "/api/resume/upload")
                    .hasRole("ADMIN")
                    .requestMatchers("/actuator/health")
                    .permitAll()
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
    http.addFilterAfter(csrfCookieFilter(), CsrfFilter.class);

    return http.build();
  }

  private Filter csrfCookieFilter() {
    return (request, response, chain) -> {
      CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
      if (token != null) {
        token.getToken();
      }
      chain.doFilter(request, response);
    };
  }
}
