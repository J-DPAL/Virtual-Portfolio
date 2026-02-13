package com.portfolio.messages.businessLayer.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.portfolio.messages.config.ResilienceConfig;
import com.portfolio.messages.mappingLayer.dto.MailNotificationStatus;
import com.portfolio.messages.mappingLayer.dto.MessageDTO;
import com.portfolio.messages.utils.exceptions.MailNotificationException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for sending email notifications with resilience patterns.
 *
 * <p>Implements: - Retry: Up to 3 attempts with exponential backoff (1s, 2s, 4s) - Circuit Breaker:
 * Opens after 5 consecutive failures, waits 30s before recovery
 *
 * <p>Returns detailed status information visible to API consumers.
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "spring.mail.host")
public class EmailService {

  private final JavaMailSender mailSender;
  private final String adminEmail;
  private final Retry mailSendRetry;
  private final CircuitBreaker mailSendCircuitBreaker;

  public EmailService(
      JavaMailSender mailSender,
      @Value("${ADMIN_EMAIL:admin@example.com}") String adminEmail,
      RetryRegistry retryRegistry,
      CircuitBreakerRegistry circuitBreakerRegistry) {
    this.mailSender = mailSender;
    this.adminEmail = adminEmail;
    this.mailSendRetry = retryRegistry.retry(ResilienceConfig.MAIL_RETRY);
    this.mailSendCircuitBreaker =
        circuitBreakerRegistry.circuitBreaker(ResilienceConfig.MAIL_CIRCUIT_BREAKER);
  }

  /**
   * Sends a message notification email with resilience patterns.
   *
   * @param messageDTO Message data
   * @return MailNotificationStatus with detailed status information
   * @throws MailNotificationException if mail fails after retries and circuit breaker engagement
   */
  public MailNotificationStatus sendMessageNotification(MessageDTO messageDTO) {
    try {
      // Wrap mail sending with circuit breaker and retry decorators
      Supplier<Void> sendMail =
          () -> {
            doSendMail(messageDTO);
            return null;
          };

      Supplier<Void> withCircuitBreaker =
          CircuitBreaker.decorateSupplier(mailSendCircuitBreaker, sendMail);
      Supplier<Void> withRetry = Retry.decorateSupplier(mailSendRetry, withCircuitBreaker);

      // Execute with both decorators
      withRetry.get();

      log.info(
          "Email notification successfully sent to admin for message from: {}",
          messageDTO.getSenderName());
      return MailNotificationStatus.success();

    } catch (io.github.resilience4j.circuitbreaker.CallNotPermittedException e) {
      // Circuit breaker is open - service temporarily unavailable
      log.warn("Mail circuit breaker is OPEN - service temporarily unavailable", e);
      MailNotificationStatus status = MailNotificationStatus.circuitBreakerOpen(e.getMessage());
      throw new MailNotificationException(
          "Mail service circuit breaker is open. Will retry later.", "CIRCUIT_BREAKER_OPEN", e);

    } catch (io.github.resilience4j.retry.MaxRetriesExceededException e) {
      // Retries exhausted
      String errorMsg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
      log.error(
          "Failed to send email after {} attempts: {}",
          mailSendRetry.getRetryConfig().getMaxAttempts(),
          errorMsg,
          e);
      throw new MailNotificationException(
          "Failed to send notification after "
              + mailSendRetry.getRetryConfig().getMaxAttempts()
              + " attempts: "
              + errorMsg,
          "RETRY_EXHAUSTED",
          mailSendRetry.getRetryConfig().getMaxAttempts(),
          0);

    } catch (MailException e) {
      // Mail provider error not caught by decorators
      log.error("Mail provider error while sending notification", e);
      throw new MailNotificationException(
          "Mail provider error: " + e.getMessage(), "MAIL_PROVIDER_ERROR", e);

    } catch (Exception e) {
      // Unexpected error
      log.error("Unexpected error while sending mail notification", e);
      throw new MailNotificationException(
          "Unexpected error: " + e.getMessage(), "UNEXPECTED_ERROR", e);
    }
  }

  /**
   * Sends the actual SMTP message (wrapped by resilience decorators). This method is called by
   * resilience decorators and will be retried/circuit-broken.
   */
  private void doSendMail(MessageDTO messageDTO) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(adminEmail);
    mailMessage.setSubject("New Contact Message: " + messageDTO.getSubject());
    mailMessage.setReplyTo(messageDTO.getSenderEmail());

    String emailBody =
        String.format(
            "You have received a new message from your portfolio contact form.\n\n"
                + "From: %s <%s>\n"
                + "Subject: %s\n\n"
                + "Message:\n%s\n\n"
                + "---\n"
                + "This is an automated notification from your Virtual Portfolio application.",
            messageDTO.getSenderName(),
            messageDTO.getSenderEmail(),
            messageDTO.getSubject(),
            messageDTO.getMessage());

    mailMessage.setText(emailBody);

    // This call will be wrapped by retry and circuit breaker
    mailSender.send(mailMessage);
  }
}
