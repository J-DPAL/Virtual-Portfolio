import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../context/ThemeContext';
import { getAllExperiences } from '../services/experienceService';

export default function ExperiencePage() {
  const { t, i18n } = useTranslation();
  const { isDark } = useTheme();
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

  const getExperienceIcon = (exp) => {
    if (exp.icon && exp.icon.trim()) return exp.icon.trim();
    const haystack = `${exp.positionEn || ''} ${exp.positionFr || ''} ${
      exp.positionEs || ''
    } ${exp.companyNameEn || ''} ${exp.companyNameFr || ''} ${
      exp.companyNameEs || ''
    } ${exp.descriptionEn || ''} ${exp.descriptionFr || ''} ${
      exp.descriptionEs || ''
    }`.toLowerCase();

    const rules = [
      {
        match: ['voter', 'election', 'poll', 'ballot', 'attendant'],
        icon: '🗳️',
      },
      { match: ['cook', 'chef', 'kitchen', 'culinary'], icon: '🍳' },
      {
        match: ['developer', 'engineer', 'software', 'frontend', 'backend'],
        icon: '💻',
      },
      { match: ['designer', 'design', 'ux', 'ui', 'creative'], icon: '🎨' },
      { match: ['manager', 'lead', 'director', 'supervisor'], icon: '🎯' },
      { match: ['data', 'analyst', 'analytics', 'insights'], icon: '📊' },
      { match: ['teacher', 'instructor', 'trainer', 'coach'], icon: '📚' },
      { match: ['marketing', 'brand', 'content', 'social'], icon: '📣' },
      { match: ['sales', 'business development', 'account'], icon: '🤝' },
      { match: ['support', 'customer', 'service'], icon: '🎧' },
      { match: ['research', 'lab', 'scientist'], icon: '🔬' },
      { match: ['finance', 'accounting', 'audit'], icon: '💰' },
      { match: ['health', 'nurse', 'doctor', 'medical'], icon: '🩺' },
      { match: ['construction', 'builder', 'engineer'], icon: '🏗️' },
      { match: ['startup', 'founder', 'entrepreneur'], icon: '🚀' },
    ];

    const rule = rules.find((r) => r.match.some((m) => haystack.includes(m)));
    return rule ? rule.icon : '💼';
  };

  const getSkillsList = (exp) => {
    if (!exp.skillsUsed || !exp.skillsUsed.trim()) return [];
    return exp.skillsUsed
      .split(',')
      .map((skill) => skill.trim())
      .filter(Boolean);
  };

  const getSkillBadges = (exp) => {
    const base = getSkillsList(exp);
    const responsibilitiesText = Array.isArray(exp.responsibilities)
      ? exp.responsibilities.join(' ')
      : exp.responsibilities || '';
    const haystack = `${exp.positionEn || ''} ${exp.positionFr || ''} ${
      exp.positionEs || ''
    } ${exp.companyNameEn || ''} ${exp.companyNameFr || ''} ${
      exp.companyNameEs || ''
    } ${exp.descriptionEn || ''} ${exp.descriptionFr || ''} ${
      exp.descriptionEs || ''
    } ${responsibilitiesText}`.toLowerCase();

    const softSkills = [
      { match: ['team', 'collaborat'], label: '🤝 Teamwork' },
      {
        match: ['time', 'schedule', 'deadline', 'shift'],
        label: '⏱️ Time Management',
      },
      {
        match: ['customer', 'client', 'service', 'voter'],
        label: '🗣️ Communication',
      },
      {
        match: ['detail', 'accuracy', 'record', 'audit'],
        label: '🔍 Attention to Detail',
      },
      {
        match: ['problem', 'resolve', 'troubleshoot'],
        label: '🧠 Problem Solving',
      },
      { match: ['lead', 'manage', 'supervise'], label: '🎯 Leadership' },
      { match: ['adapt', 'fast-paced', 'change'], label: '🌊 Adaptability' },
      {
        match: ['compliance', 'policy', 'safety', 'protocol'],
        label: '✅ Compliance',
      },
      { match: ['reliab', 'punctual'], label: '🧭 Reliability' },
      {
        match: ['election', 'integrity', 'confidential'],
        label: '🧾 Integrity',
      },
    ]
      .filter((rule) => rule.match.some((m) => haystack.includes(m)))
      .map((rule) => rule.label);

    const deduped = new Map();
    [...base, ...softSkills].forEach((label) => {
      const key = label.toLowerCase();
      if (!deduped.has(key)) deduped.set(key, label);
    });

    return [...deduped.values()];
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
            : 'bg-gradient-to-br from-cyan-50 via-white to-blue-50'
        }`}
      >
        <div className="text-center">
          <div
            className={`inline-block animate-spin rounded-full h-12 w-12 border-b-2 ${
              isDark ? 'border-cyan-400' : 'border-cyan-600'
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
          : 'bg-gradient-to-br from-cyan-50 via-white to-blue-50'
      }`}
    >
      <div className="pointer-events-none absolute inset-0" aria-hidden="true">
        <div
          className={`absolute -top-24 right-10 h-72 w-72 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-cyan-700' : 'bg-cyan-300'
          }`}
        ></div>
        <div
          className={`absolute -bottom-28 -left-10 h-96 w-96 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-blue-700' : 'bg-blue-300'
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
            Career Timeline
          </span>
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-cyan-600 to-blue-600 mb-4">
            {t('experience')}
          </h1>
          <p
            className={`text-lg max-w-2xl mx-auto ${
              isDark ? 'text-slate-400' : 'text-slate-600'
            }`}
          >
            {t('professionalJourneyMilestones')}
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

        <div className="max-w-4xl mx-auto relative">
          <div
            className={`absolute left-3 top-0 bottom-0 w-px ${
              isDark ? 'bg-slate-800' : 'bg-slate-200'
            }`}
            aria-hidden="true"
          ></div>
          <div className="space-y-8">
            {experiences.map((exp) => {
              const icon = getExperienceIcon(exp);
              const skills = getSkillBadges(exp);
              const isCurrentRole = exp?.isCurrent || !exp?.endDate;

              return (
                <div
                  key={exp.id}
                  className={`group relative rounded-3xl p-8 md:p-10 transition-all duration-300 ring-1 hover:-translate-y-1 overflow-hidden ${
                    isDark
                      ? 'bg-slate-900/70 ring-slate-800 hover:ring-cyan-500/40'
                      : 'bg-slate-50/80 ring-slate-200 hover:ring-cyan-400/40'
                  }`}
                >
                  <div
                    className={`absolute inset-x-0 top-0 h-1 bg-gradient-to-r ${
                      isDark
                        ? 'from-cyan-500/60 via-blue-500/40 to-indigo-500/60'
                        : 'from-cyan-500 via-blue-500 to-indigo-500'
                    }`}
                  ></div>
                  <div
                    className={`absolute inset-0 rounded-3xl opacity-0 transition-opacity duration-300 pointer-events-none ${
                      isDark
                        ? 'bg-gradient-to-br from-cyan-500/10 via-transparent to-blue-500/10'
                        : 'bg-gradient-to-br from-cyan-400/10 via-transparent to-blue-400/10'
                    } group-hover:opacity-100`}
                  ></div>
                  <div
                    className={`absolute -left-4 top-8 w-8 h-8 bg-gradient-to-br from-cyan-500 to-blue-600 rounded-full border-4 shadow-lg ${
                      isDark ? 'border-slate-800' : 'border-white'
                    }`}
                  ></div>
                  <div className="absolute right-6 top-6">
                    <span
                      className={`inline-flex items-center px-3 py-1.5 rounded-full text-xs font-semibold border shadow-sm ${
                        isCurrentRole
                          ? isDark
                            ? 'bg-emerald-900/40 text-emerald-200 border-emerald-700/60'
                            : 'bg-emerald-50 text-emerald-700 border-emerald-200'
                          : isDark
                            ? 'bg-slate-900/60 text-slate-300 border-slate-700'
                            : 'bg-slate-100 text-slate-600 border-slate-200'
                      }`}
                    >
                      {isCurrentRole ? t('presentDate') : 'Past Role'}
                    </span>
                  </div>

                  <div className="flex flex-col md:flex-row md:items-start md:justify-between mb-6 gap-4">
                    <div className="flex-1 flex gap-4">
                      <div
                        className={`w-16 h-16 rounded-2xl flex items-center justify-center text-3xl shadow-lg ring-1 ${
                          isDark
                            ? 'bg-slate-900 ring-slate-700'
                            : 'bg-slate-50 ring-slate-200'
                        }`}
                      >
                        {icon}
                      </div>
                      <div className="flex-1">
                        <h2
                          className={`text-2xl font-bold mb-2 ${
                            isDark ? 'text-slate-100' : 'text-slate-900'
                          }`}
                        >
                          {currentLang === 'es' && exp.positionEs
                            ? exp.positionEs
                            : currentLang === 'fr' && exp.positionFr
                              ? exp.positionFr
                              : exp.positionEn}
                        </h2>
                        <h3
                          className={`text-xl font-semibold mb-2 ${
                            isDark ? 'text-cyan-400' : 'text-cyan-600'
                          }`}
                        >
                          {currentLang === 'es' && exp.companyNameEs
                            ? exp.companyNameEs
                            : currentLang === 'fr' && exp.companyNameFr
                              ? exp.companyNameFr
                              : exp.companyNameEn}
                        </h3>
                        <div className="flex flex-wrap items-center gap-2 mb-3">
                          {(exp.locationEn || exp.locationFr) && (
                            <span
                              className={`inline-flex items-center px-3 py-1 rounded-full text-xs font-medium border ${
                                isDark
                                  ? 'bg-slate-900/70 text-slate-300 border-slate-700'
                                  : 'bg-slate-50 text-slate-600 border-slate-200'
                              }`}
                            >
                              <svg
                                className="w-3.5 h-3.5 mr-1"
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
                            </span>
                          )}
                          <span
                            className={`inline-flex items-center px-3 py-1 rounded-full text-xs font-medium border ${
                              isDark
                                ? 'bg-cyan-900/20 text-cyan-200 border-cyan-800/60'
                                : 'bg-cyan-50 text-cyan-700 border-cyan-200'
                            }`}
                          >
                            <svg
                              className="w-3.5 h-3.5 mr-1"
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
                            {exp.endDate
                              ? formatDate(exp.endDate)
                              : t('presentDate')}
                          </span>
                        </div>
                      </div>
                    </div>
                  </div>

                  {exp.descriptionEn && (
                    <p
                      className={`leading-relaxed mb-4 ${
                        isDark ? 'text-slate-400' : 'text-slate-600'
                      }`}
                    >
                      {currentLang === 'es' && exp.descriptionEs
                        ? exp.descriptionEs
                        : currentLang === 'fr' && exp.descriptionFr
                          ? exp.descriptionFr
                          : exp.descriptionEn}
                    </p>
                  )}

                  {skills.length > 0 && (
                    <div className="mt-6">
                      <h4
                        className={`font-semibold mb-3 flex items-center gap-2 ${
                          isDark ? 'text-slate-100' : 'text-slate-900'
                        }`}
                      >
                        <svg
                          className="w-5 h-5"
                          fill="none"
                          stroke="currentColor"
                          viewBox="0 0 24 24"
                        >
                          <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"
                          />
                        </svg>
                        Skills & Strengths:
                      </h4>
                      <div className="flex flex-wrap gap-2">
                        {skills.map((skill, idx) => (
                          <span
                            key={idx}
                            className={`px-3 py-1.5 rounded-full text-sm font-medium transition-colors ${
                              isDark
                                ? 'bg-blue-900/40 text-blue-200 border border-blue-700/50 hover:bg-blue-900/60'
                                : 'bg-blue-50 text-blue-700 border border-blue-200 hover:bg-blue-100'
                            }`}
                          >
                            {skill.trim()}
                          </span>
                        ))}
                      </div>
                    </div>
                  )}

                  {exp.responsibilities && exp.responsibilities.length > 0 && (
                    <div className="mt-6">
                      <h4
                        className={`font-semibold mb-2 ${
                          isDark ? 'text-slate-100' : 'text-slate-900'
                        }`}
                      >
                        Key Responsibilities:
                      </h4>
                      <div className="grid gap-2">
                        {exp.responsibilities.map((resp, idx) => (
                          <div
                            key={idx}
                            className={`flex items-start gap-3 rounded-xl px-4 py-3 ${
                              isDark
                                ? 'bg-slate-900/60 border border-slate-800 text-slate-300'
                                : 'bg-slate-50 border border-slate-200 text-slate-700'
                            }`}
                          >
                            <span
                              className={`mt-0.5 inline-flex h-6 w-6 items-center justify-center rounded-full text-xs ${
                                isDark
                                  ? 'bg-cyan-900/40 text-cyan-300'
                                  : 'bg-cyan-100 text-cyan-700'
                              }`}
                            >
                              ✓
                            </span>
                            <span className="leading-relaxed">{resp}</span>
                          </div>
                        ))}
                      </div>
                    </div>
                  )}
                </div>
              );
            })}
          </div>
        </div>

        {experiences.length === 0 && !loading && (
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
                d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
              />
            </svg>
            <p
              className={`text-lg ${
                isDark ? 'text-slate-400' : 'text-slate-600'
              }`}
            >
              {t('noExperienceAvailable')}
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
