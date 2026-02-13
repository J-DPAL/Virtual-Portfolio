import apiClient from './apiClient';

export const loginUser = (email, password) => {
  return apiClient.post('/v1/auth/login', { email, password });
};

export const checkHealth = () => {
  return apiClient.get('/v1/auth/health');
};

export const getCsrfToken = () => {
  return apiClient.get('/v1/auth/csrf');
};

export const getCurrentUser = () => {
  return apiClient.get('/v1/auth/me');
};

export const logoutUser = () => {
  return apiClient.post('/v1/auth/logout');
};
