package com.portfolio.monolith.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.monolith.dto.MessageDto;
import com.portfolio.monolith.service.ContactEmailNotificationService;
import com.portfolio.monolith.service.ContactProtectionService;
import com.portfolio.monolith.service.MessageDataService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/messages")
@Validated
public class MessageController {

  private static final Logger log = LoggerFactory.getLogger(MessageController.class);

  private final MessageDataService service;
  private final ContactProtectionService contactProtectionService;
  private final ContactEmailNotificationService contactEmailNotificationService;

  public MessageController(
      MessageDataService service,
      ContactProtectionService contactProtectionService,
      ContactEmailNotificationService contactEmailNotificationService) {
    this.service = service;
    this.contactProtectionService = contactProtectionService;
    this.contactEmailNotificationService = contactEmailNotificationService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<MessageDto>> getAllMessages() {
    return ResponseEntity.ok(service.getAllMessages());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<MessageDto> getMessageById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getMessageById(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/read/{isRead}")
  public ResponseEntity<List<MessageDto>> getMessagesByReadStatus(@PathVariable Boolean isRead) {
    return ResponseEntity.ok(service.getMessagesByReadStatus(isRead));
  }

  @PostMapping
  public ResponseEntity<MessageDto> createMessage(
      @Valid @RequestBody MessageDto dto, HttpServletRequest request) {
    String clientIp = resolveClientIp(request);
    String userAgent = request.getHeader("User-Agent");

    contactProtectionService.validateSubmission(dto, clientIp, userAgent);
    contactProtectionService.sanitize(dto);

    MessageDto created = service.createMessage(dto);
    try {
      contactEmailNotificationService.sendNewMessageNotification(created);
    } catch (Exception ex) {
      log.error("Message saved but email notification failed for message id={}", created.id, ex);
    }
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<MessageDto> updateMessage(@PathVariable Long id, @Valid @RequestBody MessageDto dto) {
    return ResponseEntity.ok(service.updateMessage(id, dto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}/mark-read")
  public ResponseEntity<MessageDto> markAsRead(@PathVariable Long id) {
    return ResponseEntity.ok(service.markMessageAsRead(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
    service.deleteMessage(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Messages service is running");
  }

  private String resolveClientIp(HttpServletRequest request) {
    String forwardedFor = request.getHeader("X-Forwarded-For");
    if (forwardedFor != null && !forwardedFor.isBlank()) {
      return forwardedFor.split(",")[0].trim();
    }
    String realIp = request.getHeader("X-Real-IP");
    if (realIp != null && !realIp.isBlank()) {
      return realIp.trim();
    }
    return request.getRemoteAddr();
  }
}
