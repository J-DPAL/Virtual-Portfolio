CREATE TABLE IF NOT EXISTS education (
    id BIGSERIAL PRIMARY KEY,
    institution_name_en VARCHAR(255) NOT NULL,
    institution_name_fr VARCHAR(255) NOT NULL,
    institution_name_es VARCHAR(255) NOT NULL,
    degree_en VARCHAR(255) NOT NULL,
    degree_fr VARCHAR(255) NOT NULL,
    degree_es VARCHAR(255) NOT NULL,
    field_of_study_en VARCHAR(255) NOT NULL,
    field_of_study_fr VARCHAR(255) NOT NULL,
    field_of_study_es VARCHAR(255) NOT NULL,
    description_en TEXT,
    description_fr TEXT,
    description_es TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    is_current BOOLEAN NOT NULL DEFAULT false,
    gpa DECIMAL(3,2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_education_dates ON education(start_date, end_date);
