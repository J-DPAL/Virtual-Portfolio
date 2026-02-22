import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import * as filesService from '../../services/filesService';

const ResumeUpload = () => {
  const { t } = useTranslation();
  const [enFile, setEnFile] = useState(null);
  const [frFile, setFrFile] = useState(null);
  const [esFile, setEsFile] = useState(null);
  const [enUploading, setEnUploading] = useState(false);
  const [frUploading, setFrUploading] = useState(false);
  const [esUploading, setEsUploading] = useState(false);
  const [enProgress, setEnProgress] = useState(0);
  const [frProgress, setFrProgress] = useState(0);
  const [esProgress, setEsProgress] = useState(0);
  const [enSuccess, setEnSuccess] = useState(null);
  const [frSuccess, setFrSuccess] = useState(null);
  const [esSuccess, setEsSuccess] = useState(null);
  const [error, setError] = useState(null);
  const [dragActiveEn, setDragActiveEn] = useState(false);
  const [dragActiveFr, setDragActiveFr] = useState(false);
  const [dragActiveEs, setDragActiveEs] = useState(false);

  const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

  const validateFile = (file) => {
    if (!file) {
      return t('pleasSelectFile');
    }
    if (file.type !== 'application/pdf') {
      return t('onlyPDFAllowed');
    }
    if (file.size > MAX_FILE_SIZE) {
      return t('fileSizeMustBeLess');
    }
    return null;
  };

  const handleFileChange = (e, language) => {
    const file = e.target.files[0];
    if (language === 'en') {
      setEnFile(file);
      setEnSuccess(null);
    } else if (language === 'fr') {
      setFrFile(file);
      setFrSuccess(null);
    } else {
      setEsFile(file);
      setEsSuccess(null);
    }
    setError(null);
  };

  const handleDrag = (e, language) => {
    e.preventDefault();
    e.stopPropagation();
    if (e.type === 'dragenter' || e.type === 'dragover') {
      if (language === 'en') {
        setDragActiveEn(true);
      } else if (language === 'fr') {
        setDragActiveFr(true);
      } else {
        setDragActiveEs(true);
      }
    } else if (e.type === 'dragleave') {
      if (language === 'en') {
        setDragActiveEn(false);
      } else if (language === 'fr') {
        setDragActiveFr(false);
      } else {
        setDragActiveEs(false);
      }
    }
  };

  const handleDrop = (e, language) => {
    e.preventDefault();
    e.stopPropagation();
    if (language === 'en') {
      setDragActiveEn(false);
    } else if (language === 'fr') {
      setDragActiveFr(false);
    } else {
      setDragActiveEs(false);
    }

    const file = e.dataTransfer.files[0];
    if (file) {
      if (language === 'en') {
        setEnFile(file);
        setEnSuccess(null);
      } else if (language === 'fr') {
        setFrFile(file);
        setFrSuccess(null);
      } else {
        setEsFile(file);
        setEsSuccess(null);
      }
      setError(null);
    }
  };

  const handleUpload = async (language) => {
    const fileByLanguage = {
      en: enFile,
      fr: frFile,
      es: esFile,
    };
    const file = fileByLanguage[language];
    const validationError = validateFile(file);

    if (validationError) {
      setError(validationError);
      return;
    }

    const setUploadingByLanguage = {
      en: setEnUploading,
      fr: setFrUploading,
      es: setEsUploading,
    };
    const setProgressByLanguage = {
      en: setEnProgress,
      fr: setFrProgress,
      es: setEsProgress,
    };
    const setSuccessByLanguage = {
      en: setEnSuccess,
      fr: setFrSuccess,
      es: setEsSuccess,
    };
    const setUploading = setUploadingByLanguage[language];
    const setProgress = setProgressByLanguage[language];
    const setSuccess = setSuccessByLanguage[language];

    try {
      setUploading(true);
      setError(null);
      setProgress(0);

      // Simulate progress
      const progressInterval = setInterval(() => {
        setProgress((prev) => {
          if (prev >= 90) {
            clearInterval(progressInterval);
            return prev;
          }
          return prev + 10;
        });
      }, 200);

      await filesService.uploadResume(file, language);

      clearInterval(progressInterval);
      setProgress(100);
      setSuccess(t('uploadSuccessfully'));

      // Reset after 3 seconds
      setTimeout(() => {
        setSuccess(null);
        if (language === 'en') {
          setEnFile(null);
        } else if (language === 'fr') {
          setFrFile(null);
        } else {
          setEsFile(null);
        }
        setProgress(0);
      }, 3000);
    } catch (err) {
      setError(t('uploadFailedTryAgain'));
      console.error(err);
    } finally {
      setUploading(false);
    }
  };

  const formatFileSize = (bytes) => {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round((bytes / Math.pow(k, i)) * 100) / 100 + ' ' + sizes[i];
  };

  const renderUploadSection = (
    language,
    file,
    setFile,
    uploading,
    progress,
    success,
    dragActive
  ) => {
    const isEnglish = language === 'en';
    const isFrench = language === 'fr';
    const title = isEnglish
      ? `${t('language')}: ${t('english')}`
      : isFrench
        ? `${t('language')}: ${t('french')}`
        : `${t('language')}: ES`;

    return (
      <div className="bg-white dark:bg-slate-900 rounded-lg shadow-md p-6 border border-slate-200 dark:border-slate-700">
        <h3 className="text-xl font-semibold mb-4 text-gray-800 dark:text-slate-100">{title}</h3>

        <div
          className={`border-2 border-dashed rounded-lg p-8 text-center transition ${
            dragActive
              ? 'border-green-500 bg-green-50 dark:bg-green-900/20'
              : 'border-gray-300 dark:border-slate-600 hover:border-green-400'
          }`}
          onDragEnter={(e) => handleDrag(e, language)}
          onDragLeave={(e) => handleDrag(e, language)}
          onDragOver={(e) => handleDrag(e, language)}
          onDrop={(e) => handleDrop(e, language)}
        >
          <svg
            className="mx-auto h-12 w-12 text-gray-400 dark:text-slate-500"
            stroke="currentColor"
            fill="none"
            viewBox="0 0 48 48"
          >
            <path
              d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02"
              strokeWidth={2}
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
          <div className="mt-4">
            <label
              htmlFor={`file-upload-${language}`}
              className="cursor-pointer bg-gradient-to-r from-green-600 to-emerald-600 text-white px-4 py-2 rounded-md hover:from-green-700 hover:to-emerald-700 transition"
            >
              {t('selectPDFFile')}
              <input
                id={`file-upload-${language}`}
                type="file"
                className="sr-only"
                accept=".pdf"
                onChange={(e) => handleFileChange(e, language)}
                disabled={uploading}
              />
            </label>
          </div>
          <p className="mt-2 text-sm text-gray-600 dark:text-slate-400">{t('dragDropResume')}</p>
        </div>

        {file && (
          <div className="mt-4 p-4 bg-gray-50 dark:bg-slate-800 rounded-lg">
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-3">
                <svg
                  className="h-8 w-8 text-red-600"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                >
                  <path
                    fillRule="evenodd"
                    d="M4 4a2 2 0 012-2h4.586A2 2 0 0112 2.586L15.414 6A2 2 0 0116 7.414V16a2 2 0 01-2 2H6a2 2 0 01-2-2V4z"
                    clipRule="evenodd"
                  />
                </svg>
                <div>
                  <p className="text-sm font-medium text-gray-900 dark:text-slate-100">
                    {file.name}
                  </p>
                  <p className="text-xs text-gray-500 dark:text-slate-400">
                    {formatFileSize(file.size)}
                  </p>
                </div>
              </div>
              <button
                onClick={() => {
                  if (isEnglish) {
                    setEnFile(null);
                    setEnSuccess(null);
                  } else if (isFrench) {
                    setFrFile(null);
                    setFrSuccess(null);
                  } else {
                    setEsFile(null);
                    setEsSuccess(null);
                  }
                }}
                className="text-red-600 dark:text-red-400 hover:text-red-800 dark:hover:text-red-300"
                disabled={uploading}
              >
                <svg
                  className="h-5 w-5"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                >
                  <path
                    fillRule="evenodd"
                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                    clipRule="evenodd"
                  />
                </svg>
              </button>
            </div>

            {uploading && (
              <div className="mt-3">
                <div className="flex justify-between text-sm text-gray-600 dark:text-slate-400 mb-1">
                  <span>{t('uploading')}</span>
                  <span>{progress}%</span>
                </div>
                <div className="w-full bg-gray-200 dark:bg-slate-700 rounded-full h-2">
                  <div
                    className="bg-gradient-to-r from-green-600 to-emerald-600 h-2 rounded-full transition-all duration-300"
                    style={{ width: `${progress}%` }}
                  ></div>
                </div>
              </div>
            )}
          </div>
        )}

        {success && (
          <div className="mt-4 bg-green-100 dark:bg-green-900/30 border border-green-400 dark:border-green-700 text-green-700 dark:text-green-300 px-4 py-3 rounded">
            {success}
          </div>
        )}

        <button
          onClick={() => handleUpload(language)}
          disabled={!file || uploading}
          className={`mt-4 w-full py-2 px-4 rounded-md font-medium transition ${
            !file || uploading
              ? 'bg-gray-300 dark:bg-slate-700 text-gray-500 dark:text-slate-400 cursor-not-allowed'
              : 'bg-gradient-to-r from-green-600 to-emerald-600 text-white hover:from-green-700 hover:to-emerald-700'
          }`}
        >
          {uploading ? t('uploading') : `${t('upload')} ${title}`}
        </button>
      </div>
    );
  };

  return (
    <div className="space-y-6">
      <div className="bg-gradient-to-r from-green-600 to-emerald-600 text-white p-6 rounded-lg shadow-lg">
        <h2 className="text-3xl font-bold">{t('uploadResumePDF')}</h2>
        <p className="mt-2 opacity-90">{t('uploadResume')}</p>
      </div>

      {error && (
        <div className="bg-red-100 dark:bg-red-900/30 border border-red-400 dark:border-red-700 text-red-700 dark:text-red-300 px-4 py-3 rounded">
          {error}
        </div>
      )}

      <div className="grid md:grid-cols-3 gap-6">
        {renderUploadSection(
          'en',
          enFile,
          setEnFile,
          enUploading,
          enProgress,
          enSuccess,
          dragActiveEn
        )}
        {renderUploadSection(
          'fr',
          frFile,
          setFrFile,
          frUploading,
          frProgress,
          frSuccess,
          dragActiveFr
        )}
        {renderUploadSection(
          'es',
          esFile,
          setEsFile,
          esUploading,
          esProgress,
          esSuccess,
          dragActiveEs
        )}
      </div>
    </div>
  );
};

export default ResumeUpload;



