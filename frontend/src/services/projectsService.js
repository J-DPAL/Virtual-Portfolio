import apiClient from './apiClient';
import { mapProjectDtoToRow, mapProjectRow } from './dbMappers';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

export const getAllProjects = async () => {
  if (!isSupabaseConfigured()) return apiClient.get('/projects');

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('projects')
    .select('*')
    .order('created_at', { ascending: false });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapProjectRow));
};

export const getProjectById = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.get(`/projects/${id}`);

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('projects')
    .select('*')
    .eq('id', id)
    .maybeSingle();
  if (error) throw error;
  return toAxiosLikeResponse(mapProjectRow(data));
};

export const createProject = async (projectData) => {
  if (!isSupabaseConfigured()) return apiClient.post('/projects', projectData);

  assertSupabaseConfigured();
  const row = mapProjectDtoToRow(projectData);
  const { data, error } = await supabase
    .from('projects')
    .insert(row)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapProjectRow(data));
};

export const updateProject = async (id, projectData) => {
  if (!isSupabaseConfigured())
    return apiClient.put(`/projects/${id}`, projectData);

  assertSupabaseConfigured();
  const row = mapProjectDtoToRow(projectData);
  const { data, error } = await supabase
    .from('projects')
    .update(row)
    .eq('id', id)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapProjectRow(data));
};

export const deleteProject = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.delete(`/projects/${id}`);

  assertSupabaseConfigured();
  const { error } = await supabase.from('projects').delete().eq('id', id);
  if (error) throw error;
  return toAxiosLikeResponse({ ok: true });
};
