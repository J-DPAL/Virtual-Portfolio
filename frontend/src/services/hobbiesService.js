import apiClient from './apiClient';
import { mapHobbyDtoToRow, mapHobbyRow } from './dbMappers';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

export const getAllHobbies = async () => {
  if (!isSupabaseConfigured()) return apiClient.get('/hobbies');

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('hobbies')
    .select('*')
    .order('created_at', { ascending: false });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapHobbyRow));
};

export const getHobbyById = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.get(`/hobbies/${id}`);

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('hobbies')
    .select('*')
    .eq('id', id)
    .maybeSingle();
  if (error) throw error;
  return toAxiosLikeResponse(mapHobbyRow(data));
};

export const createHobby = async (hobbyData) => {
  if (!isSupabaseConfigured()) return apiClient.post('/hobbies', hobbyData);

  assertSupabaseConfigured();
  const row = mapHobbyDtoToRow(hobbyData);
  const { data, error } = await supabase
    .from('hobbies')
    .insert(row)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapHobbyRow(data));
};

export const updateHobby = async (id, hobbyData) => {
  if (!isSupabaseConfigured()) return apiClient.put(`/hobbies/${id}`, hobbyData);

  assertSupabaseConfigured();
  const row = mapHobbyDtoToRow(hobbyData);
  const { data, error } = await supabase
    .from('hobbies')
    .update(row)
    .eq('id', id)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapHobbyRow(data));
};

export const deleteHobby = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.delete(`/hobbies/${id}`);

  assertSupabaseConfigured();
  const { error } = await supabase.from('hobbies').delete().eq('id', id);
  if (error) throw error;
  return toAxiosLikeResponse({ ok: true });
};
