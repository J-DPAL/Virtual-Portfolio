import apiClient from './apiClient';
import { mapEducationDtoToRow, mapEducationRow } from './dbMappers';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

export const getAllEducation = async () => {
  if (!isSupabaseConfigured()) return apiClient.get('/education');

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('education')
    .select('*')
    .order('start_date', { ascending: false });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapEducationRow));
};

export const getEducationById = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.get(`/education/${id}`);

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('education')
    .select('*')
    .eq('id', id)
    .maybeSingle();
  if (error) throw error;
  return toAxiosLikeResponse(mapEducationRow(data));
};

export const createEducation = async (educationData) => {
  if (!isSupabaseConfigured())
    return apiClient.post('/education', educationData);

  assertSupabaseConfigured();
  const row = mapEducationDtoToRow(educationData);
  const { data, error } = await supabase
    .from('education')
    .insert(row)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapEducationRow(data));
};

export const updateEducation = async (id, educationData) => {
  if (!isSupabaseConfigured())
    return apiClient.put(`/education/${id}`, educationData);

  assertSupabaseConfigured();
  const row = mapEducationDtoToRow(educationData);
  const { data, error } = await supabase
    .from('education')
    .update(row)
    .eq('id', id)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapEducationRow(data));
};

export const deleteEducation = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.delete(`/education/${id}`);

  assertSupabaseConfigured();
  const { error } = await supabase.from('education').delete().eq('id', id);
  if (error) throw error;
  return toAxiosLikeResponse({ ok: true });
};
