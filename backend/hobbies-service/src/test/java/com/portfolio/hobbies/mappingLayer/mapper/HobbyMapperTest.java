package com.portfolio.hobbies.mappingLayer.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.portfolio.hobbies.dataAccessLayer.entity.Hobby;
import com.portfolio.hobbies.mappingLayer.dto.HobbyDTO;

import static org.junit.jupiter.api.Assertions.*;

class HobbyMapperTest {

  private HobbyMapper hobbyMapper;
  private ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    modelMapper = new ModelMapper();
    hobbyMapper = new HobbyMapper(modelMapper);
  }

  @Test
  void testToDTO() {
    LocalDateTime now = LocalDateTime.now();
    Hobby hobby =
        Hobby.builder()
            .id(1L)
            .nameEn("Reading")
            .nameFr("Lecture")
            .nameEs("Lectura")
            .descriptionEn("Reading books")
            .descriptionFr("Lire des livres")
            .descriptionEs("Leer libros")
            .icon("book.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    HobbyDTO dto = hobbyMapper.toDTO(hobby);

    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("Reading", dto.getNameEn());
    assertEquals("Lecture", dto.getNameFr());
    assertEquals("Lectura", dto.getNameEs());
    assertEquals("Reading books", dto.getDescriptionEn());
    assertEquals("Lire des livres", dto.getDescriptionFr());
    assertEquals("Leer libros", dto.getDescriptionEs());
    assertEquals("book.png", dto.getIcon());
  }

  @Test
  void testToEntity() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO dto =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Gaming")
            .nameFr("Jeux")
            .nameEs("Juegos")
            .descriptionEn("Playing video games")
            .descriptionFr("Jouer à des jeux vidéo")
            .descriptionEs("Jugar videojuegos")
            .icon("game.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    Hobby hobby = hobbyMapper.toEntity(dto);

    assertNotNull(hobby);
    assertEquals(1L, hobby.getId());
    assertEquals("Gaming", hobby.getNameEn());
    assertEquals("Jeux", hobby.getNameFr());
    assertEquals("Juegos", hobby.getNameEs());
    assertEquals("Playing video games", hobby.getDescriptionEn());
    assertEquals("Jouer à des jeux vidéo", hobby.getDescriptionFr());
    assertEquals("Jugar videojuegos", hobby.getDescriptionEs());
    assertEquals("game.png", hobby.getIcon());
  }

  @Test
  void testNullOptionalFieldsMapping() {
    Hobby hobby =
        Hobby.builder()
            .id(2L)
            .nameEn("Cooking")
            .nameFr("Cuisine")
            .nameEs("Cocina")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .icon(null)
            .build();

    HobbyDTO dto = hobbyMapper.toDTO(hobby);

    assertNotNull(dto);
    assertNull(dto.getDescriptionEn());
    assertNull(dto.getDescriptionFr());
    assertNull(dto.getDescriptionEs());
    assertNull(dto.getIcon());
    assertEquals("Cooking", dto.getNameEn());
  }

  @Test
  void testRoundTripMapping() {
    LocalDateTime now = LocalDateTime.now();
    Hobby originalHobby =
        Hobby.builder()
            .id(3L)
            .nameEn("Sports")
            .nameFr("Sports")
            .nameEs("Deportes")
            .descriptionEn("Physical activities")
            .descriptionFr("Activités physiques")
            .descriptionEs("Actividades físicas")
            .icon("sports.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    HobbyDTO dto = hobbyMapper.toDTO(originalHobby);
    Hobby mappedHobby = hobbyMapper.toEntity(dto);

    assertEquals(originalHobby.getId(), mappedHobby.getId());
    assertEquals(originalHobby.getNameEn(), mappedHobby.getNameEn());
    assertEquals(originalHobby.getNameFr(), mappedHobby.getNameFr());
    assertEquals(originalHobby.getNameEs(), mappedHobby.getNameEs());
    assertEquals(originalHobby.getDescriptionEn(), mappedHobby.getDescriptionEn());
    assertEquals(originalHobby.getIcon(), mappedHobby.getIcon());
  }

  @Test
  void testMapperPreservesAllFields() {
    LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 10, 0, 0);
    LocalDateTime updatedAt = LocalDateTime.of(2024, 12, 31, 18, 30, 0);

    Hobby hobby =
        Hobby.builder()
            .id(4L)
            .nameEn("Painting")
            .nameFr("Peinture")
            .nameEs("Pintura")
            .descriptionEn("Art painting")
            .descriptionFr("Peinture artistique")
            .descriptionEs("Pintura artística")
            .icon("paint.png")
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();

    HobbyDTO dto = hobbyMapper.toDTO(hobby);

    assertEquals("Painting", dto.getNameEn());
    assertEquals("Peinture", dto.getNameFr());
    assertEquals("Pintura", dto.getNameEs());
    assertEquals("Art painting", dto.getDescriptionEn());
    assertEquals("paint.png", dto.getIcon());
  }
}
