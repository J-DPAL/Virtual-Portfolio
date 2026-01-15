import apiClient from './apiClient';

export const loginUser = (email, password) => {
  return apiClient.post('/v1/auth/login', { email, password });
};

export const checkHealth = () => {
  return apiClient.get('/v1/auth/health');
};
