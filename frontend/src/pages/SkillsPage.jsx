import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { getAllSkills } from '../services/skillsService';

export default function SkillsPage() {
  const { t, i18n } = useTranslation();
  const [skills, setSkills] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const currentLang = i18n.language;

  useEffect(() => {
    fetchSkills();
  }, []);

  const fetchSkills = async () => {
    try {
      setLoading(true);
      const response = await getAllSkills();
      setSkills(response.data);
    } catch (err) {
      setError(t('loadSkillsFailed'));
      console.error('Error fetching skills:', err);
    } finally {
      setLoading(false);
    }
  };

  const getLevelColor = (level) => {
    const levels = {
      beginner: 'bg-green-50 text-green-700 border-green-200',
      intermediate: 'bg-blue-50 text-blue-700 border-blue-200',
      advanced: 'bg-teal-50 text-teal-700 border-teal-200',
      expert: 'bg-orange-50 text-orange-700 border-orange-200',
    };
    return (
      levels[level?.toLowerCase()] || 'bg-gray-50 text-gray-700 border-gray-200'
    );
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 via-white to-cyan-50">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
          <p className="mt-4 text-slate-600">{t('loading')}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-cyan-50 py-12">
      <div className="max-w-6xl mx-auto px-4">
        <div className="text-center mb-12">
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-cyan-600 mb-4">
            {t('skills')}
          </h1>
          <p className="text-lg text-slate-600 max-w-2xl mx-auto">
            {t('discoverTechnologiesTools')}
          </p>
        </div>

        {error && (
          <div className="max-w-2xl mx-auto mb-6 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
            {t('errorOccurred')}
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl mx-auto">
          {skills.map((skill) => (
            <div
              key={skill.id}
              className="group bg-white rounded-2xl shadow-lg border border-slate-200 p-6 hover:-translate-y-2 hover:shadow-2xl transition-all duration-300"
            >
              <div className="flex items-center justify-between mb-3">
                <h2 className="text-xl font-bold text-slate-900 group-hover:text-blue-600 transition">
                  {currentLang === 'fr' && skill.nameFr
                    ? skill.nameFr
                    : skill.nameEn}
                </h2>
                <span
                  className={`text-xs px-3 py-1 rounded-full border font-medium ${getLevelColor(skill.proficiencyLevel)}`}
                >
                  {skill.proficiencyLevel}
                </span>
              </div>
              <p className="text-slate-600 mb-4">
                {currentLang === 'fr' && skill.descriptionFr
                  ? skill.descriptionFr
                  : skill.descriptionEn}
              </p>
              <div className="flex items-center text-sm text-slate-500">
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
                    d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"
                  />
                </svg>
                {skill.category || 'Technology'}
              </div>
            </div>
          ))}
        </div>

        {skills.length === 0 && !loading && (
          <div className="text-center py-12">
            <p className="text-slate-600 text-lg">{t('noSkillsAvailable')}</p>
          </div>
        )}
      </div>
    </div>
  );
}
