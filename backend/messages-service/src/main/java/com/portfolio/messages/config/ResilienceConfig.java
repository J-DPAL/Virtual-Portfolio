package com.portfolio.messages.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * Resilience4j configuration for mail sending with retry and circuit breaker patterns.
 *
 * <p>RETRY POLICY: - Max 3 attempts total (initial + 2 retries) - Wait times: 1s, 2s, 4s
 * (exponential backoff) - Retries on transient mail exceptions (connection timeouts, etc.) - Does
 * NOT retry on permanent failures (invalid email, auth errors)
 *
 * <p>CIRCUIT BREAKER POLICY: - Opens after 5 consecutive failures - Stays open for 30 seconds
 * before attempting recovery - Fails fast when open to prevent cascading failures - Logs state
 * transitions for monitoring
 */
@Configuration
@Slf4j
@ConditionalOnProperty(name = "spring.mail.host")
public class ResilienceConfig {

  public static final String MAIL_RETRY = "mailSendRetry";
  public static final String MAIL_CIRCUIT_BREAKER = "mailSendCircuitBreaker";

  /**
   * Configures retry policy for mail sending. Uses exponential backoff: attempt 1 at T, attempt 2
   * at T+1s, attempt 3 at T+3s.
   */
  @Bean
  public RetryRegistry retryRegistry() {
    RetryConfig mailRetryConfig =
        RetryConfig.custom()
            .maxAttempts(3) // Initial attempt + 2 retries
            .intervalFunction(
                io.github.resilience4j.core.IntervalFunction.ofExponentialBackoff(
                    1000, // Initial interval: 1 second
                    2 // Multiplier: 1s -> 2s -> 4s
                    ))
            .retryOnException(this::isTransientMailException)
            .failAfterMaxAttempts(true)
            .build();

    RetryRegistry retryRegistry = RetryRegistry.of(mailRetryConfig);

    retryRegistry
        .getEventPublisher()
        .onEntryAdded(
            event -> {
              Retry retry = event.getAddedEntry();
              retry
                  .getEventPublisher()
                  .onRetry(retryEvent -> log.warn("Mail send retry: Attempt failed, retrying..."))
                  .onSuccess(successEvent -> log.info("Mail send succeeded after retry"));
            });

    return retryRegistry;
  }

  /**
   * Configures circuit breaker for mail sending. Opens after 5 consecutive failures to prevent
   * hammering failing mail server. Attempts recovery after 30 seconds.
   */
  @Bean
  public CircuitBreakerRegistry circuitBreakerRegistry() {
    CircuitBreakerConfig mailCircuitBreakerConfig =
        CircuitBreakerConfig.custom()
            .failureRateThreshold(100) // Open immediately on any failure
            .slowCallRateThreshold(100)
            .waitDurationInOpenState(Duration.ofSeconds(30)) // Half-open after 30s
            .minimumNumberOfCalls(5) // Need 5 calls to evaluate failure rate
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slowCallDurationThreshold(Duration.ofSeconds(5))
            .recordException(this::isMailException)
            .build();

    CircuitBreakerRegistry circuitBreakerRegistry =
        CircuitBreakerRegistry.of(mailCircuitBreakerConfig);

    circuitBreakerRegistry
        .getEventPublisher()
        .onEntryAdded(
            event -> {
              CircuitBreaker circuitBreaker = event.getAddedEntry();
              circuitBreaker
                  .getEventPublisher()
                  .onStateTransition(
                      transitionEvent -> {
                        log.warn(
                            "Mail circuit breaker state transition: {} -> {}",
                            transitionEvent.getStateTransition().getFromState(),
                            transitionEvent.getStateTransition().getToState());
                      })
                  .onError(
                      errorEvent -> {
                        log.error(
                            "Mail circuit breaker recorded error: {}",
                            errorEvent.getThrowable().getMessage());
                      })
                  .onSuccess(
                      successEvent -> {
                        log.debug("Mail circuit breaker recorded success");
                      });
            });

    return circuitBreakerRegistry;
  }

  /**
   * Determines if a mail exception is transient (worth retrying). Transient: connection timeouts,
   * temporary SMTP issues, network errors Non-transient: invalid email, authentication failure,
   * permanently invalid address
   */
  private boolean isTransientMailException(Throwable throwable) {
    if (!(throwable instanceof MailException)) {
      return false; // Don't retry non-mail exceptions
    }

    String message = throwable.getMessage() != null ? throwable.getMessage() : "";

    // Transient failures (worth retrying)
    if (message.contains("Connection timed out")
        || message.contains("timeout")
        || message.contains("temporarily unavailable")
        || message.contains("503")
        || message.contains("Mail server is busy")
        || message.contains("SMTP")
        || throwable.getCause() instanceof java.net.SocketTimeoutException
        || throwable.getCause() instanceof java.net.ConnectException) {
      return true;
    }

    // Non-transient failures (don't retry)
    if (message.contains("Invalid email")
        || message.contains("550")
        || message.contains("Authentication failed")
        || message.contains("Invalid credentials")) {
      return false;
    }

    // Default: retry unknown mail exceptions
    return true;
  }

  /** Checks if a throwable is a mail-related exception. */
  private boolean isMailException(Throwable throwable) {
    return throwable instanceof MailException;
  }
}
