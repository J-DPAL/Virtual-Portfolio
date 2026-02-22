# Supabase Setup for Monolith Backend

This repo now uses:
- Frontend: React/Vite
- Backend: Spring monolith (`backend/monolith-service`)
- Supabase: Postgres + Auth + Storage

Supabase is infrastructure only. Frontend should call backend endpoints, not Supabase directly.

## Run SQL in order
1. `deploy/supabase/schema.sql`
1. `deploy/supabase/rls.sql`
1. `deploy/supabase/seed.sql`

## Create admin auth user
1. Supabase -> Authentication -> Users -> Add user
1. Copy user UUID
1. Run:
```sql
insert into public.admin_users(user_id)
values ('YOUR_AUTH_USER_UUID')
on conflict do nothing;
```

## Storage bucket
Create bucket:
- Name: `resumes`
- Public: ON

Upload files with either naming style:
- `resume_en.pdf`, `resume_fr.pdf`, `resume_es.pdf`
- or `CV_JD_EN.pdf`, `CV_JD_FR.pdf`, `CV_JD_ES.pdf`

## Backend env vars (required)
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SUPABASE_URL`
- `SUPABASE_ANON_KEY`
- `SUPABASE_SERVICE_ROLE_KEY`

## Frontend env vars (required)
- `VITE_API_BASE_URL`
- `VITE_ADMIN_EMAILS`
- `VITE_SUPABASE_DIRECT=false`

## Notes
- Do not expose service-role keys in frontend env files.
- Contact anti-spam can use Turnstile via backend (`TURNSTILE_*` env vars).
