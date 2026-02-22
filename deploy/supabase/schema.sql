-- Supabase schema for Option A (Supabase Backend + Static Frontend)
--
-- Run this in Supabase SQL Editor.
-- This creates the minimum set of tables required by the frontend Supabase mode.

-- Keep timestamps consistent and auto-maintain updated_at.
create or replace function public.set_updated_at()
returns trigger
language plpgsql
as $$
begin
  new.updated_at = now();
  return new;
end;
$$;

create table if not exists public.projects (
  id bigserial primary key,
  title_en varchar(255) not null,
  title_fr varchar(255) not null,
  title_es varchar(255) not null,
  description_en text,
  description_fr text,
  description_es text,
  technologies text,
  project_url varchar(500),
  github_url varchar(500),
  image_url varchar(500),
  start_date date,
  end_date date,
  status varchar(50) not null,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create index if not exists idx_projects_status on public.projects(status);

drop trigger if exists trg_projects_updated_at on public.projects;
create trigger trg_projects_updated_at
before update on public.projects
for each row execute function public.set_updated_at();

create table if not exists public.skills (
  id bigserial primary key,
  name_en varchar(255) not null,
  name_fr varchar(255) not null,
  name_es varchar(255) not null,
  description_en text,
  description_fr text,
  description_es text,
  proficiency_level varchar(50) not null,
  category varchar(100) not null,
  years_of_experience integer,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create index if not exists idx_skills_category on public.skills(category);

drop trigger if exists trg_skills_updated_at on public.skills;
create trigger trg_skills_updated_at
before update on public.skills
for each row execute function public.set_updated_at();

create table if not exists public.education (
  id bigserial primary key,
  institution_name_en varchar(255) not null,
  institution_name_fr varchar(255) not null,
  institution_name_es varchar(255) not null,
  degree_en varchar(255) not null,
  degree_fr varchar(255) not null,
  degree_es varchar(255) not null,
  field_of_study_en varchar(255) not null,
  field_of_study_fr varchar(255) not null,
  field_of_study_es varchar(255) not null,
  description_en text,
  description_fr text,
  description_es text,
  start_date date not null,
  end_date date,
  is_current boolean not null default false,
  gpa numeric(3,2),
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create index if not exists idx_education_dates on public.education(start_date, end_date);

drop trigger if exists trg_education_updated_at on public.education;
create trigger trg_education_updated_at
before update on public.education
for each row execute function public.set_updated_at();

create table if not exists public.work_experience (
  id bigserial primary key,
  company_name_en varchar(255) not null,
  company_name_fr varchar(255) not null,
  company_name_es varchar(255) not null,
  position_en varchar(255) not null,
  position_fr varchar(255) not null,
  position_es varchar(255) not null,
  description_en text,
  description_fr text,
  description_es text,
  location_en varchar(255),
  location_fr varchar(255),
  location_es varchar(255),
  start_date date not null,
  end_date date,
  is_current boolean not null default false,
  skills_used varchar(500),
  icon varchar(100),
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create index if not exists idx_work_experience_dates on public.work_experience(start_date, end_date);

drop trigger if exists trg_work_experience_updated_at on public.work_experience;
create trigger trg_work_experience_updated_at
before update on public.work_experience
for each row execute function public.set_updated_at();

create table if not exists public.hobbies (
  id bigserial primary key,
  name_en varchar(255) not null,
  name_fr varchar(255) not null,
  name_es varchar(255) not null,
  description_en text,
  description_fr text,
  description_es text,
  icon varchar(100),
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

drop trigger if exists trg_hobbies_updated_at on public.hobbies;
create trigger trg_hobbies_updated_at
before update on public.hobbies
for each row execute function public.set_updated_at();

create table if not exists public.testimonials (
  id bigserial primary key,
  client_name varchar(255) not null,
  client_position varchar(255) not null,
  client_company varchar(255) not null,
  testimonial_text_en text not null,
  testimonial_text_fr text not null,
  testimonial_text_es text not null,
  rating integer not null check (rating >= 1 and rating <= 5),
  client_image_url varchar(500),
  status varchar(50) not null default 'PENDING',
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

alter table public.testimonials drop constraint if exists testimonials_status_chk;
alter table public.testimonials
  add constraint testimonials_status_chk check (status in ('PENDING', 'APPROVED', 'REJECTED'));

create index if not exists idx_testimonials_status on public.testimonials(status);

drop trigger if exists trg_testimonials_updated_at on public.testimonials;
create trigger trg_testimonials_updated_at
before update on public.testimonials
for each row execute function public.set_updated_at();

create table if not exists public.messages (
  id bigserial primary key,
  sender_name varchar(255) not null,
  sender_email varchar(255) not null,
  subject varchar(500) not null,
  message text not null,
  is_read boolean not null default false,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create index if not exists idx_messages_read on public.messages(is_read);

drop trigger if exists trg_messages_updated_at on public.messages;
create trigger trg_messages_updated_at
before update on public.messages
for each row execute function public.set_updated_at();
