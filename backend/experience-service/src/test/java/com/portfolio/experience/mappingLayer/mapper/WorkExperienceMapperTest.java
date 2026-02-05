package com.portfolio.experience.mappingLayer.mapper;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;
import com.portfolio.experience.mappingLayer.dto.WorkExperienceDTO;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WorkExperienceMapper Tests")
class WorkExperienceMapperTest {

  private final WorkExperienceMapper mapper = new WorkExperienceMapper(new ModelMapper());

  @Test
  @DisplayName("Should map entity to DTO")
  void testToDto() {
    WorkExperience entity = buildEntity();

    WorkExperienceDTO dto = mapper.toDTO(entity);

    assertNotNull(dto);
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getCompanyNameEn(), dto.getCompanyNameEn());
    assertEquals(entity.getCompanyNameFr(), dto.getCompanyNameFr());
    assertEquals(entity.getCompanyNameEs(), dto.getCompanyNameEs());
    assertEquals(entity.getPositionEn(), dto.getPositionEn());
    assertEquals(entity.getPositionFr(), dto.getPositionFr());
    assertEquals(entity.getPositionEs(), dto.getPositionEs());
    assertEquals(entity.getDescriptionEn(), dto.getDescriptionEn());
    assertEquals(entity.getDescriptionFr(), dto.getDescriptionFr());
    assertEquals(entity.getDescriptionEs(), dto.getDescriptionEs());
    assertEquals(entity.getLocationEn(), dto.getLocationEn());
    assertEquals(entity.getLocationFr(), dto.getLocationFr());
    assertEquals(entity.getLocationEs(), dto.getLocationEs());
    assertEquals(entity.getStartDate(), dto.getStartDate());
    assertEquals(entity.getEndDate(), dto.getEndDate());
    assertEquals(entity.getIsCurrent(), dto.getIsCurrent());
    assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
    assertEquals(entity.getUpdatedAt(), dto.getUpdatedAt());
  }

  @Test
  @DisplayName("Should map DTO to entity")
  void testToEntity() {
    WorkExperienceDTO dto = buildDto();

    WorkExperience entity = mapper.toEntity(dto);

    assertNotNull(entity);
    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getCompanyNameEn(), entity.getCompanyNameEn());
    assertEquals(dto.getCompanyNameFr(), entity.getCompanyNameFr());
    assertEquals(dto.getCompanyNameEs(), entity.getCompanyNameEs());
    assertEquals(dto.getPositionEn(), entity.getPositionEn());
    assertEquals(dto.getPositionFr(), entity.getPositionFr());
    assertEquals(dto.getPositionEs(), entity.getPositionEs());
    assertEquals(dto.getDescriptionEn(), entity.getDescriptionEn());
    assertEquals(dto.getDescriptionFr(), entity.getDescriptionFr());
    assertEquals(dto.getDescriptionEs(), entity.getDescriptionEs());
    assertEquals(dto.getLocationEn(), entity.getLocationEn());
    assertEquals(dto.getLocationFr(), entity.getLocationFr());
    assertEquals(dto.getLocationEs(), entity.getLocationEs());
    assertEquals(dto.getStartDate(), entity.getStartDate());
    assertEquals(dto.getEndDate(), entity.getEndDate());
    assertEquals(dto.getIsCurrent(), entity.getIsCurrent());
    assertEquals(dto.getCreatedAt(), entity.getCreatedAt());
    assertEquals(dto.getUpdatedAt(), entity.getUpdatedAt());
  }

  @Test
  @DisplayName("Should handle null optional fields")
  void testNullOptionalFieldsMapping() {
    WorkExperience entity =
        WorkExperience.builder()
            .id(3L)
            .companyNameEn("Company EN")
            .companyNameFr("Company FR")
            .companyNameEs("Company ES")
            .positionEn("Position EN")
            .positionFr("Position FR")
            .positionEs("Position ES")
            .startDate(LocalDate.of(2021, 1, 1))
            .isCurrent(false)
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .locationEn(null)
            .locationFr(null)
            .locationEs(null)
            .endDate(null)
            .build();

    WorkExperienceDTO dto = mapper.toDTO(entity);

    assertNotNull(dto);
    assertNull(dto.getDescriptionEn());
    assertNull(dto.getDescriptionFr());
    assertNull(dto.getDescriptionEs());
    assertNull(dto.getLocationEn());
    assertNull(dto.getLocationFr());
    assertNull(dto.getLocationEs());
    assertNull(dto.getEndDate());
  }

  @Test
  @DisplayName("Should preserve values in round-trip mapping")
  void testRoundTripMapping() {
    WorkExperience entity = buildEntity();

    WorkExperienceDTO dto = mapper.toDTO(entity);
    WorkExperience result = mapper.toEntity(dto);

    assertEquals(entity.getCompanyNameEn(), result.getCompanyNameEn());
    assertEquals(entity.getCompanyNameFr(), result.getCompanyNameFr());
    assertEquals(entity.getCompanyNameEs(), result.getCompanyNameEs());
    assertEquals(entity.getPositionEn(), result.getPositionEn());
    assertEquals(entity.getPositionFr(), result.getPositionFr());
    assertEquals(entity.getPositionEs(), result.getPositionEs());
    assertEquals(entity.getStartDate(), result.getStartDate());
    assertEquals(entity.getIsCurrent(), result.getIsCurrent());
  }

  private WorkExperience buildEntity() {
    return WorkExperience.builder()
        .id(1L)
        .companyNameEn("Company EN")
        .companyNameFr("Company FR")
        .companyNameEs("Company ES")
        .positionEn("Position EN")
        .positionFr("Position FR")
        .positionEs("Position ES")
        .descriptionEn("Description EN")
        .descriptionFr("Description FR")
        .descriptionEs("Description ES")
        .locationEn("Location EN")
        .locationFr("Location FR")
        .locationEs("Location ES")
        .startDate(LocalDate.of(2021, 1, 1))
        .endDate(LocalDate.of(2022, 1, 1))
        .isCurrent(false)
        .build();
  }

  private WorkExperienceDTO buildDto() {
    return WorkExperienceDTO.builder()
        .id(2L)
        .companyNameEn("Company EN")
        .companyNameFr("Company FR")
        .companyNameEs("Company ES")
        .positionEn("Position EN")
        .positionFr("Position FR")
        .positionEs("Position ES")
        .descriptionEn("Description EN")
        .descriptionFr("Description FR")
        .descriptionEs("Description ES")
        .locationEn("Location EN")
        .locationFr("Location FR")
        .locationEs("Location ES")
        .startDate(LocalDate.of(2021, 1, 1))
        .endDate(LocalDate.of(2022, 1, 1))
        .isCurrent(false)
        .build();
  }
}
