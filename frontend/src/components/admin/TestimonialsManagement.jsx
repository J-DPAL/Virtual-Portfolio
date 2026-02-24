import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

import { useTheme } from '../../context/ThemeContext';
import * as testimonialsService from '../../services/testimonialsService';

const TestimonialsManagement = () => {
  const { t, i18n } = useTranslation();
  const { isDark } = useTheme();
  const [testimonials, setTestimonials] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [expandedId, setExpandedId] = useState(null);

  useEffect(() => {
    fetchTestimonials();
  }, []);

  const fetchTestimonials = async () => {
    try {
      setLoading(true);
      const [pendingResponse, approvedResponse] = await Promise.all([
        testimonialsService.getPendingTestimonials(),
        testimonialsService.getApprovedTestimonials(),
      ]);

      const merged = [...(pendingResponse.data || []), ...(approvedResponse.data || [])]
        .reduce((acc, current) => {
          if (!acc.some((item) => item.id === current.id)) {
            acc.push(current);
          }
          return acc;
        }, [])
        .sort((a, b) => new Date(b.createdAt || 0) - new Date(a.createdAt || 0));

      setTestimonials(merged);
      setError(null);
    } catch (err) {
      setError(t('manageTestimonialsFailedLoad'));
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleApprove = async (id) => {
    try {
      await testimonialsService.approveTestimonial(id);
      await fetchTestimonials();
    } catch (err) {
      setError(t('manageTestimonialsFailedApprove'));
      console.error(err);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm(t('confirmDeleteTestimonial'))) {
      return;
    }
    try {
      await testimonialsService.deleteTestimonial(id);
      await fetchTestimonials();
    } catch (err) {
      setError(t('manageTestimonialsFailedDelete'));
      console.error(err);
    }
  };

  const toggleExpand = (id) => {
    setExpandedId(expandedId === id ? null : id);
  };

  const getTestimonialContent = (testimonial) => {
    if (i18n.language === 'fr' && testimonial.testimonialTextFr) {
      return testimonial.testimonialTextFr;
    }
    if (i18n.language === 'es' && testimonial.testimonialTextEs) {
      return testimonial.testimonialTextEs;
    }
    return testimonial.testimonialTextEn || testimonial.content || '';
  };

  const renderStars = (rating) => {
    return (
      <div className="flex gap-1">
        {[...Array(5)].map((_, index) => (
          <svg
            key={index}
            className={`w-5 h-5 ${
              index < rating ? 'text-yellow-400' : isDark ? 'text-slate-600' : 'text-gray-300'
            }`}
            fill="currentColor"
            viewBox="0 0 20 20"
          >
            <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
          </svg>
        ))}
      </div>
    );
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-rose-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="bg-gradient-to-r from-yellow-600 to-orange-600 text-white p-6 rounded-lg shadow-lg">
        <h2 className="text-3xl font-bold">{t('testimonialManagement')}</h2>
        <p className="mt-2 opacity-90">{t('reviewApproveTestimonials')}</p>
      </div>

      {error && (
        <div className="bg-red-100 dark:bg-red-900/30 border border-red-400 dark:border-red-700 text-red-700 dark:text-red-300 px-4 py-3 rounded">
          {error}
        </div>
      )}

      <div className="bg-white dark:bg-slate-900 rounded-lg shadow overflow-hidden border border-slate-200 dark:border-slate-700">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200 dark:divide-slate-700">
            <thead className="bg-gray-50 dark:bg-slate-800">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('name')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('position')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('company')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('rating')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('status')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('actions')}
                </th>
              </tr>
            </thead>
            <tbody className="bg-white dark:bg-slate-900 divide-y divide-gray-200 dark:divide-slate-700">
              {testimonials.map((testimonial) => (
                <React.Fragment key={testimonial.id}>
                  <tr className="hover:bg-gray-50 dark:hover:bg-slate-800">
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="font-medium text-gray-900 dark:text-slate-100">
                        {testimonial.clientName}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-gray-900 dark:text-slate-100">
                        {testimonial.clientPosition}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-gray-900 dark:text-slate-100">
                        {testimonial.clientCompany}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {renderStars(testimonial.rating)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {testimonial.approved ? (
                        <span className="px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 dark:bg-green-900/40 text-green-800 dark:text-green-300">
                          {t('approved')}
                        </span>
                      ) : (
                        <span className="px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 dark:bg-yellow-900/40 text-yellow-800 dark:text-yellow-300">
                          {t('pending')}
                        </span>
                      )}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm">
                      <div className="flex gap-2">
                        <button
                          onClick={() => toggleExpand(testimonial.id)}
                          className="text-blue-600 dark:text-blue-400 hover:text-blue-900 dark:hover:text-blue-300"
                        >
                          {expandedId === testimonial.id ? t('hide') : t('view')}
                        </button>
                        {!testimonial.approved && (
                          <button
                            onClick={() => handleApprove(testimonial.id)}
                            className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700 transition"
                          >
                            {t('approve')}
                          </button>
                        )}
                        <button
                          onClick={() => handleDelete(testimonial.id)}
                          className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 transition"
                        >
                          {t('delete')}
                        </button>
                      </div>
                    </td>
                  </tr>
                  {expandedId === testimonial.id && (
                    <tr>
                      <td colSpan="6" className="px-6 py-4 bg-gray-50 dark:bg-slate-800">
                        <div className="space-y-2">
                          <h4 className="font-semibold text-gray-900 dark:text-slate-100">
                            {t('contentLabel')}
                          </h4>
                          <p className="text-gray-700 dark:text-slate-300 whitespace-pre-wrap">
                            {getTestimonialContent(testimonial)}
                          </p>
                        </div>
                      </td>
                    </tr>
                  )}
                </React.Fragment>
              ))}
            </tbody>
          </table>
          {testimonials.length === 0 && (
            <div className="text-center py-8 text-gray-500 dark:text-slate-400">
              {t('noTestimonialsFound')}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default TestimonialsManagement;
