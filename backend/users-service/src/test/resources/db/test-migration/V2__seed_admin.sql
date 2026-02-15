INSERT INTO users (email, password, full_name, role, active)
SELECT
    '${admin_email}',
    '${admin_password_hash}',
    COALESCE(NULLIF('${admin_full_name}', ''), 'Admin User'),
    'ADMIN',
    true
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = '${admin_email}'
);
