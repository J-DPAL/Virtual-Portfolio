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

  const techPatternStyle = {
    backgroundImage: isDark
      ? `linear-gradient(90deg, rgba(14, 165, 233, 0.12) 1px, transparent 1px),
         linear-gradient(rgba(14, 165, 233, 0.12) 1px, transparent 1px),
         radial-gradient(circle at 18% 22%, rgba(34, 211, 238, 0.28) 0 2px, transparent 2px),
         radial-gradient(circle at 68% 68%, rgba(59, 130, 246, 0.25) 0 2px, transparent 2px),
         radial-gradient(circle at 82% 28%, rgba(56, 189, 248, 0.2) 0 1.5px, transparent 1.5px)`
      : `linear-gradient(90deg, rgba(37, 99, 235, 0.12) 1px, transparent 1px),
         linear-gradient(rgba(37, 99, 235, 0.12) 1px, transparent 1px),
         radial-gradient(circle at 16% 24%, rgba(59, 130, 246, 0.25) 0 2px, transparent 2px),
         radial-gradient(circle at 72% 70%, rgba(14, 165, 233, 0.22) 0 2px, transparent 2px),
         radial-gradient(circle at 84% 26%, rgba(56, 189, 248, 0.18) 0 1.5px, transparent 1.5px)`,
    backgroundSize:
      '80px 80px, 80px 80px, 220px 220px, 260px 260px, 200px 200px',
    backgroundPosition: 'center, center, left top, right bottom, right top',
    maskImage:
      'radial-gradient(circle at top, rgba(0,0,0,0.9) 0%, rgba(0,0,0,0.35) 55%, transparent 80%)',
    WebkitMaskImage:
      'radial-gradient(circle at top, rgba(0,0,0,0.9) 0%, rgba(0,0,0,0.35) 55%, transparent 80%)',
  };

  const circuitPatternStyle = {
    backgroundImage: isDark
      ? `url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='140' height='140' viewBox='0 0 140 140'><g fill='none' stroke='%2322d3ee' stroke-width='1' opacity='0.35'><path d='M10 18h36v22h32'/><path d='M78 40v30h48'/><path d='M22 92h38v28h46'/><path d='M58 120h28'/></g><g fill='%2322d3ee' opacity='0.55'><circle cx='10' cy='18' r='2'/><circle cx='46' cy='40' r='2'/><circle cx='78' cy='40' r='2'/><circle cx='22' cy='92' r='2'/><circle cx='60' cy='120' r='2'/><circle cx='106' cy='120' r='2'/></g></svg>")`
      : `url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='140' height='140' viewBox='0 0 140 140'><g fill='none' stroke='%233b82f6' stroke-width='1' opacity='0.3'><path d='M10 18h36v22h32'/><path d='M78 40v30h48'/><path d='M22 92h38v28h46'/><path d='M58 120h28'/></g><g fill='%233b82f6' opacity='0.5'><circle cx='10' cy='18' r='2'/><circle cx='46' cy='40' r='2'/><circle cx='78' cy='40' r='2'/><circle cx='22' cy='92' r='2'/><circle cx='60' cy='120' r='2'/><circle cx='106' cy='120' r='2'/></g></svg>")`,
    backgroundSize: '260px 260px',
    backgroundPosition: 'center',
  };

  const dataStreamStyle = {
    backgroundImage: isDark
      ? `repeating-linear-gradient(120deg, rgba(45, 212, 191, 0.18) 0 1px, transparent 1px 14px),
         repeating-linear-gradient(60deg, rgba(59, 130, 246, 0.16) 0 1px, transparent 1px 18px)`
      : `repeating-linear-gradient(120deg, rgba(37, 99, 235, 0.18) 0 1px, transparent 1px 14px),
         repeating-linear-gradient(60deg, rgba(14, 165, 233, 0.16) 0 1px, transparent 1px 18px)`,
    backgroundSize: '100% 100%',
    maskImage:
      'linear-gradient(180deg, rgba(0,0,0,0.75) 0%, rgba(0,0,0,0.25) 55%, transparent 85%)',
    WebkitMaskImage:
      'linear-gradient(180deg, rgba(0,0,0,0.75) 0%, rgba(0,0,0,0.25) 55%, transparent 85%)',
  };

  const glowFieldStyle = {
    backgroundImage: isDark
      ? `radial-gradient(circle at 12% 18%, rgba(56, 189, 248, 0.35), transparent 45%),
         radial-gradient(circle at 78% 32%, rgba(99, 102, 241, 0.28), transparent 48%),
         radial-gradient(circle at 48% 80%, rgba(14, 165, 233, 0.25), transparent 50%)`
      : `radial-gradient(circle at 12% 18%, rgba(59, 130, 246, 0.25), transparent 45%),
         radial-gradient(circle at 78% 32%, rgba(14, 165, 233, 0.22), transparent 48%),
         radial-gradient(circle at 48% 80%, rgba(99, 102, 241, 0.18), transparent 50%)`,
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
      className={`min-h-screen py-12 transition-colors duration-200 relative overflow-hidden ${
        isDark
          ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950'
          : 'bg-gradient-to-br from-emerald-50 via-white to-teal-50'
      }`}
    >
      <div className="pointer-events-none absolute inset-0" aria-hidden="true">
        <div
          className={`absolute -top-16 left-8 h-72 w-72 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-emerald-700' : 'bg-emerald-300'
          }`}
        ></div>
        <div
          className={`absolute -bottom-28 right-0 h-96 w-96 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-teal-700' : 'bg-teal-300'
          }`}
        ></div>
      </div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-60 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={techPatternStyle}
      ></div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-35 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={circuitPatternStyle}
      ></div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-40 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={dataStreamStyle}
      ></div>
      <div
        className="pointer-events-none absolute inset-0 opacity-60"
        aria-hidden="true"
        style={glowFieldStyle}
      ></div>
      <div className="container mx-auto px-4 relative z-10">
        <div className="text-center mb-12">
          <span
            className={`inline-flex items-center px-4 py-1.5 rounded-full text-sm font-semibold border mb-4 ${
              isDark
                ? 'bg-slate-800/60 border-slate-700 text-slate-200'
                : 'bg-slate-50/70 border-slate-200 text-slate-700'
            }`}
          >
            Learning Path
          </span>
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
            className={`max-w-2xl mx-auto mb-6 border px-4 py-3 rounded-lg ${
              isDark
                ? 'bg-red-900/30 border-red-700 text-red-300'
                : 'bg-red-50 border-red-200 text-red-700'
            }`}
          >
            {error}
          </div>
        )}

        <div className="max-w-4xl mx-auto relative">
          <div
            className={`absolute left-3 top-0 bottom-0 w-px ${
              isDark ? 'bg-slate-800' : 'bg-slate-200'
            }`}
            aria-hidden="true"
          ></div>
          <div className="space-y-8">
            {education.map((edu) => (
              <div
                key={edu.id}
                className={`relative rounded-2xl p-8 transition-all duration-300 ring-1 hover:-translate-y-1 ${
                  isDark
                    ? 'bg-slate-900/70 ring-slate-800 hover:ring-emerald-500/40'
                    : 'bg-slate-50/80 ring-slate-200 hover:ring-emerald-400/40'
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
                      className={`inline-flex items-center px-4 py-2 rounded-full font-medium text-sm border mb-2 shadow-sm ${
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
                      {edu.endDate ? formatDate(edu.endDate) : t('presentDate')}
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
