import apiClient from './apiClient';

export const getAllMessages = () => apiClient.get('/messages');

export const getMessageById = (id) => apiClient.get(`/messages/${id}`);

export const sendMessage = (messageData) =>
  apiClient.post('/messages', messageData);

export const deleteMessage = (id) => apiClient.delete(`/messages/${id}`);
