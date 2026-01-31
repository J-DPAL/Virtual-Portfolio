import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import {
  getApprovedTestimonials,
  submitTestimonial,
} from '../services/testimonialsService';

export default function TestimonialsPage() {
  const { t } = useTranslation();
  const [testimonials, setTestimonials] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);
  const [formData, setFormData] = useState({
    authorName: '',
    authorTitle: '',
    content: '',
    company: '',
  });

  useEffect(() => {
    fetchTestimonials();
  }, []);

  const fetchTestimonials = async () => {
    try {
      setLoading(true);
      const response = await getApprovedTestimonials();
      setTestimonials(response.data);
    } catch (err) {
      setError(t('errorOccurred'));
      console.error('Error fetching testimonials:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setError(null);

    try {
      await submitTestimonial(formData);
      setSuccess(true);
      setFormData({
        authorName: '',
        authorTitle: '',
        content: '',
        company: '',
      });
      setTimeout(() => {
        setSuccess(false);
        setShowForm(false);
      }, 3000);
    } catch (err) {
      setError(t('submitTestimonialFailed'));
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-yellow-50 via-white to-orange-50">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-rose-600"></div>
          <p className="mt-4 text-slate-600">{t('loading')}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-yellow-50 via-white to-orange-50 py-12">
      <div className="max-w-6xl mx-auto px-4">
        <div className="text-center mb-12">
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-yellow-600 to-orange-600 mb-4">
            {t('testimonials')}
          </h1>
          <p className="text-lg text-slate-600 max-w-2xl mx-auto mb-6">
            {t('addTestimonial')}
          </p>
          <button
            onClick={() => setShowForm(!showForm)}
            className="inline-flex items-center px-6 py-3 rounded-xl bg-gradient-to-r from-yellow-600 to-orange-600 text-white font-medium hover:shadow-lg transform hover:-translate-y-0.5 transition-all duration-200"
          >
            {showForm ? t('close') : t('addTestimonial')}
            <svg
              className="w-5 h-5 ml-2"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d={showForm ? 'M6 18L18 6M6 6l12 12' : 'M12 4v16m8-8H4'}
              />
            </svg>
          </button>
        </div>

        {showForm && (
          <div className="max-w-2xl mx-auto mb-12 bg-white rounded-2xl shadow-xl border border-slate-200 p-8">
            <h2 className="text-2xl font-bold text-slate-900 mb-6">
              Submit a Testimonial
            </h2>

            {success && (
              <div className="mb-6 bg-green-50 border border-green-200 text-green-700 px-4 py-3 rounded-lg">
                Thank you! Your testimonial has been submitted for review.
              </div>
            )}

            {error && (
              <div className="mb-6 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-2">
                  Your Name *
                </label>
                <input
                  type="text"
                  required
                  className="w-full px-4 py-3 rounded-lg border border-slate-300 focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition"
                  value={formData.authorName}
                  onChange={(e) =>
                    setFormData({ ...formData, authorName: e.target.value })
                  }
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-2">
                  Your Title *
                </label>
                <input
                  type="text"
                  required
                  className="w-full px-4 py-3 rounded-lg border border-slate-300 focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition"
                  value={formData.authorTitle}
                  onChange={(e) =>
                    setFormData({ ...formData, authorTitle: e.target.value })
                  }
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-2">
                  Company
                </label>
                <input
                  type="text"
                  className="w-full px-4 py-3 rounded-lg border border-slate-300 focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition"
                  value={formData.company}
                  onChange={(e) =>
                    setFormData({ ...formData, company: e.target.value })
                  }
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-2">
                  Your Testimonial *
                </label>
                <textarea
                  required
                  rows={4}
                  className="w-full px-4 py-3 rounded-lg border border-slate-300 focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition"
                  value={formData.content}
                  onChange={(e) =>
                    setFormData({ ...formData, content: e.target.value })
                  }
                />
              </div>
              <button
                type="submit"
                disabled={submitting}
                className="w-full px-6 py-3 rounded-xl bg-gradient-to-r from-yellow-600 to-orange-600 text-white font-medium hover:shadow-lg disabled:opacity-50 transition-all"
              >
                {submitting ? t('submitting') : t('submitTestimonial')}
              </button>
            </form>
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl mx-auto">
          {testimonials.map((testimonial) => (
            <div
              key={testimonial.id}
              className="bg-white rounded-2xl shadow-lg border border-slate-200 p-6 hover:-translate-y-2 hover:shadow-2xl transition-all duration-300"
            >
              <div className="flex items-center mb-4">
                <div className="w-12 h-12 bg-gradient-to-br from-yellow-500 to-orange-600 rounded-full flex items-center justify-center text-white font-bold text-xl">
                  {testimonial.authorName.charAt(0)}
                </div>
                <div className="ml-4">
                  <h3 className="font-bold text-slate-900">
                    {testimonial.authorName}
                  </h3>
                  <p className="text-sm text-slate-600">
                    {testimonial.authorTitle}
                  </p>
                  {testimonial.company && (
                    <p className="text-sm text-slate-500">
                      {testimonial.company}
                    </p>
                  )}
                </div>
              </div>
              <div className="flex mb-3">
                {[...Array(5)].map((_, i) => (
                  <svg
                    key={i}
                    className="w-5 h-5 text-amber-400"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                  </svg>
                ))}
              </div>
              <p className="text-slate-600 leading-relaxed italic">
                &ldquo;{testimonial.content}&rdquo;
              </p>
            </div>
          ))}
        </div>

        {testimonials.length === 0 && !loading && (
          <div className="text-center py-12">
            <p className="text-slate-600 text-lg">{t('noTestimonialsYet')}</p>
          </div>
        )}
      </div>
    </div>
  );
}
