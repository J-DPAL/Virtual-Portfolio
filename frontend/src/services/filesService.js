import apiClient from './apiClient';

export const uploadResume = (file, language) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('language', language);

  return apiClient.post('/v1/files/resume/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

export const downloadResume = (language = 'en') => {
  return apiClient.get(`/v1/files/resume/download`, {
    params: { language },
    responseType: 'blob',
  });
};
