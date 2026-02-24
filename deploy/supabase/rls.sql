-- Supabase RLS policies for the monolith deploy mode (Frontend + Supabase).
--
-- Run this AFTER deploy/supabase/schema.sql.
--
-- Security model:
-- - Public read for portfolio content
-- - Admin-only write for portfolio content, messages moderation, testimonial moderation, storage upload
-- - Public contact message insert
-- - Public testimonial insert, forced to PENDING
--
-- Admin identity:
-- - Uses auth.users + public.admin_users table
-- - Add your admin user id into public.admin_users after creating the user in Auth.

-- 1) Admin helpers
create table if not exists public.admin_users (
  user_id uuid primary key references auth.users(id) on delete cascade,
  created_at timestamptz not null default now()
);

create or replace function public.is_admin()
returns boolean
language sql
stable
as $$
  select exists (
    select 1
    from public.admin_users a
    where a.user_id = auth.uid()
  );
$$;

-- 2) Enable RLS
alter table public.projects enable row level security;
alter table public.skills enable row level security;
alter table public.education enable row level security;
alter table public.work_experience enable row level security;
alter table public.hobbies enable row level security;
alter table public.testimonials enable row level security;
alter table public.messages enable row level security;

-- 3) PROJECTS
drop policy if exists "Public read" on public.projects;
drop policy if exists "Authenticated write" on public.projects;
drop policy if exists "Admin write" on public.projects;
create policy "Public read" on public.projects
for select
to anon, authenticated
using (true);

create policy "Admin write" on public.projects
for all
to authenticated
using (public.is_admin())
with check (public.is_admin());

-- 4) SKILLS
drop policy if exists "Public read" on public.skills;
drop policy if exists "Authenticated write" on public.skills;
drop policy if exists "Admin write" on public.skills;
create policy "Public read" on public.skills
for select
to anon, authenticated
using (true);

create policy "Admin write" on public.skills
for all
to authenticated
using (public.is_admin())
with check (public.is_admin());

-- 5) EDUCATION
drop policy if exists "Public read" on public.education;
drop policy if exists "Authenticated write" on public.education;
drop policy if exists "Admin write" on public.education;
create policy "Public read" on public.education
for select
to anon, authenticated
using (true);

create policy "Admin write" on public.education
for all
to authenticated
using (public.is_admin())
with check (public.is_admin());

-- 6) WORK EXPERIENCE
drop policy if exists "Public read" on public.work_experience;
drop policy if exists "Authenticated write" on public.work_experience;
drop policy if exists "Admin write" on public.work_experience;
create policy "Public read" on public.work_experience
for select
to anon, authenticated
using (true);

create policy "Admin write" on public.work_experience
for all
to authenticated
using (public.is_admin())
with check (public.is_admin());

-- 7) HOBBIES
drop policy if exists "Public read" on public.hobbies;
drop policy if exists "Authenticated write" on public.hobbies;
drop policy if exists "Admin write" on public.hobbies;
create policy "Public read" on public.hobbies
for select
to anon, authenticated
using (true);

create policy "Admin write" on public.hobbies
for all
to authenticated
using (public.is_admin())
with check (public.is_admin());

-- 8) MESSAGES
-- Public can create (contact form)
drop policy if exists "Public create" on public.messages;
create policy "Public create" on public.messages
for insert
to anon, authenticated
with check (true);

-- Admin-only moderation
drop policy if exists "Authenticated manage" on public.messages;
drop policy if exists "Authenticated read" on public.messages;
drop policy if exists "Authenticated update" on public.messages;
drop policy if exists "Authenticated delete" on public.messages;
drop policy if exists "Admin read" on public.messages;
drop policy if exists "Admin update" on public.messages;
drop policy if exists "Admin delete" on public.messages;

create policy "Admin read" on public.messages
for select
to authenticated
using (public.is_admin());

create policy "Admin update" on public.messages
for update
to authenticated
using (public.is_admin())
with check (public.is_admin());

create policy "Admin delete" on public.messages
for delete
to authenticated
using (public.is_admin());

-- 9) TESTIMONIALS
-- Public sees only approved
drop policy if exists "Public read approved" on public.testimonials;
create policy "Public read approved" on public.testimonials
for select
to anon, authenticated
using (status = 'APPROVED');

-- Public submission stays pending
drop policy if exists "Public create pending" on public.testimonials;
create policy "Public create pending" on public.testimonials
for insert
to anon, authenticated
with check (status = 'PENDING');

-- Admin can see all + moderate
drop policy if exists "Authenticated read all" on public.testimonials;
drop policy if exists "Authenticated update/delete" on public.testimonials;
drop policy if exists "Authenticated update" on public.testimonials;
drop policy if exists "Authenticated delete" on public.testimonials;
drop policy if exists "Admin read all" on public.testimonials;
drop policy if exists "Admin update" on public.testimonials;
drop policy if exists "Admin delete" on public.testimonials;

create policy "Admin read all" on public.testimonials
for select
to authenticated
using (public.is_admin());

create policy "Admin update" on public.testimonials
for update
to authenticated
using (public.is_admin())
with check (public.is_admin());

create policy "Admin delete" on public.testimonials
for delete
to authenticated
using (public.is_admin());

-- 9b) Admin moderation guardrail:
-- allow status transitions but block edits to testimonial content fields.
create or replace function public.prevent_testimonial_content_edit()
returns trigger
language plpgsql
as $$
begin
  if new.client_name is distinct from old.client_name
    or new.client_position is distinct from old.client_position
    or new.client_company is distinct from old.client_company
    or new.testimonial_text_en is distinct from old.testimonial_text_en
    or new.testimonial_text_fr is distinct from old.testimonial_text_fr
    or new.testimonial_text_es is distinct from old.testimonial_text_es
    or new.rating is distinct from old.rating
    or new.client_image_url is distinct from old.client_image_url
  then
    raise exception 'Editing testimonial content is not allowed. Only moderation status is editable.';
  end if;

  return new;
end;
$$;

drop trigger if exists trg_prevent_testimonial_content_edit on public.testimonials;
create trigger trg_prevent_testimonial_content_edit
before update on public.testimonials
for each row execute function public.prevent_testimonial_content_edit();

-- 10) STORAGE policies for resumes bucket
-- Storage policies target storage.objects table.
drop policy if exists "Public read resumes" on storage.objects;
create policy "Public read resumes" on storage.objects
for select
to anon, authenticated
using (bucket_id = 'resumes');

drop policy if exists "Authenticated write resumes" on storage.objects;
drop policy if exists "Admin write resumes" on storage.objects;
create policy "Admin write resumes" on storage.objects
for all
to authenticated
using (bucket_id = 'resumes' and public.is_admin())
with check (bucket_id = 'resumes' and public.is_admin());
