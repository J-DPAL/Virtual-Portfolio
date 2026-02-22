import apiClient from './apiClient';
import { mapSkillDtoToRow, mapSkillRow } from './dbMappers';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

export const getAllSkills = async () => {
  if (!isSupabaseConfigured()) return apiClient.get('/skills');

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('skills')
    .select('*')
    .order('category', { ascending: true })
    .order('name_en', { ascending: true });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapSkillRow));
};

export const getSkillById = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.get(`/skills/${id}`);

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('skills')
    .select('*')
    .eq('id', id)
    .maybeSingle();
  if (error) throw error;
  return toAxiosLikeResponse(mapSkillRow(data));
};

export const createSkill = async (skillData) => {
  if (!isSupabaseConfigured()) return apiClient.post('/skills', skillData);

  assertSupabaseConfigured();
  const row = mapSkillDtoToRow(skillData);
  const { data, error } = await supabase
    .from('skills')
    .insert(row)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapSkillRow(data));
};

export const updateSkill = async (id, skillData) => {
  if (!isSupabaseConfigured()) return apiClient.put(`/skills/${id}`, skillData);

  assertSupabaseConfigured();
  const row = mapSkillDtoToRow(skillData);
  const { data, error } = await supabase
    .from('skills')
    .update(row)
    .eq('id', id)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapSkillRow(data));
};

export const deleteSkill = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.delete(`/skills/${id}`);

  assertSupabaseConfigured();
  const { error } = await supabase.from('skills').delete().eq('id', id);
  if (error) throw error;
  return toAxiosLikeResponse({ ok: true });
};
