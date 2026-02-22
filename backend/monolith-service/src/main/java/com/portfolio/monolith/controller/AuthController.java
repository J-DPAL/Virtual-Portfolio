package com.portfolio.monolith.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.monolith.dto.AuthUserResponse;
import com.portfolio.monolith.dto.GenericResponse;
import com.portfolio.monolith.dto.LoginRequest;
import com.portfolio.monolith.dto.LoginResponse;
import com.portfolio.monolith.exception.UnauthorizedException;
import com.portfolio.monolith.security.AuthenticatedUser;
import com.portfolio.monolith.service.SupabaseAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@Validated
public class AuthController {

  private final SupabaseAuthService authService;
  private final boolean forceSecureCookie;

  public AuthController(
      SupabaseAuthService authService,
      @Value("${app.auth.cookie.force-secure:false}") boolean forceSecureCookie) {
    this.authService = authService;
    this.forceSecureCookie = forceSecureCookie;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
    SupabaseAuthService.LoginResult loginResult =
        authService.login(loginRequest.email, loginRequest.password);

    boolean secure = isSecureRequest(request);
    String sameSite = secure ? "None" : "Lax";

    ResponseCookie authCookie =
        ResponseCookie.from(authService.getAuthCookieName(), loginResult.getAccessToken())
            .httpOnly(true)
            .secure(secure)
            .sameSite(sameSite)
            .path("/")
            .maxAge(Duration.ofDays(7))
            .build();

    LoginResponse response = new LoginResponse();
    response.user = loginResult.getUser();
    response.message = "Login successful";

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authCookie.toString()).body(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<GenericResponse> logout(HttpServletRequest request) {
    boolean secure = isSecureRequest(request);
    String sameSite = secure ? "None" : "Lax";

    ResponseCookie authCookie =
        ResponseCookie.from(authService.getAuthCookieName(), "")
            .httpOnly(true)
            .secure(secure)
            .sameSite(sameSite)
            .path("/")
            .maxAge(Duration.ZERO)
            .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, authCookie.toString())
        .body(new GenericResponse("Logout successful"));
  }

  @GetMapping("/me")
  public ResponseEntity<AuthUserResponse> me(@AuthenticationPrincipal AuthenticatedUser user) {
    if (user == null) {
      throw new UnauthorizedException("Authentication required");
    }

    AuthUserResponse dto = new AuthUserResponse();
    dto.id = user.getId();
    dto.email = user.getEmail();
    dto.fullName = user.getFullName();
    dto.role = user.isAdmin() ? "ADMIN" : "USER";
    dto.active = true;
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return new ResponseEntity<>("Auth service is running", HttpStatus.OK);
  }

  private boolean isSecureRequest(HttpServletRequest request) {
    if (forceSecureCookie) {
      return true;
    }
    String forwardedProto = request.getHeader("X-Forwarded-Proto");
    if (forwardedProto != null && "https".equalsIgnoreCase(forwardedProto.trim())) {
      return true;
    }
    String forwardedSsl = request.getHeader("X-Forwarded-Ssl");
    if (forwardedSsl != null && "on".equalsIgnoreCase(forwardedSsl.trim())) {
      return true;
    }
    return request.isSecure();
  }
}
