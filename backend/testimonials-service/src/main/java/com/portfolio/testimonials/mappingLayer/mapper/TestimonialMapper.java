package com.portfolio.testimonials.mappingLayer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial;
import com.portfolio.testimonials.mappingLayer.dto.TestimonialDTO;

@Component
public class TestimonialMapper {

  private final ModelMapper modelMapper;

  public TestimonialMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public TestimonialDTO toDTO(Testimonial testimonial) {
    return modelMapper.map(testimonial, TestimonialDTO.class);
  }

  public Testimonial toEntity(TestimonialDTO testimonialDTO) {
    return modelMapper.map(testimonialDTO, Testimonial.class);
  }
}
