package com.portfolio.messages.presentationLayer.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.messages.businessLayer.service.MessageService;
import com.portfolio.messages.mappingLayer.dto.MessageDTO;
import com.portfolio.messages.utils.exceptions.ResourceNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("MessageController Integration Tests")
class MessageControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private MessageService messageService;

  private MessageDTO messageDTO;
  private List<MessageDTO> messageList;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    messageDTO =
        MessageDTO.builder()
            .id(1L)
            .senderName("John Doe")
            .senderEmail("john@example.com")
            .subject("Hello")
            .message("Test message")
            .isRead(false)
            .build();

    messageList = Arrays.asList(messageDTO);
  }

  @Test
  @DisplayName("Should retrieve all messages via GET /messages")
  void testGetAllMessages_ReturnsAllMessages() throws Exception {
    // Arrange: Setup mock
    when(messageService.getAllMessages()).thenReturn(messageList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/api/messages").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L));

    verify(messageService, times(1)).getAllMessages();
  }

  @Test
  @DisplayName("Should retrieve message by ID via GET /messages/{id}")
  void testGetMessageById_WithValidId_ReturnsMessage() throws Exception {
    // Arrange: Setup mock
    when(messageService.getMessageById(1L)).thenReturn(messageDTO);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/api/messages/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L));

    verify(messageService, times(1)).getMessageById(1L);
  }

  @Test
  @DisplayName("Should return 404 when message ID does not exist")
  void testGetMessageById_WithInvalidId_ReturnsNotFound() throws Exception {
    // Arrange: Setup mock
    when(messageService.getMessageById(999L))
        .thenThrow(new ResourceNotFoundException("Message not found"));

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/api/messages/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("Should retrieve messages by read status via GET /messages/read/{isRead}")
  void testGetMessagesByReadStatus_ReturnsMessages() throws Exception {
    // Arrange: Setup mock
    when(messageService.getMessagesByReadStatus(false)).thenReturn(messageList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/api/messages/read/false").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L));
  }

  @Test
  @DisplayName("Should create message via POST /messages for admin")
  @WithMockUser(roles = "ADMIN")
  void testCreateMessage_WithValidDTO_ReturnsCreatedMessage() throws Exception {
    // Arrange: Setup mock
    MessageDTO createDTO =
        MessageDTO.builder()
            .senderName("Jane Doe")
            .senderEmail("jane@example.com")
            .subject("Hello")
            .message("New message")
            .isRead(false)
            .build();

    MessageDTO createdDTO =
        MessageDTO.builder()
            .id(2L)
            .senderName("Jane Doe")
            .senderEmail("jane@example.com")
            .subject("Hello")
            .message("New message")
            .isRead(false)
            .build();

    when(messageService.createMessage(
            any(MessageDTO.class), nullable(String.class), nullable(String.class)))
        .thenReturn(createdDTO);

    // Act & Assert: Perform POST request and verify response
    mockMvc
        .perform(
            post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.senderName").value("Jane Doe"));

    verify(messageService, times(1))
        .createMessage(any(MessageDTO.class), nullable(String.class), nullable(String.class));
  }

  @Test
  @DisplayName("Should return 403 when non-admin creates message")
  @WithMockUser(roles = "USER")
  void testCreateMessage_WithUserRole_ReturnsForbidden() throws Exception {
    // Arrange: Create DTO
    MessageDTO createDTO =
        MessageDTO.builder()
            .senderName("Jane Doe")
            .senderEmail("jane@example.com")
            .subject("Hello")
            .message("New message")
            .isRead(false)
            .build();

    // Act & Assert: Perform POST request
    mockMvc
        .perform(
            post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Should update message via PUT /messages/{id} for admin")
  @WithMockUser(roles = "ADMIN")
  void testUpdateMessage_WithValidIdAndDTO_ReturnsUpdatedMessage() throws Exception {
    // Arrange: Setup mock
    MessageDTO updateDTO =
        MessageDTO.builder()
            .senderName("Updated Name")
            .senderEmail("updated@example.com")
            .subject("Updated")
            .message("Updated message")
            .isRead(true)
            .build();

    MessageDTO updatedDTO =
        MessageDTO.builder()
            .id(1L)
            .senderName("Updated Name")
            .senderEmail("john@example.com")
            .subject("Hello")
            .message("Test message")
            .isRead(true)
            .build();

    when(messageService.updateMessage(eq(1L), any(MessageDTO.class))).thenReturn(updatedDTO);

    // Act & Assert: Perform PUT request and verify response
    mockMvc
        .perform(
            put("/api/messages/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.senderName").value("Updated Name"));

    verify(messageService, times(1)).updateMessage(eq(1L), any(MessageDTO.class));
  }

  @Test
  @DisplayName("Should mark message as read via PATCH /messages/{id}/mark-read for admin")
  @WithMockUser(roles = "ADMIN")
  void testMarkAsRead_WithValidId_ReturnsUpdatedMessage() throws Exception {
    // Arrange: Setup mock
    MessageDTO updatedDTO =
        MessageDTO.builder()
            .id(1L)
            .senderName("John Doe")
            .senderEmail("john@example.com")
            .subject("Hello")
            .message("Test message")
            .isRead(true)
            .build();

    when(messageService.markAsRead(1L)).thenReturn(updatedDTO);

    // Act & Assert: Perform PATCH request and verify response
    mockMvc
        .perform(patch("/api/messages/1/mark-read").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isRead").value(true));

    verify(messageService, times(1)).markAsRead(1L);
  }

  @Test
  @DisplayName("Should delete message via DELETE /messages/{id} for admin")
  @WithMockUser(roles = "ADMIN")
  void testDeleteMessage_WithValidId_ReturnsNoContent() throws Exception {
    // Arrange: Setup mock
    doNothing().when(messageService).deleteMessage(1L);

    // Act & Assert: Perform DELETE request and verify response
    mockMvc
        .perform(delete("/api/messages/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(messageService, times(1)).deleteMessage(1L);
  }

  @Test
  @DisplayName("Should return health status via GET /messages/health")
  void testHealthEndpoint_ReturnsOk() throws Exception {
    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/api/messages/health").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Messages service is running"));
  }

  @Test
  @DisplayName("Should return 400 when creating message without required fields")
  @WithMockUser(roles = "ADMIN")
  void testCreateMessage_WithMissingRequiredFields_ReturnsBadRequest() throws Exception {
    // Arrange: Create invalid DTO
    MessageDTO invalidDTO = MessageDTO.builder().senderName("Only name").build();

    // Act & Assert: Perform POST request
    mockMvc
        .perform(
            post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
        .andExpect(status().isBadRequest());
  }
}
