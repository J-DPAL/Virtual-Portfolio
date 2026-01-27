import apiClient from './apiClient';

export const getAllMessages = () => apiClient.get('/v1/messages');

export const getMessageById = (id) => apiClient.get(`/v1/messages/${id}`);

export const sendMessage = (messageData) =>
  apiClient.post('/v1/messages', messageData);

export const deleteMessage = (id) => apiClient.delete(`/v1/messages/${id}`);
