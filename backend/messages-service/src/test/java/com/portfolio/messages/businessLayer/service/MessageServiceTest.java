package com.portfolio.messages.businessLayer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.portfolio.messages.dataAccessLayer.entity.Message;
import com.portfolio.messages.dataAccessLayer.repository.MessageRepository;
import com.portfolio.messages.mappingLayer.dto.MessageDTO;
import com.portfolio.messages.mappingLayer.mapper.MessageMapper;
import com.portfolio.messages.utils.exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageService Unit Tests")
class MessageServiceTest {

  @Mock private MessageRepository messageRepository;
  @Mock private MessageMapper messageMapper;
  @Mock private EmailService emailService;
  private MessageService messageService;

  private Message testMessage;
  private MessageDTO testMessageDTO;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    testMessage =
        Message.builder()
            .id(1L)
            .senderName("John Doe")
            .senderEmail("john@example.com")
            .subject("Hello")
            .message("Test message")
            .isRead(false)
            .build();

    testMessageDTO =
        MessageDTO.builder()
            .id(1L)
            .senderName("John Doe")
            .senderEmail("john@example.com")
            .subject("Hello")
            .message("Test message")
            .isRead(false)
            .build();

    // Inject mocks into MessageService, including Optional<EmailService>
    messageService =
        new MessageService(messageRepository, messageMapper, Optional.of(emailService));
  }

  @Test
  @DisplayName("Should retrieve all messages ordered by createdAt")
  void testGetAllMessages_ReturnsAllMessages() {
    // Arrange: Setup mocks
    when(messageRepository.findByOrderByCreatedAtDesc()).thenReturn(Arrays.asList(testMessage));
    when(messageMapper.toDTO(testMessage)).thenReturn(testMessageDTO);

    // Act: Call service method
    List<MessageDTO> result = messageService.getAllMessages();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Should retrieve message by ID")
  void testGetMessageById_WithValidId_ReturnsMessage() {
    // Arrange: Setup mocks
    when(messageRepository.findById(1L)).thenReturn(Optional.of(testMessage));
    when(messageMapper.toDTO(testMessage)).thenReturn(testMessageDTO);

    // Act: Call service method
    MessageDTO result = messageService.getMessageById(1L);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(testMessageDTO, result);
  }

  @Test
  @DisplayName("Should throw exception when message ID not found")
  void testGetMessageById_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(messageRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception
    assertThrows(ResourceNotFoundException.class, () -> messageService.getMessageById(999L));
  }

  @Test
  @DisplayName("Should retrieve messages by read status")
  void testGetMessagesByReadStatus_ReturnsMessages() {
    // Arrange: Setup mocks
    when(messageRepository.findByIsRead(false)).thenReturn(Arrays.asList(testMessage));
    when(messageMapper.toDTO(testMessage)).thenReturn(testMessageDTO);

    // Act: Call service method
    List<MessageDTO> result = messageService.getMessagesByReadStatus(false);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Should create message successfully")
  void testCreateMessage_WithValidDTO_CreatesMessage() {
    // Arrange: Setup mocks
    MessageDTO newMessageDTO =
        MessageDTO.builder()
            .senderName("Jane Doe")
            .senderEmail("jane@example.com")
            .subject("Hi")
            .message("New message")
            .isRead(false)
            .build();

    Message newMessage =
        Message.builder()
            .senderName("Jane Doe")
            .senderEmail("jane@example.com")
            .subject("Hi")
            .message("New message")
            .isRead(false)
            .build();

    Message savedMessage =
        Message.builder()
            .id(2L)
            .senderName("Jane Doe")
            .senderEmail("jane@example.com")
            .subject("Hi")
            .message("New message")
            .isRead(false)
            .build();

    when(messageMapper.toEntity(newMessageDTO)).thenReturn(newMessage);
    when(messageRepository.save(newMessage)).thenReturn(savedMessage);
    MessageDTO savedDTO =
        MessageDTO.builder()
            .id(2L)
            .senderName("Jane Doe")
            .senderEmail("jane@example.com")
            .subject("Hi")
            .message("New message")
            .isRead(false)
            .build();
    when(messageMapper.toDTO(savedMessage)).thenReturn(savedDTO);

    // Act: Call service method
    MessageDTO result = messageService.createMessage(newMessageDTO);

    // Assert: Verify results
    assertNotNull(result);
    assertNotNull(result.getId());
  }

  @Test
  @DisplayName("Should update message successfully")
  void testUpdateMessage_WithValidIdAndDTO_UpdatesMessage() {
    // Arrange: Setup mocks
    MessageDTO updateDTO =
        MessageDTO.builder()
            .senderName("Updated Name")
            .senderEmail("updated@example.com")
            .subject("Updated")
            .message("Updated message")
            .isRead(true)
            .build();

    when(messageRepository.findById(1L)).thenReturn(Optional.of(testMessage));
    when(messageRepository.save(any(Message.class))).thenReturn(testMessage);
    when(messageMapper.toDTO(testMessage)).thenReturn(testMessageDTO);

    // Act: Call service method
    MessageDTO result = messageService.updateMessage(1L, updateDTO);

    // Assert: Verify results
    assertNotNull(result);
    verify(messageRepository, times(1)).findById(1L);
    verify(messageRepository, times(1)).save(any(Message.class));
  }

  @Test
  @DisplayName("Should mark message as read")
  void testMarkAsRead_WithValidId_MarksAsRead() {
    // Arrange: Setup mocks
    when(messageRepository.findById(1L)).thenReturn(Optional.of(testMessage));
    when(messageRepository.save(any(Message.class))).thenReturn(testMessage);
    when(messageMapper.toDTO(testMessage)).thenReturn(testMessageDTO);

    // Act: Call service method
    MessageDTO result = messageService.markAsRead(1L);

    // Assert: Verify results
    assertNotNull(result);
    verify(messageRepository, times(1)).findById(1L);
    verify(messageRepository, times(1)).save(any(Message.class));
  }

  @Test
  @DisplayName("Should delete message successfully")
  void testDeleteMessage_WithValidId_DeletesMessage() {
    // Arrange: Setup mocks
    when(messageRepository.existsById(1L)).thenReturn(true);
    doNothing().when(messageRepository).deleteById(1L);

    // Act: Call service method
    messageService.deleteMessage(1L);

    // Assert: Verify deletion
    verify(messageRepository, times(1)).existsById(1L);
    verify(messageRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Should throw exception when deleting non-existent message")
  void testDeleteMessage_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(messageRepository.existsById(999L)).thenReturn(false);

    // Act & Assert: Verify exception
    assertThrows(ResourceNotFoundException.class, () -> messageService.deleteMessage(999L));
  }
}
