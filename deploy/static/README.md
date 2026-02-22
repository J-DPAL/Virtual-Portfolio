# Static Frontend Deployment (Monolith Backend)

Deploy frontend as static files and point it to your backend monolith API.

## Required frontend env vars
- `VITE_API_BASE_URL=https://YOUR_BACKEND_DOMAIN/api`
- `VITE_ADMIN_EMAILS=you@example.com`
- `VITE_SUPABASE_DIRECT=false`

Optional:
- `VITE_TURNSTILE_SITE_KEY`

## Cloudflare Pages example
1. Connect repository.
1. Build command:
   - `cd frontend && npm ci && npm run build`
1. Build output:
   - `frontend/dist`
1. Add env vars listed above.
1. Deploy.

## Local test
```powershell
cd frontend
npm ci
npm run dev
```

Make sure backend is running and `VITE_API_BASE_URL` points to it.
