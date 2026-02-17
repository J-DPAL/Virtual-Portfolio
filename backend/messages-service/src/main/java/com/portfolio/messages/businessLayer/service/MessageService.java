package com.portfolio.messages.businessLayer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.messages.dataAccessLayer.entity.Message;
import com.portfolio.messages.dataAccessLayer.repository.MessageRepository;
import com.portfolio.messages.mappingLayer.dto.MessageDTO;
import com.portfolio.messages.mappingLayer.mapper.MessageMapper;
import com.portfolio.messages.utils.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@SuppressWarnings("null")
public class MessageService {

  private final MessageRepository messageRepository;
  private final MessageMapper messageMapper;
  private final Optional<EmailService> emailService;
  private final ContactProtectionService contactProtectionService;

  public MessageService(
      MessageRepository messageRepository,
      MessageMapper messageMapper,
      Optional<EmailService> emailService,
      ContactProtectionService contactProtectionService) {
    this.messageRepository = messageRepository;
    this.messageMapper = messageMapper;
    this.emailService = emailService;
    this.contactProtectionService = contactProtectionService;
  }

  public List<MessageDTO> getAllMessages() {
    return messageRepository.findByOrderByCreatedAtDesc().stream()
        .map(messageMapper::toDTO)
        .collect(Collectors.toList());
  }

  public MessageDTO getMessageById(Long id) {
    Message message =
        messageRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
    return messageMapper.toDTO(message);
  }

  public List<MessageDTO> getMessagesByReadStatus(Boolean isRead) {
    return messageRepository.findByIsRead(isRead).stream()
        .map(messageMapper::toDTO)
        .collect(Collectors.toList());
  }

  public MessageDTO createMessage(MessageDTO messageDTO, String clientIp, String userAgent) {
    contactProtectionService.validateSubmission(messageDTO, clientIp, userAgent);

    MessageDTO sanitizedDTO = sanitizeMessageFields(messageDTO);
    Message message = messageMapper.toEntity(sanitizedDTO);
    Message savedMessage = messageRepository.save(message);
    MessageDTO savedDTO = messageMapper.toDTO(savedMessage);

    emailService.ifPresent(
        service -> {
          try {
            var notificationStatus = service.sendMessageNotification(savedDTO);
            if (notificationStatus.isSuccess()) {
              log.info(
                  "Email notification sent successfully for message id: {}", savedMessage.getId());
            } else {
              log.warn(
                  "Email notification returned non-success status: {}",
                  notificationStatus.getStatus());
            }
          } catch (Exception e) {
            log.warn(
                "Email notification failed, but message was saved successfully. Error: {}",
                e.getMessage(),
                e);
          }
        });

    return savedDTO;
  }

  public MessageDTO updateMessage(Long id, MessageDTO messageDTO) {
    Message existingMessage =
        messageRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));

    existingMessage.setSenderName(messageDTO.getSenderName());
    existingMessage.setSenderEmail(messageDTO.getSenderEmail());
    existingMessage.setSubject(messageDTO.getSubject());
    existingMessage.setMessage(messageDTO.getMessage());
    existingMessage.setIsRead(messageDTO.getIsRead());

    Message updatedMessage = messageRepository.save(existingMessage);
    return messageMapper.toDTO(updatedMessage);
  }

  public MessageDTO markAsRead(Long id) {
    Message message =
        messageRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));

    message.setIsRead(true);
    Message updatedMessage = messageRepository.save(message);
    return messageMapper.toDTO(updatedMessage);
  }

  public void deleteMessage(Long id) {
    if (!messageRepository.existsById(id)) {
      throw new ResourceNotFoundException("Message not found with id: " + id);
    }
    messageRepository.deleteById(id);
  }

  private MessageDTO sanitizeMessageFields(MessageDTO messageDTO) {
    return MessageDTO.builder()
        .id(messageDTO.getId())
        .senderName(contactProtectionService.sanitizeInput(messageDTO.getSenderName()))
        .senderEmail(contactProtectionService.sanitizeInput(messageDTO.getSenderEmail()))
        .subject(contactProtectionService.sanitizeInput(messageDTO.getSubject()))
        .message(contactProtectionService.sanitizeInput(messageDTO.getMessage()))
        .isRead(messageDTO.getIsRead())
        .createdAt(messageDTO.getCreatedAt())
        .updatedAt(messageDTO.getUpdatedAt())
        .mailNotificationStatus(messageDTO.getMailNotificationStatus())
        .build();
  }
}
