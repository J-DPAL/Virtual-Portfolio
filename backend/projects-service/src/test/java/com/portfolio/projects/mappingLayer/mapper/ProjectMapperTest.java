package com.portfolio.projects.mappingLayer.mapper;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.portfolio.projects.dataAccessLayer.entity.Project;
import com.portfolio.projects.mappingLayer.dto.ProjectDTO;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProjectMapper Tests")
class ProjectMapperTest {

  private final ProjectMapper mapper = new ProjectMapper(new ModelMapper());

  @Test
  @DisplayName("Should map entity to DTO")
  void testToDto() {
    Project entity = buildEntity();

    ProjectDTO dto = mapper.toDTO(entity);

    assertNotNull(dto);
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getTitleEn(), dto.getTitleEn());
    assertEquals(entity.getTitleFr(), dto.getTitleFr());
    assertEquals(entity.getTitleEs(), dto.getTitleEs());
    assertEquals(entity.getDescriptionEn(), dto.getDescriptionEn());
    assertEquals(entity.getDescriptionFr(), dto.getDescriptionFr());
    assertEquals(entity.getDescriptionEs(), dto.getDescriptionEs());
    assertEquals(entity.getTechnologies(), dto.getTechnologies());
    assertEquals(entity.getProjectUrl(), dto.getProjectUrl());
    assertEquals(entity.getGithubUrl(), dto.getGithubUrl());
    assertEquals(entity.getImageUrl(), dto.getImageUrl());
    assertEquals(entity.getStartDate(), dto.getStartDate());
    assertEquals(entity.getEndDate(), dto.getEndDate());
    assertEquals(entity.getStatus(), dto.getStatus());
    assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
    assertEquals(entity.getUpdatedAt(), dto.getUpdatedAt());
  }

  @Test
  @DisplayName("Should map DTO to entity")
  void testToEntity() {
    ProjectDTO dto = buildDto();

    Project entity = mapper.toEntity(dto);

    assertNotNull(entity);
    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getTitleEn(), entity.getTitleEn());
    assertEquals(dto.getTitleFr(), entity.getTitleFr());
    assertEquals(dto.getTitleEs(), entity.getTitleEs());
    assertEquals(dto.getDescriptionEn(), entity.getDescriptionEn());
    assertEquals(dto.getDescriptionFr(), entity.getDescriptionFr());
    assertEquals(dto.getDescriptionEs(), entity.getDescriptionEs());
    assertEquals(dto.getTechnologies(), entity.getTechnologies());
    assertEquals(dto.getProjectUrl(), entity.getProjectUrl());
    assertEquals(dto.getGithubUrl(), entity.getGithubUrl());
    assertEquals(dto.getImageUrl(), entity.getImageUrl());
    assertEquals(dto.getStartDate(), entity.getStartDate());
    assertEquals(dto.getEndDate(), entity.getEndDate());
    assertEquals(dto.getStatus(), entity.getStatus());
    assertEquals(dto.getCreatedAt(), entity.getCreatedAt());
    assertEquals(dto.getUpdatedAt(), entity.getUpdatedAt());
  }

  @Test
  @DisplayName("Should handle null optional fields")
  void testNullOptionalFieldsMapping() {
    Project entity =
        Project.builder()
            .id(3L)
            .titleEn("Title EN")
            .titleFr("Title FR")
            .titleEs("Title ES")
            .status("Active")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .technologies(null)
            .projectUrl(null)
            .githubUrl(null)
            .imageUrl(null)
            .startDate(null)
            .endDate(null)
            .build();

    ProjectDTO dto = mapper.toDTO(entity);

    assertNotNull(dto);
    assertNull(dto.getDescriptionEn());
    assertNull(dto.getDescriptionFr());
    assertNull(dto.getDescriptionEs());
    assertNull(dto.getTechnologies());
    assertNull(dto.getProjectUrl());
    assertNull(dto.getGithubUrl());
    assertNull(dto.getImageUrl());
    assertNull(dto.getStartDate());
    assertNull(dto.getEndDate());
  }

  @Test
  @DisplayName("Should preserve values in round-trip mapping")
  void testRoundTripMapping() {
    Project entity = buildEntity();

    ProjectDTO dto = mapper.toDTO(entity);
    Project result = mapper.toEntity(dto);

    assertEquals(entity.getTitleEn(), result.getTitleEn());
    assertEquals(entity.getTitleFr(), result.getTitleFr());
    assertEquals(entity.getTitleEs(), result.getTitleEs());
    assertEquals(entity.getStatus(), result.getStatus());
    assertEquals(entity.getTechnologies(), result.getTechnologies());
  }

  private Project buildEntity() {
    return Project.builder()
        .id(1L)
        .titleEn("Title EN")
        .titleFr("Title FR")
        .titleEs("Title ES")
        .descriptionEn("Desc EN")
        .descriptionFr("Desc FR")
        .descriptionEs("Desc ES")
        .technologies("Java,Spring")
        .projectUrl("https://example.com")
        .githubUrl("https://github.com/example")
        .imageUrl("https://example.com/image.png")
        .startDate(LocalDate.of(2021, 1, 1))
        .endDate(LocalDate.of(2022, 1, 1))
        .status("Completed")
        .build();
  }

  private ProjectDTO buildDto() {
    return ProjectDTO.builder()
        .id(2L)
        .titleEn("Title EN")
        .titleFr("Title FR")
        .titleEs("Title ES")
        .descriptionEn("Desc EN")
        .descriptionFr("Desc FR")
        .descriptionEs("Desc ES")
        .technologies("Java,Spring")
        .projectUrl("https://example.com")
        .githubUrl("https://github.com/example")
        .imageUrl("https://example.com/image.png")
        .startDate(LocalDate.of(2021, 1, 1))
        .endDate(LocalDate.of(2022, 1, 1))
        .status("Completed")
        .build();
  }
}
