package com.portfolio.hobbies.businessLayer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.portfolio.hobbies.dataAccessLayer.entity.Hobby;
import com.portfolio.hobbies.dataAccessLayer.repository.HobbyRepository;
import com.portfolio.hobbies.mappingLayer.dto.HobbyDTO;
import com.portfolio.hobbies.mappingLayer.mapper.HobbyMapper;
import com.portfolio.hobbies.utils.exceptions.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("HobbyService Unit Tests")
class HobbyServiceTest {

  @Mock private HobbyRepository hobbyRepository;
  @Mock private HobbyMapper hobbyMapper;

  @InjectMocks private HobbyService hobbyService;

  private Hobby testHobby;
  private HobbyDTO testHobbyDTO;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data
    testHobby =
        Hobby.builder()
            .id(1L)
            .nameEn("Photography")
            .nameFr("Photographie")
            .nameEs("Fotografía")
            .descriptionEn("Taking photos")
            .descriptionFr("Prendre des photos")
            .descriptionEs("Tomar fotos")
            .icon("camera")
            .build();

    testHobbyDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Photography")
            .nameFr("Photographie")
            .nameEs("Fotografía")
            .descriptionEn("Taking photos")
            .descriptionFr("Prendre des photos")
            .descriptionEs("Tomar fotos")
            .icon("camera")
            .build();
  }

  @Test
  @DisplayName("Should retrieve all hobbies successfully")
  void testGetAllHobbies_ReturnsAllHobbies() {
    // Arrange: Setup mocks
    List<Hobby> hobbies = Arrays.asList(testHobby);
    when(hobbyRepository.findAll()).thenReturn(hobbies);
    when(hobbyMapper.toDTO(testHobby)).thenReturn(testHobbyDTO);

    // Act: Call service method
    List<HobbyDTO> result = hobbyService.getAllHobbies();

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Should retrieve hobby by ID")
  void testGetHobbyById_WithValidId_ReturnsHobby() {
    // Arrange: Setup mocks
    when(hobbyRepository.findById(1L)).thenReturn(Optional.of(testHobby));
    when(hobbyMapper.toDTO(testHobby)).thenReturn(testHobbyDTO);

    // Act: Call service method
    HobbyDTO result = hobbyService.getHobbyById(1L);

    // Assert: Verify results
    assertNotNull(result);
    assertEquals(testHobbyDTO, result);
  }

  @Test
  @DisplayName("Should throw exception when hobby ID not found")
  void testGetHobbyById_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(hobbyRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception
    assertThrows(ResourceNotFoundException.class, () -> hobbyService.getHobbyById(999L));
  }

  @Test
  @DisplayName("Should create hobby successfully")
  void testCreateHobby_WithValidDTO_CreatesHobby() {
    // Arrange: Setup mocks
    HobbyDTO newHobbyDTO =
        HobbyDTO.builder().nameEn("Travel").nameFr("Voyage").nameEs("Viajar").build();

    Hobby newHobby = Hobby.builder().nameEn("Travel").nameFr("Voyage").nameEs("Viajar").build();

    Hobby savedHobby =
        Hobby.builder().id(2L).nameEn("Travel").nameFr("Voyage").nameEs("Viajar").build();

    when(hobbyMapper.toEntity(newHobbyDTO)).thenReturn(newHobby);
    when(hobbyRepository.save(newHobby)).thenReturn(savedHobby);
    HobbyDTO savedDTO =
        HobbyDTO.builder().id(2L).nameEn("Travel").nameFr("Voyage").nameEs("Viajar").build();
    when(hobbyMapper.toDTO(savedHobby)).thenReturn(savedDTO);

    // Act: Call service method
    HobbyDTO result = hobbyService.createHobby(newHobbyDTO);

    // Assert: Verify results
    assertNotNull(result);
    assertNotNull(result.getId());
  }

  @Test
  @DisplayName("Should update hobby successfully")
  void testUpdateHobby_WithValidIdAndDTO_UpdatesHobby() {
    // Arrange: Setup mocks
    HobbyDTO updateDTO =
        HobbyDTO.builder()
            .nameEn("Updated Hobby")
            .nameFr("Loisir mis à jour")
            .nameEs("Pasatiempo actualizado")
            .build();

    when(hobbyRepository.findById(1L)).thenReturn(Optional.of(testHobby));
    when(hobbyRepository.save(any(Hobby.class))).thenReturn(testHobby);
    when(hobbyMapper.toDTO(testHobby)).thenReturn(testHobbyDTO);

    // Act: Call service method
    HobbyDTO result = hobbyService.updateHobby(1L, updateDTO);

    // Assert: Verify results
    assertNotNull(result);
    verify(hobbyRepository, times(1)).findById(1L);
    verify(hobbyRepository, times(1)).save(any(Hobby.class));
  }

  @Test
  @DisplayName("Should throw exception when updating non-existent hobby")
  void testUpdateHobby_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    HobbyDTO updateDTO = new HobbyDTO();
    when(hobbyRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert: Verify exception
    assertThrows(ResourceNotFoundException.class, () -> hobbyService.updateHobby(999L, updateDTO));
  }

  @Test
  @DisplayName("Should delete hobby successfully")
  void testDeleteHobby_WithValidId_DeletesHobby() {
    // Arrange: Setup mocks
    when(hobbyRepository.existsById(1L)).thenReturn(true);
    doNothing().when(hobbyRepository).deleteById(1L);

    // Act: Call service method
    hobbyService.deleteHobby(1L);

    // Assert: Verify deletion
    verify(hobbyRepository, times(1)).existsById(1L);
    verify(hobbyRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Should throw exception when deleting non-existent hobby")
  void testDeleteHobby_WithInvalidId_ThrowsResourceNotFoundException() {
    // Arrange: Setup mock
    when(hobbyRepository.existsById(999L)).thenReturn(false);

    // Act & Assert: Verify exception
    assertThrows(ResourceNotFoundException.class, () -> hobbyService.deleteHobby(999L));
  }
}
