import React, { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { sendMessage } from '../services/messagesService';
import { useTheme } from '../context/ThemeContext';
import { validateContactForm } from '../utils/validation';

export default function ContactPage() {
  const { t } = useTranslation();
  const { isDark } = useTheme();
  const [form, setForm] = useState({
    senderName: '',
    senderEmail: '',
    subject: '',
    message: '',
    website: '',
  });
  const [submitted, setSubmitted] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [captchaToken, setCaptchaToken] = useState('');
  const [cooldownSeconds, setCooldownSeconds] = useState(0);
  const turnstileContainerRef = useRef(null);
  const turnstileWidgetRef = useRef(null);
  const turnstileSiteKey = import.meta.env.VITE_TURNSTILE_SITE_KEY;
  const turnstileEnabled = Boolean(turnstileSiteKey);

  const updateField = (field, value) =>
    setForm((prev) => ({ ...prev, [field]: value }));

  useEffect(() => {
    if (cooldownSeconds <= 0) {
      return undefined;
    }

    const intervalId = setInterval(() => {
      setCooldownSeconds((prev) => (prev > 0 ? prev - 1 : 0));
    }, 1000);

    return () => clearInterval(intervalId);
  }, [cooldownSeconds]);

  useEffect(() => {
    if (!turnstileEnabled || !turnstileContainerRef.current) {
      return undefined;
    }

    let cancelled = false;

    const renderTurnstile = () => {
      if (cancelled || !window.turnstile || !turnstileContainerRef.current) {
        return;
      }

      if (turnstileWidgetRef.current !== null) {
        window.turnstile.remove(turnstileWidgetRef.current);
      }

      turnstileWidgetRef.current = window.turnstile.render(
        turnstileContainerRef.current,
        {
          sitekey: turnstileSiteKey,
          theme: isDark ? 'dark' : 'light',
          callback: (token) => setCaptchaToken(token),
          'error-callback': () => setCaptchaToken(''),
          'expired-callback': () => setCaptchaToken(''),
        }
      );
    };

    if (window.turnstile) {
      renderTurnstile();
    } else {
      const scriptId = 'cloudflare-turnstile-script';
      let script = document.getElementById(scriptId);
      if (!script) {
        script = document.createElement('script');
        script.id = scriptId;
        script.src =
          'https://challenges.cloudflare.com/turnstile/v0/api.js?render=explicit';
        script.async = true;
        script.defer = true;
        document.body.appendChild(script);
      }
      script.onload = () => renderTurnstile();
    }

    return () => {
      cancelled = true;
    };
  }, [isDark, turnstileEnabled, turnstileSiteKey]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (cooldownSeconds > 0 || loading) {
      return;
    }

    const validationMessage = validateContactForm(form, t);
    if (validationMessage) {
      setError(validationMessage);
      return;
    }

    if (turnstileEnabled && !captchaToken) {
      setError(t('captchaRequired'));
      return;
    }

    setLoading(true);
    setError(null);

    try {
      await sendMessage({ ...form, captchaToken });
      setSubmitted(true);
      setForm({
        senderName: '',
        senderEmail: '',
        subject: '',
        message: '',
        website: '',
      });
      setCaptchaToken('');
      if (window.turnstile && turnstileWidgetRef.current !== null) {
        window.turnstile.reset(turnstileWidgetRef.current);
      }
      setCooldownSeconds(60);
      setTimeout(() => setSubmitted(false), 5000);
    } catch (err) {
      if (err?.response?.status === 429) {
        setError(t('contactRateLimited'));
      } else if (err?.response?.status === 400) {
        const backendMessage = err?.response?.data?.message;
        if (backendMessage === 'Invalid captcha') {
          setError(t('captchaInvalid'));
        } else {
          setError(t('contactValidationFailed'));
        }
      } else {
        setError(t('sendMessageFailed'));
      }
      console.error('Error sending message:', err);
    } finally {
      setLoading(false);
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

  return (
    <div
      className={`min-h-screen py-12 transition-colors duration-200 relative overflow-hidden ${
        isDark
          ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950'
          : 'bg-gradient-to-br from-blue-50 via-white to-cyan-50'
      }`}
    >
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
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-cyan-600 mb-4">
            {t('contact')}
          </h1>
          <p
            className={`text-lg max-w-2xl mx-auto ${
              isDark ? 'text-slate-400' : 'text-slate-600'
            }`}
          >
            {t('letsConnectDiscuss')}
          </p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 max-w-6xl mx-auto">
          <div
            className={`rounded-2xl shadow-xl p-8 transition-colors duration-200 ${
              isDark
                ? 'bg-slate-800 border border-slate-700'
                : 'bg-slate-50 border border-slate-200'
            }`}
          >
            <h2
              className={`text-2xl font-bold mb-6 ${
                isDark ? 'text-slate-100' : 'text-slate-900'
              }`}
            >
              {t('sendMessage')}
            </h2>

            {submitted && (
              <div
                className={`mb-6 px-4 py-3 rounded-lg flex items-center border ${
                  isDark
                    ? 'bg-emerald-900 border-emerald-700 text-emerald-200'
                    : 'bg-emerald-50 border-emerald-200 text-emerald-800'
                }`}
              >
                <svg
                  className="w-5 h-5 mr-2"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M5 13l4 4L19 7"
                  />
                </svg>
                {t('messageSuccessfully')}
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
              <input
                type="text"
                name="website"
                className="absolute -left-[9999px] opacity-0 pointer-events-none"
                tabIndex={-1}
                autoComplete="off"
                value={form.website}
                onChange={(e) => updateField('website', e.target.value)}
                aria-hidden="true"
              />

              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  {t('name')} *
                </label>
                <input
                  type="text"
                  className={`w-full rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={form.senderName}
                  onChange={(e) => updateField('senderName', e.target.value)}
                  required
                />
                <p
                  className={`mt-1 text-xs ${
                    isDark ? 'text-slate-400' : 'text-slate-500'
                  }`}
                >
                  {t('validationContactNameHint', { min: 2, max: 100 })}
                </p>
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  {t('email')} *
                </label>
                <input
                  type="email"
                  className={`w-full rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={form.senderEmail}
                  onChange={(e) => updateField('senderEmail', e.target.value)}
                  required
                />
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  {t('subject')} *
                </label>
                <input
                  type="text"
                  className={`w-full rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={form.subject}
                  onChange={(e) => updateField('subject', e.target.value)}
                  required
                />
                <p
                  className={`mt-1 text-xs ${
                    isDark ? 'text-slate-400' : 'text-slate-500'
                  }`}
                >
                  {t('validationContactSubjectHint', { min: 3, max: 200 })}
                </p>
              </div>
              <div>
                <label
                  className={`block text-sm font-medium mb-2 ${
                    isDark ? 'text-slate-300' : 'text-slate-700'
                  }`}
                >
                  {t('message')} *
                </label>
                <textarea
                  className={`w-full rounded-lg px-4 py-3 h-32 resize-none focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition ${
                    isDark
                      ? 'bg-slate-700 border border-slate-600 text-slate-100 placeholder-slate-400'
                      : 'border border-slate-300 bg-slate-50 text-slate-900 placeholder-slate-500'
                  }`}
                  value={form.message}
                  onChange={(e) => updateField('message', e.target.value)}
                  required
                />
                <p
                  className={`mt-1 text-xs ${
                    isDark ? 'text-slate-400' : 'text-slate-500'
                  }`}
                >
                  {t('validationContactMessageHint', {
                    minChars: 20,
                    minWords: 5,
                    maxChars: 2000,
                  })}
                </p>
              </div>
              {turnstileEnabled && (
                <div>
                  <label
                    className={`block text-sm font-medium mb-2 ${
                      isDark ? 'text-slate-300' : 'text-slate-700'
                    }`}
                  >
                    {t('securityCheck')}
                  </label>
                  <div
                    ref={turnstileContainerRef}
                    className="min-h-[65px]"
                  ></div>
                </div>
              )}

              {cooldownSeconds > 0 && (
                <div
                  className={`text-sm font-medium ${
                    isDark ? 'text-amber-300' : 'text-amber-700'
                  }`}
                >
                  {t('contactCooldown', { seconds: cooldownSeconds })}
                </div>
              )}

              <button
                type="submit"
                disabled={loading || cooldownSeconds > 0}
                className="w-full flex justify-center items-center px-6 py-3 rounded-xl bg-gradient-to-r from-blue-600 to-cyan-600 text-white font-medium hover:shadow-lg disabled:opacity-50 transition-all"
              >
                {loading ? (
                  <>
                    <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white mr-2"></div>
                    {t('loading')}
                  </>
                ) : cooldownSeconds > 0 ? (
                  t('contactCooldownButton', { seconds: cooldownSeconds })
                ) : (
                  <>
                    {t('sendMessage')}
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
                        d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
                      />
                    </svg>
                  </>
                )}
              </button>
            </form>
          </div>

          <div className="space-y-6">
            <div
              className={`rounded-2xl shadow-lg p-8 transition-colors duration-200 ${
                isDark
                  ? 'bg-slate-800 border border-slate-700'
                  : 'bg-slate-50 border border-slate-200'
              }`}
            >
              <div className="flex items-center mb-4">
                <div
                  className={`w-12 h-12 rounded-xl flex items-center justify-center ${
                    isDark
                      ? 'bg-slate-700/50 text-blue-400'
                      : 'bg-gradient-to-br from-blue-100 to-cyan-100 text-blue-600'
                  }`}
                >
                  <svg
                    className="w-6 h-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
                    />
                  </svg>
                </div>
                <div className="ml-4">
                  <h3
                    className={`text-lg font-semibold ${
                      isDark ? 'text-slate-100' : 'text-slate-900'
                    }`}
                  >
                    {t('email')}
                  </h3>
                  <p
                    className={`${
                      isDark ? 'text-slate-400' : 'text-slate-600'
                    }`}
                  >
                    jeandavid.pallares@gmail.com
                  </p>
                </div>
              </div>
            </div>

            <div
              className={`rounded-2xl shadow-lg p-8 transition-colors duration-200 ${
                isDark
                  ? 'bg-slate-800 border border-slate-700'
                  : 'bg-slate-50 border border-slate-200'
              }`}
            >
              <div className="flex items-center mb-4">
                <div
                  className={`w-12 h-12 rounded-xl flex items-center justify-center ${
                    isDark
                      ? 'bg-slate-700/50 text-blue-400'
                      : 'bg-gradient-to-br from-blue-100 to-cyan-100 text-blue-600'
                  }`}
                >
                  <svg
                    className="w-6 h-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                    />
                  </svg>
                </div>
                <div className="ml-4">
                  <h3
                    className={`text-lg font-semibold ${
                      isDark ? 'text-slate-100' : 'text-slate-900'
                    }`}
                  >
                    {t('responseTime')}
                  </h3>
                  <p
                    className={`${
                      isDark ? 'text-slate-400' : 'text-slate-600'
                    }`}
                  >
                    {t('within24Hours')}
                  </p>
                </div>
              </div>
            </div>

            <div className="bg-gradient-to-br from-blue-600 to-cyan-600 rounded-2xl shadow-lg p-8 text-white">
              <h2 className="text-2xl font-bold mb-4">
                {t('letsCollaborate')}
              </h2>
              <p className="mb-4 text-blue-100">
                {t('alwaysInterestedProjects')}
              </p>
              <div className="flex space-x-4">
                <a
                  href="https://www.linkedin.com/in/jean-david-pallares/"
                  target="_blank"
                  rel="noopener noreferrer"
                  aria-label="LinkedIn"
                  className={`w-10 h-10 rounded-lg flex items-center justify-center transition ${
                    isDark
                      ? 'bg-white/20 hover:bg-white/30'
                      : 'bg-slate-100/70 hover:bg-slate-100'
                  }`}
                >
                  <svg
                    className="w-5 h-5"
                    fill="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path d="M19 0H5C2.239 0 0 2.239 0 5v14c0 2.761 2.239 5 5 5h14c2.761 0 5-2.239 5-5V5c0-2.761-2.239-5-5-5zM8 19H5V9h3v10zM6.5 7.732A1.768 1.768 0 116.5 4.196a1.768 1.768 0 010 3.536zM20 19h-3v-5.604c0-1.337-.027-3.059-1.865-3.059-1.867 0-2.153 1.459-2.153 2.967V19h-3V9h2.881v1.367h.041c.401-.76 1.38-1.561 2.84-1.561 3.037 0 3.6 2 3.6 4.599V19z" />
                  </svg>
                </a>
                <a
                  href="https://wa.me/15148204770"
                  target="_blank"
                  rel="noopener noreferrer"
                  aria-label="WhatsApp"
                  className={`w-10 h-10 rounded-lg flex items-center justify-center transition ${
                    isDark
                      ? 'bg-white/20 hover:bg-white/30'
                      : 'bg-slate-100/70 hover:bg-slate-100'
                  }`}
                >
                  <svg
                    className="w-5 h-5"
                    fill="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path d="M20.52 3.48A11.82 11.82 0 0012.03 0C5.4 0 .02 5.37.02 12c0 2.11.55 4.17 1.59 5.99L0 24l6.17-1.62A11.98 11.98 0 0012.03 24c6.62 0 12-5.37 12-12 0-3.2-1.25-6.2-3.51-8.52zM12.03 21.9c-1.8 0-3.57-.48-5.1-1.38l-.37-.22-3.66.96.98-3.57-.24-.37A9.88 9.88 0 012.13 12c0-5.45 4.44-9.89 9.9-9.89 2.64 0 5.12 1.03 6.98 2.9A9.79 9.79 0 0121.93 12c0 5.45-4.44 9.9-9.9 9.9zm5.43-7.42c-.3-.15-1.77-.87-2.04-.97-.27-.1-.47-.15-.67.15-.2.3-.77.97-.95 1.17-.17.2-.35.22-.65.07-.3-.15-1.26-.46-2.4-1.47-.89-.79-1.48-1.76-1.66-2.06-.17-.3-.02-.46.13-.61.14-.14.3-.35.45-.52.15-.18.2-.3.3-.5.1-.2.05-.37-.02-.52-.08-.15-.67-1.61-.92-2.21-.24-.57-.49-.5-.67-.51h-.57c-.2 0-.52.08-.8.37-.27.3-1.04 1.02-1.04 2.48s1.07 2.86 1.22 3.06c.15.2 2.09 3.19 5.07 4.47.71.31 1.27.49 1.7.63.72.23 1.37.2 1.89.12.58-.09 1.77-.72 2.02-1.41.25-.7.25-1.29.17-1.42-.07-.12-.27-.2-.57-.35z" />
                  </svg>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
