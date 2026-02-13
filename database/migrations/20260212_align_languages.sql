-- Align multilingual columns to EN/FR/ES and remove Arabic fields

DO $$
BEGIN
  -- Projects
  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'projects'
        AND column_name = 'title_fr'
    ) THEN
    ALTER TABLE projects ADD COLUMN title_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'projects'
        AND column_name = 'title_es'
    ) THEN
    ALTER TABLE projects ADD COLUMN title_es VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'projects'
        AND column_name = 'description_fr'
    ) THEN
    ALTER TABLE projects ADD COLUMN description_fr TEXT;
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'projects'
        AND column_name = 'description_es'
    ) THEN
    ALTER TABLE projects ADD COLUMN description_es TEXT;
  END IF;

  UPDATE projects
  SET title_fr = COALESCE(title_fr, title_en),
      title_es = COALESCE(title_es, title_ar, title_en),
      description_fr = COALESCE(description_fr, description_en),
      description_es = COALESCE(description_es, description_ar, description_en);

  ALTER TABLE projects ALTER COLUMN title_fr SET NOT NULL;
  ALTER TABLE projects ALTER COLUMN title_es SET NOT NULL;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'projects'
        AND column_name = 'title_ar'
    ) THEN
    ALTER TABLE projects DROP COLUMN title_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'projects'
        AND column_name = 'description_ar'
    ) THEN
    ALTER TABLE projects DROP COLUMN description_ar;
  END IF;

  -- Hobbies
  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'hobbies'
        AND column_name = 'name_fr'
    ) THEN
    ALTER TABLE hobbies ADD COLUMN name_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'hobbies'
        AND column_name = 'name_es'
    ) THEN
    ALTER TABLE hobbies ADD COLUMN name_es VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'hobbies'
        AND column_name = 'description_fr'
    ) THEN
    ALTER TABLE hobbies ADD COLUMN description_fr TEXT;
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'hobbies'
        AND column_name = 'description_es'
    ) THEN
    ALTER TABLE hobbies ADD COLUMN description_es TEXT;
  END IF;

  UPDATE hobbies
  SET name_fr = COALESCE(name_fr, name_en),
      name_es = COALESCE(name_es, name_ar, name_en),
      description_fr = COALESCE(description_fr, description_en),
      description_es = COALESCE(description_es, description_ar, description_en);

  ALTER TABLE hobbies ALTER COLUMN name_fr SET NOT NULL;
  ALTER TABLE hobbies ALTER COLUMN name_es SET NOT NULL;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'hobbies'
        AND column_name = 'name_ar'
    ) THEN
    ALTER TABLE hobbies DROP COLUMN name_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'hobbies'
        AND column_name = 'description_ar'
    ) THEN
    ALTER TABLE hobbies DROP COLUMN description_ar;
  END IF;

  -- Skills
  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'skills'
        AND column_name = 'name_fr'
    ) THEN
    ALTER TABLE skills ADD COLUMN name_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'skills'
        AND column_name = 'name_es'
    ) THEN
    ALTER TABLE skills ADD COLUMN name_es VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'skills'
        AND column_name = 'description_fr'
    ) THEN
    ALTER TABLE skills ADD COLUMN description_fr TEXT;
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'skills'
        AND column_name = 'description_es'
    ) THEN
    ALTER TABLE skills ADD COLUMN description_es TEXT;
  END IF;

  UPDATE skills
  SET name_fr = COALESCE(name_fr, name_en),
      name_es = COALESCE(name_es, name_ar, name_en),
      description_fr = COALESCE(description_fr, description_en),
      description_es = COALESCE(description_es, description_ar, description_en);

  ALTER TABLE skills ALTER COLUMN name_fr SET NOT NULL;
  ALTER TABLE skills ALTER COLUMN name_es SET NOT NULL;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'skills'
        AND column_name = 'name_ar'
    ) THEN
    ALTER TABLE skills DROP COLUMN name_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'skills'
        AND column_name = 'description_ar'
    ) THEN
    ALTER TABLE skills DROP COLUMN description_ar;
  END IF;

  -- Work experience
  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'company_name_fr'
    ) THEN
    ALTER TABLE work_experience ADD COLUMN company_name_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'company_name_es'
    ) THEN
    ALTER TABLE work_experience ADD COLUMN company_name_es VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'position_fr'
    ) THEN
    ALTER TABLE work_experience ADD COLUMN position_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'position_es'
    ) THEN
    ALTER TABLE work_experience ADD COLUMN position_es VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'description_fr'
    ) THEN
    ALTER TABLE work_experience ADD COLUMN description_fr TEXT;
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'description_es'
    ) THEN
    ALTER TABLE work_experience ADD COLUMN description_es TEXT;
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'location_fr'
    ) THEN
    ALTER TABLE work_experience ADD COLUMN location_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'location_es'
    ) THEN
    ALTER TABLE work_experience ADD COLUMN location_es VARCHAR(255);
  END IF;

  UPDATE work_experience
  SET company_name_fr = COALESCE(company_name_fr, company_name_en),
      company_name_es = COALESCE(company_name_es, company_name_en),
      position_fr = COALESCE(position_fr, position_en),
      position_es = COALESCE(position_es, position_en),
      description_fr = COALESCE(description_fr, description_en),
      description_es = COALESCE(description_es, description_en),
      location_fr = COALESCE(location_fr, location_en),
      location_es = COALESCE(location_es, location_en);

  ALTER TABLE work_experience ALTER COLUMN company_name_fr SET NOT NULL;
  ALTER TABLE work_experience ALTER COLUMN company_name_es SET NOT NULL;
  ALTER TABLE work_experience ALTER COLUMN position_fr SET NOT NULL;
  ALTER TABLE work_experience ALTER COLUMN position_es SET NOT NULL;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'company_name_ar'
    ) THEN
    ALTER TABLE work_experience DROP COLUMN company_name_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'position_ar'
    ) THEN
    ALTER TABLE work_experience DROP COLUMN position_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'description_ar'
    ) THEN
    ALTER TABLE work_experience DROP COLUMN description_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'work_experience'
        AND column_name = 'location_ar'
    ) THEN
    ALTER TABLE work_experience DROP COLUMN location_ar;
  END IF;

  -- Education
  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'institution_name_fr'
    ) THEN
    ALTER TABLE education ADD COLUMN institution_name_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'institution_name_es'
    ) THEN
    ALTER TABLE education ADD COLUMN institution_name_es VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'degree_fr'
    ) THEN
    ALTER TABLE education ADD COLUMN degree_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'degree_es'
    ) THEN
    ALTER TABLE education ADD COLUMN degree_es VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'field_of_study_fr'
    ) THEN
    ALTER TABLE education ADD COLUMN field_of_study_fr VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'field_of_study_es'
    ) THEN
    ALTER TABLE education ADD COLUMN field_of_study_es VARCHAR(255);
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'description_fr'
    ) THEN
    ALTER TABLE education ADD COLUMN description_fr TEXT;
  END IF;

  IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'description_es'
    ) THEN
    ALTER TABLE education ADD COLUMN description_es TEXT;
  END IF;

  UPDATE education
  SET institution_name_fr = COALESCE(institution_name_fr, institution_name_en),
      institution_name_es = COALESCE(institution_name_es, institution_name_en),
      degree_fr = COALESCE(degree_fr, degree_en),
      degree_es = COALESCE(degree_es, degree_en),
      field_of_study_fr = COALESCE(field_of_study_fr, field_of_study_en),
      field_of_study_es = COALESCE(field_of_study_es, field_of_study_en),
      description_fr = COALESCE(description_fr, description_en),
      description_es = COALESCE(description_es, description_en);

  ALTER TABLE education ALTER COLUMN institution_name_fr SET NOT NULL;
  ALTER TABLE education ALTER COLUMN institution_name_es SET NOT NULL;
  ALTER TABLE education ALTER COLUMN degree_fr SET NOT NULL;
  ALTER TABLE education ALTER COLUMN degree_es SET NOT NULL;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'institution_name_ar'
    ) THEN
    ALTER TABLE education DROP COLUMN institution_name_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'degree_ar'
    ) THEN
    ALTER TABLE education DROP COLUMN degree_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'field_of_study_ar'
    ) THEN
    ALTER TABLE education DROP COLUMN field_of_study_ar;
  END IF;

  IF EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_schema = 'public'
        AND table_name = 'education'
        AND column_name = 'description_ar'
    ) THEN
    ALTER TABLE education DROP COLUMN description_ar;
  END IF;
END $$;
