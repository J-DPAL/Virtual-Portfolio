package com.portfolio.monolith.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.portfolio.monolith.dto.AuthUserResponse;
import com.portfolio.monolith.exception.ApiException;
import com.portfolio.monolith.exception.UnauthorizedException;
import com.portfolio.monolith.security.AuthenticatedUser;

@Service
public class SupabaseAuthService {

  private final RestTemplate restTemplate;
  private final NamedParameterJdbcTemplate jdbc;

  private final String supabaseUrl;
  private final String anonKey;
  private final String authCookieName;

  public SupabaseAuthService(
      RestTemplate restTemplate,
      NamedParameterJdbcTemplate jdbc,
      @Value("${supabase.url}") String supabaseUrl,
      @Value("${supabase.anon-key}") String anonKey,
      @Value("${supabase.auth-cookie-name:vp_auth_token}") String authCookieName) {
    this.restTemplate = restTemplate;
    this.jdbc = jdbc;
    this.supabaseUrl = trimTrailingSlash(supabaseUrl);
    this.anonKey = anonKey;
    this.authCookieName = authCookieName;
  }

  public LoginResult login(String email, String password) {
    ensureConfigured();

    String tokenUrl = supabaseUrl + "/auth/v1/token?grant_type=password";
    HttpHeaders headers = baseHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, String> body = new HashMap<>();
    body.put("email", email);
    body.put("password", password);

    try {
      ResponseEntity<JsonNode> response =
          restTemplate.postForEntity(tokenUrl, new HttpEntity<>(body, headers), JsonNode.class);

      JsonNode payload = response.getBody();
      if (payload == null) {
        throw new UnauthorizedException("Invalid email or password");
      }

      String accessToken = textOrNull(payload.path("access_token"));
      if (isBlank(accessToken)) {
        throw new UnauthorizedException("Invalid email or password");
      }

      AuthenticatedUser user = mapUserNode(payload.path("user"));
      return new LoginResult(accessToken, toUserResponse(user));
    } catch (HttpStatusCodeException ex) {
      if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED || ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
        throw new UnauthorizedException("Invalid email or password");
      }
      throw new ApiException(HttpStatus.BAD_GATEWAY, "Supabase authentication failed");
    }
  }

  public AuthenticatedUser getUserFromToken(String token) {
    ensureConfigured();
    if (isBlank(token)) {
      throw new UnauthorizedException("Authentication token is missing");
    }

    String meUrl = supabaseUrl + "/auth/v1/user";
    HttpHeaders headers = baseHeaders();
    headers.setBearerAuth(token);

    try {
      ResponseEntity<JsonNode> response =
          restTemplate.exchange(meUrl, HttpMethod.GET, new HttpEntity<>(headers), JsonNode.class);
      JsonNode payload = response.getBody();
      if (payload == null) {
        throw new UnauthorizedException("Invalid authentication token");
      }
      return mapUserNode(payload);
    } catch (HttpStatusCodeException ex) {
      throw new UnauthorizedException("Invalid authentication token");
    }
  }

  public String getAuthCookieName() {
    return authCookieName;
  }

  private AuthenticatedUser mapUserNode(JsonNode userNode) {
    String userId = textOrNull(userNode.path("id"));
    String email = textOrNull(userNode.path("email"));
    String fullName = textOrNull(userNode.path("user_metadata").path("full_name"));

    if (isBlank(fullName) && !isBlank(email)) {
      int at = email.indexOf('@');
      fullName = at > 0 ? email.substring(0, at) : email;
    }

    boolean isAdmin = isAdminUser(userId);
    return new AuthenticatedUser(userId, email, fullName, isAdmin);
  }

  private boolean isAdminUser(String userId) {
    if (isBlank(userId)) {
      return false;
    }

    String sql =
        "select exists(select 1 from public.admin_users where user_id = cast(:userId as uuid))";

    try {
      Boolean exists =
          jdbc.queryForObject(sql, new MapSqlParameterSource("userId", userId), Boolean.class);
      return Boolean.TRUE.equals(exists);
    } catch (DataAccessException ex) {
      return false;
    }
  }

  private AuthUserResponse toUserResponse(AuthenticatedUser user) {
    AuthUserResponse dto = new AuthUserResponse();
    dto.id = user.getId();
    dto.email = user.getEmail();
    dto.fullName = user.getFullName();
    dto.role = user.isAdmin() ? "ADMIN" : "USER";
    dto.active = true;
    return dto;
  }

  private HttpHeaders baseHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("apikey", anonKey);
    return headers;
  }

  private void ensureConfigured() {
    if (isBlank(supabaseUrl) || isBlank(anonKey)) {
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Supabase auth is not configured on backend");
    }
  }

  private static String textOrNull(JsonNode node) {
    if (node == null || node.isNull()) {
      return null;
    }
    String value = node.asText(null);
    return isBlank(value) ? null : value;
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  private static String trimTrailingSlash(String value) {
    if (value == null) {
      return null;
    }
    String result = value.trim();
    while (result.endsWith("/")) {
      result = result.substring(0, result.length() - 1);
    }
    return result;
  }

  public static class LoginResult {
    private final String accessToken;
    private final AuthUserResponse user;

    public LoginResult(String accessToken, AuthUserResponse user) {
      this.accessToken = accessToken;
      this.user = user;
    }

    public String getAccessToken() {
      return accessToken;
    }

    public AuthUserResponse getUser() {
      return user;
    }
  }
}
