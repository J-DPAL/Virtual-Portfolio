package com.portfolio.hobbies.dataAccessLayer.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.portfolio.hobbies.dataAccessLayer.entity.Hobby;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("HobbyRepository Integration Tests")
class HobbyRepositoryTest {

  @Autowired private HobbyRepository hobbyRepository;

  private Hobby testHobby;

  @BeforeEach
  void setUp() {
    // Arrange: Initialize test data and clear repository
    hobbyRepository.deleteAll();
    testHobby =
        Hobby.builder()
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
  @DisplayName("Should save a new hobby successfully")
  void testSaveHobby_WithValidHobby_ReturnsSavedHobby() {
    // Arrange: Hobby entity is created in setUp

    // Act: Save hobby
    Hobby savedHobby = hobbyRepository.save(testHobby);

    // Assert: Verify hobby is saved with generated ID
    assertNotNull(savedHobby.getId());
    assertEquals("Photography", savedHobby.getNameEn());
  }

  @Test
  @DisplayName("Should find hobby by ID successfully")
  void testFindById_WithExistingHobbyId_ReturnsHobby() {
    // Arrange: Save hobby first
    Hobby savedHobby = hobbyRepository.save(testHobby);

    // Act: Find hobby by ID
    Optional<Hobby> foundHobby = hobbyRepository.findById(savedHobby.getId());

    // Assert: Verify hobby is found
    assertTrue(foundHobby.isPresent());
  }

  @Test
  @DisplayName("Should return empty Optional when hobby ID does not exist")
  void testFindById_WithNonExistentHobbyId_ReturnsEmptyOptional() {
    // Arrange: Hobby not saved

    // Act: Find hobby by ID
    Optional<Hobby> foundHobby = hobbyRepository.findById(999L);

    // Assert: Verify Optional is empty
    assertFalse(foundHobby.isPresent());
  }

  @Test
  @DisplayName("Should update hobby successfully")
  void testUpdateHobby_WithValidChanges_ReturnUpdatedHobby() {
    // Arrange: Save hobby first
    Hobby savedHobby = hobbyRepository.save(testHobby);

    // Act: Update hobby details
    savedHobby.setNameEn("Updated Hobby");
    Hobby updatedHobby = hobbyRepository.save(savedHobby);

    // Assert: Verify update
    assertEquals(savedHobby.getId(), updatedHobby.getId());
    assertEquals("Updated Hobby", updatedHobby.getNameEn());
  }

  @Test
  @DisplayName("Should delete hobby successfully")
  void testDeleteHobby_WithExistingHobby_HobbyIsRemoved() {
    // Arrange: Save hobby first
    Hobby savedHobby = hobbyRepository.save(testHobby);
    Long hobbyId = savedHobby.getId();

    // Act: Delete hobby
    hobbyRepository.deleteById(hobbyId);

    // Assert: Verify deletion
    Optional<Hobby> deletedHobby = hobbyRepository.findById(hobbyId);
    assertFalse(deletedHobby.isPresent());
  }

  @Test
  @DisplayName("Should find all hobbies successfully")
  void testFindAll_WithMultipleHobbies_ReturnsAllHobbies() {
    // Arrange: Save multiple hobbies
    Hobby hobby1 = testHobby;
    Hobby hobby2 = Hobby.builder().nameEn("Travel").nameFr("Voyage").nameEs("Viajar").build();
    hobbyRepository.save(hobby1);
    hobbyRepository.save(hobby2);

    // Act: Retrieve all hobbies
    List<Hobby> allHobbies = hobbyRepository.findAll();

    // Assert: Verify all hobbies are returned
    assertEquals(2, allHobbies.size());
  }
}
