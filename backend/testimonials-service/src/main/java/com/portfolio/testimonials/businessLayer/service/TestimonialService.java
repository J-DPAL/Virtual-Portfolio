package com.portfolio.testimonials.businessLayer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial;
import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial.TestimonialStatus;
import com.portfolio.testimonials.dataAccessLayer.repository.TestimonialRepository;
import com.portfolio.testimonials.mappingLayer.dto.TestimonialDTO;
import com.portfolio.testimonials.mappingLayer.mapper.TestimonialMapper;
import com.portfolio.testimonials.utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
@SuppressWarnings("null")
public class TestimonialService {

  private final TestimonialRepository testimonialRepository;
  private final TestimonialMapper testimonialMapper;

  public TestimonialService(
      TestimonialRepository testimonialRepository, TestimonialMapper testimonialMapper) {
    this.testimonialRepository = testimonialRepository;
    this.testimonialMapper = testimonialMapper;
  }

  public List<TestimonialDTO> getAllTestimonials() {
    return testimonialRepository.findAll().stream()
        .map(testimonialMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<TestimonialDTO> getApprovedTestimonials() {
    return testimonialRepository
        .findByStatusOrderByCreatedAtDesc(TestimonialStatus.APPROVED)
        .stream()
        .map(testimonialMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<TestimonialDTO> getPendingTestimonials() {
    return testimonialRepository
        .findByStatusOrderByCreatedAtDesc(TestimonialStatus.PENDING)
        .stream()
        .map(testimonialMapper::toDTO)
        .collect(Collectors.toList());
  }

  public TestimonialDTO getTestimonialById(Long id) {
    Testimonial testimonial =
        testimonialRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Testimonial not found with id: " + id));
    return testimonialMapper.toDTO(testimonial);
  }

  public TestimonialDTO createTestimonial(TestimonialDTO testimonialDTO) {
    Testimonial testimonial = testimonialMapper.toEntity(testimonialDTO);
    testimonial.setStatus(TestimonialStatus.PENDING);
    Testimonial savedTestimonial = testimonialRepository.save(testimonial);
    return testimonialMapper.toDTO(savedTestimonial);
  }

  public TestimonialDTO updateTestimonial(Long id, TestimonialDTO testimonialDTO) {
    Testimonial existingTestimonial =
        testimonialRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Testimonial not found with id: " + id));

    existingTestimonial.setClientName(testimonialDTO.getClientName());
    existingTestimonial.setClientPosition(testimonialDTO.getClientPosition());
    existingTestimonial.setClientCompany(testimonialDTO.getClientCompany());
    existingTestimonial.setTestimonialTextEn(testimonialDTO.getTestimonialTextEn());
    existingTestimonial.setTestimonialTextFr(testimonialDTO.getTestimonialTextFr());
    existingTestimonial.setTestimonialTextEs(testimonialDTO.getTestimonialTextEs());
    existingTestimonial.setRating(testimonialDTO.getRating());
    existingTestimonial.setClientImageUrl(testimonialDTO.getClientImageUrl());

    Testimonial updatedTestimonial = testimonialRepository.save(existingTestimonial);
    return testimonialMapper.toDTO(updatedTestimonial);
  }

  public TestimonialDTO approveTestimonial(Long id) {
    Testimonial testimonial =
        testimonialRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Testimonial not found with id: " + id));

    testimonial.setStatus(TestimonialStatus.APPROVED);
    Testimonial approvedTestimonial = testimonialRepository.save(testimonial);
    return testimonialMapper.toDTO(approvedTestimonial);
  }

  public TestimonialDTO rejectTestimonial(Long id) {
    Testimonial testimonial =
        testimonialRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Testimonial not found with id: " + id));

    testimonial.setStatus(TestimonialStatus.REJECTED);
    Testimonial rejectedTestimonial = testimonialRepository.save(testimonial);
    return testimonialMapper.toDTO(rejectedTestimonial);
  }

  public void deleteTestimonial(Long id) {
    if (!testimonialRepository.existsById(id)) {
      throw new ResourceNotFoundException("Testimonial not found with id: " + id);
    }
    testimonialRepository.deleteById(id);
  }
}
