const ADMIN_USER = {
  id: 1,
  email: 'admin@example.com',
  fullName: 'Admin User',
  role: 'ADMIN',
  active: true,
};

export const mockCurrentUserAuthenticated = async (page, user = ADMIN_USER) => {
  await page.route('**/v1/auth/me', async (route) => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify(user),
    });
  });
};

export const mockCurrentUserUnauthenticated = async (page) => {
  await page.route('**/v1/auth/me', async (route) => {
    await route.fulfill({
      status: 403,
      contentType: 'application/json',
      body: JSON.stringify({ message: 'Forbidden' }),
    });
  });
};

export const mockLogoutSuccess = async (page) => {
  await page.route('**/v1/auth/logout', async (route) => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify({ ok: true }),
    });
  });
};

export const loginAsAdmin = async (page) => {
  await mockCurrentUserAuthenticated(page);
  await mockLogoutSuccess(page);
  await page.goto('/admin/dashboard', { waitUntil: 'domcontentloaded' });
};

export const mockLoginSuccess = async (page, user = ADMIN_USER) => {
  await mockCurrentUserAuthenticated(page, user);
  await mockLogoutSuccess(page);
  await page.route('**/v1/auth/login', async (route) => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify({
        user,
        token: 'test-auth-token',
        message: 'Login successful',
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
