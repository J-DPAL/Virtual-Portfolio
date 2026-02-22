import apiClient from './apiClient';
import { mapMessageRow } from './dbMappers';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

export const getAllMessages = async () => {
  if (!isSupabaseConfigured()) return apiClient.get('/messages');

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('messages')
    .select('*')
    .order('created_at', { ascending: false });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapMessageRow));
};

export const getMessageById = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.get(`/messages/${id}`);

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('messages')
    .select('*')
    .eq('id', id)
    .maybeSingle();
  if (error) throw error;
  return toAxiosLikeResponse(mapMessageRow(data));
};

export const sendMessage = async (messageData) => {
  if (!isSupabaseConfigured()) return apiClient.post('/messages', messageData);

  // NOTE: With Supabase, your anti-spam should be enforced via:
  // - RLS policies + (recommended) an Edge Function that validates Turnstile and rate-limits.
  // This client implementation only inserts into the `messages` table.
  assertSupabaseConfigured();

  // Honeypot: if bots filled the hidden field, pretend success.
  if (messageData?.website) {
    return toAxiosLikeResponse({ ok: true });
  }

  const row = {
    sender_name: messageData.senderName,
    sender_email: messageData.senderEmail,
    subject: messageData.subject,
    message: messageData.message,
  };

  const { data, error } = await supabase
    .from('messages')
    .insert(row)
    .select('*')
    .single();

  if (error) throw error;
  return toAxiosLikeResponse(data);
};

export const deleteMessage = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.delete(`/messages/${id}`);

  assertSupabaseConfigured();
  const { error } = await supabase.from('messages').delete().eq('id', id);
  if (error) throw error;
  return toAxiosLikeResponse({ ok: true });
};
