package com.portfolio.users.presentationLayer.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.portfolio.users.businessLayer.service.AuthService;
import com.portfolio.users.mappingLayer.dto.LoginRequest;
import com.portfolio.users.mappingLayer.dto.LoginResponse;
import com.portfolio.users.mappingLayer.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(
    origins = {"http://localhost:3000", "http://localhost:5173"},
    allowCredentials = "true")
public class AuthController {

  private static final String AUTH_COOKIE_NAME = "auth_token";
  private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

  private final AuthService authService;

  @Value("${jwt.expiration}")
  private long jwtExpirationMs;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
    LoginResponse response = authService.login(loginRequest);

    String token = response.getToken();
    ResponseCookie authCookie =
        ResponseCookie.from(AUTH_COOKIE_NAME, token)
            .httpOnly(true)
            .secure(request.isSecure())
            .sameSite("Lax")
            .path("/")
            .maxAge(Duration.ofMillis(jwtExpirationMs))
            .build();

    response.setToken(null);

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authCookie.toString()).body(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request) {
    ResponseCookie authCookie =
        ResponseCookie.from(AUTH_COOKIE_NAME, "")
            .httpOnly(true)
            .secure(request.isSecure())
            .sameSite("Lax")
            .path("/")
            .maxAge(Duration.ZERO)
            .build();

    ResponseCookie csrfCookie =
        ResponseCookie.from(CSRF_COOKIE_NAME, "")
            .httpOnly(false)
            .secure(request.isSecure())
            .sameSite("Lax")
            .path("/")
            .maxAge(Duration.ZERO)
            .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, authCookie.toString())
        .header(HttpHeaders.SET_COOKIE, csrfCookie.toString())
        .build();
  }

  @GetMapping("/csrf")
  public ResponseEntity<Map<String, String>> csrf(CsrfToken token) {
    return ResponseEntity.ok(Map.of("token", token.getToken()));
  }

  @GetMapping("/me")
  public ResponseEntity<UserDTO> me(Authentication authentication) {
    UserDTO user = authService.getCurrentUser(authentication.getName());
    return ResponseEntity.ok(user);
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return new ResponseEntity<>("Auth service is running", HttpStatus.OK);
  }
}
