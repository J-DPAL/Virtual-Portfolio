package com.portfolio.skills.presentationLayer.controller;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.skills.businessLayer.service.SkillService;
import com.portfolio.skills.mappingLayer.dto.SkillDTO;
import com.portfolio.skills.utils.exceptions.ResourceNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("SkillController Integration Tests")
class SkillControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private SkillService skillService;

  private SkillDTO skillDTO;
  private List<SkillDTO> skillList;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    skillDTO =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Programming Language")
            .descriptionFr("Langage de programmation")
            .descriptionEs("Lenguaje de programación")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(5)
            .build();

    skillList = Arrays.asList(skillDTO);
  }

  @Test
  @DisplayName("Should retrieve all skills via GET /skills")
  void testGetAllSkills_ReturnsAllSkills() throws Exception {
    // Arrange: Setup mock
    when(skillService.getAllSkills()).thenReturn(skillList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/skills").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].nameEn").value("Java"))
        .andExpect(jsonPath("$[0].category").value("Backend"));

    verify(skillService, times(1)).getAllSkills();
  }

  @Test
  @DisplayName("Should retrieve skill by ID via GET /skills/{id}")
  void testGetSkillById_WithValidId_ReturnsSkill() throws Exception {
    // Arrange: Setup mock
    when(skillService.getSkillById(1L)).thenReturn(skillDTO);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/skills/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.nameEn").value("Java"));

    verify(skillService, times(1)).getSkillById(1L);
  }

  @Test
  @DisplayName("Should return 500 when skill ID does not exist")
  void testGetSkillById_WithInvalidId_ReturnsInternalServerError() throws Exception {
    // Arrange: Setup mock to throw exception
    when(skillService.getSkillById(999L))
        .thenThrow(new ResourceNotFoundException("Skill not found"));

    // Act & Assert: Perform GET request and verify error response
    mockMvc
        .perform(get("/skills/999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());

    verify(skillService, times(1)).getSkillById(999L);
  }

  @Test
  @DisplayName("Should retrieve skills by category via GET /skills/category/{category}")
  void testGetSkillsByCategory_WithValidCategory_ReturnsSkillsByCategory() throws Exception {
    // Arrange: Setup mock
    when(skillService.getSkillsByCategory("Backend")).thenReturn(skillList);

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/skills/category/Backend").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].category").value("Backend"));

    verify(skillService, times(1)).getSkillsByCategory("Backend");
  }

  @Test
  @DisplayName("Should create skill via POST /skills")
  void testCreateSkill_WithValidDTO_ReturnsCreatedSkill() throws Exception {
    // Arrange: Setup mock
    SkillDTO createDTO =
        SkillDTO.builder()
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();
    SkillDTO createdDTO =
        SkillDTO.builder()
            .id(2L)
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .proficiencyLevel("Advanced")
            .category("Backend")
            .yearsOfExperience(3)
            .build();
    when(skillService.createSkill(any(SkillDTO.class))).thenReturn(createdDTO);

    // Act & Assert: Perform POST request and verify response
    mockMvc
        .perform(
            post("/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.nameEn").value("Python"));

    verify(skillService, times(1)).createSkill(any(SkillDTO.class));
  }

  @Test
  @DisplayName("Should update skill via PUT /skills/{id}")
  void testUpdateSkill_WithValidIdAndDTO_ReturnsUpdatedSkill() throws Exception {
    // Arrange: Setup mock
    SkillDTO updateDTO =
        SkillDTO.builder()
            .nameEn("Updated Java")
            .nameFr("Java Mis à Jour")
            .nameEs("Java Actualizado")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();

    SkillDTO updatedDTO =
        SkillDTO.builder()
            .id(1L)
            .nameEn("Updated Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Programming Language")
            .descriptionFr("Langage de programmation")
            .descriptionEs("Lenguaje de programación")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(5)
            .build();
    when(skillService.updateSkill(eq(1L), any(SkillDTO.class))).thenReturn(updatedDTO);

    // Act & Assert: Perform PUT request and verify response
    mockMvc
        .perform(
            put("/skills/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nameEn").value("Updated Java"));

    verify(skillService, times(1)).updateSkill(eq(1L), any(SkillDTO.class));
  }

  @Test
  @DisplayName("Should delete skill via DELETE /skills/{id}")
  void testDeleteSkill_WithValidId_ReturnsNoContent() throws Exception {
    // Arrange: Setup mock
    doNothing().when(skillService).deleteSkill(1L);

    // Act & Assert: Perform DELETE request and verify response
    mockMvc
        .perform(delete("/skills/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(skillService, times(1)).deleteSkill(1L);
  }

  @Test
  @DisplayName("Should return health status via GET /skills/health")
  void testHealthEndpoint_ReturnsOk() throws Exception {
    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/skills/health").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Skills service is running"));
  }

  @Test
  @DisplayName("Should return 400 when creating skill without required fields")
  void testCreateSkill_WithMissingRequiredFields_ReturnsBadRequest() throws Exception {
    // Arrange: Create invalid DTO
    SkillDTO invalidDTO = SkillDTO.builder().nameEn("Java").build();

    // Act & Assert: Perform POST request and verify bad request response
    mockMvc
        .perform(
            post("/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return empty list when no skills exist")
  void testGetAllSkills_WithNoSkills_ReturnsEmptyList() throws Exception {
    // Arrange: Setup mock
    when(skillService.getAllSkills()).thenReturn(Arrays.asList());

    // Act & Assert: Perform GET request and verify response
    mockMvc
        .perform(get("/skills").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(0));
  }
}
