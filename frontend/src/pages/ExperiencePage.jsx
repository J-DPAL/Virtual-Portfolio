import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { getAllExperiences } from '../services/experienceService';

export default function ExperiencePage() {
  const { t, i18n } = useTranslation();
  const [experiences, setExperiences] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const currentLang = i18n.language;

  useEffect(() => {
    fetchExperiences();
  }, []);

  const fetchExperiences = async () => {
    try {
      setLoading(true);
      const response = await getAllExperiences();
      // Sort by start date descending
      const sorted = response.data.sort(
        (a, b) => new Date(b.startDate) - new Date(a.startDate)
      );
      setExperiences(sorted);
    } catch (err) {
      setError(t('errorOccurred'));
      console.error('Error fetching experiences:', err);
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return t('presentDate');
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
    });
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-cyan-50 via-white to-blue-50">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-cyan-600"></div>
          <p className="mt-4 text-slate-600">{t('loading')}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-cyan-50 via-white to-blue-50 py-12">
      <div className="container mx-auto px-4">
        <div className="text-center mb-12">
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-cyan-600 to-blue-600 mb-4">
            {t('experience')}
          </h1>
          <p className="text-lg text-slate-600 max-w-2xl mx-auto">
            {t('professionalJourneyMilestones')}
          </p>
        </div>

        {error && (
          <div className="max-w-3xl mx-auto mb-6 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
            {error}
          </div>
        )}

        <div className="max-w-4xl mx-auto space-y-8">
          {experiences.map((exp) => (
            <div
              key={exp.id}
              className="relative bg-white rounded-2xl shadow-lg border border-slate-200 p-8 hover:shadow-2xl transition-all duration-300"
            >
              <div className="absolute -left-4 top-8 w-8 h-8 bg-gradient-to-br from-cyan-500 to-blue-600 rounded-full border-4 border-white shadow-lg"></div>

              <div className="flex flex-col md:flex-row md:items-start md:justify-between mb-4">
                <div className="flex-1">
                  <h2 className="text-2xl font-bold text-slate-900 mb-2">
                    {currentLang === 'es' && exp.positionEs
                      ? exp.positionEs
                      : currentLang === 'fr' && exp.positionFr
                        ? exp.positionFr
                        : exp.positionEn}
                  </h2>
                  <h3 className="text-xl text-cyan-600 font-semibold mb-2">
                    {currentLang === 'es' && exp.companyNameEs
                      ? exp.companyNameEs
                      : currentLang === 'fr' && exp.companyNameFr
                        ? exp.companyNameFr
                        : exp.companyNameEn}
                  </h3>
                  {(exp.locationEn || exp.locationFr) && (
                    <p className="text-slate-600 flex items-center mb-3">
                      <svg
                        className="w-4 h-4 mr-2"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"
                        />
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"
                        />
                      </svg>
                      {currentLang === 'es' && exp.locationEs
                        ? exp.locationEs
                        : currentLang === 'fr' && exp.locationFr
                          ? exp.locationFr
                          : exp.locationEn}
                    </p>
                  )}
                </div>
                <span className="inline-flex items-center px-4 py-2 rounded-full bg-cyan-50 text-cyan-700 border border-cyan-200 font-medium text-sm">
                  <svg
                    className="w-4 h-4 mr-2"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
                    />
                  </svg>
                  {formatDate(exp.startDate)} -{' '}
                  {exp.endDate ? formatDate(exp.endDate) : 'Present'}
                </span>
              </div>

              {exp.descriptionEn && (
                <p className="text-slate-600 leading-relaxed mb-4">
                  {currentLang === 'es' && exp.descriptionEs
                    ? exp.descriptionEs
                    : currentLang === 'fr' && exp.descriptionFr
                      ? exp.descriptionFr
                      : exp.descriptionEn}
                </p>
              )}

              {exp.responsibilities && exp.responsibilities.length > 0 && (
                <div className="mt-4">
                  <h4 className="font-semibold text-slate-900 mb-2">
                    Key Responsibilities:
                  </h4>
                  <ul className="list-disc list-inside space-y-1 text-slate-600">
                    {exp.responsibilities.map((resp, idx) => (
                      <li key={idx}>{resp}</li>
                    ))}
                  </ul>
                </div>
              )}
            </div>
          ))}
        </div>

        {experiences.length === 0 && !loading && (
          <div className="text-center py-12">
            <svg
              className="w-24 h-24 mx-auto text-slate-300 mb-4"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={1.5}
                d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
              />
            </svg>
            <p className="text-slate-600 text-lg">
              {t('noExperienceAvailable')}
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
