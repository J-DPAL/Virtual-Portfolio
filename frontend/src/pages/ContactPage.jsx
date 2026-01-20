import { useState } from 'react';
import { useTranslation } from 'react-i18next';

export default function ContactPage() {
  const { t } = useTranslation();
  const [form, setForm] = useState({ name: '', email: '', message: '' });
  const [submitted, setSubmitted] = useState(false);

  const updateField = (field, value) => setForm((prev) => ({ ...prev, [field]: value }));

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmitted(true);
  };

  return (
    <div className="container mx-auto px-4 py-12">
      <p className="text-sm text-slate-500 uppercase tracking-wide mb-2">{t('contact')}</p>
      <h1 className="text-3xl font-bold text-slate-900 mb-6">Let&apos;s connect</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="bg-white rounded-2xl shadow-sm border border-slate-100 p-6">
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Name</label>
              <input
                className="w-full rounded-lg border border-slate-200 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={form.name}
                onChange={(e) => updateField('name', e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Email</label>
              <input
                type="email"
                className="w-full rounded-lg border border-slate-200 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={form.email}
                onChange={(e) => updateField('email', e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Message</label>
              <textarea
                className="w-full rounded-lg border border-slate-200 px-3 py-2 h-28 resize-none focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={form.message}
                onChange={(e) => updateField('message', e.target.value)}
                required
              />
            </div>
            <button
              type="submit"
              className="inline-flex justify-center rounded-lg bg-blue-600 text-white px-4 py-2 font-medium hover:bg-blue-700 transition"
            >
              Send message
            </button>
            {submitted && (
              <p className="text-sm text-emerald-600">Thanks! This will be wired to the backend later.</p>
            )}
          </form>
        </div>
        <div className="space-y-4">
          <div className="bg-white rounded-2xl shadow-sm border border-slate-100 p-6">
            <h2 className="text-xl font-semibold text-slate-900 mb-2">Status</h2>
            <p className="text-slate-600">Backend integration is coming soon. For now, this form just shows a success message.</p>
          </div>
          <div className="bg-white rounded-2xl shadow-sm border border-slate-100 p-6">
            <h2 className="text-xl font-semibold text-slate-900 mb-2">Availability</h2>
            <p className="text-slate-600">Open to new opportunities and collaborations.</p>
          </div>
        </div>
      </div>
    </div>
  );
}
