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

import com.portfolio.monolith.dto.TestimonialDto;
import com.portfolio.monolith.dto.TestimonialStatus;
import com.portfolio.monolith.exception.NotFoundException;

@Service
public class TestimonialDataService {

  private final NamedParameterJdbcTemplate jdbc;

  public TestimonialDataService(NamedParameterJdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<TestimonialDto> getApprovedTestimonials() {
    String sql = "select * from public.testimonials where status = 'APPROVED' order by created_at desc";
    return jdbc.query(sql, rowMapper());
  }

  public List<TestimonialDto> getPendingTestimonials() {
    String sql = "select * from public.testimonials where status = 'PENDING' order by created_at desc";
    return jdbc.query(sql, rowMapper());
  }

  public TestimonialDto getTestimonialById(Long id) {
    String sql = "select * from public.testimonials where id = :id";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Testimonial not found");
  }

  public TestimonialDto createTestimonial(TestimonialDto dto) {
    String status = dto.status == null ? TestimonialStatus.PENDING.name() : dto.status.name();

    String sql =
        "insert into public.testimonials ("
            + "client_name, client_position, client_company, "
            + "testimonial_text_en, testimonial_text_fr, testimonial_text_es, "
            + "rating, client_image_url, status"
            + ") values ("
            + ":clientName, :clientPosition, :clientCompany, "
            + ":testimonialTextEn, :testimonialTextFr, :testimonialTextEs, "
            + ":rating, :clientImageUrl, cast(:status as varchar)"
            + ") returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("clientName", dto.clientName)
            .addValue("clientPosition", dto.clientPosition)
            .addValue("clientCompany", dto.clientCompany)
            .addValue("testimonialTextEn", dto.testimonialTextEn)
            .addValue("testimonialTextFr", dto.testimonialTextFr)
            .addValue("testimonialTextEs", dto.testimonialTextEs)
            .addValue("rating", dto.rating)
            .addValue("clientImageUrl", dto.clientImageUrl)
            .addValue("status", status);

    return jdbc.queryForObject(sql, p, rowMapper());
  }

  public TestimonialDto updateTestimonial(Long id, TestimonialDto dto) {
    String status = dto.status == null ? TestimonialStatus.PENDING.name() : dto.status.name();

    String sql =
        "update public.testimonials set "
            + "client_name = :clientName, client_position = :clientPosition, client_company = :clientCompany, "
            + "testimonial_text_en = :testimonialTextEn, testimonial_text_fr = :testimonialTextFr, testimonial_text_es = :testimonialTextEs, "
            + "rating = :rating, client_image_url = :clientImageUrl, status = cast(:status as varchar), updated_at = now() "
            + "where id = :id returning *";

    MapSqlParameterSource p =
        new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("clientName", dto.clientName)
            .addValue("clientPosition", dto.clientPosition)
            .addValue("clientCompany", dto.clientCompany)
            .addValue("testimonialTextEn", dto.testimonialTextEn)
            .addValue("testimonialTextFr", dto.testimonialTextFr)
            .addValue("testimonialTextEs", dto.testimonialTextEs)
            .addValue("rating", dto.rating)
            .addValue("clientImageUrl", dto.clientImageUrl)
            .addValue("status", status);

    return queryOne(sql, p, "Testimonial not found");
  }

  public TestimonialDto approveTestimonial(Long id) {
    String sql = "update public.testimonials set status = 'APPROVED', updated_at = now() where id = :id returning *";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Testimonial not found");
  }

  public TestimonialDto rejectTestimonial(Long id) {
    String sql = "update public.testimonials set status = 'REJECTED', updated_at = now() where id = :id returning *";
    return queryOne(sql, new MapSqlParameterSource("id", id), "Testimonial not found");
  }

  public void deleteTestimonial(Long id) {
    String sql = "delete from public.testimonials where id = :id";
    int rows = jdbc.update(sql, new MapSqlParameterSource("id", id));
    if (rows == 0) {
      throw new NotFoundException("Testimonial not found");
    }
  }

  private TestimonialDto queryOne(String sql, SqlParameterSource params, String notFoundMessage) {
    try {
      return jdbc.queryForObject(sql, params, rowMapper());
    } catch (EmptyResultDataAccessException ex) {
      throw new NotFoundException(notFoundMessage);
    }
  }

  private RowMapper<TestimonialDto> rowMapper() {
    return (rs, rowNum) -> {
      TestimonialDto dto = new TestimonialDto();
      dto.id = rs.getLong("id");
      dto.clientName = rs.getString("client_name");
      dto.clientPosition = rs.getString("client_position");
      dto.clientCompany = rs.getString("client_company");
      dto.testimonialTextEn = rs.getString("testimonial_text_en");
      dto.testimonialTextFr = rs.getString("testimonial_text_fr");
      dto.testimonialTextEs = rs.getString("testimonial_text_es");
      dto.rating = (Integer) rs.getObject("rating");
      dto.clientImageUrl = rs.getString("client_image_url");

      String status = rs.getString("status");
      try {
        dto.status = TestimonialStatus.valueOf(status);
      } catch (Exception ex) {
        dto.status = TestimonialStatus.PENDING;
      }
      dto.approved = dto.status == TestimonialStatus.APPROVED;
      dto.createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
      dto.updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));
      return dto;
    };
  }

  private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toLocalDateTime();
  }
}
