import { createClient } from '@supabase/supabase-js';

const SUPABASE_URL = import.meta.env.VITE_SUPABASE_URL;
const SUPABASE_ANON_KEY = import.meta.env.VITE_SUPABASE_ANON_KEY;
const DIRECT_MODE_RAW = (import.meta.env.VITE_SUPABASE_DIRECT || '')
  .toString()
  .trim()
  .toLowerCase();

const HAS_SUPABASE_KEYS = Boolean(SUPABASE_URL && SUPABASE_ANON_KEY);

// Auto-enable direct mode when keys exist, unless explicitly disabled.
const SUPABASE_DIRECT =
  DIRECT_MODE_RAW === 'true'
    ? true
    : DIRECT_MODE_RAW === 'false'
      ? false
      : HAS_SUPABASE_KEYS;

export function isSupabaseConfigured() {
  return Boolean(SUPABASE_DIRECT && HAS_SUPABASE_KEYS);
}

export const supabase = isSupabaseConfigured()
  ? createClient(SUPABASE_URL, SUPABASE_ANON_KEY, {
      auth: {
        persistSession: true,
        autoRefreshToken: true,
        detectSessionInUrl: true,
      },
    })
  : null;

export function assertSupabaseConfigured() {
  if (isSupabaseConfigured()) return;
  throw new Error(
    'Supabase direct mode is disabled or not configured. Set VITE_SUPABASE_DIRECT=true (or leave it unset for auto mode) and provide VITE_SUPABASE_URL + VITE_SUPABASE_ANON_KEY.'
  );
}
