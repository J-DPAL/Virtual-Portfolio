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
      const data = await messagesService.getAllMessages();
      setMessages(data);
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
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
          {error}
        </div>
      )}

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  {t('senderName')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  {t('email')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  {t('subject')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  {t('dateSubmitted')}
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  {t('actions')}
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {messages.map((message) => (
                <>
                  <tr key={message.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="font-medium text-gray-900">
                        {message.name}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-gray-900">{message.email}</div>
                    </td>
                    <td className="px-6 py-4">
                      <div className="text-gray-900">{message.subject}</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-gray-500 text-sm">
                        {formatDate(message.createdAt || message.date)}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm">
                      <div className="flex gap-2">
                        <button
                          onClick={() => toggleExpand(message.id)}
                          className="text-blue-600 hover:text-blue-900 font-medium"
                        >
                          {expandedId === message.id
                            ? 'Hide Message'
                            : 'View Message'}
                        </button>
                        <button
                          onClick={() => handleDelete(message.id)}
                          className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 transition"
                        >
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                  {expandedId === message.id && (
                    <tr>
                      <td colSpan="5" className="px-6 py-4 bg-gray-50">
                        <div className="space-y-3">
                          <div>
                            <h4 className="font-semibold text-gray-900">
                              Full Message:
                            </h4>
                            <p className="text-gray-700 whitespace-pre-wrap mt-2">
                              {message.message || message.content}
                            </p>
                          </div>
                          {message.phone && (
                            <div>
                              <h4 className="font-semibold text-gray-900">
                                Phone:
                              </h4>
                              <p className="text-gray-700">{message.phone}</p>
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
            <div className="text-center py-8 text-gray-500">
              No messages found
            </div>
          )}
        </div>
      </div>

      {/* Card View Alternative (Optional) */}
      <div className="grid gap-4 md:hidden">
        {messages.map((message) => (
          <div key={message.id} className="bg-white rounded-lg shadow p-4">
            <div className="flex justify-between items-start mb-3">
              <div>
                <h3 className="font-semibold text-gray-900">{message.name}</h3>
                <p className="text-sm text-gray-600">{message.email}</p>
              </div>
              <span className="text-xs text-gray-500">
                {formatDate(message.createdAt || message.date)}
              </span>
            </div>
            <h4 className="font-medium text-gray-800 mb-2">
              {message.subject}
            </h4>
            {expandedId === message.id && (
              <p className="text-gray-700 mb-3 whitespace-pre-wrap">
                {message.message || message.content}
              </p>
            )}
            <div className="flex gap-2">
              <button
                onClick={() => toggleExpand(message.id)}
                className="text-blue-600 hover:text-blue-900 text-sm font-medium"
              >
                {expandedId === message.id ? 'Hide' : 'Read More'}
              </button>
              <button
                onClick={() => handleDelete(message.id)}
                className="bg-red-600 text-white px-3 py-1 rounded text-sm hover:bg-red-700 transition"
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MessagesView;
