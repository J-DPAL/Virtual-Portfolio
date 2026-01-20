import { useTranslation } from 'react-i18next';

export default function ExperiencePage() {
  const { t } = useTranslation();
  const experiences = [
    { role: 'Backend Engineer', org: 'Virtual Portfolio', period: '2025 - Present', summary: 'Building microservices, API gateway, and auth.' },
    { role: 'Full Stack Developer', org: 'Freelance', period: '2023 - 2025', summary: 'Delivered React + Spring Boot projects for clients.' },
  ];

  return (
    <div className="container mx-auto px-4 py-12">
      <p className="text-sm text-slate-500 uppercase tracking-wide mb-2">{t('experience')}</p>
      <h1 className="text-3xl font-bold text-slate-900 mb-6">Experience</h1>
      <div className="space-y-4">
        {experiences.map((exp) => (
          <div key={exp.role} className="bg-white rounded-2xl shadow-sm border border-slate-100 p-5">
            <div className="flex items-center justify-between flex-wrap gap-2 mb-1">
              <h2 className="text-xl font-semibold text-slate-900">{exp.role}</h2>
              <span className="text-sm text-slate-500">{exp.period}</span>
            </div>
            <p className="text-slate-700 font-medium">{exp.org}</p>
            <p className="text-slate-600 mt-2">{exp.summary}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
