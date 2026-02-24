package com.portfolio.monolith.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.portfolio.monolith.dto.HobbyDto;
import com.portfolio.monolith.exception.NotFoundException;

@Service
public class HobbyDataService {

  private final NamedParameterJdbcTemplate jdbc;

  public HobbyDataService(NamedParameterJdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<HobbyDto> getAllHobbies() {
    String sql = "select * from public.hobbies order by created_at desc";
    return jdbc.query(sql, rowMapper());
  }

  public HobbyDto getHobbyById(Long id) {
    String sql = "select * from public.hobbies where id = :id";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Hobby not found");
  }

  public HobbyDto createHobby(HobbyDto dto) {
    String sql =
        "insert into public.hobbies ("
            + "name_en, name_fr, name_es, description_en, description_fr, description_es, icon"
            + ") values ("
            + ":nameEn, :nameFr, :nameEs, :descriptionEn, :descriptionFr, :descriptionEs, :icon"
            + ") returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("nameEn", dto.nameEn)
            .addValue("nameFr", dto.nameFr)
            .addValue("nameEs", dto.nameEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("icon", dto.icon);

    return jdbc.queryForObject(sql, p, rowMapper());
  }

  public HobbyDto updateHobby(Long id, HobbyDto dto) {
    String sql =
        "update public.hobbies set "
            + "name_en = :nameEn, name_fr = :nameFr, name_es = :nameEs, "
            + "description_en = :descriptionEn, description_fr = :descriptionFr, description_es = :descriptionEs, "
            + "icon = :icon, updated_at = now() where id = :id returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("nameEn", dto.nameEn)
            .addValue("nameFr", dto.nameFr)
            .addValue("nameEs", dto.nameEs)
            .addValue("descriptionEn", dto.descriptionEn)
            .addValue("descriptionFr", dto.descriptionFr)
            .addValue("descriptionEs", dto.descriptionEs)
            .addValue("icon", dto.icon);

    return queryOne(sql, p, "Hobby not found");
  }

  public void deleteHobby(Long id) {
    String sql = "delete from public.hobbies where id = :id";
    int rows = jdbc.update(sql, new MapSqlParameterSource("id", id));
    if (rows == 0) {
      throw new NotFoundException("Hobby not found");
    }
  }

  private HobbyDto queryOne(String sql, SqlParameterSource params, String notFoundMessage) {
    try {
      return jdbc.queryForObject(sql, params, rowMapper());
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException(notFoundMessage);
    }
  }

  private RowMapper<HobbyDto> rowMapper() {
    return (rs, rowNum) -> {
      HobbyDto dto = new HobbyDto();
      dto.id = rs.getLong("id");
      dto.nameEn = rs.getString("name_en");
      dto.nameFr = rs.getString("name_fr");
      dto.nameEs = rs.getString("name_es");
      dto.descriptionEn = rs.getString("description_en");
      dto.descriptionFr = rs.getString("description_fr");
      dto.descriptionEs = rs.getString("description_es");
      dto.icon = rs.getString("icon");
      dto.createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
      dto.updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));
      return dto;
    };
  }

  private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toLocalDateTime();
  }
}
