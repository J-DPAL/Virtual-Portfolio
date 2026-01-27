package com.portfolio.testimonials.dataAccessLayer.repository;

import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial;
import com.portfolio.testimonials.dataAccessLayer.entity.Testimonial.TestimonialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    List<Testimonial> findByStatus(TestimonialStatus status);
    List<Testimonial> findByStatusOrderByCreatedAtDesc(TestimonialStatus status);
}
