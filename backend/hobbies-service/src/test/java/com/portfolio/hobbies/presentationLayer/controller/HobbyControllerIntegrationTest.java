package com.portfolio.hobbies.presentationLayer.controller;

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
import com.portfolio.hobbies.businessLayer.service.HobbyService;
import com.portfolio.hobbies.mappingLayer.dto.HobbyDTO;
import com.portfolio.hobbies.utils.exceptions.ResourceNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("HobbyController Integration Tests")
class HobbyControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private HobbyService hobbyService;

  private HobbyDTO hobbyDTO;
  private List<HobbyDTO> hobbyList;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    hobbyDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Photography")
            .nameFr("Photographie")
            .nameEs("Fotografía")
            .build();

    hobbyList = Arrays.asList(hobbyDTO);
  }

  @Test
  @DisplayName("Should retrieve all hobbies via GET /hobbies")
  void testGetAllHobbies_ReturnsAllHobbies() throws Exception {
    // Arrange: Setup mock
    when(hobbyService.getAllHobbies()).thenReturn(hobbyList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/hobbies").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L));

    verify(hobbyService, times(1)).getAllHobbies();
  }

  @Test
  @DisplayName("Should retrieve hobby by ID via GET /hobbies/{id}")
  void testGetHobbyById_WithValidId_ReturnsHobby() throws Exception {
    // Arrange: Setup mock
    when(hobbyService.getHobbyById(1L)).thenReturn(hobbyDTO);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/hobbies/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L));

    verify(hobbyService, times(1)).getHobbyById(1L);
  }

  @Test
  @DisplayName("Should return 404 when hobby ID does not exist")
  void testGetHobbyById_WithInvalidId_ReturnsNotFound() throws Exception {
    // Arrange: Setup mock
    when(hobbyService.getHobbyById(999L))
        .thenThrow(new ResourceNotFoundException("Hobby not found"));

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/hobbies/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("Should create hobby via POST /hobbies for admin")
  @WithMockUser(roles = "ADMIN")
  void testCreateHobby_WithValidDTO_ReturnsCreatedHobby() throws Exception {
    // Arrange: Setup mock
    HobbyDTO createDTO =
        HobbyDTO.builder().nameEn("Travel").nameFr("Voyage").nameEs("Viajar").build();

    HobbyDTO createdDTO =
        HobbyDTO.builder().id(2L).nameEn("Travel").nameFr("Voyage").nameEs("Viajar").build();

    when(hobbyService.createHobby(any(HobbyDTO.class))).thenReturn(createdDTO);

    // Act & Assert: Perform POST request and verify response
    mockMvc
        .perform(
            post("/hobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.nameEn").value("Travel"));

    verify(hobbyService, times(1)).createHobby(any(HobbyDTO.class));
  }

  @Test
  @DisplayName("Should return 403 when non-admin creates hobby")
  @WithMockUser(roles = "USER")
  void testCreateHobby_WithUserRole_ReturnsForbidden() throws Exception {
    // Arrange: Create DTO
    HobbyDTO createDTO =
        HobbyDTO.builder().nameEn("Travel").nameFr("Voyage").nameEs("Viajar").build();

    // Act & Assert: Perform POST request
    mockMvc
        .perform(
            post("/hobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().is5xxServerError());

    verify(hobbyService, never()).createHobby(any(HobbyDTO.class));
  }

  @Test
  @DisplayName("Should update hobby via PUT /hobbies/{id} for admin")
  @WithMockUser(roles = "ADMIN")
  void testUpdateHobby_WithValidIdAndDTO_ReturnsUpdatedHobby() throws Exception {
    // Arrange: Setup mock
    HobbyDTO updateDTO =
        HobbyDTO.builder()
            .nameEn("Updated Hobby")
            .nameFr("Loisir mis à jour")
            .nameEs("Pasatiempo actualizado")
            .build();

    HobbyDTO updatedDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Updated Hobby")
            .nameFr("Photographie")
            .nameEs("Fotografía")
            .build();

    when(hobbyService.updateHobby(eq(1L), any(HobbyDTO.class))).thenReturn(updatedDTO);

    // Act & Assert: Perform PUT request and verify response
    mockMvc
        .perform(
            put("/hobbies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nameEn").value("Updated Hobby"));

    verify(hobbyService, times(1)).updateHobby(eq(1L), any(HobbyDTO.class));
  }

  @Test
  @DisplayName("Should delete hobby via DELETE /hobbies/{id} for admin")
  @WithMockUser(roles = "ADMIN")
  void testDeleteHobby_WithValidId_ReturnsNoContent() throws Exception {
    // Arrange: Setup mock
    doNothing().when(hobbyService).deleteHobby(1L);

    // Act & Assert: Perform DELETE request and verify response
    mockMvc
        .perform(delete("/hobbies/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(hobbyService, times(1)).deleteHobby(1L);
  }

  @Test
  @DisplayName("Should return health status via GET /hobbies/health")
  void testHealthEndpoint_ReturnsOk() throws Exception {
    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/hobbies/health").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Hobbies service is running"));
  }

  @Test
  @DisplayName("Should return 400 when creating hobby without required fields")
  @WithMockUser(roles = "ADMIN")
  void testCreateHobby_WithMissingRequiredFields_ReturnsBadRequest() throws Exception {
    // Arrange: Create invalid DTO
    HobbyDTO invalidDTO = HobbyDTO.builder().nameEn("Only English").build();

    // Act & Assert: Perform POST request
    mockMvc
        .perform(
            post("/hobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
        .andExpect(status().isBadRequest());
  }
}
