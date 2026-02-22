package com.portfolio.monolith.service;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.monolith.dto.MessageDto;
import com.portfolio.monolith.exception.BadRequestException;
import com.portfolio.monolith.exception.RateLimitExceededException;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class ContactProtectionService {

  private static final Pattern URL_PATTERN = Pattern.compile("(?i)\\b(?:https?://|www\\.)\\S+");
  private static final Pattern TAG_PATTERN = Pattern.compile("<[^>]+>");
  private static final Pattern CONTROL_CHARS_PATTERN =
      Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");

  private static final List<String> SPAM_KEYWORDS =
      List.of("buy now", "crypto giveaway", "viagra", "click here", "guaranteed income");

  private final RestTemplate restTemplate;
  private final Map<String, Bucket> ipBuckets = new ConcurrentHashMap<>();

  @Value("${security.contact.turnstile.enabled:false}")
  private boolean turnstileEnabled;

  @Value("${security.contact.turnstile.secret-key:}")
  private String turnstileSecretKey;

  @Value("${security.contact.turnstile.verify-url:https://challenges.cloudflare.com/turnstile/v0/siteverify}")
  private String turnstileVerifyUrl;

  @Value("${security.contact.turnstile.minimum-score:0.5}")
  private double minimumCaptchaScore;

  @Value("${security.contact.ip-hash-salt:local-dev-salt-change-me}")
  private String ipHashSalt;

  public ContactProtectionService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void validateSubmission(MessageDto messageDto, String clientIp, String userAgent) {
    String normalizedIp = normalizeClientIp(clientIp);
    String ipHash = hashIp(normalizedIp);

    validateHoneypot(messageDto.website, ipHash, userAgent);
    enforceRateLimit(ipHash, userAgent);
    validateCaptcha(messageDto.captchaToken, normalizedIp, ipHash, userAgent);
    validateLinkCount(messageDto.message, ipHash, userAgent);
    validateSpamKeywords(messageDto.message, ipHash, userAgent);
  }

  public void sanitize(MessageDto messageDto) {
    messageDto.senderName = sanitizeInput(messageDto.senderName);
    messageDto.senderEmail = sanitizeInput(messageDto.senderEmail);
    messageDto.subject = sanitizeInput(messageDto.subject);
    messageDto.message = sanitizeInput(messageDto.message);
  }

  private String sanitizeInput(String input) {
    if (input == null) {
      return null;
    }
    String withoutTags = TAG_PATTERN.matcher(input).replaceAll("");
    String withoutControlChars = CONTROL_CHARS_PATTERN.matcher(withoutTags).replaceAll("");
    return withoutControlChars.trim();
  }

  private void validateHoneypot(String websiteField, String ipHash, String userAgent) {
    if (websiteField != null && !websiteField.trim().isEmpty()) {
      throw new BadRequestException("Validation failed");
    }
  }

  private void enforceRateLimit(String ipHash, String userAgent) {
    Bucket bucket = ipBuckets.computeIfAbsent(ipHash, this::newRateLimitedBucket);
    if (!bucket.tryConsume(1)) {
      throw new RateLimitExceededException("Too many contact form submissions");
    }
  }

  private Bucket newRateLimitedBucket(String ignored) {
    Bandwidth tenMinuteLimit = Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(10)));
    Bandwidth dailyLimit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofHours(24)));
    return Bucket.builder().addLimit(tenMinuteLimit).addLimit(dailyLimit).build();
  }

  private void validateCaptcha(String token, String clientIp, String ipHash, String userAgent) {
    if (!turnstileEnabled) {
      return;
    }

    if (token == null || token.isBlank()) {
      throw new BadRequestException("Invalid captcha");
    }

    if (turnstileSecretKey == null || turnstileSecretKey.isBlank()) {
      throw new BadRequestException("Invalid captcha");
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
      throw new BadRequestException("Invalid captcha");
    }

    if (verificationResponse == null || !Boolean.TRUE.equals(verificationResponse.success())) {
      throw new BadRequestException("Invalid captcha");
    }

    Double score = verificationResponse.score();
    if (score != null && score < minimumCaptchaScore) {
      throw new BadRequestException("Invalid captcha");
    }
  }

  private void validateLinkCount(String message, String ipHash, String userAgent) {
    int linkCount = 0;
    Matcher matcher = URL_PATTERN.matcher(message == null ? "" : message);
    while (matcher.find()) {
      linkCount++;
      if (linkCount > 2) {
        throw new BadRequestException("Validation failed");
      }
    }
  }

  private void validateSpamKeywords(String message, String ipHash, String userAgent) {
    String value = message == null ? "" : message.toLowerCase();
    for (String keyword : SPAM_KEYWORDS) {
      if (value.contains(keyword)) {
        throw new BadRequestException("Validation failed");
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
      Boolean success, Double score, @JsonProperty("error-codes") List<String> errorCodes) {}
}
