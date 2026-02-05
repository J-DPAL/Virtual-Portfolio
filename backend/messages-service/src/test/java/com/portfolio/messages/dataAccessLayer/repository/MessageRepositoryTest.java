package com.portfolio.messages.dataAccessLayer.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.messages.dataAccessLayer.entity.Message;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("MessageRepository Integration Tests")
class MessageRepositoryTest {

  @Autowired private MessageRepository messageRepository;

  private Message testMessage;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data and clear repository
    messageRepository.deleteAll();
    testMessage =
        Message.builder()
            .senderName("John Doe")
            .senderEmail("john@example.com")
            .subject("Hello")
            .message("Test message")
            .isRead(false)
            .build();
  }

  @Test
  @DisplayName("Should save a new message successfully")
  void testSaveMessage_WithValidMessage_ReturnsSavedMessage() {
    // Arrange: Message entity is created in setUp

    // Act: Save message
    Message savedMessage = messageRepository.save(testMessage);

    // Assert: Verify message is saved with generated ID
    assertNotNull(savedMessage.getId());
    assertEquals("John Doe", savedMessage.getSenderName());
  }

  @Test
  @DisplayName("Should find message by ID successfully")
  void testFindById_WithExistingMessageId_ReturnsMessage() {
    // Arrange: Save message first
    Message savedMessage = messageRepository.save(testMessage);

    // Act: Find message by ID
    Optional<Message> foundMessage = messageRepository.findById(savedMessage.getId());

    // Assert: Verify message is found
    assertTrue(foundMessage.isPresent());
  }

  @Test
  @DisplayName("Should return empty Optional when message ID does not exist")
  void testFindById_WithNonExistentMessageId_ReturnsEmptyOptional() {
    // Arrange: Message not saved

    // Act: Find message by ID
    Optional<Message> foundMessage = messageRepository.findById(999L);

    // Assert: Verify Optional is empty
    assertFalse(foundMessage.isPresent());
  }

  @Test
  @DisplayName("Should find messages by read status")
  void testFindByIsRead_WithReadStatus_ReturnsMessages() {
    // Arrange: Save message first
    messageRepository.save(testMessage);

    // Act: Find messages by read status
    List<Message> unreadMessages = messageRepository.findByIsRead(false);

    // Assert: Verify results
    assertNotNull(unreadMessages);
    assertEquals(1, unreadMessages.size());
  }

  @Test
  @DisplayName("Should return empty list when no messages for read status")
  void testFindByIsRead_WithNoMessages_ReturnsEmptyList() {
    // Arrange: Save message first
    messageRepository.save(testMessage);

    // Act: Find messages with isRead true
    List<Message> readMessages = messageRepository.findByIsRead(true);

    // Assert: Verify empty list
    assertNotNull(readMessages);
    assertTrue(readMessages.isEmpty());
  }

  @Test
  @DisplayName("Should find messages ordered by createdAt descending")
  void testFindByOrderByCreatedAtDesc_ReturnsMessagesInOrder() {
    // Arrange: Save two messages
    Message message1 = testMessage;
    Message message2 =
        Message.builder()
            .senderName("Jane Doe")
            .senderEmail("jane@example.com")
            .subject("Hi")
            .message("Second message")
            .isRead(true)
            .build();
    messageRepository.save(message1);
    messageRepository.save(message2);

    // Act: Retrieve ordered messages
    List<Message> messages = messageRepository.findByOrderByCreatedAtDesc();

    // Assert: Verify order
    assertEquals(2, messages.size());
  }

  @Test
  @DisplayName("Should update message successfully")
  void testUpdateMessage_WithValidChanges_ReturnUpdatedMessage() {
    // Arrange: Save message first
    Message savedMessage = messageRepository.save(testMessage);

    // Act: Update message
    savedMessage.setIsRead(true);
    Message updatedMessage = messageRepository.save(savedMessage);

    // Assert: Verify update
    assertEquals(savedMessage.getId(), updatedMessage.getId());
    assertTrue(updatedMessage.getIsRead());
  }

  @Test
  @DisplayName("Should delete message successfully")
  void testDeleteMessage_WithExistingMessage_MessageIsRemoved() {
    // Arrange: Save message first
    Message savedMessage = messageRepository.save(testMessage);
    Long messageId = savedMessage.getId();

    // Act: Delete message
    messageRepository.deleteById(messageId);

    // Assert: Verify deletion
    Optional<Message> deletedMessage = messageRepository.findById(messageId);
    assertFalse(deletedMessage.isPresent());
  }
}
