-- Initial database schema
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Skills table
CREATE TABLE IF NOT EXISTS skills (
    id BIGSERIAL PRIMARY KEY,
    name_en VARCHAR(255) NOT NULL,
    name_ar VARCHAR(255) NOT NULL,
    description_en TEXT,
    description_ar TEXT,
    proficiency_level VARCHAR(50) NOT NULL,
    category VARCHAR(100) NOT NULL,
    years_of_experience INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Projects table
CREATE TABLE IF NOT EXISTS projects (
    id BIGSERIAL PRIMARY KEY,
    title_en VARCHAR(255) NOT NULL,
    title_ar VARCHAR(255) NOT NULL,
    description_en TEXT,
    description_ar TEXT,
    technologies TEXT,
    project_url VARCHAR(500),
    github_url VARCHAR(500),
    image_url VARCHAR(500),
    start_date DATE,
    end_date DATE,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Work Experience table
CREATE TABLE IF NOT EXISTS work_experience (
    id BIGSERIAL PRIMARY KEY,
    company_name_en VARCHAR(255) NOT NULL,
    company_name_ar VARCHAR(255) NOT NULL,
    position_en VARCHAR(255) NOT NULL,
    position_ar VARCHAR(255) NOT NULL,
    description_en TEXT,
    description_ar TEXT,
    location_en VARCHAR(255),
    location_ar VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE,
    is_current BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Education table
CREATE TABLE IF NOT EXISTS education (
    id BIGSERIAL PRIMARY KEY,
    institution_name_en VARCHAR(255) NOT NULL,
    institution_name_ar VARCHAR(255) NOT NULL,
    degree_en VARCHAR(255) NOT NULL,
    degree_ar VARCHAR(255) NOT NULL,
    field_of_study_en VARCHAR(255),
    field_of_study_ar VARCHAR(255),
    description_en TEXT,
    description_ar TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    is_current BOOLEAN DEFAULT false,
    gpa DECIMAL(3,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Hobbies table
CREATE TABLE IF NOT EXISTS hobbies (
    id BIGSERIAL PRIMARY KEY,
    name_en VARCHAR(255) NOT NULL,
    name_ar VARCHAR(255) NOT NULL,
    description_en TEXT,
    description_ar TEXT,
    icon VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Messages table (Contact form submissions)
CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    sender_name VARCHAR(255) NOT NULL,
    sender_email VARCHAR(255) NOT NULL,
    subject VARCHAR(500),
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Testimonials table
CREATE TABLE IF NOT EXISTS testimonials (
    id BIGSERIAL PRIMARY KEY,
    client_name VARCHAR(255) NOT NULL,
    client_position VARCHAR(255),
    client_company VARCHAR(255),
    testimonial_text_en TEXT NOT NULL,
    testimonial_text_ar TEXT NOT NULL,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    client_image_url VARCHAR(500),
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index on email for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_skills_category ON skills(category);
CREATE INDEX IF NOT EXISTS idx_projects_status ON projects(status);
CREATE INDEX IF NOT EXISTS idx_work_experience_dates ON work_experience(start_date, end_date);
CREATE INDEX IF NOT EXISTS idx_education_dates ON education(start_date, end_date);
CREATE INDEX IF NOT EXISTS idx_messages_read ON messages(is_read);
CREATE INDEX IF NOT EXISTS idx_testimonials_status ON testimonials(status);

-- Insert default admin user (password: admin123 - should be changed in production)
-- BCrypt hash generated with cost 10
INSERT INTO users (email, password, full_name, role, active) 
VALUES ('admin@portfolio.com', '$2a$10$DNrpFf/oVX7ug4BkCTgVv.xEscg7I2Ri5D5qO9NEgXif/pneqs92C', 'Admin User', 'ADMIN', true)
ON CONFLICT DO NOTHING;
