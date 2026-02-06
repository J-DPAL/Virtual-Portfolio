import { expect } from '@playwright/test';

export const loginAsAdmin = async (page) => {
  await mockLoginSuccess(page);
  await page.addInitScript(() => {
    localStorage.setItem('authToken', 'test-auth-token');
  });

  await page.goto('/admin/dashboard', { waitUntil: 'domcontentloaded' });
};

export const mockLoginSuccess = async (page) => {
  await page.route('**/v1/auth/login', async (route) => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify({
        user: { id: 1, email: 'admin@example.com', fullName: 'Admin User' },
        token: 'test-auth-token',
      }),
    });
  });
};

export const mockLoginFailure = async (page) => {
  await page.route('**/v1/auth/login', async (route) => {
    await route.fulfill({
      status: 401,
      contentType: 'application/json',
      body: JSON.stringify({ message: 'Invalid credentials' }),
    });
  });
};
