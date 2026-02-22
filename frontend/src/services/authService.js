import apiClient from './apiClient';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

function throwAuthError(error) {
  const msg = error?.message || 'Authentication failed';
  const e = new Error(msg);
  e.__supabase = true;
  throw e;
}

export const loginUser = async (email, password) => {
  if (!isSupabaseConfigured()) {
    // Legacy backend auth (local dev only).
    return apiClient.post('/v1/auth/login', { email, password });
  }

  assertSupabaseConfigured();
  const { data, error } = await supabase.auth.signInWithPassword({
    email,
    password,
  });
  if (error) throwAuthError(error);
  return toAxiosLikeResponse({ user: data.user, session: data.session });
};

export const getCurrentUser = async () => {
  if (!isSupabaseConfigured()) {
    return apiClient.get('/v1/auth/me');
  }

  assertSupabaseConfigured();
  const { data, error } = await supabase.auth.getUser();
  if (error) throwAuthError(error);
  return toAxiosLikeResponse(data.user);
};

export const logoutUser = async () => {
  if (!isSupabaseConfigured()) {
    return apiClient.post('/v1/auth/logout');
  }

  assertSupabaseConfigured();
  const { error } = await supabase.auth.signOut();
  if (error) throwAuthError(error);
  return toAxiosLikeResponse({ ok: true });
};

export const checkHealth = async () => {
  if (!isSupabaseConfigured()) {
    return apiClient.get('/v1/auth/health');
  }

  // Supabase "health" from the frontend isn't meaningful (network + keys are enough).
  // Return an axios-like response for compatibility.
  return toAxiosLikeResponse({ ok: true });
};
