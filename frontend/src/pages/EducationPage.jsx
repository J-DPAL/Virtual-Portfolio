import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../context/ThemeContext';
import { getAllEducation } from '../services/educationService';

export default function EducationPage() {
  const { t, i18n } = useTranslation();
  const { isDark } = useTheme();
  const [education, setEducation] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const currentLang = i18n.language;

  useEffect(() => {
    fetchEducation();
  }, []);

  const fetchEducation = async () => {
    try {
      setLoading(true);
      const response = await getAllEducation();
      // Sort by start date descending
      const sorted = response.data.sort(
        (a, b) => new Date(b.startDate) - new Date(a.startDate)
      );
      setEducation(sorted);
    } catch (err) {
      setError(t('errorOccurred'));
      console.error('Error fetching education:', err);
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
      <div
        className={`min-h-screen flex items-center justify-center transition-colors duration-200 ${
          isDark
            ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950'
            : 'bg-gradient-to-br from-emerald-50 via-white to-teal-50'
        }`}
      >
        <div className="text-center">
          <div
            className={`inline-block animate-spin rounded-full h-12 w-12 border-b-2 ${
              isDark ? 'border-emerald-400' : 'border-emerald-600'
            }`}
          ></div>
          <p className={`mt-4 ${isDark ? 'text-slate-400' : 'text-slate-600'}`}>
            {t('loading')}
          </p>
        </div>
      </div>
    );
  }

  return (
    <div
      className={`min-h-screen py-12 transition-colors duration-200 ${
        isDark
          ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950'
          : 'bg-gradient-to-br from-emerald-50 via-white to-teal-50'
      }`}
    >
      <div className="container mx-auto px-4">
        <div className="text-center mb-12">
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-emerald-600 to-teal-600 mb-4">
            {t('education')}
          </h1>
          <p
            className={`text-lg max-w-2xl mx-auto ${
              isDark ? 'text-slate-400' : 'text-slate-600'
            }`}
          >
            {t('academicJourneyLearning')}
          </p>
        </div>

        {error && (
          <div
            className={`max-w-3xl mx-auto mb-6 border px-4 py-3 rounded-lg ${
              isDark
                ? 'bg-red-900/30 border-red-700 text-red-300'
                : 'bg-red-50 border-red-200 text-red-700'
            }`}
          >
            {error}
          </div>
        )}

        <div className="max-w-4xl mx-auto space-y-8">
          {education.map((edu) => (
            <div
              key={edu.id}
              className={`relative rounded-2xl shadow-lg p-8 hover:shadow-2xl transition-all duration-300 ${
                isDark
                  ? 'bg-slate-800 border border-slate-700'
                  : 'bg-white border border-slate-200'
              }`}
            >
              <div
                className={`absolute -left-4 top-8 w-8 h-8 bg-gradient-to-br from-emerald-500 to-teal-600 rounded-full border-4 shadow-lg ${
                  isDark ? 'border-slate-800' : 'border-white'
                }`}
              ></div>

              <div className="flex flex-col md:flex-row md:items-start md:justify-between mb-4">
                <div className="flex-1">
                  <h2
                    className={`text-2xl font-bold mb-2 ${
                      isDark ? 'text-slate-100' : 'text-slate-900'
                    }`}
                  >
                    {currentLang === 'es' && edu.degreeEs
                      ? edu.degreeEs
                      : currentLang === 'fr' && edu.degreeFr
                        ? edu.degreeFr
                        : edu.degreeEn}
                  </h2>
                  <h3
                    className={`text-xl font-semibold mb-2 ${
                      isDark ? 'text-emerald-400' : 'text-emerald-600'
                    }`}
                  >
                    {currentLang === 'es' && edu.institutionNameEs
                      ? edu.institutionNameEs
                      : currentLang === 'fr' && edu.institutionNameFr
                        ? edu.institutionNameFr
                        : edu.institutionNameEn}
                  </h3>
                  <p
                    className={`mb-4 ${
                      isDark ? 'text-slate-400' : 'text-slate-600'
                    }`}
                  >
                    {currentLang === 'es' && edu.fieldOfStudyEs
                      ? edu.fieldOfStudyEs
                      : currentLang === 'fr' && edu.fieldOfStudyFr
                        ? edu.fieldOfStudyFr
                        : edu.fieldOfStudyEn}
                  </p>
                </div>
                <div className="flex flex-col items-end">
                  <span
                    className={`inline-flex items-center px-4 py-2 rounded-full font-medium text-sm border mb-2 ${
                      isDark
                        ? 'bg-emerald-900/30 text-emerald-300 border-emerald-700'
                        : 'bg-emerald-50 text-emerald-700 border-emerald-200'
                    }`}
                  >
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
                    {formatDate(edu.startDate)} -{' '}
                    {edu.endDate ? formatDate(edu.endDate) : 'Present'}
                  </span>
                  {edu.grade && (
                    <span
                      className={`text-sm font-medium ${
                        isDark ? 'text-slate-400' : 'text-slate-600'
                      }`}
                    >
                      Grade: {edu.grade}
                    </span>
                  )}
                </div>
              </div>

              {edu.descriptionEn && (
                <p
                  className={`leading-relaxed ${
                    isDark ? 'text-slate-400' : 'text-slate-600'
                  }`}
                >
                  {currentLang === 'es' && edu.descriptionEs
                    ? edu.descriptionEs
                    : currentLang === 'fr' && edu.descriptionFr
                      ? edu.descriptionFr
                      : edu.descriptionEn}
                </p>
              )}
            </div>
          ))}
        </div>

        {education.length === 0 && !loading && (
          <div className="text-center py-12">
            <svg
              className={`w-24 h-24 mx-auto mb-4 ${
                isDark ? 'text-slate-700' : 'text-slate-300'
              }`}
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={1.5}
                d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"
              />
            </svg>
            <p
              className={`text-lg ${
                isDark ? 'text-slate-400' : 'text-slate-600'
              }`}
            >
              {t('noEducationAvailable')}
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
