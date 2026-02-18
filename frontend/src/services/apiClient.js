import axios from 'axios';

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

// Render free-tier services can "sleep" and take time to wake up, causing transient 502/503 or network errors.
// Retry only safe/idempotent requests (GET/HEAD/OPTIONS) with backoff to smooth out cold starts.
apiClient.interceptors.response.use(
  (res) => res,
  async (error) => {
    const cfg = error?.config;
    if (!cfg) throw error;

    const method = (cfg.method || 'get').toLowerCase();
    const isSafeMethod = method === 'get' || method === 'head' || method === 'options';
    if (!isSafeMethod) throw error;

    const status = error?.response?.status;
    const isTransient =
      !error.response || status === 502 || status === 503 || status === 504;

    if (!isTransient) throw error;

    cfg.__retryCount = cfg.__retryCount || 0;
    const maxRetries = 5;
    if (cfg.__retryCount >= maxRetries) throw error;

    cfg.__retryCount += 1;

    const backoffMs = Math.min(8000, 500 * 2 ** (cfg.__retryCount - 1));
    await sleep(backoffMs);
    return apiClient.request(cfg);
  }
);

export default apiClient;
