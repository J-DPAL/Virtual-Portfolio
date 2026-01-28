import apiClient from './apiClient';

export const getAllTestimonials = () => apiClient.get('/testimonials');

export const getApprovedTestimonials = () =>
  apiClient.get('/testimonials/approved');

export const getTestimonialById = (id) =>
  apiClient.get(`/testimonials/${id}`);

export const submitTestimonial = (testimonialData) =>
  apiClient.post('/testimonials', testimonialData);

export const updateTestimonial = (id, testimonialData) =>
  apiClient.put(`/testimonials/${id}`, testimonialData);

export const approveTestimonial = (id) =>
  apiClient.patch(`/testimonials/${id}/approve`);

export const deleteTestimonial = (id) =>
  apiClient.delete(`/testimonials/${id}`);
