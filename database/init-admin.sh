#!/bin/bash
# init-admin.sh - Initialize admin user from environment variables
# This script is executed by docker-entrypoint-initdb.d after init.sql

set -e

# Check if required environment variables are set
if [ -z "$ADMIN_EMAIL" ] || [ -z "$ADMIN_PASSWORD_HASH" ]; then
    echo "WARNING: ADMIN_EMAIL or ADMIN_PASSWORD_HASH not set. Admin user will not be created."
    echo "Please set these environment variables in your .env file."
    exit 0
fi

# Set default full name if not provided
ADMIN_FULL_NAME=${ADMIN_FULL_NAME:-"Admin User"}

echo "Creating admin user with email: $ADMIN_EMAIL"

USERS_DB_NAME=${USERS_DB_NAME:-"users_db"}

# Insert admin user using environment variables
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$USERS_DB_NAME" <<-EOSQL
    INSERT INTO users (email, password, full_name, role, active) 
    VALUES ('$ADMIN_EMAIL', '$ADMIN_PASSWORD_HASH', '$ADMIN_FULL_NAME', 'ADMIN', true)
    ON CONFLICT (email) DO UPDATE 
    SET password = EXCLUDED.password,
        full_name = EXCLUDED.full_name,
        role = EXCLUDED.role,
        active = EXCLUDED.active;
EOSQL

echo "Admin user created/updated successfully!"
