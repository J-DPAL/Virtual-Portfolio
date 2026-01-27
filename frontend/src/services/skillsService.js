import apiClient from './apiClient';

export const getAllSkills = () => apiClient.get('/v1/skills');

export const getSkillById = (id) => apiClient.get(`/v1/skills/${id}`);

export const createSkill = (skillData) =>
  apiClient.post('/v1/skills', skillData);

export const updateSkill = (id, skillData) =>
  apiClient.put(`/v1/skills/${id}`, skillData);

export const deleteSkill = (id) => apiClient.delete(`/v1/skills/${id}`);
