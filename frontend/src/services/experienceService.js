import apiClient from './apiClient';
import { mapExperienceDtoToRow, mapExperienceRow } from './dbMappers';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

export const getAllExperiences = async () => {
  if (!isSupabaseConfigured()) return apiClient.get('/experience');

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('work_experience')
    .select('*')
    .order('start_date', { ascending: false });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapExperienceRow));
};

export const getExperienceById = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.get(`/experience/${id}`);

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('work_experience')
    .select('*')
    .eq('id', id)
    .maybeSingle();
  if (error) throw error;
  return toAxiosLikeResponse(mapExperienceRow(data));
};

export const createExperience = async (experienceData) => {
  if (!isSupabaseConfigured())
    return apiClient.post('/experience', experienceData);

  assertSupabaseConfigured();
  const row = mapExperienceDtoToRow(experienceData);
  const { data, error } = await supabase
    .from('work_experience')
    .insert(row)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapExperienceRow(data));
};

export const updateExperience = async (id, experienceData) => {
  if (!isSupabaseConfigured())
    return apiClient.put(`/experience/${id}`, experienceData);

  assertSupabaseConfigured();
  const row = mapExperienceDtoToRow(experienceData);
  const { data, error } = await supabase
    .from('work_experience')
    .update(row)
    .eq('id', id)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapExperienceRow(data));
};

export const deleteExperience = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.delete(`/experience/${id}`);

  assertSupabaseConfigured();
  const { error } = await supabase.from('work_experience').delete().eq('id', id);
  if (error) throw error;
  return toAxiosLikeResponse({ ok: true });
};
