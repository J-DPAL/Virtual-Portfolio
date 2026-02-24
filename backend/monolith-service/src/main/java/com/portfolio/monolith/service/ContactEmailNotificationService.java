package com.portfolio.monolith.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.portfolio.monolith.dto.MessageDto;

@Service
public class ContactEmailNotificationService {

  private static final Logger log = LoggerFactory.getLogger(ContactEmailNotificationService.class);

  private final JavaMailSender mailSender;

  @Value("${app.contact.email.enabled:true}")
  private boolean enabled;

  @Value("${app.contact.email.to:}")
  private String toEmail;

  @Value("${app.contact.email.from:}")
  private String fromEmail;

  @Value("${app.contact.email.reply-to-sender:true}")
  private boolean replyToSender;

  public ContactEmailNotificationService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendNewMessageNotification(MessageDto message) {
    if (!enabled) {
      return;
    }
    if (isBlank(toEmail) || isBlank(fromEmail)) {
      log.warn("Contact email notification is enabled but app.contact.email.to/from is missing.");
      return;
    }

    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(toEmail);
    email.setFrom(fromEmail);
    if (replyToSender && !isBlank(message.senderEmail)) {
      email.setReplyTo(message.senderEmail);
    }
    email.setSubject("[Portfolio] New contact message: " + safe(message.subject));
    email.setText(buildBody(message));

    mailSender.send(email);
  }

  private String buildBody(MessageDto message) {
    StringBuilder sb = new StringBuilder();
    sb.append("You received a new message from your portfolio contact form.\n\n");
    sb.append("Name: ").append(safe(message.senderName)).append('\n');
    sb.append("Email: ").append(safe(message.senderEmail)).append('\n');
    sb.append("Subject: ").append(safe(message.subject)).append("\n\n");
    sb.append("Message:\n").append(safe(message.message)).append("\n\n");
    if (message.createdAt != null) {
      sb.append("Created At: ").append(message.createdAt).append('\n');
    }
    if (message.id != null) {
      sb.append("Message ID: ").append(message.id).append('\n');
    }
    return sb.toString();
  }

  private static String safe(String value) {
    return value == null ? "" : value.trim();
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }
}

