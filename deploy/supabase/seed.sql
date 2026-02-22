-- Seed data migrated from Java DataLoader classes.
-- Safe to run multiple times: it inserts only when target tables are empty.
-- Run after:
--   1) deploy/supabase/schema.sql
--   2) deploy/supabase/rls.sql

begin;

do $$
begin
  if not exists (select 1 from public.skills limit 1) then
    insert into public.skills (
      name_en, name_fr, name_es,
      description_en, description_fr, description_es,
      proficiency_level, category, years_of_experience
    ) values
      ('Java','Java','Java','Java - Object-oriented programming language','Java - Langage de programmation orientee objet','Java - Lenguaje de programacion orientado a objetos','Advanced','Programming',null),
      ('Python','Python','Python','Python - High-level programming language','Python - Langage de programmation haut niveau','Python - Lenguaje de programacion de alto nivel','Advanced','Programming',null),
      ('JavaScript','JavaScript','JavaScript','JavaScript - Dynamic programming language for web','JavaScript - Langage de programmation dynamique pour le web','JavaScript - Lenguaje de programacion dinamico para web','Advanced','Programming',null),
      ('SQL','SQL','SQL','SQL - Structured Query Language for databases','SQL - Langage de requete structure pour les bases de donnees','SQL - Lenguaje de consulta estructurado para bases de datos','Advanced','Programming',null),
      ('HTML/CSS','HTML/CSS','HTML/CSS','HTML/CSS - Markup and styling for web pages','HTML/CSS - Balisage et style pour les pages web','HTML/CSS - Marcado y estilo para paginas web','Advanced','Programming',null),
      ('ReactJS','ReactJS','ReactJS','ReactJS - JavaScript library for building user interfaces','ReactJS - Bibliotheque JavaScript pour la construction d interfaces utilisateur','ReactJS - Biblioteca JavaScript para construir interfaces de usuario','Advanced','Programming',null),
      ('AngularJS','AngularJS','AngularJS','AngularJS - JavaScript framework for dynamic web applications','AngularJS - Framework JavaScript pour les applications web dynamiques','AngularJS - Framework JavaScript para aplicaciones web dinamicas','Intermediate','Programming',null),
      ('PHP','PHP','PHP','PHP - Server-side scripting language used with Laravel','PHP - Langage de script cote serveur utilise avec Laravel','PHP - Lenguaje de script del lado del servidor usado con Laravel','Intermediate','Programming',null),
      ('Kotlin','Kotlin','Kotlin','Kotlin - Modern Android development language','Kotlin - Langage moderne de developpement Android','Kotlin - Lenguaje moderno de desarrollo Android','Beginner','Programming',null),
      ('C#','C#','C#','C# - Object-oriented programming language for .NET','C# - Langage de programmation oriente objet pour .NET','C# - Lenguaje de programacion orientado a objetos para .NET','Intermediate','Programming',null),
      ('.NET','.NET','.NET','.NET Framework - Cross-platform application development','Framework .NET - Developpement d applications multiplateforme','.NET Framework - Desarrollo de aplicaciones multiplataforma','Intermediate','Web & Cloud',null),
      ('Swift','Swift','Swift','Swift - Programming language for iOS and macOS development','Swift - Langage de programmation pour le developpement iOS et macOS','Swift - Lenguaje de programacion para desarrollo iOS y macOS','Beginner','Programming',null),
      ('FastAPI','FastAPI','FastAPI','FastAPI - Modern Python web framework for building APIs','FastAPI - Framework web Python moderne pour la construction d API','FastAPI - Framework web Python moderno para construir APIs','Intermediate','Web & Cloud',null),
      ('Laravel','Laravel','Laravel','Laravel - PHP web application framework','Laravel - Framework d application web PHP','Laravel - Framework de aplicaciones web PHP','Intermediate','Web & Cloud',null),
      ('Bootstrap','Bootstrap','Bootstrap','Bootstrap - CSS framework for responsive design','Bootstrap - Framework CSS pour la conception reactive','Bootstrap - Framework CSS para diseno responsivo','Intermediate','Development Tools',null),
      ('JUnit5','JUnit5','JUnit5','JUnit5 - Java testing framework for unit testing','JUnit5 - Framework de test Java pour les tests unitaires','JUnit5 - Framework de pruebas Java para pruebas unitarias','Intermediate','Development Tools',null),
      ('Xcode','Xcode','Xcode','Xcode - Apple IDE for iOS and macOS development','Xcode - IDE Apple pour le developpement iOS et macOS','Xcode - IDE de Apple para desarrollo iOS y macOS','Beginner','Development Tools',null),
      ('Vite','Vite','Vite','Vite - Fast frontend build tool and dev server','Vite - Outil de construction frontend rapide et serveur de developpement','Vite - Herramienta de construccion frontend rapida y servidor de desarrollo','Intermediate','Development Tools',null),
      ('Tailwind CSS','Tailwind CSS','Tailwind CSS','Tailwind CSS - Utility-first CSS framework','Tailwind CSS - Framework CSS utilitaire-first','Tailwind CSS - Framework CSS de utilidades','Intermediate','Development Tools',null),
      ('REST APIs','REST APIs','REST APIs','REST APIs - Representational State Transfer architecture','REST APIs - Architecture de transfert d etat representationnel','REST APIs - Arquitectura de transferencia de estado representacional','Advanced','Web & Cloud',null),
      ('Distributed Systems','Distributed Systems','Sistemas Distribuidos','Distributed Systems - Design and architecture of distributed applications','Systemes Distribues - Conception et architecture d applications distribuees','Sistemas Distribuidos - Diseno y arquitectura de aplicaciones distribuidas','Intermediate','Web & Cloud',null),
      ('Docker','Docker','Docker','Docker - Containerization platform for application deployment','Docker - Plateforme de conteneurisation pour le deploiement d applications','Docker - Plataforma de contenedorizacion para el despliegue de aplicaciones','Advanced','Web & Cloud',null),
      ('Docker Compose','Docker Compose','Docker Compose','Docker Compose - Tool for defining multi-container Docker applications','Docker Compose - Outil de definition d applications Docker multi-conteneurs','Docker Compose - Herramienta para definir aplicaciones Docker multi-contenedor','Advanced','Web & Cloud',null),
      ('Spring Boot','Spring Boot','Spring Boot','Spring Boot - Framework for building Java applications','Spring Boot - Framework pour la construction d applications Java','Spring Boot - Framework para construir aplicaciones Java','Advanced','Web & Cloud',null),
      ('Git/GitHub','Git/GitHub','Git/GitHub','Git and GitHub - Version control and collaborative development','Git et GitHub - Controle de version et developpement collaboratif','Git y GitHub - Control de versiones y desarrollo colaborativo','Advanced','Web & Cloud',null),
      ('PostgreSQL','PostgreSQL','PostgreSQL','PostgreSQL - Advanced open-source relational database','PostgreSQL - Base de donnees relationnelle open-source avancee','PostgreSQL - Base de datos relacional de codigo abierto avanzada','Advanced','Web & Cloud',null),
      ('Scrum/Agile','Scrum/Agile','Scrum/Agile','Scrum and Agile - Software development methodologies','Scrum et Agile - Methodologies de developpement logiciel','Scrum y Agile - Metodologias de desarrollo de software','Advanced','Work Methods',null),
      ('Time Management','Gestion du Temps','Gestion del Tiempo','Time Management - Organizing and prioritizing work effectively','Gestion du Temps - Organisation et priorisation du travail efficace','Gestion del Tiempo - Organizacion y priorizacion efectiva del trabajo','Advanced','Work Methods',null),
      ('Problem Solving','Resolution de Problemes','Resolucion de Problemas','Problem Solving - Analytical thinking and creative solutions','Resolution de Problemes - Pensee analytique et solutions creatives','Resolucion de Problemas - Pensamiento analitico y soluciones creativas','Advanced','Work Methods',null),
      ('Teamwork','Travail d Equipe','Trabajo en Equipo','Teamwork - Collaborating effectively in team environments','Travail d Equipe - Collaboration efficace dans les environnements d equipe','Trabajo en Equipo - Colaboracion efectiva en entornos de equipo','Advanced','Work Methods',null),
      ('Unity','Unity','Unity','Unity - Game development engine','Unity - Moteur de developpement de jeux','Unity - Motor de desarrollo de juegos','Intermediate','Development Tools',null);
  end if;

  if not exists (select 1 from public.projects limit 1) then
    insert into public.projects (
      title_en, title_fr, title_es,
      description_en, description_fr, description_es,
      technologies, project_url, github_url, image_url,
      start_date, end_date, status
    ) values
      ('OpenHand - MANA Mobile Application','OpenHand - Application mobile MANA','OpenHand - Aplicacion movil MANA',
       'Mobile application for MANA with Expo Router, responsive UI, Dockerized environment, PostgreSQL, and WhatsApp deep-link integration.',
       'Application mobile pour MANA avec Expo Router, interface responsive, environnement Docker, PostgreSQL et integration WhatsApp.',
       'Aplicacion movil para MANA con Expo Router, interfaz responsive, entorno Docker, PostgreSQL e integracion de WhatsApp.',
       'React Native (Expo), TypeScript, Expo Router, Docker, Docker Compose, PostgreSQL, Spring Boot, Node.js',
       null,'https://github.com/Chronyyx/openhand-mobile-system',null,'2026-02-01',null,'In Progress'),

      ('Champlain PetClinic (CPC) - Scrum Project','Champlain PetClinic (CPC) - Projet Scrum','Champlain PetClinic (CPC) - Proyecto Scrum',
       'Six-week agile simulation building a web app with archive, undo, and UX improvements.',
       'Simulation agile de six semaines pour construire une application web avec archivage, annulation et ameliorations UX.',
       'Simulacion agile de seis semanas para construir una aplicacion web con archivo, deshacer y mejoras UX.',
       'Java, Spring Boot, JUnit5, React, Jira',
       null,'https://github.com/cgerard321/champlain_petclinic',null,'2025-10-01','2025-10-31','Completed'),

      ('WeatherApp - Full-Stack Weather Application','WeatherApp - Application meteo full stack','WeatherApp - Aplicacion del clima full stack',
       'Full-stack weather app with current weather, forecast, historical queries, maps, export, and Dockerized deployment.',
       'Application meteo full stack avec meteo actuelle, previsions, historique, cartes, export et deploiement Docker.',
       'Aplicacion del clima full stack con clima actual, pronostico, historico, mapas, exportacion y despliegue Docker.',
       'React (Vite), Tailwind CSS, FastAPI (Python), PostgreSQL, SQLAlchemy, Docker, Docker Compose',
       null,'https://github.com/J-DPAL/Weather-App',null,'2025-10-01','2025-10-31','Completed'),

      ('System Analysis & Design - Apartment Rental System','Analyse et conception - Systeme de location d appartements','Analisis y diseno - Sistema de renta de apartamentos',
       'Web-based rental system design including requirements, UML diagrams, prototypes, and database schema.',
       'Conception d un systeme web de location avec exigences, UML, prototypes et schema de base de donnees.',
       'Diseno de un sistema web de renta con requisitos, UML, prototipos y esquema de base de datos.',
       'UML, SQL, FURPS+, UI prototyping tools',
       null,null,null,'2025-05-01','2025-05-31','Completed'),

      ('Laravel-Powered Mini E-Commerce Platform','Mini plateforme e-commerce avec Laravel','Mini plataforma de e-commerce con Laravel',
       'Mini e-commerce platform with browsing, authentication, order management, CRUD, and admin dashboard.',
       'Mini plateforme e-commerce avec navigation, authentification, gestion des commandes, CRUD et tableau de bord admin.',
       'Mini plataforma de e-commerce con navegacion, autenticacion, gestion de pedidos, CRUD y panel admin.',
       'PHP (Laravel), Bootstrap, Tailwind, MySQL',
       null,'https://github.com/J-DPAL/Mini-E-Commerce-Platform',null,'2025-05-01','2025-05-31','Completed'),

      ('Rustbound - Tower Defense Game','Rustbound - Jeu tower defense','Rustbound - Juego tower defense',
       'Team-built 3D tower defense game with enemy waves, resources, and in-game currency mechanics.',
       'Jeu tower defense 3D en equipe avec vagues d ennemis, ressources et mecaniques de monnaie.',
       'Juego tower defense 3D en equipo con oleadas enemigas, recursos y mecanicas de moneda.',
       'Unity, C#, OOP, Game Physics',
       null,'https://github.com/J-DPAL/TowerDefenseGame',null,'2025-05-01','2025-05-31','Completed'),

      ('Parking Finder Mobile App (Kotlin)','Application mobile Parking Finder (Kotlin)','Aplicacion movil Parking Finder (Kotlin)',
       'Android app to find, book, and pay for parking with dashboard, roles, and history.',
       'Application Android pour trouver, reserver et payer le stationnement avec tableau de bord, roles et historique.',
       'Aplicacion Android para encontrar, reservar y pagar estacionamiento con panel, roles e historial.',
       'Kotlin, Android Studio, Google Maps API, SQLite',
       null,'https://github.com/josevmorilla/smart-parking-system',null,'2025-05-01','2025-05-31','Completed'),

      ('RESTful Microservice Project - Game Asset Marketplace','Projet microservices REST - Marche d actifs de jeux','Proyecto microservicios REST - Mercado de activos de juegos',
       'RESTful system for managing game assets with services for users, assets, and payments.',
       'Systeme REST pour gerer des actifs de jeux avec services utilisateurs, actifs et paiements.',
       'Sistema REST para gestionar activos de juegos con servicios de usuarios, activos y pagos.',
       'Java, Spring Boot, Docker, JUnit, REST APIs, Microservices Architecture',
       null,'https://github.com/J-DPAL/Game-Asset-Market-Place-MS',null,'2025-05-01','2025-05-31','Completed'),

      ('Missile Command Game - Arcade-style Defense Game','Missile Command - Jeu d arcade de defense','Missile Command - Juego arcade de defensa',
       'Unity recreation of Missile Command with dynamic waves, bonuses, shield durability, and score UI.',
       'Recreation Unity de Missile Command avec vagues dynamiques, bonus, durabilite des boucliers et UI score.',
       'Recreacion en Unity de Missile Command con oleadas dinamicas, bonus, durabilidad de escudos y UI de puntaje.',
       'Unity Engine, C#, TextMesh Pro, Unity Input System',
       null,'https://github.com/J-DPAL/Missile-Command-Game',null,'2025-04-01','2025-04-30','Completed'),

      ('Plinko Game - Unity Plinko-style Game','Plinko - Jeu de type Plinko sous Unity','Plinko - Juego tipo Plinko en Unity',
       'Unity Plinko-style game with rotating peg board, physics, limited disks, and scene transitions.',
       'Jeu Unity de type Plinko avec plateau rotatif, physique des disques, limite de disques et transitions.',
       'Juego Unity tipo Plinko con tablero rotativo, fisica de discos, limite de discos y transiciones.',
       'Unity Engine, C#, TextMesh Pro, Unity 2D Physics (Physics2D)',
       null,'https://github.com/J-DPAL/Plinko-Game',null,'2025-03-01','2025-03-31','Completed'),

      ('LikeAHolic Social App - Full Stack Web Application','LikeAHolic - Application web full stack','LikeAHolic - Aplicacion web full stack',
       'Social app with post publishing and viewing, backed by REST services and React frontend.',
       'Application sociale avec publication et consultation de posts, backend REST et frontend React.',
       'Aplicacion social con publicacion y visualizacion de posts, backend REST y frontend React.',
       'ReactJS, Spring Boot, SQL (H2/SQL DB), DTO/ResponseEntity',
       null,null,null,'2024-12-01','2024-12-31','Completed');
  end if;

  if not exists (select 1 from public.work_experience limit 1) then
    insert into public.work_experience (
      company_name_en, company_name_fr, company_name_es,
      position_en, position_fr, position_es,
      description_en, description_fr, description_es,
      location_en, location_fr, location_es,
      start_date, end_date, is_current, skills_used, icon
    ) values
      ('Juliette & Chocolat','Juliette & Chocolat','Juliette & Chocolat',
       'Cook','Cuisinier','Cocinero',
       'Prepared and assembled dishes in a fast-paced, demanding environment. Developed time management, quality standards, and teamwork.',
       'Preparation et assemblage de plats dans un environnement rapide et exigeant. Developpement de la gestion du temps, des normes de qualite et du travail d equipe.',
       'Preparacion y ensamblaje de platos en un entorno rapido y exigente. Desarrollo de gestion del tiempo, estandares de calidad y trabajo en equipo.',
       'Montreal, QC','Montreal, QC','Montreal, QC',
       '2023-06-01','2023-06-20',false,null,null),

      ('Elections Quebec','Elections Quebec','Elections Quebec',
       'Voter Attendant','Prepose aux Electeurs','Asistente de Votantes',
       'Welcomed voters and verified identification, ensuring confidentiality and procedures.',
       'Accueil des electeurs et verification des pieces d identite avec confidentialite et respect des procedures.',
       'Recepcion de votantes y verificacion de identificacion, con confidencialidad y cumplimiento de procedimientos.',
       'Quebec, QC','Quebec, QC','Quebec, QC',
       '2022-10-01','2022-10-02',false,null,null);
  end if;

  if not exists (select 1 from public.education limit 1) then
    insert into public.education (
      institution_name_en, institution_name_fr, institution_name_es,
      degree_en, degree_fr, degree_es,
      field_of_study_en, field_of_study_fr, field_of_study_es,
      description_en, description_fr, description_es,
      start_date, end_date, is_current, gpa
    ) values
      ('Champlain College','Champlain College','Champlain College',
       'DEC in Computer Science Technology','DEC en Technologie de l informatique','DEC en Tecnologia de Ciencias de la Computacion',
       'Computer Science Technology','Technologie de l informatique','Tecnologia de Ciencias de la Computacion',
       'Computer Science Technology student (DEC), expected 2026.',
       'Etudiant en Technologie de l informatique (DEC), diplomation prevue en 2026.',
       'Estudiante de Tecnologia de Ciencias de la Computacion (DEC), graduacion prevista en 2026.',
       '2023-08-20','2026-06-11',true,null),

      ('College Charles-Lemoyne','College Charles-Lemoyne','College Charles-Lemoyne',
       'High School Diploma','Diplome d Etudes Secondaires','Diploma de Secundaria',
       'High School Diploma','Diplome d etudes secondaires','Diploma de secundaria',
       'High School Diploma, 2023.',
       'Diplome d etudes secondaires, 2023.',
       'Diploma de secundaria, 2023.',
       '2018-08-28','2023-06-20',false,null);
  end if;
end $$;

commit;
