package com.portfolio.messages.businessLayer.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.portfolio.messages.mappingLayer.dto.MessageDTO;
import com.portfolio.messages.utils.exceptions.ContactValidationException;
import com.portfolio.messages.utils.exceptions.InvalidCaptchaException;
import com.portfolio.messages.utils.exceptions.RateLimitExceededException;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactProtectionService {

  private static final Pattern URL_PATTERN = Pattern.compile("(?i)\\b(?:https?://|www\\.)\\S+");
  private static final Pattern TAG_PATTERN = Pattern.compile("<[^>]+>");
  private static final Pattern CONTROL_CHARS_PATTERN =
      Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
  private static final List<String> SPAM_KEYWORDS =
      List.of("buy now", "crypto giveaway", "viagra", "click here", "guaranteed income");

  private final RestTemplate restTemplate;
  private final Map<String, Bucket> ipBuckets = new ConcurrentHashMap<>();

  @Value("${security.contact.turnstile.enabled:true}")
  private boolean turnstileEnabled;

  @Value("${security.contact.turnstile.secret-key:}")
  private String turnstileSecretKey;

  @Value(
      "${security.contact.turnstile.verify-url:https://challenges.cloudflare.com/turnstile/v0/siteverify}")
  private String turnstileVerifyUrl;

  @Value("${security.contact.turnstile.minimum-score:0.5}")
  private double minimumCaptchaScore;

  @Value("${security.contact.ip-hash-salt}")
  private String ipHashSalt;

  public ContactProtectionService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate =
        restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(5))
            .setReadTimeout(Duration.ofSeconds(5))
            .build();
  }

  public void validateSubmission(MessageDTO messageDTO, String clientIp, String userAgent) {
    String normalizedIp = normalizeClientIp(clientIp);
    String ipHash = hashIp(normalizedIp);

    validateHoneypot(messageDTO.getWebsite(), ipHash, userAgent);
    enforceRateLimit(ipHash, userAgent);
    validateCaptcha(messageDTO.getCaptchaToken(), normalizedIp, ipHash, userAgent);
    validateLinkCount(messageDTO.getMessage(), ipHash, userAgent);
    validateSpamKeywords(messageDTO.getMessage(), ipHash, userAgent);
  }

  public String sanitizeInput(String input) {
    if (input == null) {
      return null;
    }
    String withoutTags = TAG_PATTERN.matcher(input).replaceAll("");
    String withoutControlChars = CONTROL_CHARS_PATTERN.matcher(withoutTags).replaceAll("");
    return withoutControlChars.trim();
  }

  private void validateHoneypot(String websiteField, String ipHash, String userAgent) {
    if (websiteField != null && !websiteField.trim().isEmpty()) {
      log.warn("Honeypot triggered for contact form. ipHash={}, userAgent={}", ipHash, userAgent);
      throw new ContactValidationException("Validation failed");
    }
  }

  private void enforceRateLimit(String ipHash, String userAgent) {
    Bucket bucket = ipBuckets.computeIfAbsent(ipHash, this::newRateLimitedBucket);
    if (!bucket.tryConsume(1)) {
      log.warn("Contact form rate limit exceeded. ipHash={}, userAgent={}", ipHash, userAgent);
      throw new RateLimitExceededException("Too many contact form submissions");
    }
  }

  private Bucket newRateLimitedBucket(String ignoredIpHash) {
    Bandwidth tenMinuteLimit = Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(10)));
    Bandwidth dailyLimit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofHours(24)));
    return Bucket.builder().addLimit(tenMinuteLimit).addLimit(dailyLimit).build();
  }

  private void validateCaptcha(String token, String clientIp, String ipHash, String userAgent) {
    if (!turnstileEnabled) {
      return;
    }
    if (token == null || token.isBlank()) {
      log.warn(
          "Missing captcha token for contact form. ipHash={}, userAgent={}", ipHash, userAgent);
      throw new InvalidCaptchaException("Invalid captcha");
    }
    if (turnstileSecretKey == null || turnstileSecretKey.isBlank()) {
      log.error("Turnstile is enabled but TURNSTILE secret is missing");
      throw new InvalidCaptchaException("Invalid captcha");
    }

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("secret", turnstileSecretKey);
    body.add("response", token);
    body.add("remoteip", clientIp);

    TurnstileVerifyResponse verificationResponse;
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      verificationResponse =
          restTemplate.postForObject(
              turnstileVerifyUrl, new HttpEntity<>(body, headers), TurnstileVerifyResponse.class);
    } catch (RestClientException ex) {
      log.warn("Captcha verification request failed. ipHash={}, error={}", ipHash, ex.getMessage());
      throw new InvalidCaptchaException("Invalid captcha");
    }

    if (verificationResponse == null || !Boolean.TRUE.equals(verificationResponse.success())) {
      log.warn(
          "Captcha verification failed. ipHash={}, userAgent={}, errors={}",
          ipHash,
          userAgent,
          verificationResponse != null ? verificationResponse.errorCodes() : null);
      throw new InvalidCaptchaException("Invalid captcha");
    }

    Double score = verificationResponse.score();
    if (score != null && score < minimumCaptchaScore) {
      log.warn(
          "Captcha score below threshold. ipHash={}, score={}, minScore={}",
          ipHash,
          score,
          minimumCaptchaScore);
      throw new InvalidCaptchaException("Invalid captcha");
    }
  }

  private void validateLinkCount(String message, String ipHash, String userAgent) {
    int linkCount = 0;
    Matcher matcher = URL_PATTERN.matcher(message == null ? "" : message);
    while (matcher.find()) {
      linkCount++;
      if (linkCount > 2) {
        log.warn(
            "Rejected contact message for excessive URLs. ipHash={}, userAgent={}, links={}",
            ipHash,
            userAgent,
            linkCount);
        throw new ContactValidationException("Validation failed");
      }
    }
  }

  private void validateSpamKeywords(String message, String ipHash, String userAgent) {
    String value = message == null ? "" : message.toLowerCase();
    for (String keyword : SPAM_KEYWORDS) {
      if (value.contains(keyword)) {
        log.warn(
            "Rejected contact message for spam keyword match. ipHash={}, userAgent={}, keyword={}",
            ipHash,
            userAgent,
            keyword);
        throw new ContactValidationException("Validation failed");
      }
    }
  }

  private String normalizeClientIp(String clientIp) {
    if (clientIp == null || clientIp.isBlank()) {
      return "unknown";
    }
    return clientIp.trim();
  }

  private String hashIp(String ip) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hashed = digest.digest((ipHashSalt + ":" + ip).getBytes(StandardCharsets.UTF_8));
      return Base64.getUrlEncoder().withoutPadding().encodeToString(hashed);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("Failed to hash IP address", e);
    }
  }

  private record TurnstileVerifyResponse(
      Boolean success,
      Double score,
      @com.fasterxml.jackson.annotation.JsonProperty("error-codes") List<String> errorCodes) {}
}
