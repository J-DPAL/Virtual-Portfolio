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

-- Index on email for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Insert default admin user (password: admin123 - should be changed in production)
-- Use bcrypt to hash password: $2a$10$slYQmyNdGzin7olVN3p5Be
INSERT INTO users (email, password, full_name, role, active) 
VALUES ('admin@portfolio.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7.5xwlm/EhIxYDXxMl.rM6SqKOUIxGi', 'Admin User', 'ADMIN', true)
ON CONFLICT DO NOTHING;
