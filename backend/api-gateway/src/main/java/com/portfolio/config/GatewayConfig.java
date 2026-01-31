package com.portfolio.config;

import java.net.URI;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class GatewayConfig {

  @Value("${services.users.url:http://users-service:8081}")
  private String usersServiceUrl;

  @Value("${services.skills.url:http://skills-service:8082}")
  private String skillsServiceUrl;

  @Value("${services.projects.url:http://projects-service:8083}")
  private String projectsServiceUrl;

  @Value("${services.experience.url:http://experience-service:8084}")
  private String experienceServiceUrl;

  @Value("${services.education.url:http://education-service:8085}")
  private String educationServiceUrl;

  @Value("${services.hobbies.url:http://hobbies-service:8086}")
  private String hobbiesServiceUrl;

  @Value("${services.testimonials.url:http://testimonials-service:8087}")
  private String testimonialsServiceUrl;

  @Value("${services.messages.url:http://messages-service:8088}")
  private String messagesServiceUrl;

  @Value("${services.files.url:http://files-service:8090}")
  private String filesServiceUrl;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public RouterFunction<ServerResponse> gatewayRoutes(RestTemplate restTemplate) {
    return route()
        // Auth & Users routes (users-service has no /api context-path)
        .GET("/v1/auth/**", req -> proxyRequestNoContextPath(req, usersServiceUrl, restTemplate))
        .POST("/v1/auth/**", req -> proxyRequestNoContextPath(req, usersServiceUrl, restTemplate))
        .GET("/users/**", req -> proxyRequestNoContextPath(req, usersServiceUrl, restTemplate))
        .POST("/users/**", req -> proxyRequestNoContextPath(req, usersServiceUrl, restTemplate))
        .PUT("/users/**", req -> proxyRequestNoContextPath(req, usersServiceUrl, restTemplate))
        .DELETE("/users/**", req -> proxyRequestNoContextPath(req, usersServiceUrl, restTemplate))

        // Skills routes
        .path(
            "/skills",
            builder ->
                builder
                    .GET("/**", req -> proxyRequest(req, skillsServiceUrl, restTemplate))
                    .POST("/**", req -> proxyRequest(req, skillsServiceUrl, restTemplate))
                    .PUT("/**", req -> proxyRequest(req, skillsServiceUrl, restTemplate))
                    .DELETE("/**", req -> proxyRequest(req, skillsServiceUrl, restTemplate)))

        // Projects routes
        .path(
            "/projects",
            builder ->
                builder
                    .GET("/**", req -> proxyRequest(req, projectsServiceUrl, restTemplate))
                    .POST("/**", req -> proxyRequest(req, projectsServiceUrl, restTemplate))
                    .PUT("/**", req -> proxyRequest(req, projectsServiceUrl, restTemplate))
                    .DELETE("/**", req -> proxyRequest(req, projectsServiceUrl, restTemplate)))

        // Experience routes
        .path(
            "/experience",
            builder ->
                builder
                    .GET("/**", req -> proxyRequest(req, experienceServiceUrl, restTemplate))
                    .POST("/**", req -> proxyRequest(req, experienceServiceUrl, restTemplate))
                    .PUT("/**", req -> proxyRequest(req, experienceServiceUrl, restTemplate))
                    .DELETE("/**", req -> proxyRequest(req, experienceServiceUrl, restTemplate)))

        // Education routes
        .path(
            "/education",
            builder ->
                builder
                    .GET("/**", req -> proxyRequest(req, educationServiceUrl, restTemplate))
                    .POST("/**", req -> proxyRequest(req, educationServiceUrl, restTemplate))
                    .PUT("/**", req -> proxyRequest(req, educationServiceUrl, restTemplate))
                    .DELETE("/**", req -> proxyRequest(req, educationServiceUrl, restTemplate)))

        // Hobbies routes
        .path(
            "/hobbies",
            builder ->
                builder
                    .GET("/**", req -> proxyRequest(req, hobbiesServiceUrl, restTemplate))
                    .POST("/**", req -> proxyRequest(req, hobbiesServiceUrl, restTemplate))
                    .PUT("/**", req -> proxyRequest(req, hobbiesServiceUrl, restTemplate))
                    .DELETE("/**", req -> proxyRequest(req, hobbiesServiceUrl, restTemplate)))

        // Testimonials routes
        .path(
            "/testimonials",
            builder ->
                builder
                    .GET("/**", req -> proxyRequest(req, testimonialsServiceUrl, restTemplate))
                    .POST("/**", req -> proxyRequest(req, testimonialsServiceUrl, restTemplate))
                    .PATCH("/**", req -> proxyRequest(req, testimonialsServiceUrl, restTemplate))
                    .DELETE("/**", req -> proxyRequest(req, testimonialsServiceUrl, restTemplate)))

        // Messages routes
        .path(
            "/messages",
            builder ->
                builder
                    .GET("/**", req -> proxyRequest(req, messagesServiceUrl, restTemplate))
                    .POST("/**", req -> proxyRequest(req, messagesServiceUrl, restTemplate))
                    .PATCH("/**", req -> proxyRequest(req, messagesServiceUrl, restTemplate))
                    .DELETE("/**", req -> proxyRequest(req, messagesServiceUrl, restTemplate)))

        // Files routes
        .path(
            "/v1/files",
            builder ->
                builder
                    .GET(
                        "/**",
                        req ->
                            proxyRequestWithPathRewrite(
                                req, filesServiceUrl, "/api", "/v1/files", restTemplate))
                    .POST(
                        "/**",
                        req ->
                            proxyRequestWithPathRewrite(
                                req, filesServiceUrl, "/api", "/v1/files", restTemplate)))
        .build();
  }

  @SuppressWarnings("null")
  private ServerResponse proxyRequest(
      ServerRequest request, String targetUrl, RestTemplate restTemplate) {
    try {
      // Build target URI with path and query string
      // Add /api prefix since downstream services also have /api context-path
      URI uri =
          UriComponentsBuilder.fromHttpUrl(targetUrl)
              .path("/api" + request.path())
              .query(request.uri().getRawQuery())
              .build(true)
              .toUri();

      return executeProxyRequest(request, uri, restTemplate);
    } catch (org.springframework.web.client.HttpClientErrorException
        | org.springframework.web.client.HttpServerErrorException e) {
      @SuppressWarnings("null")
      ServerResponse response =
          ServerResponse.status(e.getStatusCode())
              .body(Objects.requireNonNullElse(e.getResponseBodyAsString(), ""));
      return response;
    } catch (Exception e) {
      return ServerResponse.status(500)
          .body("{\"error\":\"Gateway error: " + e.getMessage() + "\"}");
    }
  }

  @SuppressWarnings("null")
  private ServerResponse proxyRequestNoContextPath(
      ServerRequest request, String targetUrl, RestTemplate restTemplate) {
    try {
      // Build target URI without adding /api prefix (for services without context-path)
      URI uri =
          UriComponentsBuilder.fromHttpUrl(targetUrl)
              .path(request.path())
              .query(request.uri().getRawQuery())
              .build(true)
              .toUri();

      return executeProxyRequest(request, uri, restTemplate);
    } catch (org.springframework.web.client.HttpClientErrorException
        | org.springframework.web.client.HttpServerErrorException e) {
      return ServerResponse.status(e.getStatusCode())
          .body(Objects.requireNonNullElse(e.getResponseBodyAsString(), ""));
    } catch (Exception e) {
      return ServerResponse.status(500)
          .body("{\"error\":\"Gateway error: " + e.getMessage() + "\"}");
    }
  }

  private ServerResponse proxyRequestWithPathRewrite(
      ServerRequest request,
      String targetUrl,
      String apiPrefix,
      String stripPrefix,
      RestTemplate restTemplate) {
    try {
      String requestPath = request.path();
      @SuppressWarnings("null")
      String strippedPath =
          requestPath.startsWith(stripPrefix)
              ? requestPath.substring(stripPrefix.length())
              : requestPath;

      @SuppressWarnings("null")
      URI uri =
          UriComponentsBuilder.fromHttpUrl(targetUrl)
              .path(apiPrefix + strippedPath)
              .query(request.uri().getRawQuery())
              .build(true)
              .toUri();

      return executeProxyRequest(request, uri, restTemplate);
    } catch (org.springframework.web.client.HttpClientErrorException
        | org.springframework.web.client.HttpServerErrorException e) {
      @SuppressWarnings("null")
      String body = Objects.requireNonNullElse(e.getResponseBodyAsString(), "");
      return ServerResponse.status(e.getStatusCode()).body(body);
    } catch (Exception e) {
      return ServerResponse.status(500)
          .body("{\"error\":\"Gateway error: " + e.getMessage() + "\"}");
    }
  }

  private ServerResponse executeProxyRequest(
      ServerRequest request, URI uri, RestTemplate restTemplate) {
    try {

      // Copy headers except Host
      HttpHeaders headers = new HttpHeaders();
      request
          .headers()
          .asHttpHeaders()
          .forEach(
              (key, value) -> {
                if (key != null && !key.equalsIgnoreCase("host") && value != null) {
                  headers.addAll(key, value);
                }
              });

      // Create entity with body when applicable
      // Always provide a non-null HttpEntity instance
      @SuppressWarnings("null")
      HttpMethod httpMethod = Objects.requireNonNullElse(request.method(), HttpMethod.GET);
      String body = null;
      if (!(httpMethod == HttpMethod.GET
          || httpMethod == HttpMethod.DELETE
          || httpMethod == HttpMethod.HEAD
          || httpMethod == HttpMethod.OPTIONS)) {
        try {
          body = request.body(String.class);
        } catch (Exception ignore) {
          body = null;
        }
      }
      HttpEntity<String> entity =
          (body == null) ? new HttpEntity<>(headers) : new HttpEntity<>(body, headers);

      // Use byte[] for binary responses to avoid corruption
      @SuppressWarnings("null")
      ResponseEntity<byte[]> response =
          restTemplate.exchange(uri, httpMethod, entity, byte[].class);

      byte[] responseBody = response.getBody();

      return ServerResponse.status(response.getStatusCode())
          .headers(h -> h.addAll(response.getHeaders()))
          .body(responseBody != null ? responseBody : new byte[0]);

    } catch (org.springframework.web.client.HttpClientErrorException
        | org.springframework.web.client.HttpServerErrorException e) {
      @SuppressWarnings("null")
      String body = Objects.requireNonNullElse(e.getResponseBodyAsString(), "");
      return ServerResponse.status(e.getStatusCode()).body(body);
    } catch (Exception e) {
      return ServerResponse.status(500)
          .body("{\"error\":\"Gateway error: " + e.getMessage() + "\"}");
    }
  }
}
