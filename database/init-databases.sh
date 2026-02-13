#!/bin/bash
# init-databases.sh - create per-service databases and apply schema files

set -e

DBS=(
  "users_db"
  "skills_db"
  "projects_db"
  "experience_db"
  "education_db"
  "hobbies_db"
  "testimonials_db"
  "messages_db"
  "files_db"
)

for db in "${DBS[@]}"; do
  echo "Ensuring database exists: ${db}"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    SELECT 'CREATE DATABASE ${db}'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '${db}')\gexec
EOSQL
done

echo "Databases created. Schema migrations are handled by Flyway on service startup."
