CREATE TABLE IF NOT EXISTS skills (
    id BIGSERIAL PRIMARY KEY,
    name_en VARCHAR(255) NOT NULL,
    name_fr VARCHAR(255) NOT NULL,
    name_es VARCHAR(255) NOT NULL,
    description_en TEXT,
    description_fr TEXT,
    description_es TEXT,
    proficiency_level VARCHAR(50) NOT NULL,
    category VARCHAR(100) NOT NULL,
    years_of_experience INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_skills_category ON skills(category);
