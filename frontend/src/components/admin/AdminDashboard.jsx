import { useTranslation } from 'react-i18next';

export default function AdminDashboard() {
  const { t } = useTranslation();

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="container mx-auto px-4 py-12">
        <h1 className="text-4xl font-bold mb-8">{t('admin')}</h1>
        
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {/* Placeholder cards for admin sections */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-bold mb-2">{t('skills')}</h2>
            <p className="text-gray-600">Manage your skills</p>
          </div>
          
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-bold mb-2">{t('projects')}</h2>
            <p className="text-gray-600">Manage your projects</p>
          </div>
          
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-bold mb-2">{t('experience')}</h2>
            <p className="text-gray-600">Manage your experience</p>
          </div>
        </div>
      </div>
    </div>
  );
}
