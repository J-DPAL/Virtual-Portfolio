package com.portfolio.monolith.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.portfolio.monolith.dto.EducationDto;
import com.portfolio.monolith.exception.NotFoundException;

@Service
public class EducationDataService {

  private final NamedParameterJdbcTemplate jdbc;

  public EducationDataService(NamedParameterJdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<EducationDto> getAllEducation() {
    String sql = "select * from public.education order by start_date desc";
    return jdbc.query(sql, rowMapper());
  }

  public EducationDto getEducationById(Long id) {
    String sql = "select * from public.education where id = :id";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Education not found");
  }

  public List<EducationDto> getCurrentEducation() {
    String sql = "select * from public.education where is_current = true order by start_date desc";
    return jdbc.query(sql, rowMapper());
  }

  public EducationDto createEducation(EducationDto dto) {
    String sql =
        "insert into public.education ("
            + "institution_name_en, institution_name_fr, institution_name_es, "
            + "degree_en, degree_fr, degree_es, "
            + "field_of_study_en, field_of_study_fr, field_of_study_es, "
            + "description_en, description_fr, description_es, "
            + "start_date, end_date, is_current, gpa"
            + ") values ("
            + ":institutionNameEn, :institutionNameFr, :institutionNameEs, "
            + ":degreeEn, :degreeFr, :degreeEs, "
            + ":fieldOfStudyEn, :fieldOfStudyFr, :fieldOfStudyEs, "
            + ":descriptionEn, :descriptionFr, :descriptionEs, "
            + ":startDate, :endDate, :isCurrent, :gpa"
            + ") returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("institutionNameEn", dto.institutionNameEn)
            .addValue("institutionNameFr", dto.institutionNameFr)
            .addValue("institutionNameEs", dto.institutionNameEs)
            .addValue("degreeEn", dto.degreeEn)
            .addValue("degreeFr", dto.degreeFr)
            .addValue("degreeEs", dto.degreeEs)
            .addValue("fieldOfStudyEn", dto.fieldOfStudyEn)
            .addValue("fieldOfStudyFr", dto.fieldOfStudyFr)
            .addValue("fieldOfStudyEs", dto.fieldOfStudyEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("startDate", dto.startDate)
            .addValue("endDate", dto.endDate)
            .addValue("isCurrent", dto.isCurrent)
            .addValue("gpa", dto.gpa);

    return jdbc.queryForObject(sql, p, rowMapper());
  }

  public EducationDto updateEducation(Long id, EducationDto dto) {
    String sql =
        "update public.education set "
            + "institution_name_en = :institutionNameEn, institution_name_fr = :institutionNameFr, institution_name_es = :institutionNameEs, "
            + "degree_en = :degreeEn, degree_fr = :degreeFr, degree_es = :degreeEs, "
            + "field_of_study_en = :fieldOfStudyEn, field_of_study_fr = :fieldOfStudyFr, field_of_study_es = :fieldOfStudyEs, "
            + "description_en = :descriptionEn, description_fr = :descriptionFr, description_es = :descriptionEs, "
            + "start_date = :startDate, end_date = :endDate, is_current = :isCurrent, gpa = :gpa, updated_at = now() "
            + "where id = :id returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("institutionNameEn", dto.institutionNameEn)
            .addValue("institutionNameFr", dto.institutionNameFr)
            .addValue("institutionNameEs", dto.institutionNameEs)
            .addValue("degreeEn", dto.degreeEn)
            .addValue("degreeFr", dto.degreeFr)
            .addValue("degreeEs", dto.degreeEs)
            .addValue("fieldOfStudyEn", dto.fieldOfStudyEn)
            .addValue("fieldOfStudyFr", dto.fieldOfStudyFr)
            .addValue("fieldOfStudyEs", dto.fieldOfStudyEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("startDate", dto.startDate)
            .addValue("endDate", dto.endDate)
            .addValue("isCurrent", dto.isCurrent)
            .addValue("gpa", dto.gpa);

    return queryOne(sql, p, "Education not found");
  }

  public void deleteEducation(Long id) {
    String sql = "delete from public.education where id = :id";
    int rows = jdbc.update(sql, new MapSqlParameterSource("id", id));
    if (rows == 0) {
      throw new NotFoundException("Education not found");
    }
  }

  private EducationDto queryOne(String sql, SqlParameterSource params, String notFoundMessage) {
    try {
      return jdbc.queryForObject(sql, params, rowMapper());
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException(notFoundMessage);
    }
  }

  private RowMapper<EducationDto> rowMapper() {
    return (rs, rowNum) -> {
      EducationDto dto = new EducationDto();
      dto.id = rs.getLong("id");
      dto.institutionNameEn = rs.getString("institution_name_en");
      dto.institutionNameFr = rs.getString("institution_name_fr");
      dto.institutionNameEs = rs.getString("institution_name_es");
      dto.degreeEn = rs.getString("degree_en");
      dto.degreeFr = rs.getString("degree_fr");
      dto.degreeEs = rs.getString("degree_es");
      dto.fieldOfStudyEn = rs.getString("field_of_study_en");
      dto.fieldOfStudyFr = rs.getString("field_of_study_fr");
      dto.fieldOfStudyEs = rs.getString("field_of_study_es");
      dto.descriptionEn = rs.getString("description_en");
      dto.descriptionFr = rs.getString("description_fr");
      dto.descriptionEs = rs.getString("description_es");
      dto.startDate = rs.getObject("start_date", LocalDate.class);
      dto.endDate = rs.getObject("end_date", LocalDate.class);
      dto.isCurrent = (Boolean) rs.getObject("is_current");
      dto.gpa = rs.getBigDecimal("gpa");
      dto.createdAt = rs.getObject("created_at", LocalDateTime.class);
      dto.updatedAt = rs.getObject("updated_at", LocalDateTime.class);
      return dto;
    };
  }
}
