import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';

export default function ProjectsPage() {
  const { t } = useTranslation();
  const projects = [
    { title: 'Portfolio Website', stack: 'React, Tailwind, Vite', status: 'Live' },
    { title: 'API Gateway', stack: 'Spring Boot, Eureka', status: 'Planned' },
    { title: 'Skills Service', stack: 'Spring Boot, PostgreSQL', status: 'In Progress' },
  ];

  return (
    <div className="container mx-auto px-4 py-12">
      <header className="mb-8 flex items-center justify-between flex-wrap gap-3">
        <div>
          <p className="text-sm text-slate-500 uppercase tracking-wide">{t('projects')}</p>
          <h1 className="text-3xl font-bold text-slate-900">Highlighted Projects</h1>
          <p className="text-slate-600 mt-2">A quick look at what is built and what is coming next.</p>
        </div>
        <Link to="/contact" className="inline-flex items-center px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 transition">
          Get in touch
        </Link>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {projects.map((project) => (
          <div key={project.title} className="bg-white rounded-2xl shadow-sm border border-slate-100 p-6 hover:-translate-y-1 hover:shadow-md transition">
            <div className="flex items-center justify-between mb-3">
              <h2 className="text-xl font-semibold text-slate-900">{project.title}</h2>
              <span className="text-xs px-2 py-1 rounded-full bg-emerald-50 text-emerald-600 border border-emerald-100">{project.status}</span>
            </div>
            <p className="text-slate-600">{project.stack}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
