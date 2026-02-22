import apiClient from './apiClient';
import { mapTestimonialDtoToRow, mapTestimonialRow } from './dbMappers';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

export const getAllTestimonials = async () => {
  // Public: return approved only
  return getApprovedTestimonials();
};

export const getApprovedTestimonials = async () => {
  if (!isSupabaseConfigured()) return apiClient.get('/testimonials');

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('testimonials')
    .select('*')
    .eq('status', 'APPROVED')
    .order('created_at', { ascending: false });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapTestimonialRow));
};

export const getPendingTestimonials = async () => {
  if (!isSupabaseConfigured()) return apiClient.get('/testimonials/pending');

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('testimonials')
    .select('*')
    .eq('status', 'PENDING')
    .order('created_at', { ascending: false });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapTestimonialRow));
};

export const getTestimonialById = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.get(`/testimonials/${id}`);

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('testimonials')
    .select('*')
    .eq('id', id)
    .maybeSingle();
  if (error) throw error;
  return toAxiosLikeResponse(mapTestimonialRow(data));
};

export const submitTestimonial = async (testimonialData) => {
  if (!isSupabaseConfigured())
    return apiClient.post('/testimonials', testimonialData);

  assertSupabaseConfigured();
  const row = mapTestimonialDtoToRow({
    ...testimonialData,
    status: testimonialData.status ?? 'PENDING',
  });

  const { data, error } = await supabase
    .from('testimonials')
    .insert(row)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapTestimonialRow(data));
};

export const updateTestimonial = async (id, testimonialData) => {
  if (!isSupabaseConfigured())
    return apiClient.put(`/testimonials/${id}`, testimonialData);

  assertSupabaseConfigured();
  const row = mapTestimonialDtoToRow(testimonialData);
  const { data, error } = await supabase
    .from('testimonials')
    .update(row)
    .eq('id', id)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapTestimonialRow(data));
};

export const approveTestimonial = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.patch(`/testimonials/${id}/approve`);

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('testimonials')
    .update({ status: 'APPROVED' })
    .eq('id', id)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapTestimonialRow(data));
};

export const deleteTestimonial = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.delete(`/testimonials/${id}`);

  assertSupabaseConfigured();
  const { error } = await supabase.from('testimonials').delete().eq('id', id);
  if (error) throw error;
  return toAxiosLikeResponse({ ok: true });
};

