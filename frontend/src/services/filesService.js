import apiClient from './apiClient';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

const RESUME_BUCKET = 'resumes';

function normalizeLang(language) {
  const lang = (language || 'en').toString().toLowerCase();
  if (lang === 'fr' || lang === 'en' || lang === 'es') return lang;
  return 'en';
}

function resolveConfiguredResumePath(language) {
  const lang = normalizeLang(language);
  const envPathByLang = {
    en: import.meta.env.VITE_RESUME_FILE_EN,
    fr: import.meta.env.VITE_RESUME_FILE_FR,
    es: import.meta.env.VITE_RESUME_FILE_ES,
  };

  const configured = envPathByLang[lang];
  if (configured && configured.trim()) {
    return configured.trim();
  }

  return `resume_${lang}.pdf`;
}

function resumePath(language) {
  return resolveConfiguredResumePath(language);
}

function resumeFallbackPaths(language) {
  const lang = normalizeLang(language);
  const upper = lang.toUpperCase();
  const paths = [
    resumePath(lang),
    `resume_${lang}.pdf`,
    `CV_JD_${upper}.pdf`,
  ];

  // Keep first occurrence of each path.
  return [...new Set(paths)];
}

export function getResumeDownloadFileName(language) {
  const path = resumePath(language);
  const parts = path.split('/');
  return parts[parts.length - 1] || `resume_${normalizeLang(language)}.pdf`;
}

function toAxiosLikeResponse(data) {
  return { data };
}

export const uploadResume = async (file, language) => {
  if (!isSupabaseConfigured()) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('language', language);

    return apiClient.post('/v1/files/resume/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
  }

  assertSupabaseConfigured();
  const path = resumePath(language);

  const { error } = await supabase.storage
    .from(RESUME_BUCKET)
    .upload(path, file, {
      upsert: true,
      contentType: file?.type || 'application/pdf',
    });

  if (error) throw error;
  return toAxiosLikeResponse({ ok: true, path });
};

export const downloadResume = async (language = 'en') => {
  if (!isSupabaseConfigured()) {
    return apiClient.get(`/v1/files/resume/download`, {
      params: { language },
      responseType: 'blob',
    });
  }

  assertSupabaseConfigured();
  // Easiest path: make the `resumes` bucket public in Supabase Storage.
  // Then `getPublicUrl` works from the browser without any secret key.
  for (const path of resumeFallbackPaths(language)) {
    const { data } = supabase.storage.from(RESUME_BUCKET).getPublicUrl(path);
    const url = data?.publicUrl;
    if (!url) continue;

    const res = await fetch(url);
    if (!res.ok) {
      continue;
    }

    const blob = await res.blob();
    return toAxiosLikeResponse(blob);
  }

  throw new Error('Resume file not found');
};
