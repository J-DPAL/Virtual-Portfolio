import { test, expect } from '@playwright/test';
import { mockLoginFailure, mockLoginSuccess } from './utils/auth';

test.describe('Admin Login & Authentication', () => {
  test.beforeEach(async ({ page }) => {
    // Navigate to login page before each test
    await page.goto('/login', { waitUntil: 'domcontentloaded' });
    await page.waitForSelector('input[type="email"]', {
      state: 'visible',
      timeout: 10000,
    });
    await page.waitForSelector('input[type="password"]', {
      state: 'visible',
      timeout: 10000,
    });
  });

  test('should display login form with required fields', async ({ page }) => {
    // Verify login page title/heading
    const heading = page.getByRole('heading', { name: /admin/i });
    await expect(heading).toBeVisible();

    // Verify email input field exists
    const emailInput = page.locator('input[type="email"]');
    await expect(emailInput).toBeVisible();
    await expect(emailInput).toHaveAttribute('placeholder', /email|e-mail/i);

    // Verify password input field exists
    const passwordInput = page.locator('input[type="password"]');
    await expect(passwordInput).toBeVisible();
    await expect(passwordInput).toHaveAttribute('placeholder', /password/i);

    // Verify submit button exists
    const submitButton = page.locator('button[type="submit"]');
    await expect(submitButton).toBeVisible();
    await expect(submitButton).toContainText(/login|sign in/i);
  });

  test('should show validation error with empty credentials', async ({
    page,
  }) => {
    // Attempt to submit form with empty fields
    const submitButton = page.locator('button[type="submit"]');
    await submitButton.click({ force: true });

    // Check for validation error message or required field indication
    const emailInput = page.locator('input[type="email"]');
    const passwordInput = page.locator('input[type="password"]');

    // HTML5 validation - inputs should be invalid
    const emailValidity = await emailInput.evaluate((el) => el.validity.valid);
    const passwordValidity = await passwordInput.evaluate(
      (el) => el.validity.valid
    );

    expect(emailValidity).toBe(false);
    expect(passwordValidity).toBe(false);
  });

  test('should show error message with invalid credentials', async ({
    page,
  }) => {
    await mockLoginFailure(page);
    // Fill in invalid credentials
    const emailInput = page.locator('input[type="email"]');
    const passwordInput = page.locator('input[type="password"]');
    const submitButton = page.locator('button[type="submit"]');

    await emailInput.fill('invalid@example.com');
    await passwordInput.fill('wrongpassword123');
    await submitButton.click({ force: true });

    // Wait for error message to appear
    const errorMessage = page.locator(
      'text=/login.*failed|invalid.*credentials|email.*not.*found|wrong.*password/i'
    );
    await expect(errorMessage).toBeVisible({ timeout: 5000 });

    // Verify we're still on login page
    await expect(page).toHaveURL('/login');
  });

  test('should successfully login with valid credentials and redirect to dashboard', async ({
    page,
  }) => {
    await mockLoginSuccess(page);
    // Get admin credentials from environment or use defaults
    const adminEmail = process.env.ADMIN_EMAIL || 'admin@example.com';
    const adminPassword = process.env.ADMIN_PASSWORD || 'admin123';

    // Fill in login credentials
    const emailInput = page.locator('input[type="email"]');
    const passwordInput = page.locator('input[type="password"]');
    const submitButton = page.locator('button[type="submit"]');

    await emailInput.fill(adminEmail);
    await passwordInput.fill(adminPassword);

    await submitButton.click();

    // Should redirect to admin dashboard
    await expect(page).toHaveURL('/admin/dashboard', { timeout: 10000 });
    await page.waitForLoadState('domcontentloaded');

    // Verify dashboard content is visible
    await expect(page.locator('main')).toBeVisible({ timeout: 15000 });
  });

  test('should persist login token and allow access to protected routes', async ({
    page,
  }) => {
    await mockLoginSuccess(page);
    // Login first
    const adminEmail = process.env.ADMIN_EMAIL || 'admin@example.com';
    const adminPassword = process.env.ADMIN_PASSWORD || 'admin123';

    const emailInput = page.locator('input[type="email"]');
    const passwordInput = page.locator('input[type="password"]');
    const submitButton = page.locator('button[type="submit"]');

    await emailInput.fill(adminEmail);
    await passwordInput.fill(adminPassword);
    await submitButton.click();

    // Wait for redirect to dashboard
    await page.waitForURL('/admin/dashboard', { timeout: 5000 });

    // Verify localStorage contains auth token
    const authToken = await page.evaluate(() =>
      localStorage.getItem('authToken')
    );
    expect(authToken).toBeTruthy();

    // Navigate to another protected route
    await page.goto('/admin/skills', { waitUntil: 'domcontentloaded' });

    // Should be able to access protected route
    await expect(page).toHaveURL('/admin/skills');
    await expect(page.locator('main')).toBeVisible({ timeout: 15000 });
  });

  test('should prevent unauthorized access to protected routes', async ({
    page,
  }) => {
    // Clear any existing auth token
    await page.evaluate(() => localStorage.removeItem('authToken'));

    // Try to access protected route directly
    await page.goto('/admin/dashboard', { waitUntil: 'domcontentloaded' });

    // Should be redirected to login page
    await expect(page).toHaveURL('/login', { timeout: 10000 });
  });

  test('should logout and clear session', async ({ page }) => {
    await mockLoginSuccess(page);
    // Login first
    const adminEmail = process.env.ADMIN_EMAIL || 'admin@example.com';
    const adminPassword = process.env.ADMIN_PASSWORD || 'admin123';

    await page.goto('/login');
    const emailInput = page.locator('input[type="email"]');
    const passwordInput = page.locator('input[type="password"]');
    const submitButton = page.locator('button[type="submit"]');

    await emailInput.fill(adminEmail);
    await passwordInput.fill(adminPassword);
    await submitButton.click();

    // Wait for successful login
    await page.waitForURL('/admin/dashboard', { timeout: 5000 });

    // Find and click logout button (in header or mobile menu)
    const logoutButton = page.getByRole('button', { name: /logout/i });
    if (await logoutButton.isVisible()) {
      await logoutButton.scrollIntoViewIfNeeded();
      await logoutButton.click({ force: true });
    } else {
      const mobileMenuButton = page.locator('button.lg\\:hidden').first();
      if (await mobileMenuButton.isVisible()) {
        await mobileMenuButton.click({ force: true });
        await page.getByRole('button', { name: /logout/i }).click({ force: true });
      }
    }

    // Verify redirect to login page after logout
    await expect(page).toHaveURL('/login', { timeout: 10000 });
    const loginHeading = page.getByRole('heading', { name: /admin/i });
    await expect(loginHeading).toBeVisible({ timeout: 10000 });

    // Protected-route guard is covered in a dedicated test
  });
});
