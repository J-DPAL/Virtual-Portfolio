INSERT INTO users (email, password, full_name, role, active)
SELECT
    '${admin_email}',
    '${admin_password_hash}',
    COALESCE(NULLIF('${admin_full_name}', ''), 'Admin User'),
    'ADMIN',
    true
WHERE NULLIF('${admin_email}', '') IS NOT NULL
  AND NULLIF('${admin_password_hash}', '') IS NOT NULL
ON CONFLICT (email) DO UPDATE
SET password = EXCLUDED.password,
    full_name = EXCLUDED.full_name,
    role = EXCLUDED.role,
    active = EXCLUDED.active;
