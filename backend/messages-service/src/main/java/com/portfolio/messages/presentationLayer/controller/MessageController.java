package com.portfolio.messages.presentationLayer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.portfolio.messages.businessLayer.service.MessageService;
import com.portfolio.messages.mappingLayer.dto.MessageDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @GetMapping
  public ResponseEntity<List<MessageDTO>> getAllMessages() {
    List<MessageDTO> messages = messageService.getAllMessages();
    return ResponseEntity.ok(messages);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MessageDTO> getMessageById(@PathVariable Long id) {
    MessageDTO message = messageService.getMessageById(id);
    return ResponseEntity.ok(message);
  }

  @GetMapping("/read/{isRead}")
  public ResponseEntity<List<MessageDTO>> getMessagesByReadStatus(@PathVariable Boolean isRead) {
    List<MessageDTO> messages = messageService.getMessagesByReadStatus(isRead);
    return ResponseEntity.ok(messages);
  }

  @PostMapping
  public ResponseEntity<MessageDTO> createMessage(
      @Valid @RequestBody MessageDTO messageDTO, HttpServletRequest request) {
    MessageDTO createdMessage =
        messageService.createMessage(
            messageDTO, resolveClientIp(request), request.getHeader("User-Agent"));
    return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<MessageDTO> updateMessage(
      @PathVariable Long id, @Valid @RequestBody MessageDTO messageDTO) {
    MessageDTO updatedMessage = messageService.updateMessage(id, messageDTO);
    return ResponseEntity.ok(updatedMessage);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}/mark-read")
  public ResponseEntity<MessageDTO> markAsRead(@PathVariable Long id) {
    MessageDTO updatedMessage = messageService.markAsRead(id);
    return ResponseEntity.ok(updatedMessage);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
    messageService.deleteMessage(id);
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
