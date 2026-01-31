package com.portfolio.experience.utils;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.portfolio.experience.dataAccessLayer.entity.WorkExperience;
import com.portfolio.experience.dataAccessLayer.repository.WorkExperienceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExperienceDataLoader implements CommandLineRunner {

  private final WorkExperienceRepository workExperienceRepository;

  public ExperienceDataLoader(WorkExperienceRepository workExperienceRepository) {
    this.workExperienceRepository = workExperienceRepository;
  }

  @Override
  public void run(String... args) {
    if (workExperienceRepository.count() == 0) {
      log.info("Loading initial work experience data...");
      loadWorkExperiences();
    } else {
      log.info("Work experience data already exists, skipping initialization");
    }
  }

  private void loadWorkExperiences() {
    workExperienceRepository.save(
        WorkExperience.builder()
            .companyNameEn("Juliette & Chocolat")
            .companyNameFr("Juliette & Chocolat")
            .positionEn("Cook")
            .positionFr("Cuisinier")
            .descriptionEn(
                "Prepared high-quality pastries and chocolates, maintained kitchen hygiene standards, and collaborated with team members to ensure efficient operations.")
            .descriptionFr(
                "Préparation de pâtisseries et chocolats de haute qualité, maintien des normes d'hygiène de la cuisine, et collaboration avec l'équipe pour assurer une exploitation efficace.")
            .locationEn("Montreal, Canada")
            .locationFr("Montréal, Canada")
            .startDate(LocalDate.of(2023, 6, 1))
            .endDate(null)
            .isCurrent(true)
            .build());

    workExperienceRepository.save(
        WorkExperience.builder()
            .companyNameEn("Élections Québec")
            .companyNameFr("Élections Québec")
            .positionEn("Voter Attendant")
            .positionFr("Préposé aux Électeurs")
            .descriptionEn(
                "Assisted voters with the voting process, managed voting stations, verified voter eligibility, and ensured compliance with electoral regulations.")
            .descriptionFr(
                "Assistance des électeurs dans le processus de vote, gestion des bureaux de vote, vérification de l'admissibilité des électeurs, et respect de la conformité aux règlements électoraux.")
            .locationEn("Quebec, Canada")
            .locationFr("Québec, Canada")
            .startDate(LocalDate.of(2022, 10, 1))
            .endDate(LocalDate.of(2022, 10, 31))
            .isCurrent(false)
            .build());

    log.info("Completed loading {} work experiences", workExperienceRepository.count());
  }
}
