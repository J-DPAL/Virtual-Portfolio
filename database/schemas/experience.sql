CREATE TABLE IF NOT EXISTS work_experience (
    id BIGSERIAL PRIMARY KEY,
    company_name_en VARCHAR(255) NOT NULL,
    company_name_fr VARCHAR(255) NOT NULL,
    company_name_es VARCHAR(255) NOT NULL,
    position_en VARCHAR(255) NOT NULL,
    position_fr VARCHAR(255) NOT NULL,
    position_es VARCHAR(255) NOT NULL,
    description_en TEXT,
    description_fr TEXT,
    description_es TEXT,
    location_en VARCHAR(255),
    location_fr VARCHAR(255),
    location_es VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE,
    is_current BOOLEAN NOT NULL DEFAULT false,
    skills_used VARCHAR(500),
    icon VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_work_experience_dates ON work_experience(start_date, end_date);
