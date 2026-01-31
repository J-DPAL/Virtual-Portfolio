package com.portfolio.education.utils;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.portfolio.education.dataAccessLayer.entity.Education;
import com.portfolio.education.dataAccessLayer.repository.EducationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EducationDataLoader implements CommandLineRunner {

  private final EducationRepository educationRepository;

  public EducationDataLoader(EducationRepository educationRepository) {
    this.educationRepository = educationRepository;
  }

  @Override
  public void run(String... args) {
    if (educationRepository.count() == 0) {
      log.info("Loading initial education data...");
      loadEducation();
    } else {
      log.info("Education data already exists, skipping initialization");
    }
  }

  private void loadEducation() {
    educationRepository.save(
        Education.builder()
            .institutionNameEn("Champlain College")
            .institutionNameFr("Champlain College")
            .degreeEn("DEC (Diploma of College Studies)")
            .degreeFr("DEC (Diplôme d'Études Collégiales)")
            .fieldOfStudyEn("Computer Science / Information Technology")
            .fieldOfStudyFr("Informatique / Technologie de l'Information")
            .descriptionEn(
                "Comprehensive IT program covering programming, databases, web development, and software engineering best practices. Gained hands-on experience with Java, Python, and various frameworks.")
            .descriptionFr(
                "Programme informatique complet couvrant la programmation, les bases de données, le développement web et les meilleures pratiques d'ingénierie logicielle. Expérience pratique avec Java, Python et divers frameworks.")
            .startDate(LocalDate.of(2023, 9, 1))
            .endDate(LocalDate.of(2026, 5, 31))
            .isCurrent(true)
            .gpa(new java.math.BigDecimal("3.8"))
            .build());

    educationRepository.save(
        Education.builder()
            .institutionNameEn("Collège Charles-Lemoyne")
            .institutionNameFr("Collège Charles-Lemoyne")
            .degreeEn("High School Diploma")
            .degreeFr("Diplôme d'Études Secondaires")
            .fieldOfStudyEn("General Sciences")
            .fieldOfStudyFr("Sciences Générales")
            .descriptionEn(
                "Secondary education with focus on sciences and mathematics. Developed strong foundational knowledge in physics, chemistry, and mathematics that support software development.")
            .descriptionFr(
                "Enseignement secondaire avec un accent sur les sciences et les mathématiques. Développement de connaissances fondamentales solides en physique, chimie et mathématiques.")
            .startDate(LocalDate.of(2019, 9, 1))
            .endDate(LocalDate.of(2023, 6, 30))
            .isCurrent(false)
            .gpa(new java.math.BigDecimal("3.9"))
            .build());

    log.info("Completed loading {} education records", educationRepository.count());
  }
}
