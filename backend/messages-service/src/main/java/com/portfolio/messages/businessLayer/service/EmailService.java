package com.portfolio.messages.businessLayer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.portfolio.messages.mappingLayer.dto.MessageDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "spring.mail.host")
public class EmailService {

  private final JavaMailSender mailSender;
  private final String adminEmail;

  public EmailService(
      JavaMailSender mailSender, @Value("${ADMIN_EMAIL:admin@example.com}") String adminEmail) {
    this.mailSender = mailSender;
    this.adminEmail = adminEmail;
  }

  public void sendMessageNotification(MessageDTO messageDTO) {
    try {
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

      mailSender.send(mailMessage);
      log.info("Email notification sent to admin for message from: {}", messageDTO.getSenderName());

    } catch (MailException e) {
      log.error(
          "Failed to send email notification for message from: {}", messageDTO.getSenderName(), e);
      // Don't throw exception - we don't want email failures to break message saving
    }
  }
}
