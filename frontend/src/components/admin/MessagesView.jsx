import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import * as messagesService from '../../services/messagesService';

const MessagesView = () => {
  const { t } = useTranslation();
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [expandedId, setExpandedId] = useState(null);

  useEffect(() => {
    fetchMessages();
  }, []);

  const fetchMessages = async () => {
    try {
      setLoading(true);
      const response = await messagesService.getAllMessages();
      setMessages(response.data);
      setError(null);
    } catch (err) {
      setError(t('manageMessagesFailedLoad'));
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm(t('confirmDeleteMessage'))) {
      try {
        await messagesService.deleteMessage(id);
        await fetchMessages();
      } catch (err) {
        setError(t('manageMessagesFailedDelete'));
        console.error(err);
      }
    }
  };

  const toggleExpand = (id) => {
    setExpandedId(expandedId === id ? null : id);
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="bg-gradient-to-r from-blue-600 to-cyan-600 text-white p-6 rounded-lg shadow-lg">
        <h2 className="text-3xl font-bold">{t('contactMessages')}</h2>
        <p className="mt-2 opacity-90">{t('viewManageMessages')}</p>
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
                  {t('senderName')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('email')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('subject')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('dateSubmitted')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-slate-400 uppercase tracking-wider">
                  {t('actions')}
                </th>
              </tr>
            </thead>
            <tbody className="bg-white dark:bg-slate-900 divide-y divide-gray-200 dark:divide-slate-700">
              {messages.map((message) => (
                <>
                  <tr key={message.id} className="hover:bg-gray-50 dark:hover:bg-slate-800">
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="font-medium text-gray-900 dark:text-slate-100">
                        {message.name}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-gray-900 dark:text-slate-100">{message.email}</div>
                    </td>
                    <td className="px-6 py-4">
                      <div className="text-gray-900 dark:text-slate-100">{message.subject}</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-gray-500 dark:text-slate-400 text-sm">
                        {formatDate(message.createdAt || message.date)}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm">
                      <div className="flex gap-2">
                        <button
                          onClick={() => toggleExpand(message.id)}
                          className="text-blue-600 dark:text-blue-400 hover:text-blue-900 dark:hover:text-blue-300 font-medium"
                        >
                          {expandedId === message.id
                            ? t('hideMessage')
                            : t('viewMessage')}
                        </button>
                        <button
                          onClick={() => handleDelete(message.id)}
                          className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 transition"
                        >
                          {t('delete')}
                        </button>
                      </div>
                    </td>
                  </tr>
                  {expandedId === message.id && (
                    <tr>
                      <td colSpan="5" className="px-6 py-4 bg-gray-50 dark:bg-slate-800">
                        <div className="space-y-3">
                          <div>
                            <h4 className="font-semibold text-gray-900 dark:text-slate-100">
                              {t('fullMessage')}
                            </h4>
                            <p className="text-gray-700 dark:text-slate-300 whitespace-pre-wrap mt-2">
                              {message.message || message.content}
                            </p>
                          </div>
                          {message.phone && (
                            <div>
                              <h4 className="font-semibold text-gray-900 dark:text-slate-100">
                                {t('phone')}
                              </h4>
                              <p className="text-gray-700 dark:text-slate-300">{message.phone}</p>
                            </div>
                          )}
                        </div>
                      </td>
                    </tr>
                  )}
                </>
              ))}
            </tbody>
          </table>
          {messages.length === 0 && (
            <div className="text-center py-8 text-gray-500 dark:text-slate-400">
              {t('noMessagesFound')}
            </div>
          )}
        </div>
      </div>

      {/* Card View Alternative (Optional) */}
      <div className="grid gap-4 md:hidden">
        {messages.map((message) => (
          <div key={message.id} className="bg-white dark:bg-slate-900 rounded-lg shadow p-4 border border-slate-200 dark:border-slate-700">
            <div className="flex justify-between items-start mb-3">
              <div>
                <h3 className="font-semibold text-gray-900 dark:text-slate-100">{message.name}</h3>
                <p className="text-sm text-gray-600 dark:text-slate-400">{message.email}</p>
              </div>
              <span className="text-xs text-gray-500 dark:text-slate-400">
                {formatDate(message.createdAt || message.date)}
              </span>
            </div>
            <h4 className="font-medium text-gray-800 dark:text-slate-200 mb-2">
              {message.subject}
            </h4>
            {expandedId === message.id && (
              <p className="text-gray-700 dark:text-slate-300 mb-3 whitespace-pre-wrap">
                {message.message || message.content}
              </p>
            )}
            <div className="flex gap-2">
              <button
                onClick={() => toggleExpand(message.id)}
                className="text-blue-600 dark:text-blue-400 hover:text-blue-900 dark:hover:text-blue-300 text-sm font-medium"
              >
                {expandedId === message.id ? t('hide') : t('readMore')}
              </button>
              <button
                onClick={() => handleDelete(message.id)}
                className="bg-red-600 text-white px-3 py-1 rounded text-sm hover:bg-red-700 transition"
              >
                {t('delete')}
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MessagesView;




