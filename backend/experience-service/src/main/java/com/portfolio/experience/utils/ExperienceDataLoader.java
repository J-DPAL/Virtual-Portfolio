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
            .companyNameEs("Juliette & Chocolat")
            .positionEn("Cook")
            .positionFr("Cuisinier")
            .positionEs("Cocinero")
            .descriptionEn(
                "Prepared and assembled dishes in a fast-paced, demanding environment. Developed time management skills, adherence to quality standards, and teamwork.")
            .descriptionFr(
                "Preparation et assemblage de plats dans un environnement rapide et exigeant. Developpement de la gestion du temps, du respect des normes de qualite et du travail d'equipe.")
            .descriptionEs(
                "Preparacion y ensamblaje de platos en un entorno rapido y exigente. Desarrollo de la gestion del tiempo, el cumplimiento de estandares de calidad y el trabajo en equipo.")
            .startDate(LocalDate.of(2023, 6, 1))
            .endDate(LocalDate.of(2023, 6, 20))
            .isCurrent(false)
            .build());

    workExperienceRepository.save(
        WorkExperience.builder()
            .companyNameEn("Élections Québec")
            .companyNameFr("Élections Québec")
            .companyNameEs("Élections Québec")
            .positionEn("Voter Attendant")
            .positionFr("Préposé aux Électeurs")
            .positionEs("Asistente de Votantes")
            .descriptionEn(
                "Welcomed voters and verified identification, ensuring confidentiality and adherence to procedures. Demonstrated reliability and professionalism in a high-pressure environment.")
            .descriptionFr(
                "Accueil des electeurs et verification des pieces d'identite, en assurant la confidentialite et le respect des procedures. Fiabilite et professionnalisme en environnement a haute pression.")
            .descriptionEs(
                "Recepcion de votantes y verificacion de identificacion, garantizando la confidencialidad y el cumplimiento de los procedimientos. Fiabilidad y profesionalismo en un entorno de alta presion.")
            .startDate(LocalDate.of(2022, 10, 1))
            .endDate(LocalDate.of(2022, 10, 2))
            .isCurrent(false)
            .build());

    log.info("Completed loading {} work experiences", workExperienceRepository.count());
  }
}
