import { test, expect } from '@playwright/test';
import { loginAsAdmin } from './utils/auth';

test.describe('Admin Dashboard', () => {
  test('loads dashboard stats and cards', async ({ page }) => {
    const emptyList = {
      status: 200,
      contentType: 'application/json',
      body: '[]',
    };

    await page.route('**/api/skills', (route) => route.fulfill(emptyList));
    await page.route('**/api/projects', (route) => route.fulfill(emptyList));
    await page.route('**/api/experience', (route) => route.fulfill(emptyList));
    await page.route('**/api/education', (route) => route.fulfill(emptyList));
    await page.route('**/api/hobbies', (route) => route.fulfill(emptyList));
    await page.route('**/api/testimonials', (route) => route.fulfill(emptyList));
    await page.route('**/api/messages', (route) => route.fulfill(emptyList));

    await loginAsAdmin(page);

    const heading = page.getByRole('heading', { name: /dashboard|admin/i });
    await expect(heading).toBeVisible();

    const statsCards = page.locator('div[class*="rounded"]');
    await expect(statsCards.first()).toBeVisible();
  });
});
