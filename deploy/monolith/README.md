# Monolith Deployment (frontend + backend + Supabase)

Target architecture:
- Frontend (React/Vite)
- Backend monolith (`backend/monolith-service`)
- Supabase for Postgres + Auth + Storage

## 1) Supabase setup
Run in SQL Editor (in order):
1. `deploy/supabase/schema.sql`
1. `deploy/supabase/rls.sql`
1. `deploy/supabase/seed.sql`

Create admin user in Supabase Auth, then link it in DB:
```sql
insert into public.admin_users(user_id)
values ('YOUR_AUTH_USER_UUID')
on conflict do nothing;
```

Create Storage bucket:
- Name: `resumes`
- Public: ON

## 2) Backend env vars (monolith)
Required:
- `SPRING_DATASOURCE_URL` (Supabase Postgres URI)
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SUPABASE_URL` (https://YOUR_REF.supabase.co)
- `SUPABASE_ANON_KEY`
- `SUPABASE_SERVICE_ROLE_KEY`

Recommended:
- `APP_CORS_ALLOWED_ORIGINS` (comma-separated frontend origins)
- `APP_AUTH_COOKIE_FORCE_SECURE` (`true` if deployed behind proxy/CDN and cookies are not marked secure)
- `CONTACT_IP_HASH_SALT`
- `TURNSTILE_ENABLED`
- `TURNSTILE_SECRET_KEY`
- `TURNSTILE_MINIMUM_SCORE`

Optional:
- `RESUME_FILE_EN`, `RESUME_FILE_FR`, `RESUME_FILE_ES`
- `SUPABASE_RESUME_BUCKET` (default `resumes`)

## 3) Frontend env vars
Required:
- `VITE_API_BASE_URL=https://YOUR_BACKEND_DOMAIN/api`
- `VITE_ADMIN_EMAILS=you@example.com`

Recommended:
- `VITE_SUPABASE_DIRECT=false`

Optional:
- `VITE_TURNSTILE_SITE_KEY`

## 4) Local run
Backend:
```powershell
cd backend
mvn -pl monolith-service -am spring-boot:run
```

Frontend:
```powershell
cd frontend
npm ci
npm run dev
```

## 5) Docker build for backend
`backend/Dockerfile` now builds the monolith module:
```bash
docker build -t portfolio-monolith ./backend
```
