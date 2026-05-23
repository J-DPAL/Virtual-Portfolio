package com.portfolio.monolith.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

  private final NamedParameterJdbcTemplate jdbc;
  private final Logger log = LoggerFactory.getLogger(DataSeeder.class);

  public DataSeeder(NamedParameterJdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public void run(String... args) throws Exception {
    try {
      // Ensure SmartCD.ai project exists
      String checkProjectSql = "select 1 from public.projects where lower(title_en)=lower(:title) limit 1";
      Integer p = null;
      try {
        p = jdbc.queryForObject(checkProjectSql, new MapSqlParameterSource("title", "SmartCD.ai"), Integer.class);
      } catch (Exception ex) {
        // queryForObject may throw when result is empty; swallow here and treat as missing
      }
      if (p == null) {
        String insertProject = "insert into public.projects (title_en,title_fr,title_es,description_en,description_fr,description_es,technologies,project_url,github_url,image_url,status) values (:t,:t,:t,:de,:df,:ds,:tech,:url,:gh,:img,:status)";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("t", "SmartCD.ai")
            .addValue("de", "A fintech web application that helps users compare CD options, view ranked recommendations, and get AI-powered explanations for the top results.")
            .addValue("df", "Une application fintech qui aide les utilisateurs à comparer des options de CD, voir des recommandations classées et obtenir des explications générées par l’IA.")
            .addValue("ds", "Una aplicación fintech que ayuda a los usuarios a comparar opciones de CD, ver recomendaciones clasificadas y recibir explicaciones generadas por IA.")
            .addValue("tech", "React, Vite, Tailwind CSS, Python, FastAPI, Supabase, Render, Vercel, SQL, Redis, FAISS")
            .addValue("url", "https://www.smartcd.ai/")
            .addValue("gh", "https://github.com/Sylv1011/smart-cd-ai")
            .addValue("img", "SmartCD.png")
            .addValue("status", "Completed");
        jdbc.update(insertProject, params);
        log.info("Seeded project SmartCD.ai");
      }

      // Ensure Product Manager Accelerator experience exists
      String checkExpSql = "select 1 from public.work_experience where lower(company_name_en)=lower(:company) and lower(position_en)=lower(:position) limit 1";
      Integer e = null;
      try {
        e = jdbc.queryForObject(checkExpSql, new MapSqlParameterSource().addValue("company", "Product Manager Accelerator").addValue("position", "Full-Stack AI Engineering Intern"), Integer.class);
      } catch (Exception ex) {
        // treat as missing
      }
      if (e == null) {
        String insertExp = "insert into public.work_experience (company_name_en,company_name_fr,company_name_es,position_en,position_fr,position_es,description_en,description_fr,description_es,location_en,location_fr,location_es,start_date,end_date,is_current,skills_used) values (:cn,:cn,:cn,:pos,:posf,:poss,:de,:df,:ds,:loc,:loc,:loc,:start,:end,:iscur,:skills)";
        MapSqlParameterSource p2 = new MapSqlParameterSource()
            .addValue("cn", "Product Manager Accelerator")
            .addValue("pos", "Full-Stack AI Engineering Intern")
            .addValue("posf", "Stagiaire en ingénierie IA Full-Stack")
            .addValue("poss", "Pasante de Ingeniería Full-Stack e IA")
            .addValue("de", "Worked as a Full-Stack and AI Engineering Intern at Product Manager Accelerator, contributing to frontend and backend development, deployment architecture, AI-assisted features, and system reliability improvements. Helped improve the user experience by implementing interactive UI features, integrating AI-powered recommendation systems, fixing production issues, and optimizing application performance. Collaborated with developers, designers, and project teams in an Agile-style environment while working on real-world software systems involving cloud deployment, AI integration, and data reliability.")
            .addValue("df", "J’ai travaillé comme stagiaire en ingénierie Full-Stack et IA chez Product Manager Accelerator, en contribuant au développement frontend et backend, à l’architecture de déploiement, aux fonctionnalités assistées par l’IA et aux améliorations de la fiabilité du système. J’ai aidé à améliorer l’expérience utilisateur en développant des fonctionnalités interactives pour l’interface, en intégrant des systèmes de recommandations alimentados par l’IA, en corrigeant des problèmes en production et en optimisant les performances de l’application.")
            .addValue("ds", "Trabajé como pasante de Ingeniería Full-Stack e IA en Product Manager Accelerator, contribuyendo al desarrollo frontend y backend, la arquitectura de despliegue, funciones asistidas por IA y mejoras en la confiabilidad del sistema. Ayudé a mejorar la experiencia del usuario implementando funciones interactivas de interfaz, integrando sistemas de recomendaciones impulsados por IA, corrigiendo problemas en producción y optimizando el rendimiento de la aplicación. Colaboré con desarrolladores, diseñadores y equipos de proyecto en un entorno de trabajo estilo Agile mientras trabajaba en sistemas de software reales relacionados con despliegue en la nube, integración de IA y confiabilidad de datos.")
            .addValue("loc", "Remote")
            .addValue("start", java.sql.Date.valueOf("2026-01-19"))
            .addValue("end", java.sql.Date.valueOf("2026-06-19"))
            .addValue("iscur", false)
            .addValue("skills", "Python, FastAPI, React, Vite, Tailwind CSS, SQL, GitHub, Docker, Redis, FAISS, Supabase, Render, Vercel, REST APIs, Frontend Development, Backend Development, Full-Stack Development, Cloud Deployment, AI Integration, RAG Systems, Debugging, UI/UX Improvements");
        jdbc.update(insertExp, p2);
        log.info("Seeded experience Product Manager Accelerator");
      }
    } catch (Exception ex) {
      log.warn("DataSeeder skipped: database unavailable or error during seeding - {}", ex.getMessage());
    }
  }
}
