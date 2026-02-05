package com.portfolio.messages.utils.config;

import java.util.Properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

  @Bean
  @ConditionalOnProperty(name = "spring.mail.host")
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    // Mail server properties will be auto-configured from application.yml
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.starttls.required", "true");
    props.put("mail.smtp.connectiontimeout", "5000");
    props.put("mail.smtp.timeout", "3000");
    props.put("mail.smtp.writetimeout", "5000");

    return mailSender;
  }
}
