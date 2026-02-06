import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../context/ThemeContext';
import {
  getApprovedTestimonials,
  submitTestimonial,
} from '../services/testimonialsService';

export default function TestimonialsPage() {
  const { t, i18n } = useTranslation();
  const { isDark } = useTheme();
  const [testimonials, setTestimonials] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const currentLang = i18n.language;
  const [showForm, setShowForm] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);
  const [formData, setFormData] = useState({
    clientName: '',
    clientPosition: '',
    testimonialTextEn: '',
    testimonialTextFr: '',
    testimonialTextEs: '',
    clientCompany: '',
    rating: 5,
    clientImageUrl: '',
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
        clientName: '',
        clientPosition: '',
        testimonialTextEn: '',
        testimonialTextFr: '',
        testimonialTextEs: '',
        clientCompany: '',
        rating: 5,
        clientImageUrl: '',
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
            : 'bg-gradient-to-br from-yellow-50 via-white to-orange-50'
        }`}
      >
        <div className="text-center">
          <div
            className={`inline-block animate-spin rounded-full h-12 w-12 border-b-2 ${
              isDark ? 'border-rose-400' : 'border-rose-600'
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
          : 'bg-gradient-to-br from-yellow-50 via-white to-orange-50'
      }`}
    >
      <div className="pointer-events-none absolute inset-0" aria-hidden="true">
        <div
          className={`absolute -top-20 left-10 h-72 w-72 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-yellow-700' : 'bg-yellow-300'
          }`}
        ></div>
        <div
          className={`absolute -bottom-24 right-0 h-96 w-96 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-orange-700' : 'bg-orange-300'
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
      <div className="max-w-6xl mx-auto px-4 relative z-10">
        <div className="text-center mb-12">
          <span
            className={`inline-flex items-center px-4 py-1.5 rounded-full text-sm font-semibold border mb-4 ${
              isDark
                ? 'bg-slate-800/60 border-slate-700 text-slate-200'
                : 'bg-slate-50/70 border-slate-200 text-slate-700'
            }`}
          >
            Client Voices
          </span>
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-yellow-600 to-orange-600 mb-4">
            {t('testimonials')}
          </h1>
          <p
            className={`text-lg max-w-2xl mx-auto mb-6 ${
              isDark ? 'text-slate-400' : 'text-slate-600'
            }`}
          >
            {t('addTestimonial')}
          </p>
          <button
            onClick={() => setShowForm(!showForm)}
            className="inline-flex items-center px-6 py-3 rounded-xl bg-gradient-to-r from-yellow-600 to-orange-600 text-white font-medium hover:shadow-xl hover:-translate-y-0.5 transition-all duration-200 shadow-lg shadow-yellow-600/20"
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
          <div
            className={`max-w-2xl mx-auto mb-12 rounded-2xl p-8 transition-colors duration-200 ring-1 ${
              isDark
                ? 'bg-slate-900/70 ring-slate-800'
                : 'bg-slate-50/80 ring-slate-200'
            }`}
          >
            <h2
              className={`text-2xl font-bold mb-6 ${
                isDark ? 'text-slate-100' : 'text-slate-900'
              }`}
            >
              Submit a Testimonial
            </h2>

            {success && (
              <div
                className={`mb-6 px-4 py-3 rounded-lg border ${
                  isDark
                    ? 'bg-emerald-900 border-emerald-700 text-emerald-200'
                    : 'bg-emerald-50 border-emerald-200 text-emerald-800'
                }`}
              >
                Thank you! Your testimonial has been submitted for review.
              </div>
            )}

            {error && (
              <div
                className={`mb-6 px-4 py-3 rounded-lg border ${
                  isDark
                    ? 'bg-rose-900 border-rose-700 text-rose-200'
                    : 'bg-rose-50 border-rose-200 text-rose-800'
                }`}
              >
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  Your Name *
                </label>
                <input
                  type="text"
                  required
                  className={`w-full px-4 py-3 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={formData.clientName}
                  onChange={(e) =>
                    setFormData({ ...formData, clientName: e.target.value })
                  }
                />
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  Your Title *
                </label>
                <input
                  type="text"
                  required
                  className={`w-full px-4 py-3 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={formData.clientPosition}
                  onChange={(e) =>
                    setFormData({ ...formData, clientPosition: e.target.value })
                  }
                />
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  Company
                </label>
                <input
                  type="text"
                  className={`w-full px-4 py-3 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={formData.clientCompany}
                  onChange={(e) =>
                    setFormData({ ...formData, clientCompany: e.target.value })
                  }
                />
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  Your Testimonial in English *
                </label>
                <textarea
                  required
                  rows={4}
                  className={`w-full px-4 py-3 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={formData.testimonialTextEn}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      testimonialTextEn: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  Your Testimonial in French *
                </label>
                <textarea
                  required
                  rows={4}
                  className={`w-full px-4 py-3 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={formData.testimonialTextFr}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      testimonialTextFr: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  Your Testimonial in Spanish *
                </label>
                <textarea
                  required
                  rows={4}
                  className={`w-full px-4 py-3 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={formData.testimonialTextEs}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      testimonialTextEs: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  Rating (1-5) *
                </label>
                <input
                  type="number"
                  min="1"
                  max="5"
                  required
                  className={`w-full px-4 py-3 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-rose-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={formData.rating}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      rating: parseInt(e.target.value),
                    })
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
              className={`relative rounded-2xl p-6 transition-all duration-300 ring-1 hover:-translate-y-2 ${
                isDark
                  ? 'bg-slate-900/70 ring-slate-800 hover:ring-yellow-500/40'
                  : 'bg-slate-50/80 ring-slate-200 hover:ring-yellow-400/40'
              }`}
            >
              <div
                className={`absolute right-4 top-4 text-4xl font-serif opacity-20 ${
                  isDark ? 'text-yellow-300' : 'text-yellow-500'
                }`}
                aria-hidden="true"
              >
                “
              </div>
              <div className="flex items-center mb-4">
                <div className="w-12 h-12 bg-gradient-to-br from-yellow-500 to-orange-600 rounded-full flex items-center justify-center text-white font-bold text-xl">
                  {testimonial.clientName?.charAt(0) || 'A'}
                </div>
                <div className="ml-4">
                  <h3
                    className={`font-bold ${
                      isDark ? 'text-slate-100' : 'text-slate-900'
                    }`}
                  >
                    {testimonial.clientName}
                  </h3>
                  <p
                    className={`text-sm ${
                      isDark ? 'text-slate-400' : 'text-slate-600'
                    }`}
                  >
                    {testimonial.clientPosition}
                  </p>
                  {testimonial.clientCompany && (
                    <p
                      className={`text-sm ${
                        isDark ? 'text-slate-500' : 'text-slate-500'
                      }`}
                    >
                      {testimonial.clientCompany}
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
              <p
                className={`leading-relaxed italic ${
                  isDark ? 'text-slate-300' : 'text-slate-600'
                }`}
              >
                &ldquo;
                {currentLang === 'es' && testimonial.testimonialTextEs
                  ? testimonial.testimonialTextEs
                  : currentLang === 'fr' && testimonial.testimonialTextFr
                    ? testimonial.testimonialTextFr
                    : testimonial.testimonialTextEn}
                &rdquo;
              </p>
            </div>
          ))}
        </div>

        {testimonials.length === 0 && !loading && (
          <div className="text-center py-12">
            <p
              className={`text-lg ${
                isDark ? 'text-slate-400' : 'text-slate-600'
              }`}
            >
              {t('noTestimonialsYet')}
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
