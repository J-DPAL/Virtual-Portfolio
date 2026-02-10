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
            .institutionNameEs("Champlain College")
            .degreeEn("DEC in Computer Science Technology")
            .degreeFr("DEC en Technologie de l'informatique")
            .degreeEs("DEC en Tecnologia de Ciencias de la Computacion")
            .fieldOfStudyEn("Computer Science Technology")
            .fieldOfStudyFr("Technologie de l'informatique")
            .fieldOfStudyEs("Tecnologia de Ciencias de la Computacion")
            .descriptionEn("Computer Science Technology student (DEC), expected 2026.")
            .descriptionFr(
                "Etudiant en Technologie de l'informatique (DEC), diplomation prevue en 2026.")
            .descriptionEs(
                "Estudiante de Tecnologia de Ciencias de la Computacion (DEC), graduacion prevista en 2026.")
            .startDate(LocalDate.of(2023, 8, 20))
            .endDate(LocalDate.of(2026, 6, 11))
            .isCurrent(true)
            .build());

    educationRepository.save(
        Education.builder()
            .institutionNameEn("Collège Charles-Lemoyne")
            .institutionNameFr("Collège Charles-Lemoyne")
            .institutionNameEs("Collège Charles-Lemoyne")
            .degreeEn("High School Diploma")
            .degreeFr("Diplôme d'Études Secondaires")
            .degreeEs("Diploma de Secundaria")
            .fieldOfStudyEn("High School Diploma")
            .fieldOfStudyFr("Diplome d'etudes secondaires")
            .fieldOfStudyEs("Diploma de secundaria")
            .descriptionEn("High School Diploma, 2023.")
            .descriptionFr("Diplome d'etudes secondaires, 2023.")
            .descriptionEs("Diploma de secundaria, 2023.")
            .startDate(LocalDate.of(2018, 8, 28))
            .endDate(LocalDate.of(2023, 6, 20))
            .isCurrent(false)
            .build());

    log.info("Completed loading {} education records", educationRepository.count());
  }
}
