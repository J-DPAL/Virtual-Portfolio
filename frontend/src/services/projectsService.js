import apiClient from './apiClient';

export const getAllProjects = () => apiClient.get('/projects');

export const getProjectById = (id) => apiClient.get(`/projects/${id}`);

export const createProject = (projectData) =>
  apiClient.post('/projects', projectData);

export const updateProject = (id, projectData) =>
  apiClient.put(`/projects/${id}`, projectData);

export const deleteProject = (id) => apiClient.delete(`/projects/${id}`);
