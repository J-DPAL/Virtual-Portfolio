import { useTranslation } from 'react-i18next';

export default function SkillsPage() {
  const { t } = useTranslation();
  const skills = [
    { name: 'Java & Spring Boot', level: 'Advanced', focus: 'Microservices, Security, Cloud' },
    { name: 'React & Vite', level: 'Advanced', focus: 'SPA, Routing, State' },
    { name: 'PostgreSQL', level: 'Intermediate', focus: 'Schema design, migrations' },
    { name: 'Docker & Compose', level: 'Intermediate', focus: 'Local orchestration' },
  ];

  return (
    <div className="container mx-auto px-4 py-12">
      <p className="text-sm text-slate-500 uppercase tracking-wide mb-2">{t('skills')}</p>
      <h1 className="text-3xl font-bold text-slate-900 mb-6">Core Skills</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {skills.map((skill) => (
          <div key={skill.name} className="bg-white rounded-2xl shadow-sm border border-slate-100 p-5 hover:-translate-y-1 hover:shadow-md transition">
            <div className="flex items-center justify-between mb-2">
              <h2 className="text-xl font-semibold text-slate-900">{skill.name}</h2>
              <span className="text-xs px-2 py-1 rounded-full bg-blue-50 text-blue-700 border border-blue-100">{skill.level}</span>
            </div>
            <p className="text-slate-600">{skill.focus}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
