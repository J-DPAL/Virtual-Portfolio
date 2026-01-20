import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function HomePage() {
  const { t } = useTranslation();

  const highlights = [
    { title: t('projects'), desc: 'Recent builds and experiments.', to: '/projects' },
    { title: t('skills'), desc: 'Tech stack and strengths.', to: '/skills' },
    { title: t('experience'), desc: 'Work journey and roles.', to: '/experience' },
    { title: t('contact'), desc: 'Let’s start a conversation.', to: '/contact' },
  ];

  return (
    <div className="bg-gradient-to-b from-slate-100 via-white to-slate-100">
      <section className="container mx-auto px-4 pt-14 pb-10 grid gap-8 lg:grid-cols-12 items-center">
        <div className="lg:col-span-7 space-y-5">
          <p className="text-sm font-semibold text-blue-600 uppercase tracking-wide">Virtual Portfolio</p>
          <h1 className="text-4xl lg:text-5xl font-bold text-slate-900 leading-tight">{t('welcome')}</h1>
          <p className="text-lg text-slate-600 max-w-2xl">
            A clean starting point to showcase projects, skills, and experience. Backend wiring will be added next.
          </p>
          <div className="flex flex-wrap gap-3">
            <Link to="/projects" className="px-5 py-3 rounded-xl bg-blue-600 text-white font-medium hover:bg-blue-700 transition">View projects</Link>
            <Link to="/contact" className="px-5 py-3 rounded-xl border border-slate-200 text-slate-800 font-medium hover:border-blue-200 hover:text-blue-700 transition">Contact</Link>
          </div>
        </div>
        <div className="lg:col-span-5">
          <div className="bg-white rounded-3xl shadow-lg border border-slate-100 p-6 space-y-4">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-slate-500">Current focus</p>
                <p className="text-lg font-semibold text-slate-900">Spring Boot • React • PostgreSQL</p>
              </div>
              <span className="text-xs px-3 py-1 rounded-full bg-emerald-50 text-emerald-700 border border-emerald-100">In progress</span>
            </div>
            <p className="text-slate-600">Docker-compose spins up gateway, services, database, and frontend. This UI is a starter you can refine later.</p>
          </div>
        </div>
      </section>

      <section className="container mx-auto px-4 pb-14">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {highlights.map((item) => (
            <Link
              key={item.title}
              to={item.to}
              className="group bg-white rounded-2xl shadow-sm border border-slate-100 p-5 hover:-translate-y-1 hover:shadow-md transition block"
            >
              <div className="flex items-center justify-between mb-2">
                <h2 className="text-xl font-semibold text-slate-900">{item.title}</h2>
                <span className="text-blue-600 group-hover:translate-x-1 transition">→</span>
              </div>
              <p className="text-slate-600">{item.desc}</p>
            </Link>
          ))}
        </div>
      </section>
    </div>
  );
}
