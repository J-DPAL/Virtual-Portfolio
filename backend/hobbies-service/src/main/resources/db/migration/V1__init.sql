CREATE TABLE IF NOT EXISTS hobbies (
    id BIGSERIAL PRIMARY KEY,
    name_en VARCHAR(255) NOT NULL,
    name_fr VARCHAR(255) NOT NULL,
    name_es VARCHAR(255) NOT NULL,
    description_en TEXT,
    description_fr TEXT,
    description_es TEXT,
    icon VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
