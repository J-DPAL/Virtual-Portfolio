# Virtual Portfolio

Frontend + backend monolith architecture:

- `frontend/` (React + Vite)
- `backend/monolith-service/` (Spring Boot API)
- Supabase (Postgres + Auth + Storage)

This repository no longer uses the old microservices layout.

## Architecture

```text
frontend -> backend monolith -> supabase
```

- Frontend calls backend API (`/api/...`).
- Backend handles business logic, validation, anti-spam checks, and authorization.
- Supabase is used as infrastructure (database, auth provider, storage bucket).

## Quick Start (Local)

1. Configure root env:
   - Copy `.env.example` to `.env`
   - Fill Supabase and app values
2. Run backend:
   - `cd backend`
   - `mvn -pl monolith-service -am spring-boot:run`
3. Run frontend:
   - `cd frontend`
   - `npm ci`
   - `npm run dev`

Frontend runs on `http://localhost:5173` and backend on `http://localhost:8080/api`.

## Docker (Local)

Use root compose:

```bash
docker compose up --build
```

It starts:
- `monolith-service` on `8080`
- `frontend` on `3000`

## Deployment Guides

- Monolith deployment: `deploy/monolith/README.md`
- Supabase setup: `deploy/supabase/README.md`
- Static frontend deployment: `deploy/static/README.md`

## Important Env Vars

Backend:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SUPABASE_URL`
- `SUPABASE_ANON_KEY`
- `SUPABASE_SERVICE_ROLE_KEY`
- `APP_CORS_ALLOWED_ORIGINS`

Frontend:
- `VITE_API_BASE_URL`
- `VITE_ADMIN_EMAILS`
- `VITE_SUPABASE_DIRECT=false`

## Notes

- Do not expose `SUPABASE_SERVICE_ROLE_KEY` in frontend variables.
- Keep `VITE_SUPABASE_DIRECT=false` for normal operation.
- Seed and RLS scripts are in `deploy/supabase/`.
