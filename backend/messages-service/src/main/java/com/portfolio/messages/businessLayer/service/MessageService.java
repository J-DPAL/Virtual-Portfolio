package com.portfolio.messages.businessLayer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.messages.dataAccessLayer.entity.Message;
import com.portfolio.messages.dataAccessLayer.repository.MessageRepository;
import com.portfolio.messages.mappingLayer.dto.MessageDTO;
import com.portfolio.messages.mappingLayer.mapper.MessageMapper;
import com.portfolio.messages.utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
@SuppressWarnings("null")
public class MessageService {

  private final MessageRepository messageRepository;
  private final MessageMapper messageMapper;

  public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
    this.messageRepository = messageRepository;
    this.messageMapper = messageMapper;
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

  public MessageDTO createMessage(MessageDTO messageDTO) {
    Message message = messageMapper.toEntity(messageDTO);
    Message savedMessage = messageRepository.save(message);
    return messageMapper.toDTO(savedMessage);
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
}
